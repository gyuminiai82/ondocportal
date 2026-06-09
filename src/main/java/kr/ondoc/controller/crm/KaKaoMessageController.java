package kr.ondoc.controller.crm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ondoc.domain.sybase.crm.CrmSettingVO;
import kr.ondoc.mapper.sybase.crm.CrmSettingMapper;
import kr.ondoc.service.KakaoTemplateService;

@CrossOrigin(origins = "*")
@RestController
public class KaKaoMessageController {
	
	@Autowired
    private KakaoTemplateService kakaoTemplateService;
	
	@Autowired
	private CrmSettingMapper settingMapper;
	
	@RequestMapping(value = "/crmKakaoMessageSend", 
            method = RequestMethod.GET, 
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void sendKakaoMessage() throws Exception {
		
		// 2. 수신자 리스트 구성
        List<Map<String, Object>> receiversList = new ArrayList<>();

        // 첫 번째 수신자 데이터 생성
        Map<String, Object> user1 = new HashMap<>();
        user1.put("phone", "821031577158"); // 수신 번호
        
        Map<String, String> vars1 = new HashMap<>();
        vars1.put("이름", "홍길동");
        vars1.put("예약일시", "2025-01-20 08:31");
        user1.put("variables", vars1);
        
        receiversList.add(user1);

        // 두 번째 수신자 데이터 생성
//        Map<String, Object> user2 = new HashMap<>();
//        user2.put("phone", "821087654321");
//        
//        Map<String, String> vars2 = new HashMap<>();
//        vars2.put("name", "김철수");
//        vars2.put("date", "2025-01-21");
//        user2.put("variables", vars2);
//        
//        receiversList.add(user2);
		
		// 서비스 호출
        //return kakaoTemplateService.sendKakaoAlimtalk(receiversList, "RCMLAB0000_01196");
    }
	
	@RequestMapping(value = "/crmKakaoMessageTemplate", 
            method = RequestMethod.GET, 
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String selectTemplate() throws Exception {
		CrmSettingVO vo = settingMapper.setting();
		
		// 서비스 호출
        return kakaoTemplateService.getKakaoTemplates(vo.getSms_kakao_hospitalCode(), 1);
    }
	
	/**
     * 2. 특정 카카오 알림톡 템플릿 상세 조회
     * GET /api/kakao/templates/{templateCode}
     */
    @RequestMapping(value = "/crmKakaoMessageTemplateDetail", 
                   method = RequestMethod.GET, 
                   produces = MediaType.APPLICATION_JSON_VALUE)
    public String getTemplateDetail(String templateCode) throws Exception {
        
    	CrmSettingVO vo = settingMapper.setting();

        // 서비스의 상세 조회 메서드 호출
        return kakaoTemplateService.getKakaoTemplateDetail(vo.getSms_kakao_hospitalCode(), templateCode);
    }
}
