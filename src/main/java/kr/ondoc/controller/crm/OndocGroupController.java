package kr.ondoc.controller.crm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ondoc.domain.sybase.common.CommonVO;
import kr.ondoc.domain.sybase.crm.CrmAuthMenuVO;
import kr.ondoc.domain.sybase.crm.CrmOndocGroupVO;
import kr.ondoc.mapper.sybase.crm.CrmOndocGroupMapper;

@CrossOrigin(origins = "*")
@RestController
public class OndocGroupController {
	
	@Autowired
	private CrmOndocGroupMapper groupMapper;
	
	@RequestMapping(value="/crmGroupList", method=RequestMethod.GET)
    public List<CrmOndocGroupVO> selectList(CrmOndocGroupVO vo) throws Exception {
		return groupMapper.selectListGroup(vo);
    }
	
	@RequestMapping(value="/crmGroup", method=RequestMethod.POST)
	public CommonVO insert(CrmOndocGroupVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			groupMapper.insertGroup(vo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmGroup", method=RequestMethod.PUT)
	public CommonVO update(CrmOndocGroupVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			groupMapper.updateGroup(vo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmGroup", method=RequestMethod.DELETE)
    public CommonVO delete(CrmOndocGroupVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			groupMapper.deleteGroup(vo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			return commonVO;
		}
		
		return commonVO;
    }
	@RequestMapping(value="/crmAuthMenuList", method=RequestMethod.GET)
    public List<CrmAuthMenuVO> selectList(String id) throws Exception {
		return groupMapper.selectListAuthMenu(id);
    }
}
