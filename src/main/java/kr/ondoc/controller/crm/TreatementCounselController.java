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
import kr.ondoc.domain.sybase.crm.CrmTreatementCounselItemRelationVO;
import kr.ondoc.domain.sybase.crm.CrmTreatementCounselVO;
import kr.ondoc.mapper.sybase.crm.CrmTreatementCounselItemRelationMapper;
import kr.ondoc.mapper.sybase.crm.CrmTreatementCounselMapper;

@CrossOrigin(origins = "*")
@RestController
public class TreatementCounselController {
	@Autowired
	private CrmTreatementCounselMapper counselMapper;
	
	@Autowired
	private CrmTreatementCounselItemRelationMapper counselItemRelationMapper;
	
	@RequestMapping(value="/crmTreatementCounselList", method=RequestMethod.GET)
    public List<CrmTreatementCounselVO> selectListCounsel(CrmTreatementCounselVO vo) throws Exception {
		return counselMapper.selectListCounsel(vo);
    }
	
	@RequestMapping(value="/crmTreatementCounsel", method=RequestMethod.POST)
	public CommonVO insertCounsel(CrmTreatementCounselVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			counselMapper.insertCounsel(vo);
			
			String idx = counselMapper.selectMaxIdx();
			
			JSONArray arr = new JSONArray(vo.getItem_arr());
			
			for (int i = 0; i < arr.length(); i++) {
				String str = arr.get(i).toString() ;
				
				JSONObject obj = new JSONObject(str);
				
				CrmTreatementCounselItemRelationVO opVO = new CrmTreatementCounselItemRelationVO();
				opVO.setCounsel_idx(idx);
				opVO.setItem_idx(obj.getString("item_idx"));
				opVO.setItem_detail_idx(obj.getString("item_detail_idx"));
				
				counselItemRelationMapper.insertTreatementCounselItemRelation(opVO);
			}
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmTreatementCounsel", method=RequestMethod.PUT)
	public CommonVO updateCounsel(CrmTreatementCounselVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			counselMapper.updateCounsel(vo);
			
			JSONArray arr = new JSONArray(vo.getItem_arr());
			
			counselItemRelationMapper.deleteTreatementCounselItemRelation(vo.getCounsel_idx());
			for (int i = 0; i < arr.length(); i++) {
				String str = arr.get(i).toString() ;
				
				JSONObject obj = new JSONObject(str);
				
				CrmTreatementCounselItemRelationVO opVO = new CrmTreatementCounselItemRelationVO();
				opVO.setCounsel_idx(vo.getCounsel_idx());
				opVO.setItem_idx(obj.getString("item_idx"));
				opVO.setItem_detail_idx(obj.getString("item_detail_idx"));
				
				counselItemRelationMapper.insertTreatementCounselItemRelation(opVO);
			}
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmTreatementCounsel", method=RequestMethod.DELETE)
    public CommonVO deleteCounsel(CrmTreatementCounselVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			counselMapper.deleteCounsel(vo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			return commonVO;
		}
		
		return commonVO;
    }
}
