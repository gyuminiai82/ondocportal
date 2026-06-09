package kr.ondoc.controller.crm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ondoc.domain.sybase.common.CommonVO;
import kr.ondoc.domain.sybase.crm.CrmOndocGroupMenuRelationVO;
import kr.ondoc.mapper.sybase.crm.CrmOndocGroupMenuRelationMapper;

@CrossOrigin(origins = "*")
@RestController
public class OndocGroupMenuRelationController {
	
	@Autowired
	private CrmOndocGroupMenuRelationMapper groupMenuRelationMapper;
	
	@RequestMapping(value="/crmGroupMenuRelationList", method=RequestMethod.GET)
    public List<CrmOndocGroupMenuRelationVO> selectList(CrmOndocGroupMenuRelationVO vo) throws Exception {
		return groupMenuRelationMapper.selectList(vo);
    }
	
	@RequestMapping(value="/crmGroupMenuRelation", method=RequestMethod.POST)
	public CommonVO insert(CrmOndocGroupMenuRelationVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			groupMenuRelationMapper.insert(vo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmGroupMenuRelation", method=RequestMethod.DELETE)
    public List<CrmOndocGroupMenuRelationVO> delete(CrmOndocGroupMenuRelationVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			groupMenuRelationMapper.delete(vo);
		} catch (Exception e) {
		}
		
		return groupMenuRelationMapper.selectList(vo);
    }
}
