package kr.ondoc.controller.crm;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.ondoc.domain.sybase.common.CommonVO;
import kr.ondoc.domain.sybase.crm.CrmArrDepartmentVO;
import kr.ondoc.domain.sybase.crm.CrmArrSmsVO;
import kr.ondoc.domain.sybase.crm.CrmDepartmentVO;
import kr.ondoc.domain.sybase.crm.CrmPatientParamVO;
import kr.ondoc.domain.sybase.crm.CrmPatientVO;
import kr.ondoc.domain.sybase.crm.CrmSendSmsBoilerplateExecuteVO;
import kr.ondoc.domain.sybase.crm.CrmSendSmsBoilerplateVO;
import kr.ondoc.domain.sybase.crm.CrmSettingVO;
import kr.ondoc.domain.sybase.crm.CrmSmsLogVO;
import kr.ondoc.domain.sybase.crm.CrmSmsPatientParamVO;
import kr.ondoc.mapper.sybase.crm.CrmSendSmsBoilerplateLogMapper;
import kr.ondoc.mapper.sybase.crm.CrmSendSmsBoilerplateMapper;
import kr.ondoc.mapper.sybase.crm.CrmSettingMapper;
import kr.ondoc.sendsms.SendsmsRestClient;
import kr.ondoc.sendsms.SmsBoilerplateVO;
import kr.ondoc.sendsms.SmsMessageVO;
import kr.ondoc.sendsms.SmsVO;

@CrossOrigin(origins = "*")
@RestController
public class SendSmsBoilerplateController {
	
	@Autowired
	private CrmSettingMapper settingMapper;
	
	@Autowired
	SendsmsRestClient sendsmsRestClient;
	
	@Autowired
	private CrmSendSmsBoilerplateMapper sendSmsBoilerplateMapper;
	
	@Autowired
	private CrmSendSmsBoilerplateLogMapper sendSmsBoilerplateLogMapper;
	
	@RequestMapping(value="/crmSendSmsBoilerplateList", method=RequestMethod.GET)
    public List<CrmSendSmsBoilerplateVO> selectListSendSmsBoilerplate(CrmSendSmsBoilerplateVO vo) throws Exception {
		return sendSmsBoilerplateMapper.selectListSendSmsBoilerplate(vo);
    }
	
	@RequestMapping(value="/crmSendSmsBoilerplate", method=RequestMethod.GET)
    public CrmSendSmsBoilerplateVO selectSendSmsBoilerplate(CrmSendSmsBoilerplateVO vo) throws Exception {
		return sendSmsBoilerplateMapper.selectSendSmsBoilerplate(vo);
    }
	
	@RequestMapping(value="/crmSendSmsBoilerplate", method=RequestMethod.POST)
	public CommonVO insertSendSms(CrmSendSmsBoilerplateVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			sendSmsBoilerplateMapper.insertSendSmsBoilerplate(vo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmSendSmsBoilerplate", method=RequestMethod.PUT)
	public CommonVO updateSendSmsBoilerplate(CrmSendSmsBoilerplateVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			sendSmsBoilerplateMapper.updateSendSmsBoilerplate(vo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmSendSmsBoilerplate", method=RequestMethod.DELETE)
    public CommonVO deleteSendSmsBoilerplate(CrmSendSmsBoilerplateVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			sendSmsBoilerplateMapper.deleteSendSmsBoilerplate(vo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmSendSmsBoilerplateSort", method=RequestMethod.PUT)
	public CommonVO updateSendSmsBoilerplateSort(CrmArrSmsVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		
		JSONArray arr = new JSONArray(vo.getArrSms());
		
		for (int i = 0; i < arr.length(); i++) {
			String str = arr.get(i).toString() ;
			
			JSONObject obj = new JSONObject(str);
			
			CrmSendSmsBoilerplateVO boilerplate = new CrmSendSmsBoilerplateVO();
			boilerplate.setSms_boilerplate_idx(obj.getString("sms_boilerplate_idx"));
			boilerplate.setSort(Integer.toString(obj.getInt("sort")));
			
			try {
				sendSmsBoilerplateMapper.updateSendSmsBoilerplate(boilerplate);  
			} catch (Exception e) {
				commonVO.setStatus("500");
				commonVO.setMessage("failure");
				return commonVO;
			}
		}
		
		try {
			
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmSmsPatientList", method=RequestMethod.GET)
    public List<CrmPatientVO> selectListSmsPatientList(CrmSmsPatientParamVO vo) throws Exception {
		return sendSmsBoilerplateMapper.selectListSmsPatientList(vo);
    }
	
	@RequestMapping(value="/crmSendSmsBoilerplateExecute", method=RequestMethod.POST)
	public CommonVO sendSmsBoilerplateExecute(CrmSendSmsBoilerplateExecuteVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		CrmSmsLogVO smsLogVO = new CrmSmsLogVO();//로그
		smsLogVO.setStatus("register");//로그
		
		CrmSettingVO settingVO = settingMapper.setting();  
		
		ObjectMapper mapper = new ObjectMapper();
		SmsBoilerplateVO smsBoilerplate = mapper.readValue(vo.getData(), SmsBoilerplateVO.class);
		
		String random6Number = getRandomNumber();
		AtomicInteger index = new AtomicInteger();
		
		SmsVO sms = new SmsVO();	//받는사람list
		sms.setMedicareNum(settingVO.getSms_member_id());//setting에서 가져옴
		sms.setFrom(settingVO.getSms_send_number());//setting에서 가져옴
		sms.setReserved_time(smsBoilerplate.getDatetime());
		
		smsLogVO.setSend_datetime(smsBoilerplate.getDatetime());
		
		smsBoilerplate.getToPatient().forEach(patient -> {
			//START 받는 사람 list
			SmsMessageVO smsMessageVO = new SmsMessageVO();
			smsMessageVO.setSenderName(patient.getToName());
			smsMessageVO.setTo(patient.getToHpno());
			
			List<SmsMessageVO> listMessage = new ArrayList<>();
			listMessage.add(smsMessageVO);
			//listMessage에 받는 사람 배열로 존재
			//END 받는 사람 list

			sms.setMessages(listMessage);
			
			smsLogVO.setReceive_name(patient.getToName());	//로그
			smsLogVO.setReceive_phone(patient.getToHpno());	//로그
			
			try {
				String sms_message = changeSmsMessage(smsBoilerplate.getMessage(), settingMapper.selectHosName(), patient.getToName());
				
				smsLogVO.setSms_message(sms_message);//로그
				sms.setText(sms_message);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String reserved_id = random6Number + String.format("%06d", index.getAndIncrement());
			sms.setReserved_id(reserved_id);
			smsLogVO.setReserved_id(reserved_id);	//로그
			
			String jsonBody = "";
			String response = "";
			
			ObjectMapper smsMapper = new ObjectMapper();
			try {
				jsonBody = smsMapper.writeValueAsString(sms);
				
				if(sms.getText().getBytes("EUC-KR").length <= 90) {
					//SMS 문자발송
					response = sendsmsRestClient.sendSms(jsonBody);
				} else {
					//LMS 문자발송
					response = sendsmsRestClient.sendLms(jsonBody);
				}
				
				smsLogVO.setWrite_id(vo.getWirte_id());
				sendSmsBoilerplateLogMapper.insertSmsLog(smsLogVO);//로그
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		try {
			
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			
			return commonVO;
		}
		
		return commonVO;
    }
	
	public String getRandomNumber() {
		Random random = new Random();
        int number = 100000 + random.nextInt(900000); 
        
        return Integer.toString(number);
	}
	
	public String changeSmsMessage(String message, String hospitalName, String patientName) throws Exception {
		message = message.replaceAll(Pattern.quote("{환자명}"), patientName)
				.replaceAll(Pattern.quote("{병원명}"), hospitalName)
				.replaceAll("\r\n", "\n");
		
		return message;
	}
}
