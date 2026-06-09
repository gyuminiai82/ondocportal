package kr.ondoc.controller.crm;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ondoc.domain.sybase.common.CommonVO;
import kr.ondoc.domain.sybase.crm.CrmCodeParamVO;
import kr.ondoc.domain.sybase.crm.CrmCounselChartParamVO;
import kr.ondoc.domain.sybase.crm.CrmFieldVO;
import kr.ondoc.domain.sybase.crm.CrmFieldkindVO;
import kr.ondoc.domain.sybase.crm.CrmOpcodeVO;
import kr.ondoc.domain.sybase.crm.CrmPartVO;
import kr.ondoc.domain.sybase.crm.CrmSettingVO;
import kr.ondoc.mapper.sybase.crm.CrmSettingMapper;

@CrossOrigin(origins = "*")
@RestController
public class SettingController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CrmSettingMapper settingMapper;
	
	@RequestMapping(value="/crmSetting", method=RequestMethod.GET)
	public List<CrmSettingVO> selectSetting() throws Exception {
		return settingMapper.selectSetting();
	}
	
	@RequestMapping(value="/crmSetting", method=RequestMethod.POST)
	public CommonVO insertSetting(CrmSettingVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			settingMapper.insertSetting(vo);  
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			return commonVO;
		}
		
		return commonVO;
	  }
	
	@RequestMapping(value="/crmSetting", method=RequestMethod.PUT)
	public CommonVO updateSetting(CrmSettingVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			settingMapper.updateSetting(vo);  
		} catch (Exception e) {
			commonVO.setStatus("500");
		commonVO.setMessage("failure");
			return commonVO;
		}
		
		return commonVO;
	}
	
	@RequestMapping(value="/crmColorSetting", method=RequestMethod.PUT)
	public CommonVO updateColorSetting(CrmSettingVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			settingMapper.updateColorSetting(vo);  
		} catch (Exception e) {
			commonVO.setStatus("500");
		commonVO.setMessage("failure");
			return commonVO;
		}
		
		return commonVO;
	}
	

//	@RequestMapping(value="/crmFieldkind", method=RequestMethod.GET)
//    public List<CrmFieldkindVO> selectFieldkind() throws Exception {
//		return settingMapper.selectFieldkind();
//    }
//	
//	@RequestMapping(value="/crmFieldkindAll", method=RequestMethod.GET)
//    public List<CrmFieldkindVO> selectFieldkindAll() throws Exception {
//		return settingMapper.selectFieldkindAll();
//    }
//	
//	@RequestMapping(value="/crmFieldkind", method=RequestMethod.POST)
//    public CommonVO insertFieldkind(CrmCodeParamVO vo) throws Exception {
//		CommonVO commonVO = new CommonVO();
//		try {
//			settingMapper.insertFieldkind(vo);  
//		} catch (Exception e) {
//			commonVO.setStatus("500");
//			commonVO.setMessage("failure");
//			return commonVO;
//		}
//		
//		return commonVO;
//    }
//	
//	@RequestMapping(value="/crmFieldkind", method=RequestMethod.PUT)
//    public CommonVO updateFieldkind(CrmCodeParamVO vo) throws Exception {
//		CommonVO commonVO = new CommonVO();
//		try {
//			settingMapper.updateFieldkind(vo);  
//		} catch (Exception e) {
//			commonVO.setStatus("500");
//			commonVO.setMessage("failure");
//			return commonVO;
//		}
//		
//		return commonVO;
//    }
//	
//	@RequestMapping(value="/crmFieldkindSort", method=RequestMethod.PUT)
//    public CommonVO updateFieldkindSort(CrmCodeParamVO vo) throws Exception {
//		CommonVO commonVO = new CommonVO();
//		
//		vo.setSeq(vo.getSeq());
//		
//		try {
//			settingMapper.updateFieldkindSort(vo);  
//		} catch (Exception e) {
//			return commonVO;
//		}
//		
//		vo.setCode(vo.getCode());
//		vo.setSeq(vo.getSeq());
//		try {
//			settingMapper.updateFieldkind(vo);  
//		} catch (Exception e) {
//			commonVO.setStatus("500");
//			commonVO.setMessage("failure");
//			return commonVO;
//		}
//		
//		return commonVO;
//    }
//	
//	@RequestMapping(value="/crmFieldkindSortLast", method=RequestMethod.PUT)
//    public CommonVO updateFieldkindSortLast(CrmCodeParamVO vo) throws Exception {
//		CommonVO commonVO = new CommonVO();
//		
//		vo.setSeq(vo.getSeq());
//		
//		try {
//			settingMapper.updateFieldkindSortLast(vo);  
//		} catch (Exception e) {
//			commonVO.setStatus("500");
//			commonVO.setMessage("failure");
//			return commonVO;
//		}
//		
//		return commonVO;
//    }
//	
//	@RequestMapping(value="/crmFieldkind", method=RequestMethod.DELETE)
//    public CommonVO deleteFieldkind(CrmCodeParamVO vo) throws Exception {
//		CommonVO commonVO = new CommonVO();
//		try {
//			settingMapper.deleteFieldkind(vo);  
//		} catch (Exception e) {
//			commonVO.setStatus("500");
//			commonVO.setMessage("failure");
//			return commonVO;
//		}
//		
//		return commonVO;
//    }
//	
//	@RequestMapping(value="/crmField", method=RequestMethod.GET)
//    public List<CrmFieldVO> selectField(String code) throws Exception {
//		return settingMapper.selectField(code);
//    }
//	
//	@RequestMapping(value="/crmPart", method=RequestMethod.GET)
//    public List<CrmPartVO> selectPart() throws Exception {
//		return settingMapper.selectPart();
//    }
//	
//	@RequestMapping(value="/crmPartAll", method=RequestMethod.GET)
//    public List<CrmPartVO> selectPartAll() throws Exception {
//		return settingMapper.selectPartAll();
//    }
//	
//	@RequestMapping(value="/crmPart", method=RequestMethod.POST)
//    public CommonVO insertPart(CrmCodeParamVO vo) throws Exception {
//		CommonVO commonVO = new CommonVO();
//		try {
//			settingMapper.insertPart(vo);  
//		} catch (Exception e) {
//			commonVO.setStatus("500");
//			commonVO.setMessage("failure");
//			return commonVO;
//		}
//		
//		return commonVO;
//    }
//	
//	@RequestMapping(value="/crmPart", method=RequestMethod.PUT)
//    public CommonVO updatePart(CrmCodeParamVO vo) throws Exception {
//		CommonVO commonVO = new CommonVO();
//		try {
//			settingMapper.updatePart(vo);  
//		} catch (Exception e) {
//			commonVO.setStatus("500");
//			commonVO.setMessage("failure");
//			return commonVO;
//		}
//		
//		return commonVO;
//    }
//	
//	@RequestMapping(value="/crmPartSort", method=RequestMethod.PUT)
//    public CommonVO updatePartSort(CrmCodeParamVO vo) throws Exception {
//		CommonVO commonVO = new CommonVO();
//		
//		vo.setSeq(vo.getSeq());
//		
//		try {
//			settingMapper.updatePartSort(vo);  
//		} catch (Exception e) {
//			return commonVO;
//		}
//		
//		vo.setCode(vo.getCode());
//		vo.setSeq(vo.getSeq());
//		try {
//			settingMapper.updatePart(vo);  
//		} catch (Exception e) {
//			commonVO.setStatus("500");
//			commonVO.setMessage("failure");
//			return commonVO;
//		}
//		
//		return commonVO;
//    }
//	
//	@RequestMapping(value="/crmPartSortLast", method=RequestMethod.PUT)
//    public CommonVO updatePartSortLast(CrmCodeParamVO vo) throws Exception {
//		CommonVO commonVO = new CommonVO();
//		
//		vo.setSeq(vo.getSeq());
//		
//		try {
//			settingMapper.updatePartSortLast(vo);  
//		} catch (Exception e) {
//			commonVO.setStatus("500");
//			commonVO.setMessage("failure");
//			return commonVO;
//		}
//		
//		return commonVO;
//    }
//	
//	@RequestMapping(value="/crmPart", method=RequestMethod.DELETE)
//    public CommonVO deletePart(CrmCodeParamVO vo) throws Exception {
//		CommonVO commonVO = new CommonVO();
//		try {
//			settingMapper.deletePart(vo);  
//		} catch (Exception e) {
//			commonVO.setStatus("500");
//			commonVO.setMessage("failure");
//			return commonVO;
//		}
//		
//		return commonVO;
//    }
//	
//	@RequestMapping(value="/crmOpcode", method=RequestMethod.GET)
//    public List<CrmOpcodeVO> selectOpcode() throws Exception {
//		return settingMapper.selectOpcode();
//    }
//	
//	@RequestMapping(value="/crmOpcodeAll", method=RequestMethod.GET)
//    public List<CrmOpcodeVO> selectOpcodeAll() throws Exception {
//		return settingMapper.selectOpcodeAll();
//    }
//	
//	@RequestMapping(value="/crmOpcode", method=RequestMethod.POST)
//    public CommonVO insertOpcode(CrmCodeParamVO vo) throws Exception {
//		CommonVO commonVO = new CommonVO();
//		try {
//			settingMapper.insertOpcode(vo);  
//		} catch (Exception e) {
//			commonVO.setStatus("500");
//			commonVO.setMessage("failure");
//			return commonVO;
//		}
//		
//		return commonVO;
//    }
//	
//	@RequestMapping(value="/crmOpcode", method=RequestMethod.PUT)
//    public CommonVO updateOpcode(CrmCodeParamVO vo) throws Exception {
//		CommonVO commonVO = new CommonVO();
//		try {
//			settingMapper.updateOpcode(vo);  
//		} catch (Exception e) {
//			commonVO.setStatus("500");
//			commonVO.setMessage("failure");
//			return commonVO;
//		}
//		
//		return commonVO;
//    }
//	
//	@RequestMapping(value="/crmOpcodeSort", method=RequestMethod.PUT)
//    public CommonVO updateOpcodeSort(CrmCodeParamVO vo) throws Exception {
//		CommonVO commonVO = new CommonVO();
//		
//		vo.setSeq(vo.getSeq());
//		
//		try {
//			settingMapper.updateOpcodeSort(vo);  
//		} catch (Exception e) {
//			return commonVO;
//		}
//		
//		vo.setCode(vo.getCode());
//		vo.setSeq(vo.getSeq());
//		try {
//			settingMapper.updateOpcode(vo);  
//		} catch (Exception e) {
//			commonVO.setStatus("500");
//			commonVO.setMessage("failure");
//			return commonVO;
//		}
//		
//		return commonVO;
//    }
//	
//	@RequestMapping(value="/crmOpcodeSortLast", method=RequestMethod.PUT)
//    public CommonVO updateOpcodeSortLast(CrmCodeParamVO vo) throws Exception {
//		CommonVO commonVO = new CommonVO();
//		
//		vo.setSeq(vo.getSeq());
//		
//		try {
//			settingMapper.updateOpcodeSortLast(vo);  
//		} catch (Exception e) {
//			commonVO.setStatus("500");
//			commonVO.setMessage("failure");
//			return commonVO;
//		}
//		
//		return commonVO;
//    }
//	
//	@RequestMapping(value="/crmOpcode", method=RequestMethod.DELETE)
//    public CommonVO deleteOpcode(CrmCodeParamVO vo) throws Exception {
//		CommonVO commonVO = new CommonVO();
//		try {
//			settingMapper.deleteOpcode(vo);  
//		} catch (Exception e) {
//			commonVO.setStatus("500");
//			commonVO.setMessage("failure");
//			return commonVO;
//		}
//		
//		return commonVO;
//    }
}
