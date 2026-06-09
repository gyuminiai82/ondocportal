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
import kr.ondoc.domain.sybase.crm.CrmDepartmentVO;
import kr.ondoc.domain.sybase.crm.CrmOperationScheduleClosedVO;
import kr.ondoc.mapper.sybase.crm.CrmDepartmentMapper;

@CrossOrigin(origins = "*")
@RestController
public class DepartmentController {
	@Autowired
	private CrmDepartmentMapper departmentMapper;
	
	@RequestMapping(value="/crmDepartmentList", method=RequestMethod.GET)
    public List<CrmDepartmentVO> selectListDepartment(CrmDepartmentVO vo) throws Exception {
		return departmentMapper.selectListDepartment(vo);
    }
	
	@RequestMapping(value="/crmDepartment", method=RequestMethod.POST)
	public CommonVO insertDepartment(CrmDepartmentVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			departmentMapper.insertDepartment(vo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmDepartment", method=RequestMethod.PUT)
	public CommonVO updateDepartment(CrmDepartmentVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			departmentMapper.updateDepartment(vo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmDepartmentSort", method=RequestMethod.PUT)
	public CommonVO updateDepartmentSort(CrmArrDepartmentVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		
		JSONArray arr = new JSONArray(vo.getArrDepartment());
		
		for (int i = 0; i < arr.length(); i++) {
			String str = arr.get(i).toString() ;
			
			JSONObject obj = new JSONObject(str);
			
			CrmDepartmentVO department = new CrmDepartmentVO();
			department.setDepartment_idx(obj.getString("department_idx"));
			department.setSort(Integer.toString(obj.getInt("sort")));
			
			try {
				departmentMapper.updateDepartment(department);  
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
	
	@RequestMapping(value="/crmDepartmentReceiptDefaultYn", method=RequestMethod.PUT)
	public CommonVO updateDepartmentReceiptDefaultYnReset(CrmDepartmentVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			//기본부서 전체 N으로 수정
			departmentMapper.updateDepartmentReceiptDefaultYnReset();
			
			//선택한 부서만 Y로 업데이트 
			departmentMapper.updateDepartmentReceiptDefaultYn(vo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmDepartment", method=RequestMethod.DELETE)
    public CommonVO deleteDepartment(CrmDepartmentVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			departmentMapper.deleteDepartment(vo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			return commonVO;
		}
		
		return commonVO;
    }
}
