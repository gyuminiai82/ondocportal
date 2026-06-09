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
import kr.ondoc.domain.sybase.crm.CrmArrSetcolorVO;
import kr.ondoc.domain.sybase.crm.CrmArrStatusVO;
import kr.ondoc.domain.sybase.crm.CrmSetcolorVO;
import kr.ondoc.mapper.sybase.crm.CrmSetcolorMapper;

@CrossOrigin(origins = "*")
@RestController
public class SetcolorController {
	@Autowired
	private CrmSetcolorMapper setcolorMapper;
	
	@RequestMapping(value="/crmSetcolorList", method=RequestMethod.GET)
    public List<CrmSetcolorVO> selectListSetcolor(CrmSetcolorVO vo) throws Exception {
		return setcolorMapper.selectListSetcolor(vo);
    }
	
	@RequestMapping(value="/crmSetcolor", method=RequestMethod.POST)
	public CommonVO insertDepartment(CrmSetcolorVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			setcolorMapper.insertSetcolor(vo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmSetcolor", method=RequestMethod.PUT)
	public CommonVO updateSetcolor(CrmSetcolorVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			setcolorMapper.updateSetcolor(vo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmSetcolorSort", method=RequestMethod.PUT)
	public CommonVO updateSetcolorSort(CrmArrSetcolorVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		
		JSONArray arr = new JSONArray(vo.getArrColor());
		
		for (int i = 0; i < arr.length(); i++) {
			String str = arr.get(i).toString() ;
			
			JSONObject obj = new JSONObject(str);
			
			CrmSetcolorVO department = new CrmSetcolorVO();
			department.setSetcolor_idx(obj.getString("setcolor_idx"));
			department.setSort(Integer.toString(obj.getInt("sort")));
			
			try {
				setcolorMapper.updateSetcolor(department);  
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
	
	@RequestMapping(value="/crmSetcolor", method=RequestMethod.DELETE)
    public CommonVO deleteSetcolor(CrmSetcolorVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			setcolorMapper.deleteSetcolor(vo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			return commonVO;
		}
		
		return commonVO;
    }
}
