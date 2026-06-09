package kr.ondoc.controller.crm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ondoc.domain.sybase.common.CommonVO;
import kr.ondoc.domain.sybase.crm.CrmUsrmngrGroupRelationVO;
import kr.ondoc.mapper.sybase.crm.CrmUsrmngrGroupRelationMapper;

@CrossOrigin(origins = "*")
@RestController
public class UsrmngrGroupRelationController {

	@Autowired
	private CrmUsrmngrGroupRelationMapper usrmngrGroupRelationMapper;
	
	@RequestMapping(value="/crmUsrmngrGroupRelationList", method=RequestMethod.GET)
    public List<CrmUsrmngrGroupRelationVO> selectList(CrmUsrmngrGroupRelationVO vo) throws Exception {
		return usrmngrGroupRelationMapper.selectList(vo);
    }
	
	@RequestMapping(value="/crmUsrmngrGroupRelation", method=RequestMethod.POST)
	public CommonVO insert(CrmUsrmngrGroupRelationVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			usrmngrGroupRelationMapper.insert(vo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmUsrmngrGroupRelation", method=RequestMethod.DELETE)
    public List<CrmUsrmngrGroupRelationVO> delete(CrmUsrmngrGroupRelationVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			usrmngrGroupRelationMapper.delete(vo);
		} catch (Exception e) {
		}
		
		return usrmngrGroupRelationMapper.selectList(vo);
    }
}
