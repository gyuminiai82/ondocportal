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
import kr.ondoc.domain.sybase.crm.CrmArrDepartmentVO;
import kr.ondoc.domain.sybase.crm.CrmArrStatusVO;
import kr.ondoc.domain.sybase.crm.CrmOperationScheduleStatusVO;
import kr.ondoc.mapper.sybase.crm.CrmOperationScheduleStatusMapper;

@CrossOrigin(origins = "*")
@RestController
public class OperationScheduleStatusController {
	@Autowired
	private CrmOperationScheduleStatusMapper statusMapper;
	
	@RequestMapping(value="/crmOperationScheduleStatusList", method=RequestMethod.GET)
    public List<CrmOperationScheduleStatusVO> selectListDepartment(CrmOperationScheduleStatusVO vo) throws Exception {
		return statusMapper.selectListOperationScheduleStatus(vo);
    }
	
	@RequestMapping(value="/crmOperationScheduleStatus", method=RequestMethod.POST)
	public CommonVO insertDepartment(CrmOperationScheduleStatusVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			statusMapper.insertOperationScheduleStatus(vo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmOperationScheduleStatus", method=RequestMethod.PUT)
	public CommonVO updateDepartment(CrmOperationScheduleStatusVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			statusMapper.updateOperationScheduleStatus(vo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmOperationScheduleStatusSort", method=RequestMethod.PUT)
	public CommonVO updateDepartmentSort(CrmArrStatusVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		
		JSONArray arr = new JSONArray(vo.getArrStatus());
		
		for (int i = 0; i < arr.length(); i++) {
			String str = arr.get(i).toString() ;
			
			JSONObject obj = new JSONObject(str);
			
			CrmOperationScheduleStatusVO department = new CrmOperationScheduleStatusVO();
			department.setStatus_idx(obj.getString("status_idx"));
			department.setSort(Integer.toString(obj.getInt("sort")));
			
			try {
				statusMapper.updateOperationScheduleStatus(department);  
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
	
	@RequestMapping(value="/crmOperationScheduleStatus", method=RequestMethod.DELETE)
    public CommonVO deleteDepartment(CrmOperationScheduleStatusVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			statusMapper.deleteOperationScheduleStatus(vo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			return commonVO;
		}
		
		return commonVO;
    }
}
