package kr.ondoc.mapper.sybase.crm;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import kr.ondoc.domain.sybase.crm.CrmOperationScheduleClosedVO;

public interface CrmOperationScheduleClosedMapper {
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT * from crm_operation_schedule_closed WHERE 0=0 "
			+ "]]>"
			+ "<if test=\"schedule_date != null and schedule_date != ''\">"
			+ "AND schedule_date = #{schedule_date} "
			+ "</if>"
			+ "<if test=\"department_idx != null and department_idx != ''\">"
			+ "AND department_idx = #{department_idx} "
			+ "</if>"
			+ "</script>")
	public List<CrmOperationScheduleClosedVO> selectListOperationScheduleClosed(CrmOperationScheduleClosedVO vo) throws Exception;
	
	@Insert("<script>"
			+ "INSERT INTO crm_operation_schedule_closed ("
			+ "department_idx, department_number, schedule_date, schedule_time, write_date, write_id) "
			+ "VALUES ( "
			+ "#{department_idx}, #{department_number}, "
			+ "#{schedule_date}, #{schedule_time}, "
			+ "(convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)), "
			+ "#{write_id})"
			+ "</script>")
	public void insertOperationScheduleClosed(CrmOperationScheduleClosedVO vo) throws Exception;

	@Delete("<script>"
			+ "DELETE FROM crm_operation_schedule_closed  "
			+ "WHERE 0=0  "
			+ "<if test=\"schedule_date != null and schedule_date != ''\">"
			+ "AND schedule_date = #{schedule_date} "
			+ "</if>"
			+ "<if test=\"schedule_time != null and schedule_time != ''\">"
			+ "AND schedule_time = #{schedule_time} "
			+ "</if>"
			+ "<if test=\"department_idx != null and department_idx != ''\">"
			+ "AND department_idx = #{department_idx} "
			+ "</if>"
			+ "<if test=\"department_number != null and department_number != ''\">"
			+ "AND department_number = #{department_number} "
			+ "</if>"
			+ "</script>")
	public void deleteOperationScheduleClosed(CrmOperationScheduleClosedVO vo) throws Exception;
}
