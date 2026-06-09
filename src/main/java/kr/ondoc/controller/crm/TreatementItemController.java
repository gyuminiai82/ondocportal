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
import kr.ondoc.domain.sybase.crm.CrmTreatementItemSearchVO;
import kr.ondoc.domain.sybase.crm.CrmTreatementItemVO;
import kr.ondoc.mapper.sybase.crm.CrmTreatementItemMapper;

@CrossOrigin(origins = "*")
@RestController
public class TreatementItemController {
	@Autowired
	private CrmTreatementItemMapper treatementItemMapper;
	
	@RequestMapping(value="/crmTreatementItemList", method=RequestMethod.GET)
    public List<CrmTreatementItemVO> selectListTreatementItem(CrmTreatementItemVO vo) throws Exception {
		return treatementItemMapper.selectListTreatementItem(vo);
    }
	
	@RequestMapping(value="/crmTreatementItem", method=RequestMethod.POST)
	public CommonVO insertTreatementItem(CrmTreatementItemVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			treatementItemMapper.insertTreatementItem(vo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmTreatementItem", method=RequestMethod.PUT)
	public CommonVO updateTreatementItem(CrmTreatementItemVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			treatementItemMapper.updateTreatementItem(vo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmTreatementItemSort", method=RequestMethod.PUT)
	public CommonVO updateTreatementItemSort(String arrValue) throws Exception {
		CommonVO commonVO = new CommonVO();
		
		JSONArray arr = new JSONArray(arrValue);
		
		for (int i = 0; i < arr.length(); i++) {
			String str = arr.get(i).toString() ;
			
			JSONObject obj = new JSONObject(str);
			
			CrmTreatementItemVO treatementItem = new CrmTreatementItemVO();
			treatementItem.setTreatement_item_idx(obj.getString("treatement_item_idx"));
			treatementItem.setSort(Integer.toString(obj.getInt("sort")));
			
			try {
				treatementItemMapper.updateTreatementItem(treatementItem);  
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
	
	@RequestMapping(value="/crmTreatementItem", method=RequestMethod.DELETE)
    public CommonVO deleteTreatementItem(CrmTreatementItemVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			treatementItemMapper.deleteTreatementItem(vo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmTreatementItemListSearch", method=RequestMethod.GET)
    public List<CrmTreatementItemSearchVO> selectListTreatementItem(String name) throws Exception {
		return treatementItemMapper.selectListSearchTreatementItem(name);
    }
}
