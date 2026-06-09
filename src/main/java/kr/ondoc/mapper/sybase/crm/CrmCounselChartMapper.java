package kr.ondoc.mapper.sybase.crm;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.ondoc.domain.sybase.crm.CrmCounselChartParamVO;
import kr.ondoc.domain.sybase.crm.CrmCounselChartVO;
import kr.ondoc.domain.sybase.crm.CrmDoctorVO;
import kr.ondoc.domain.sybase.crm.CrmUsrmngrVO;

public interface CrmCounselChartMapper {
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT * from crm_counsel WHERE 0=0 "
			+ "]]>"
			+ "<if test=\"con_date != null and con_date != ''\">"
			+ "AND con_date = #{con_date} "
			+ "</if>"
			+ "ORDER BY con_date ASC, con_time ASC, con_seq ASC "
			+ "</script>")
	public List<CrmCounselChartVO> selectCounsel(CrmCounselChartParamVO vo) throws Exception;
	
	@Insert("<script>"
			+ "INSERT INTO crm_counsel ("
			+ "con_date, con_time, con_no, con_ptno, con_doctor, "
			+ "con_manager, con_verifier, con_name, con_birth, con_age, con_sex, "
			+ "con_hpno, con_telno, con_memo, con_memo2, con_gubun, "
			+ "con_fieldkind, con_field, con_part, con_opcode, con_uid, con_int_date) "
			+ "VALUES ( "
			+ "#{con_date}, #{con_time}, #{con_no}, #{con_ptno}, #{con_doctor}, "
			+ "#{con_manager}, #{con_verifier}, #{con_name}, #{con_birth}, #{con_age}, #{con_sex},"
			+ "#{con_hpno}, #{con_telno}, #{con_memo}, #{con_memo2}, #{con_gubun},"
			+ "#{con_fieldkind}, #{con_field}, #{con_part}, #{con_opcode}, #{con_uid}, GETDATE())"
			+ "</script>")
	public void insertCounsel(CrmCounselChartParamVO vo) throws Exception;
	
	@Update("<script>"
			+ "UPDATE crm_counsel SET "
			+ "con_seq = con_seq "
			+ "<if test=\"con_date != null and con_date != ''\">"
			+ ",con_date = #{con_date} "
			+ "</if>"
			+ "<if test=\"con_time != null and con_time != ''\">"
			+ ",con_time = #{con_time} "
			+ "</if>"
			+ "<if test=\"con_no != null and con_no != ''\">"
			+ ",con_no = #{con_no} "
			+ "</if>"
			+ "<if test=\"con_ptno != null and con_ptno != ''\">"
			+ ",con_ptno = #{con_ptno} "
			+ "</if>"
			+ "<if test=\"con_doctor != null and con_doctor != ''\">"
			+ ",con_doctor = #{con_doctor} "
			+ "</if>"
			+ "<if test=\"con_manager != null and con_manager != ''\">"
			+ ",con_manager = #{con_manager} "
			+ "</if>"
			+ "<if test=\"con_verifier != null and con_verifier != ''\">"
			+ ",con_verifier = #{con_verifier} "
			+ "</if>"
			+ "<if test=\"con_name != null and con_name != ''\">"
			+ ",con_name = #{con_name} "
			+ "</if>"
			+ "<if test=\"con_birth != null and con_birth != ''\">"
			+ ",con_birth = #{con_birth} "
			+ "</if>"
			+ "<if test=\"con_age != null and con_age != ''\">"
			+ ",con_age = #{con_age} "
			+ "</if>"
			+ "<if test=\"con_sex != null and con_sex != ''\">"
			+ ",con_sex = #{con_sex} "
			+ "</if>"
			+ "<if test=\"con_hpno != null and con_hpno != ''\">"
			+ ",con_hpno = #{con_hpno} "
			+ "</if>"
			+ "<if test=\"con_telno != null and con_telno != ''\">"
			+ ",con_telno = #{con_telno} "
			+ "</if>"
			+ "<if test=\"con_memo != null and con_memo != ''\">"
			+ ",con_memo = #{con_memo} "
			+ "</if>"
			+ "<if test=\"con_memo2 != null and con_memo2 != ''\">"
			+ ",con_memo2 = #{con_memo2} "
			+ "</if>"
			+ "<if test=\"con_gubun != null and con_gubun != ''\">"
			+ ",con_gubun = #{con_gubun} "
			+ "</if>"
			+ "<if test=\"con_fieldkind != null and con_fieldkind != ''\">"
			+ ",con_fieldkind = #{con_fieldkind} "
			+ "</if>"
			+ "<if test=\"con_field != null and con_field != ''\">"
			+ ",con_field = #{con_field} "
			+ "</if>"
			+ "<if test=\"con_part != null and con_part != ''\">"
			+ ",con_part = #{con_part} "
			+ "</if>"
			+ "<if test=\"con_opcode != null and con_opcode != ''\">"
			+ ",con_opcode = #{con_opcode} "
			+ "</if>"
			+ "<if test=\"con_up_uid != null and con_up_uid != ''\">"
			+ ",con_up_uid = #{con_up_uid} "
			+ "</if>"
			+ "<if test=\"con_up_uid != null and con_up_uid != ''\">"
			+ ",con_up_date = GETDATE() "
			+ "</if>"
			+ "<if test=\"con_cancel_uid != null and con_cancel_uid != ''\">"
			+ ",con_cancel_uid = #{con_cancel_uid} "
			+ "</if>"
			+ "<if test=\"con_cancel_uid != null and con_cancel_uid != ''\">"
			+ ",con_cancel_date = GETDATE() "
			+ "</if>"
			+ "<if test=\"con_cancel_memo != null and con_cancel_memo != ''\">"
			+ ",con_cancel_memo = #{con_cancel_memo} "
			+ "</if>"
			+ "WHERE 0=0 "
			+ "AND con_seq = #{con_seq}"
			+ "</script>")
	public void updateCounsel(CrmCounselChartParamVO vo) throws Exception;
	
}