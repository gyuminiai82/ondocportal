package kr.ondoc.controller.crm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ondoc.domain.sybase.common.CommonVO;
import kr.ondoc.domain.sybase.crm.CrmSendKakaoVO;
import kr.ondoc.mapper.sybase.crm.CrmSendKakaoMapper;

@CrossOrigin(origins = "*")
@RestController
public class SendKakaoController {
	@Autowired
	private CrmSendKakaoMapper sendKakaoMapper;
	
	@RequestMapping(value="/crmSendKakaoList", method=RequestMethod.GET)
    public List<CrmSendKakaoVO> selectListSendKakao(CrmSendKakaoVO vo) throws Exception {
		return sendKakaoMapper.selectListSendKakao(vo);
    }
	
	@RequestMapping(value="/crmSendKakao", method=RequestMethod.GET)
    public CrmSendKakaoVO selectSendKakao(CrmSendKakaoVO vo) throws Exception {
		return sendKakaoMapper.selectSendKakao(vo);
    }
	
	@RequestMapping(value="/crmSendKakao", method=RequestMethod.POST)
	public CommonVO insertSendKakao(CrmSendKakaoVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			sendKakaoMapper.insertSendKakao(vo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmSendKakao", method=RequestMethod.PUT)
	public CommonVO updateSendKakao(CrmSendKakaoVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			sendKakaoMapper.updateSendKakao(vo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmSendKakao", method=RequestMethod.DELETE)
    public CommonVO deleteSendKakao(CrmSendKakaoVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			sendKakaoMapper.deleteSendKakao(vo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			return commonVO;
		}
		
		return commonVO;
    }
}
