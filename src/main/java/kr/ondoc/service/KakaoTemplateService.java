package kr.ondoc.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.ondoc.domain.sybase.crm.CrmOperationScheduleVO;
import kr.ondoc.domain.sybase.crm.CrmSendKakaoLogVO;
import kr.ondoc.domain.sybase.crm.CrmSendKakaoVO;
import kr.ondoc.domain.sybase.crm.CrmSendSmsVO;
import kr.ondoc.domain.sybase.crm.CrmSettingVO;
import kr.ondoc.domain.sybase.crm.CrmSmsLogVO;
import kr.ondoc.mapper.sybase.crm.CrmSendKakaoLogMapper;
import kr.ondoc.mapper.sybase.crm.CrmSendKakaoMapper;
import kr.ondoc.mapper.sybase.crm.CrmSendSmsLogMapper;
import kr.ondoc.mapper.sybase.crm.CrmSettingMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class KakaoTemplateService {

    @Autowired 
    private RestTemplate externalApiTemplate;
    
    @Autowired
	private CrmSettingMapper settingMapper;

    @Value("${api.base-url}") 
    private String baseUrl;
    
    /**
     * [중요] ObjectMapper 객체가 클래스 필드로 선언되어 있어야 합니다.
     * 이 부분이 누락되면 아래 메서드에서 objectMapper를 참조할 때 에러가 발생합니다.
     */
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Autowired
	private CrmSendKakaoMapper sendKakaoMapper;
    
    @Autowired
	private CrmSendKakaoLogMapper sendKakaoLogMapper;

    /**
     * 카카오 알림톡을 전송합니다.
     * @throws Exception 
     */
    public String sendKakaoAlimtalk(CrmOperationScheduleVO vo, String action) throws Exception {
    	CrmSendKakaoLogVO crmSendKakaoLogVO = new CrmSendKakaoLogVO();
    	crmSendKakaoLogVO.setStatus(action);
    	StringBuilder arrGroupId = new StringBuilder();
    	CrmSettingVO settingVO = settingMapper.setting();
    	String hospitalCode = settingVO.getSms_kakao_hospitalCode();
    	
    	//카카오메시지 사용 안하면 빈값 리턴
    	if(settingVO.getSms_kakao_use_yn().equals("N")) return "";
    	
    	//START 예약시 메시지 템플릿 가져오기 
    	CrmSendKakaoVO crmSendKakaoVO = new CrmSendKakaoVO();
    	crmSendKakaoVO.setUse_yn("Y");//사용중인 건
    	crmSendKakaoVO.setDel_yn("N");//삭제가안된 건
    	crmSendKakaoVO.setAction(action);
		//crmSendSmsVO.setAction("cancel");
		
		List<CrmSendKakaoVO> kakaoList = sendKakaoMapper.selectListSendKakao(crmSendKakaoVO);
		//END 예약시 메시지 템플릿 가져오기
		
        String url = baseUrl + "/messages/kakao";
        
		///////////////////////////////////////////////////////////////////////////////
		//START 받는 사람 list
        List<Map<String, Object>> receiversList = new ArrayList<>();

        // 첫 번째 수신자 데이터 생성
        Map<String, Object> user1 = new HashMap<>();
        user1.put("phone", "82"+vo.getHpno().substring(1)); // 수신 번호
        
        Map<String, String> vars1 = new HashMap<>();
        vars1.put("이름", vo.getName());
        vars1.put("예약일시", changeMessage("예약일시", vo));
        vars1.put("예약요일", changeMessage("예약요일", vo));
        vars1.put("예약시", changeMessage("예약시", vo));
        vars1.put("예약일", "");
        vars1.put("초진일", "");
        vars1.put("추천인", "");
        vars1.put("피추천인", "");
        vars1.put("초진일", "");
        
        user1.put("variables", vars1);
        
        receiversList.add(user1);
        
        List<KakaoMessageRequest.Receiver> receivers = new ArrayList<>();
        
        for (Map<String, Object> target : receiversList) {
            KakaoMessageRequest.Receiver receiver = new KakaoMessageRequest.Receiver();
            receiver.setPhone((String) target.get("phone"));
            receiver.setVariables((Map<String, String>) target.get("variables"));
            receivers.add(receiver);
        }
        //END 받는 사람 list
		///////////////////////////////////////////////////////////////////////////////
        crmSendKakaoLogVO.setReceive_name(vo.getName());	//로그
        crmSendKakaoLogVO.setReceive_phone("82"+vo.getHpno().substring(1));	//로그
        
        // 2. 메인 요청 객체 생성
        KakaoMessageRequest body = new KakaoMessageRequest();
        
        kakaoList.forEach(crmSendKakao -> {
        	String groupId = System.currentTimeMillis()+"";
			arrGroupId.append(groupId + ",");
        	String templateCode = crmSendKakao.getTemplate_code();
        	
        	body.setGroupId("GROUP_" + groupId);
            body.setCaller(settingVO.getSms_kakao_send_number()); // 등록된 발신번호
            body.setTemplateCode(templateCode);
            body.setReceivers(receivers); // 리스트 주입

            body.setUseFallback(true);
            body.setFallbackMessage("[알림] 알림톡 전송 실패로 문자로 대체 발송되었습니다.");

            HttpHeaders headers = new HttpHeaders();
            headers.set("x-hospital-code", hospitalCode);
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            String jsonBody;
			try {
				jsonBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(body);
	            System.out.println("전송되는 Body 데이터:\n" + jsonBody);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            String rtnVal = externalApiTemplate.postForObject(url, new HttpEntity<>(body, headers), String.class);
            
            System.out.println(rtnVal);
            
            String reserved_time = "";
			
			String send_time = crmSendKakao.getSend_time();
			String sent_ago_day = crmSendKakao.getSent_ago_day();
			
			//0000이면 문자 즉시발송이라 불필요
			if(!send_time.equals("0000")) {
				if(vo.getSchedule_date().length() == 8 & vo.getSchedule_time().length() == 4) {
					int year = Integer.valueOf(vo.getSchedule_date().substring(0, 4));
					int month = Integer.valueOf(vo.getSchedule_date().substring(4, 6));
					int day = Integer.valueOf(vo.getSchedule_date().substring(6, 8));
					
					LocalDate date = LocalDate.of(year, month, day);
					LocalDate dayBefore = date.minusDays(Integer.valueOf(sent_ago_day)); 
					
					reserved_time = dayBefore + "T" + send_time.substring(0, 2) + ":" + send_time.substring(2, 4) + ":00";
				}
			}
			
            crmSendKakaoLogVO.setMessage(templateCode);//로그
            crmSendKakaoLogVO.setSend_datetime(reserved_time);	//로그
            crmSendKakaoLogVO.setGroup_id(groupId);	//로그
            
            try {
				sendKakaoLogMapper.insertSendKakaoLog(crmSendKakaoLogVO);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

        return arrGroupId.toString();
    }

    /**
     * 카카오 알림톡 템플릿 목록을 조회합니다.
     */
    public String getKakaoTemplates(String hospitalCode, int page) {
        String url = baseUrl + "/templates/kakao?page=" + page;
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-hospital-code", hospitalCode);
        
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        return externalApiTemplate.exchange(
                url, 
                HttpMethod.GET, 
                entity, 
                String.class
        ).getBody();
    }
    
    /**
     * 특정 카카오 알림톡 템플릿 상세 정보를 조회합니다.
     * GET /templates/kakao/{templateCode}
     */
    public String getKakaoTemplateDetail(String hospitalCode, String templateCode) {
        // API 명세에 따라 경로에 templateCode를 포함
        String url = baseUrl + "/templates/kakao/" + templateCode;
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-hospital-code", hospitalCode);
        
        // GET 요청을 위한 엔티티 구성
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        // 상세 조회 API 호출 및 바디 반환
        return externalApiTemplate.exchange(
                url, 
                HttpMethod.GET, 
                entity, 
                String.class
        ).getBody();
    }
    
    public String changeMessage(String type, CrmOperationScheduleVO operationScheduleVO) throws Exception {
		if(operationScheduleVO.getSchedule_date().length() == 8 & operationScheduleVO.getSchedule_time().length() == 4) {
			int year = Integer.valueOf(operationScheduleVO.getSchedule_date().substring(0, 4));
			int month = Integer.valueOf(operationScheduleVO.getSchedule_date().substring(4, 6));
			int day = Integer.valueOf(operationScheduleVO.getSchedule_date().substring(6, 8));
			
			int hour = Integer.valueOf(operationScheduleVO.getSchedule_time().substring(0, 2));
			int minute = Integer.valueOf(operationScheduleVO.getSchedule_time().substring(2, 4));
			
			LocalDateTime datetime = LocalDateTime.of(year, month, day, hour, minute);
			LocalDateTime toDatetime = datetime.plusMinutes(Integer.valueOf(operationScheduleVO.getTreatement_time()));
			
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM월dd일 ahh시mm분", Locale.KOREAN);
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM월dd일");
			DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH시mm분");
			
			if(type.equals("예약일시")) {
				return datetime.format(dateTimeFormatter);
			} else if(type.equals("예약요일")) {
				return datetime.format(dateFormatter);
			} else if(type.equals("예약시")) {
				return datetime.format(timeFormatter);
			}
		}
		
		
		return "";
	}
}