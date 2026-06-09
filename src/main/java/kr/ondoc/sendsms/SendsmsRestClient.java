package kr.ondoc.sendsms;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.ondoc.domain.sybase.crm.CrmOperationScheduleVO;
import kr.ondoc.domain.sybase.crm.CrmSendSmsVO;
import kr.ondoc.domain.sybase.crm.CrmSettingVO;
import kr.ondoc.domain.sybase.crm.CrmSmsLogVO;
import kr.ondoc.mapper.sybase.crm.CrmSendSmsBoilerplateLogMapper;
import kr.ondoc.mapper.sybase.crm.CrmSendSmsLogMapper;
import kr.ondoc.mapper.sybase.crm.CrmSendSmsMapper;
import kr.ondoc.mapper.sybase.crm.CrmSettingMapper;

@Service
public class SendsmsRestClient {
	
	@Autowired
	private CrmSettingMapper settingMapper;
	
	@Autowired
	private CrmSendSmsMapper sendSmsMapper;
	
	@Autowired
	private CrmSendSmsLogMapper sendSmsLogMapper;
	
	String smsDomain = "http://ds.messagelab.co.kr:8000";
	
	public void deleteMunja(String reserved_ids) throws Exception {
		CrmSettingVO settingVO = settingMapper.setting();
		
		//
		SmsDeleteVO smsDeleteVO = new SmsDeleteVO();
		smsDeleteVO.setMedicareNum(settingVO.getSms_member_id());
		//
		
		StringBuilder jsonBody = new StringBuilder();
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			jsonBody.append(mapper.writeValueAsString(smsDeleteVO));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		String[] arrId = reserved_ids.split(",");

		Arrays.stream(arrId).forEach(id -> {
			deleteSms(id, jsonBody.toString());
		});
	}
	
	public String sendMunJa(CrmOperationScheduleVO vo, String action) throws Exception {
		CrmSmsLogVO smsLogVO = new CrmSmsLogVO();
		smsLogVO.setStatus(action);//로그
		StringBuilder arrRandomNumber = new StringBuilder();
		//세팅정보 가져오기(회원아이디-요양기관기호, 발송번호)
		CrmSettingVO settingVO = settingMapper.setting();
		
		//문자 사용 안하면 빈값 리턴
		if(settingVO.getSms_use_yn().equals("N")) return "";
		
		//START 예약시 메시지 템플릿 가져오기 
		CrmSendSmsVO crmSendSmsVO = new CrmSendSmsVO();
		crmSendSmsVO.setUse_yn("Y");//사용중인 건
		crmSendSmsVO.setDel_yn("N");//삭제가안된 건
		crmSendSmsVO.setAction(action);
		//crmSendSmsVO.setAction("cancel");
		
		List<CrmSendSmsVO> smsList = sendSmsMapper.selectListSendSms(crmSendSmsVO);
		//END 예약시 메시지 템플릿 가져오기 
		
		///////////////////////////////////////////////////////////////////////////////
		//START 받는 사람 list
		SmsMessageVO smsMessageVO = new SmsMessageVO();
		smsMessageVO.setSenderName(vo.getName());
		smsMessageVO.setTo(vo.getHpno());
		
		List<SmsMessageVO> listMessage = new ArrayList<>();
		listMessage.add(smsMessageVO);
		//listMessage에 받는 사람 배열로 존재
		//END 받는 사람 list
		///////////////////////////////////////////////////////////////////////////////
		
		smsLogVO.setReceive_name(vo.getName());	//로그
		smsLogVO.setReceive_phone(vo.getHpno());	//로그
		
		SmsVO sms = new SmsVO();
		sms.setMessages(listMessage);	//받는사람list
		sms.setMedicareNum(settingVO.getSms_member_id());//setting에서 가져옴
		sms.setFrom(settingVO.getSms_send_number());//setting에서 가져옴
		
		smsList.forEach(sendSmsVO -> {
			String randomNumber = getRandomNumber();
			arrRandomNumber.append(randomNumber + ",");
			
			String reserved_time = "";
			
			String sms_message = sendSmsVO.getSms_message();
			String send_time = sendSmsVO.getSend_time();
			String sent_ago_day = sendSmsVO.getSent_ago_day();
			
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
			
			try {
				sms_message = changeSmsMessage(sms_message, vo);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			sms.setText(sms_message);
			sms.setReserved_time(reserved_time);
			sms.setReserved_id(randomNumber);
			
			smsLogVO.setSms_message(sms_message);//로그
			smsLogVO.setSend_datetime(reserved_time);	//로그
			smsLogVO.setReserved_id(randomNumber);	//로그
			
			String jsonBody = "";
			String response = "";
			
			try {
				ObjectMapper mapper = new ObjectMapper();
				jsonBody = mapper.writeValueAsString(sms);
				
				if(sms_message.getBytes("EUC-KR").length <= 90) {
					//SMS 문자발송
					response = sendSms(jsonBody);
				} else {
					//LMS 문자발송
					response = sendLms(jsonBody);
				}
				
				sendSmsLogMapper.insertSmsLog(smsLogVO);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("////////////////////////////////////////////////////////////////////");
				System.out.println("문자발송 오류 : " + e.getMessage());
				System.out.println("////////////////////////////////////////////////////////////////////");
			}
		});
		
		return arrRandomNumber.toString();
	}
	
	public String sendSms(String jsonBody) {
		RestTemplate restTemplate = new RestTemplate();

		String url = "/api/v2/surem/sms";
		
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(
        		smsDomain + url,
                HttpMethod.POST,
                requestEntity,
                String.class
            );
        
        return response.getBody();
	}
	
	public String sendLms(String jsonBody) {
		RestTemplate restTemplate = new RestTemplate();

		String url = "/api/v2/surem/lms";
		
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(
        		smsDomain + url,
                HttpMethod.POST,
                requestEntity,
                String.class
            );
        
        return response.getBody();
	}
	
	public void deleteSms(String reserved_id, String jsonBody) {
		RestTemplate restTemplate = new RestTemplate();

		String url = "/api/v2/surem/"+reserved_id;
		
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(
        		smsDomain + url,
                HttpMethod.DELETE,
                requestEntity,
                String.class
            );
	}
	
	public String getRandomNumber() {
		StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 12; i++) {
            sb.append(random.nextInt(10)); // 0~9 사이의 숫자
        }
        
        return sb.toString();
	}

	public String changeSmsMessage(String message, CrmOperationScheduleVO operationScheduleVO) throws Exception {
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
			
			message = message.replaceAll(Pattern.quote("{MM월DD 오전HH시MM분}"), datetime.format(dateTimeFormatter))
					.replaceAll(Pattern.quote("{MM월DD일}"), datetime.format(dateFormatter))
					.replaceAll(Pattern.quote("{HH시MM분~HH시MM분}"), datetime.format(timeFormatter)+"~"+toDatetime.format(timeFormatter))
					.replaceAll(Pattern.quote("{환자명}"), operationScheduleVO.getName())
					.replaceAll(Pattern.quote("{병원명}"), settingMapper.selectHosName())
					.replaceAll("\r\n", "\n");
		}
		
		
		
		return message;
	}
}
