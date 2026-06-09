package kr.ondoc.sendsms;

import java.io.UnsupportedEncodingException;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.ondoc.domain.sybase.crm.CrmOperationScheduleLimitVO;
import kr.ondoc.domain.sybase.crm.CrmOperationScheduleVO;
import kr.ondoc.domain.sybase.crm.CrmSendSmsVO;
import kr.ondoc.domain.sybase.crm.CrmSettingVO;
import kr.ondoc.mapper.sybase.crm.CrmSendSmsMapper;
import kr.ondoc.mapper.sybase.crm.CrmSettingMapper;

@CrossOrigin(origins = "*")
@RestController
public class SendsmsController {

	@Autowired
	SendsmsRestClient sendsmsRestClient;
	
	@Autowired
	private CrmSettingMapper settingMapper;
	
	@Autowired
	private CrmSendSmsMapper sendSmsMapper;
	
	@RequestMapping(value="/sendsmsSendSms", method=RequestMethod.GET)
    public void sendsmsSendSms() throws Exception {
		//파라미터로 넘어온 값
		CrmOperationScheduleVO vo = new CrmOperationScheduleVO();
		vo.setSchedule_date("20250730");
		vo.setSchedule_time("1400");
		vo.setTreatement_time("30");
		vo.setName("김규민");
		vo.setHpno("01031577158");
		
		String smsRandomNumber = sendsmsRestClient.sendMunJa(vo, "register");
    }
	
	@RequestMapping(value="/deleteSms", method=RequestMethod.GET)
    public void deleteSms(String ids) throws Exception {
		
		sendsmsRestClient.deleteMunja(ids);
    }
	
	@RequestMapping(value="/sendsmsSendLms", method=RequestMethod.GET)
    public void sendsmsSendLms() throws Exception {
		
		CrmSettingVO settingVO = settingMapper.setting();
		
		//
		SmsMessageVO message = new SmsMessageVO();
		message.setSenderName("김규민");
		message.setTo("01031577158");
		
		List<SmsMessageVO> listMessage = new ArrayList<>();
		listMessage.add(message);
		//
		
		SmsVO sms = new SmsVO();
		sms.setMessages(listMessage);
		sms.setMedicareNum(settingVO.getSms_member_id());//추후 디비
		sms.setFrom(settingVO.getSms_send_number());//추후 디비
		sms.setText("");
		sms.setReserved_time("2025-07-30T12:00:00");
		sms.setReserved_id(getRandomNumber());
		
		String jsonBody = "";
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			jsonBody = mapper.writeValueAsString(sms);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		String response = sendsmsRestClient.sendLms(jsonBody);
    }
	
	public String getRandomNumber() {
		StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 12; i++) {
            sb.append(random.nextInt(10)); // 0~9 사이의 숫자
        }
        
        return sb.toString();
	}

	public String changeSmsMessage(String message, CrmOperationScheduleVO operationScheduleVO) {
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
					.replaceAll("\r\n", "\n");
		}
		
		return message;
	}
}
