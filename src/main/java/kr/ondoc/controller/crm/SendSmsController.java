package kr.ondoc.controller.crm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ondoc.domain.sybase.common.CommonVO;
import kr.ondoc.domain.sybase.crm.CrmSendSmsVO;
import kr.ondoc.mapper.sybase.crm.CrmSendSmsMapper;

@CrossOrigin(origins = "*")
@RestController
public class SendSmsController {
	@Autowired
	private CrmSendSmsMapper sendSmsMapper;
	
	@RequestMapping(value="/crmSendSmsList", method=RequestMethod.GET)
    public List<CrmSendSmsVO> selectListSendSms(CrmSendSmsVO vo) throws Exception {
		return sendSmsMapper.selectListSendSms(vo);
    }
	
	@RequestMapping(value="/crmSendSms", method=RequestMethod.GET)
    public CrmSendSmsVO selectSendSms(CrmSendSmsVO vo) throws Exception {
		return sendSmsMapper.selectSendSms(vo);
    }
	
	@RequestMapping(value="/crmSendSms", method=RequestMethod.POST)
	public CommonVO insertSendSms(CrmSendSmsVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			sendSmsMapper.insertSendSms(vo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmSendSms", method=RequestMethod.PUT)
	public CommonVO updateSendSms(CrmSendSmsVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			sendSmsMapper.updateSendSms(vo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmSendSms", method=RequestMethod.DELETE)
    public CommonVO deleteSendSms(CrmSendSmsVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			sendSmsMapper.deleteSendSms(vo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			return commonVO;
		}
		
		return commonVO;
    }
}
