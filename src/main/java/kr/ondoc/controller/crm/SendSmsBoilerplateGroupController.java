package kr.ondoc.controller.crm;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ondoc.domain.sybase.common.CommonVO;
import kr.ondoc.domain.sybase.crm.CrmArrSmsVO;
import kr.ondoc.domain.sybase.crm.CrmSendSmsBoilerplateGroupVO;
import kr.ondoc.domain.sybase.crm.CrmSendSmsBoilerplateVO;
import kr.ondoc.mapper.sybase.crm.CrmSendSmsBoilerplateGroupMapper;

@CrossOrigin(origins = "*")
@RestController
public class SendSmsBoilerplateGroupController {
	
	@Autowired
	private CrmSendSmsBoilerplateGroupMapper sendSmsBoilerplateGroupMapper;
	
	@RequestMapping(value="/crmSendSmsBoilerplateGroupList", method=RequestMethod.GET)
    public List<CrmSendSmsBoilerplateGroupVO> selectListSendSmsBoilerplateGroup(CrmSendSmsBoilerplateGroupVO vo) throws Exception {
		return sendSmsBoilerplateGroupMapper.selectListSendSmsBoilerplateGroup(vo);
    }
	
	@RequestMapping(value="/crmSendSmsBoilerplateGroup", method=RequestMethod.POST)
	public CommonVO insertSendSmsGroup(CrmSendSmsBoilerplateGroupVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			sendSmsBoilerplateGroupMapper.insertSendSmsBoilerplateGroup(vo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmSendSmsBoilerplateGroup", method=RequestMethod.PUT)
	public CommonVO updateSendSmsBoilerplateGroup(CrmSendSmsBoilerplateGroupVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			sendSmsBoilerplateGroupMapper.updateSendSmsBoilerplateGroup(vo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			
			return commonVO;
		}
		 
		return commonVO;
    }
	
	@RequestMapping(value="/crmSendSmsBoilerplateGroup", method=RequestMethod.DELETE)
    public CommonVO deleteSendSmsBoilerplateGroup(CrmSendSmsBoilerplateGroupVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			sendSmsBoilerplateGroupMapper.deleteSendSmsBoilerplateGroup(vo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmSendSmsBoilerplateGroupSort", method=RequestMethod.PUT)
	public CommonVO updateSendSmsBoilerplateGroupSort(CrmArrSmsVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		
		JSONArray arr = new JSONArray(vo.getArrSms());
		
		for (int i = 0; i < arr.length(); i++) {
			String str = arr.get(i).toString() ;
			
			JSONObject obj = new JSONObject(str);
			
			CrmSendSmsBoilerplateGroupVO boilerplate = new CrmSendSmsBoilerplateGroupVO();
			boilerplate.setSms_boilerplate_group_idx(obj.getString("sms_boilerplate_group_idx"));
			boilerplate.setSort(Integer.toString(obj.getInt("sort")));
			
			try {
				sendSmsBoilerplateGroupMapper.updateSendSmsBoilerplateGroup(boilerplate);  
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
}
