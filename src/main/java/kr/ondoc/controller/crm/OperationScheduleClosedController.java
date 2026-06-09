package kr.ondoc.controller.crm;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ondoc.domain.sybase.common.CommonVO;
import kr.ondoc.domain.sybase.crm.CrmArrOperationScheduleClosedVO;
import kr.ondoc.domain.sybase.crm.CrmOperationScheduleClosedVO;
import kr.ondoc.mapper.sybase.crm.CrmOperationScheduleClosedMapper;

//휴진설정/휴진설정취소
@CrossOrigin(origins = "*")
@RestController
public class OperationScheduleClosedController {
private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CrmOperationScheduleClosedMapper operationScheduleClosedMapper;

	@RequestMapping(value="/crmOperationScheduleClosed", method=RequestMethod.GET)
	public List<CrmOperationScheduleClosedVO> selectListOperationScheduleClosed(CrmOperationScheduleClosedVO vo) throws Exception {
		return operationScheduleClosedMapper.selectListOperationScheduleClosed(vo);
	}
	
	@RequestMapping(value="/crmOperationScheduleClosed", method=RequestMethod.POST)
	public CommonVO insertOperationScheduleClosed(CrmArrOperationScheduleClosedVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		
		JSONArray arr = new JSONArray(vo.getArrOperationScheduleClosed());
		
		for (int i = 0; i < arr.length(); i++) {
			String str = arr.get(i).toString() ;
			
			JSONObject obj = new JSONObject(str);
			
			CrmOperationScheduleClosedVO operationScheduleClosed = new CrmOperationScheduleClosedVO();
			operationScheduleClosed.setDepartment_idx(obj.getString("department_idx"));
			operationScheduleClosed.setDepartment_number(Integer.toString(obj.getInt("department_number")));
			operationScheduleClosed.setSchedule_date(obj.getString("schedule_date"));
			operationScheduleClosed.setSchedule_time(obj.getString("schedule_time"));
			operationScheduleClosed.setWrite_id(vo.getWrite_id());
			
			try {
				operationScheduleClosedMapper.insertOperationScheduleClosed(operationScheduleClosed);  
			} catch (Exception e) {
				commonVO.setStatus("500");
				commonVO.setMessage("failure");
				return commonVO;
			}
		}
		
		return commonVO;
	  }
	
	@RequestMapping(value="/crmOperationScheduleClosed", method=RequestMethod.DELETE)
	public CommonVO deleteOperationScheduleClosed(CrmArrOperationScheduleClosedVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();

		JSONArray arr = new JSONArray(vo.getArrOperationScheduleClosed());
		
		for (int i = 0; i < arr.length(); i++) {
			String str = arr.get(i).toString() ;
			
			JSONObject obj = new JSONObject(str);
			
			CrmOperationScheduleClosedVO operationScheduleClosed = new CrmOperationScheduleClosedVO();
			operationScheduleClosed.setDepartment_idx(obj.getString("department_idx"));
			operationScheduleClosed.setDepartment_number(Integer.toString(obj.getInt("department_number")));
			operationScheduleClosed.setSchedule_date(obj.getString("schedule_date"));
			operationScheduleClosed.setSchedule_time(obj.getString("schedule_time"));
			operationScheduleClosed.setWrite_id(vo.getWrite_id());
			
			try {
				operationScheduleClosedMapper.deleteOperationScheduleClosed(operationScheduleClosed);  
			} catch (Exception e) {
				commonVO.setStatus("500");
				commonVO.setMessage("failure");
				return commonVO;
			}
		}
		
		return commonVO;
	}
}
