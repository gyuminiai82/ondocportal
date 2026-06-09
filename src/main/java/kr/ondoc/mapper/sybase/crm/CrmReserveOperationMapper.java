package kr.ondoc.mapper.sybase.crm;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.ondoc.domain.sybase.crm.CrmReserveOperationVO;

public interface CrmReserveOperationMapper {
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT * from crm_revpt AS cr "
			+ "LEFT OUTER JOIN crm_counsel AS cc "
			+ "ON cr.con_seq=cc.con_seq WHERE 0=0 "
			+ "]]>"
			+ "<if test=\"rev_date != null and rev_date != ''\">"
			+ "AND rev_date = #{rev_date} "
			+ "</if>"
			+ "AND (cr.rev_cancel_uid is null OR cr.rev_cancel_uid = '') "
			+ "ORDER BY rev_date ASC, rev_time_start ASC, rev_time_end ASC "
			+ "</script>")
	public List<CrmReserveOperationVO> selectReserveOperation(CrmReserveOperationVO vo) throws Exception;
	
	@Insert("<script>"
			+ "INSERT INTO crm_revpt ("
			+ "rev_date, rev_ptno, rev_time_start, rev_time_end, rev_name, "
			+ "rev_birth, rev_age, rev_sex, rev_hpno, rev_telno, "
			+ "rev_email, rev_revchk, rev_opcode, rev_in_uid, "
			+ "rev_sms, rev_reserved_id, con_seq) "
			+ "VALUES ( "
			+ "#{rev_date}, #{rev_ptno}, #{rev_time_start}, #{rev_time_end}, #{rev_name}, "
			+ "#{rev_birth}, #{rev_age}, #{rev_sex}, #{rev_hpno}, #{rev_telno},"
			+ "#{rev_email}, #{rev_revchk}, #{rev_opcode}, #{rev_in_uid},"
			+ "#{rev_sms}, #{rev_reserved_id}, #{con_seq})"
			+ "</script>")
	public void insertReserveOperation(CrmReserveOperationVO vo) throws Exception;
	
	@Update("<script>"
			+ "UPDATE crm_revpt SET "
			+ "rev_seq = rev_seq "
			+ "<if test=\"rev_date != null and rev_date != ''\">"
			+ ",rev_date = #{rev_date} "
			+ "</if>"
			+ "<if test=\"rev_ptno != null and rev_ptno != ''\">"
			+ ",rev_ptno = #{rev_ptno} "
			+ "</if>"
			+ "<if test=\"rev_time_start != null and rev_time_start != ''\">"
			+ ",rev_time_start = #{rev_time_start} "
			+ "</if>"
			+ "<if test=\"rev_time_end != null and rev_time_end != ''\">"
			+ ",rev_time_end = #{rev_time_end} "
			+ "</if>"
			+ "<if test=\"rev_name != null and rev_name != ''\">"
			+ ",rev_names = #{rev_name} "
			+ "</if>"
			+ "<if test=\"rev_birth != null and rev_birth != ''\">"
			+ ",rev_birth = #{rev_birth} "
			+ "</if>"
			+ "<if test=\"rev_age != null and rev_age != ''\">"
			+ ",rev_age = #{rev_age} "
			+ "</if>"
			+ "<if test=\"rev_sex != null and rev_sex != ''\">"
			+ ",rev_sex = #{rev_sex} "
			+ "</if>"
			+ "<if test=\"rev_hpno != null and rev_hpno != ''\">"
			+ ",rev_hpno = #{rev_hpno} "
			+ "</if>"
			+ "<if test=\"rev_telno != null and rev_telno != ''\">"
			+ ",rev_telno = #{rev_telno} "
			+ "</if>"
			+ "<if test=\"rev_email != null and rev_email != ''\">"
			+ ",rev_email = #{rev_email} "
			+ "</if>"
			+ "<if test=\"rev_revchk != null and rev_revchk != ''\">"
			+ ",rev_revchk = #{rev_revchk} "
			+ "</if>"
			+ "<if test=\"rev_opcode != null and rev_opcode != ''\">"
			+ ",rev_opcode = #{rev_opcode} "
			+ "</if>"
			+ "<if test=\"rev_up_uid != null and rev_up_uid != ''\">"
			+ ",rev_up_uid = #{rev_up_uid} "
			+ "</if>"
			+ "<if test=\"rev_up_uid != null and rev_up_uid != ''\">"
			+ ",rev_up_date = GETDATE() "
			+ "</if>"
			+ "<if test=\"rev_cancel_uid != null and rev_cancel_uid != ''\">"
			+ ",rev_cancel_uid = #{rev_cancel_uid} "
			+ "</if>"
			+ "<if test=\"rev_cancel_uid != null and rev_cancel_uid != ''\">"
			+ ",rev_cancel_date = GETDATE() "
			+ "</if>"
			+ "<if test=\"rev_sms != null and rev_sms != ''\">"
			+ ",rev_sms = #{rev_sms} "
			+ "</if>"
			+ "<if test=\"rev_reserved_id != null and rev_reserved_id != ''\">"
			+ ",rev_reserved_id = #{rev_reserved_id} "
			+ "</if>"
			+ "WHERE 0=0 "
			+ "AND rev_seq = #{rev_seq}"
			+ "</script>")
	public void updateReserveOperation(CrmReserveOperationVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT * from crm_revpt AS cr "
			+ "LEFT OUTER JOIN crm_counsel AS cc "
			+ "ON cr.con_seq=cc.con_seq WHERE 0=0 "
			+ "]]>"
			+ "<if test=\"seqs != null and seqs != ''\">"
			+ "AND cr.con_seq in (${seqs}) "
			+ "</if>"
			+ "AND (cr.rev_cancel_uid is null OR cr.rev_cancel_uid = '') "
			+ "ORDER BY rev_date ASC, rev_time_start ASC, rev_time_end ASC "
			+ "</script>")
	public List<CrmReserveOperationVO> selectCounselRelationReserveOperation(String seqs) throws Exception;
}
