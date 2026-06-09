package kr.ondoc.mapper.sybase.crm;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.ondoc.domain.sybase.crm.CrmDepartmentVO;

public interface CrmDepartmentMapper {
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT * from crm_department WHERE 0=0 "
			+ "]]>"
			+ "<if test=\"use_yn != null and use_yn != ''\">"
			+ "AND use_yn = #{use_yn} "
			+ "</if>"
			+ "<if test=\"del_yn != null and del_yn != ''\">"
			+ "AND del_yn = #{del_yn} "
			+ "</if>"
			+ "ORDER BY sort ASC "
			+ "</script>")
	public List<CrmDepartmentVO> selectListDepartment(CrmDepartmentVO vo) throws Exception;
	
	@Insert("<script>"
			+ "INSERT INTO crm_department ("
			+ "department_name, department_number, sort, use_yn, online_reserve_yn, receipt_default_yn, manless_reserve_yn,  write_date, write_id) "
			+ "VALUES ( "
			+ "#{department_name}, #{department_number}, "
			+ "ISNULL((SELECT MAX(sort) FROM crm_department), 0)+1, #{use_yn}, #{online_reserve_yn}, #{receipt_default_yn}, #{manless_reserve_yn}, "
			+ "(convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)), "
			+ "#{write_id})"
			+ "</script>")
	public void insertDepartment(CrmDepartmentVO vo) throws Exception;
	
	@Update("<script>"
			+ "UPDATE crm_department SET "
			+ "department_idx = department_idx "
			+ "<if test=\"department_name != null and department_name != ''\">"
			+ ",department_name = #{department_name} "
			+ "</if>"
			+ "<if test=\"department_number != null and department_number != ''\">"
			+ ",department_number = #{department_number} "
			+ "</if>"
			+ "<if test=\"sort != null and sort != ''\">"
			+ ",sort = #{sort} "
			+ "</if>"
			+ "<if test=\"use_yn != null and use_yn != ''\">"
			+ ",use_yn = #{use_yn} "
			+ "</if>"
			+ "<if test=\"online_reserve_yn != null and online_reserve_yn != ''\">"
			+ ",online_reserve_yn = #{online_reserve_yn} "
			+ "</if>"
			+ "<if test=\"manless_reserve_yn != null and manless_reserve_yn != ''\">"
			+ ",manless_reserve_yn = #{manless_reserve_yn} "
			+ "</if>"
			+ "<if test=\"modify_id != null and modify_id != ''\">"
			+ ",modify_date = (convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)) "
			+ ",modify_id = #{modify_id} "
			+ "</if>"
			+ "WHERE 0=0 "
			+ "AND department_idx = #{department_idx}"
			+ "</script>")
	public void updateDepartment(CrmDepartmentVO vo) throws Exception;
	
	@Update("<script>"
			+ "UPDATE crm_department SET "
			+ "receipt_default_yn = 'N' "
			+ "WHERE 0=0 "
			+ "</script>")
	public void updateDepartmentReceiptDefaultYnReset() throws Exception;
	
	@Update("<script>"
			+ "UPDATE crm_department SET "
			+ "receipt_default_yn = 'Y' "
			+ "WHERE 0=0 "
			+ "AND department_idx = #{department_idx}"
			+ "</script>")
	public void updateDepartmentReceiptDefaultYn(CrmDepartmentVO vo) throws Exception;
	
	@Delete("<script>"
			+ "UPDATE crm_department SET "
			+ " del_yn = 'Y', "
			+ " del_date = (convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)), "
			+ " del_id = #{del_id}"
			+ "WHERE 0=0  "
			+ "AND department_idx=#{department_idx} "
			+ "</script>")
	public void deleteDepartment(CrmDepartmentVO vo) throws Exception;
}
