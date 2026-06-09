package kr.ondoc.mapper.sybase.crm;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.ondoc.domain.sybase.crm.CrmOperationSchedulePatientVO;

public interface CrmOperationSchedulePatientMapper {
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT * FROM patient WHERE 0=0 "
			+ "]]>"
			+ "<if test=\"bpt_ptno != null and bpt_ptno != ''\">"
			+ "AND bpt_ptno = #{bpt_ptno} "
			+ "</if>"
			+ "<if test=\"schedule_date != null and schedule_date != ''\">"
			+ "AND schedule_date = #{schedule_date} "
			+ "</if>"
			+ "<if test=\"del_yn != null and del_yn != ''\">"
			+ "AND del_yn = #{del_yn} "
			+ "</if>"
			+ "</script>")
	public List<CrmOperationSchedulePatientVO> selectListOperationSchedulePatient(CrmOperationSchedulePatientVO vo) throws Exception;
	
	@Insert("<script>"
			+ "INSERT INTO patient ("
			+ "status_idx, department_idx, department_number, schedule_date, schedule_time, "
			+ "ptno, receptionist_id, counselor_id, doctor_id, visit_type, "
			+ "sms_send_check, reserve_memo, item_arr, del_yn, "
			+ "write_date, "
			+ "write_id) "
			+ "VALUES ( "
			+ "#{status_idx}, #{department_idx}, #{department_number}, #{schedule_date}, #{schedule_time}, "
			+ "#{ptno}, #{receptionist_id}, #{counselor_id}, #{doctor_id}, #{visit_type}, "
			+ "#{sms_send_check}, #{reserve_memo}, #{item_arr}, #{del_yn}, "
			+ "(convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)), "
			+ "#{write_id})"
			+ "</script>")
	public void insertOperationSchedulePatient(CrmOperationSchedulePatientVO vo) throws Exception;
	
	@Update("<script>"
			+ "UPDATE patient SET "
			+ "operation_schedule_idx = operation_schedule_idx "
			+ "<if test=\"status_idx != null and status_idx != ''\">"
			+ ",status_idx = #{status_idx} "
			+ "</if>"
			+ "<if test=\"department_idx != null and department_idx != ''\">"
			+ ",department_idx = #{department_idx} "
			+ "</if>"
			+ "<if test=\"department_number != null and department_number != ''\">"
			+ ",department_number = #{department_number} "
			+ "</if>"
			+ "<if test=\"schedule_date != null and schedule_date != ''\">"
			+ ",schedule_date = #{schedule_date} "
			+ "</if>"
			+ "<if test=\"schedule_time != null and schedule_time != ''\">"
			+ ",schedule_time = #{schedule_time} "
			+ "</if>"
			+ "<if test=\"ptno != null and ptno != ''\">"
			+ ",ptno = #{ptno} "
			+ "</if>"
			+ "<if test=\"receptionist_id != null and receptionist_id != ''\">"
			+ ",receptionist_id = #{receptionist_id} "
			+ "</if>"
			+ "<if test=\"counselor_id != null and counselor_id != ''\">"
			+ ",counselor_id = #{counselor_id} "
			+ "</if>"
			+ "<if test=\"doctor_id != null and doctor_id != ''\">"
			+ ",doctor_id = #{doctor_id} "
			+ "</if>"
			+ "<if test=\"visit_type != null and visit_type != ''\">"
			+ ",visit_type = #{visit_type} "
			+ "</if>"
			+ "<if test=\"sms_send_check != null and sms_send_check != ''\">"
			+ ",sms_send_check = #{sms_send_check} "
			+ "</if>"
			+ "<if test=\"reserve_memo != null and reserve_memo != ''\">"
			+ ",reserve_memo = #{reserve_memo} "
			+ "</if>"
			+ "<if test=\"item_arr != null and item_arr != ''\">"
			+ ",item_arr = #{item_arr} "
			+ "</if>"
			+ "<if test=\"del_yn != null and del_yn != ''\">"
			+ ",del_yn = #{del_yn} "
			+ "</if>"
			+ "<if test=\"modify_id != null and modify_id != ''\">"
			+ ",modify_date = (convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)) "
			+ ",modify_id = #{modify_id} "
			+ "</if>"
			+ "WHERE 0=0 "
			+ "AND operation_schedule_idx = #{operation_schedule_idx}"
			+ "</script>")
	public void updateOperationSchedulePatient(CrmOperationSchedulePatientVO vo) throws Exception;
}
