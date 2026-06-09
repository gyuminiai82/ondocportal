package kr.ondoc.scheduler;

import java.net.InetAddress;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import kr.ondoc.domain.sybase.crm.CrmRevptNaverVO;
import kr.ondoc.mapper.sybase.common.InitMapper;
import kr.ondoc.mapper.sybase.crm.CrmRevptNaverMapper;

@CrossOrigin(origins = "*")
@RestController
public class OnlineSchedulerController {
	@Value("${doctalk.emrAccessKey}")
	private String emrAccessKey;
	
	@Autowired
	private OnlineScheduler onlineScheduler;
	
	@Autowired
	private CrmRevptNaverMapper revptNaverMapper;
	
	@Autowired
	private InitMapper mapper;
	
	private String hosnum;
	
	@RequestMapping(value="/onlineReserveSchedulerStart", method=RequestMethod.GET)
    public void onlineReserveSchedulerStart(@RequestParam(name = "cron") String cron) throws Exception {
		// 1. 현재 서버(로컬 호스트)의 InetAddress 객체를 얻어옵니다.
        InetAddress localHost = InetAddress.getLocalHost();
        
        // 2. IP 주소를 문자열로 변환합니다.
        String currentIP = localHost.getHostAddress().substring(0, localHost.getHostAddress().lastIndexOf('.'));
        
        
        //java에서도 회사면 실행 안되게 막음
        if(!(currentIP.equals("210.220.255"))) {
        	onlineScheduler.stopScheduler();
    		onlineScheduler.changeCronSet("0/"+cron+" * * * * *");
    		onlineScheduler.startScheduler();
        }
        
		
    }
	
	@RequestMapping(value="/onlineReserveSchedulerStop", method=RequestMethod.GET)
    public void onlineReserveSchedulerStop() throws Exception {
		onlineScheduler.stopScheduler();
    }
	
	//예약데이터 가져오는 로직 테스트중
	@RequestMapping(value="/getDoctalk", method=RequestMethod.GET)
	public void getDoctalkReserve() throws Exception {
//		if(hosnum == null) {
//			hosnum = mapper.selectHosnum();
//		}
//		
//		OnlineScheduleRestClient client = new OnlineScheduleRestClient(revptNaverMapper);
//		
//		client.pollingDocTalk(emrAccessKey, hosnum);
		
		
		
//		List<CrmRevptNaverVO> list = revptNaverMapper.selectCheckRevptNaver();
//		
//		list.forEach(vo -> {
//			System.out.println(vo.getRev_bookingkey());
//		});
		
		
//		RestTemplate restTemplate = new RestTemplate();
//		String url = "https://emr-p.doctalk.co.kr/apis/emr/v1/polling?accessKey="+emrAccessKey+"&hospitalId="+hosnum;
//		
//		HttpHeaders headers = new HttpHeaders();
//
//        MultiValueMap<String, String> bodyForm = new LinkedMultiValueMap<>();
//		
//        HttpEntity httpEntity = new HttpEntity<>(bodyForm, headers);
//		ResponseEntity<Map> response = restTemplate.postForEntity(url, httpEntity, Map.class);
	}
}
