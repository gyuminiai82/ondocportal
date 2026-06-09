package kr.ondoc.controller.crm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ondoc.domain.sybase.common.CommonVO;
import kr.ondoc.domain.sybase.crm.CrmUsrmngrSelectRelationVO;
import kr.ondoc.mapper.sybase.crm.CrmUsrmngrSelectRelationMapper;

@CrossOrigin(origins = "*")
@RestController
public class UsrmngrSelectRelationController {
	
	@Autowired
	private CrmUsrmngrSelectRelationMapper usrmngrSelectRelationMapper;
	
	@RequestMapping(value="/crmUsrmngrSelectRelationList", method=RequestMethod.GET)
    public List<CrmUsrmngrSelectRelationVO> selectList(CrmUsrmngrSelectRelationVO vo) throws Exception {
		return usrmngrSelectRelationMapper.selectListUsrmngrSelectRelation(vo);
    }
	
	@RequestMapping(value="/crmUsrmngrSelectRelation", method=RequestMethod.POST)
	public CommonVO insert(CrmUsrmngrSelectRelationVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			usrmngrSelectRelationMapper.insertUsrmngrSelectRelation(vo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmUsrmngrSelectRelation", method=RequestMethod.DELETE)
    public CommonVO delete(CrmUsrmngrSelectRelationVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			usrmngrSelectRelationMapper.deleteUsrmngrSelectRelation(vo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			return commonVO;
		}
		
		return commonVO;
    }
}
