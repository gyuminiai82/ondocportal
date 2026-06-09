package kr.ondoc.scheduler;

import java.net.InetAddress;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import kr.ondoc.domain.sybase.crm.CrmSettingVO;
import kr.ondoc.mapper.sybase.common.InitMapper;
import kr.ondoc.mapper.sybase.crm.CrmInstallMapper;
import kr.ondoc.mapper.sybase.crm.CrmRevptNaverMapper;
import kr.ondoc.mapper.sybase.crm.CrmSettingMapper;

@Service
public class OnlineScheduler {
	@Value("${doctalk.emrAccessKey}")
	private String emrAccessKey;
	
	private String hosnum;
	
	private String online_reserve_yn;
	
	@Autowired
	private InitMapper mapper;
	
	@Autowired
	private CrmInstallMapper installMapper;
	
	@Autowired
	private CrmSettingMapper settingMapper;
	
	@Autowired
	private CrmRevptNaverMapper revptNaverMapper;
	
	private ThreadPoolTaskScheduler scheduler;
	private String cron = "0/10 * * * * *";

	public void startScheduler() throws Exception {
		// 먼저 설정값 가져오기
		hosnum = mapper.selectHosnum();
		
		if("0".equals(installMapper.checkTable("crm_setting"))) {
			online_reserve_yn = "N";
			return;  // 스케줄러 시작하지 않음
		} else {
			List<CrmSettingVO> list = settingMapper.selectSetting();
			if(list != null && !list.isEmpty()) {
				CrmSettingVO vo = list.get(0);
				// null 체크 후 기본값 설정
				online_reserve_yn = vo.getOnline_reserve_yn() != null 
									? vo.getOnline_reserve_yn() 
									: "N";
			} else {
				online_reserve_yn = "N";
			}
		}
		
		// 1. 현재 서버(로컬 호스트)의 InetAddress 객체를 얻어옵니다.
        InetAddress localHost = InetAddress.getLocalHost();
        
        // 2. IP 주소를 문자열로 변환합니다.
        String currentIP = localHost.getHostAddress().substring(0, localHost.getHostAddress().lastIndexOf('.'));
        
        
        //java에서도 회사면 실행 안되게 막음
        if(currentIP.equals("210.220.255")) online_reserve_yn = "N";
		
		// 설정값 확인 후 스케줄러 시작
		if("Y".equals(online_reserve_yn)) {
			scheduler = new ThreadPoolTaskScheduler();
			scheduler.initialize();
			scheduler.schedule(getRunnable(), getTrigger());
		}
	}
	
	public void changeCronSet(String cron) {
		this.cron = cron;
	}
	
	public void stopScheduler() {
		if(scheduler != null) {
			scheduler.shutdown();
		}
	}
	
	private Runnable getRunnable() {
		return () -> {
			if("Y".equals(online_reserve_yn)) {
				OnlineScheduleRestClient client = new OnlineScheduleRestClient(revptNaverMapper);
				client.pollingDocTalk(emrAccessKey, hosnum);
			} else if("N".equals(online_reserve_yn)) {
				stopScheduler();
			}
		};
	}
	
	private Trigger getTrigger() {
		return new CronTrigger(cron);
	}
	
	@PostConstruct
	public void init() throws Exception {
		startScheduler();
	}
	
	@PreDestroy
	public void destroy() {
		stopScheduler();
	}
}
