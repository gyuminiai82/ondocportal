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
import kr.ondoc.domain.sybase.crm.CrmTreatementItemDetailVO;
import kr.ondoc.mapper.sybase.crm.CrmTreatementItemDetailMapper;

@CrossOrigin(origins = "*")
@RestController
public class TreatementItemDetailController {
	@Autowired
	private CrmTreatementItemDetailMapper treatementItemDetailMapper;
	
	@RequestMapping(value="/crmTreatementItemDetailList", method=RequestMethod.GET)
    public List<CrmTreatementItemDetailVO> selectListTreatementItemDetail(CrmTreatementItemDetailVO vo) throws Exception {
		return treatementItemDetailMapper.selectListTreatementItemDetail(vo);
    }
	
	@RequestMapping(value="/crmTreatementItemDetail", method=RequestMethod.POST)
	public CommonVO insertTreatementItemDetail(CrmTreatementItemDetailVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			treatementItemDetailMapper.insertTreatementItemDetail(vo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmTreatementItemDetail", method=RequestMethod.PUT)
	public CommonVO updateTreatementItemDetail(CrmTreatementItemDetailVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			treatementItemDetailMapper.updateTreatementItemDetail(vo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmTreatementItemDetailSort", method=RequestMethod.PUT)
	public CommonVO updateTreatementItemDetailSort(String arrValue) throws Exception {
		CommonVO commonVO = new CommonVO();
		
		JSONArray arr = new JSONArray(arrValue);
		
		for (int i = 0; i < arr.length(); i++) {
			String str = arr.get(i).toString() ;
			
			JSONObject obj = new JSONObject(str);
			
			CrmTreatementItemDetailVO department = new CrmTreatementItemDetailVO();
			department.setTreatement_item_detail_idx(obj.getString("treatement_item_detail_idx"));
			department.setSort(Integer.toString(obj.getInt("sort")));
			
			try {
				treatementItemDetailMapper.updateTreatementItemDetail(department);  
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
	
	@RequestMapping(value="/crmTreatementItemDetail", method=RequestMethod.DELETE)
    public CommonVO deleteTreatementItemDetail(CrmTreatementItemDetailVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			treatementItemDetailMapper.deleteTreatementItemDetail(vo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			return commonVO;
		}
		
		return commonVO;
    }
}
