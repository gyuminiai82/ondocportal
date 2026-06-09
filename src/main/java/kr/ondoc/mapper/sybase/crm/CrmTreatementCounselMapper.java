package kr.ondoc.mapper.sybase.crm;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.ondoc.domain.sybase.crm.CrmTreatementCounselVO;

public interface CrmTreatementCounselMapper {
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT * FROM crm_treatement_counsel WHERE 0=0 "
			+ "]]>"
			+ "<if test=\"ptno != null and ptno != ''\">"
			+ "AND ptno = #{ptno} "
			+ "</if>"
			+ "<if test=\"counsel_date != null and counsel_date != ''\">"
			+ "AND counsel_date = #{counsel_date} "
			+ "</if>"
			+ "<if test=\"del_yn != null and del_yn != ''\">"
			+ "AND del_yn = #{del_yn} "
			+ "</if>"
			+ "ORDER BY counsel_date DESC, counsel_idx DESC"
			+ "</script>")
	public List<CrmTreatementCounselVO> selectListCounsel(CrmTreatementCounselVO vo) throws Exception;
	
	@Select("<script>"
			+ "SELECT MAX(counsel_idx) FROM crm_treatement_counsel"
			+ "</script>")
	public String selectMaxIdx() throws Exception;
	
	@Insert("<script>"
			+ "INSERT INTO crm_treatement_counsel ("
			+ "operation_schedule_idx, counsel_date, ptno, receptionist_id, counselor_id, therapist_id, doctor_id, "
			+ "counsel_memo, item_arr, "
			+ "write_date, "
			+ "write_id) "
			+ "VALUES ( "
			+ "#{operation_schedule_idx}, #{counsel_date}, #{ptno}, #{receptionist_id}, #{counselor_id}, #{therapist_id}, #{doctor_id}, "
			+ "#{counsel_memo}, #{item_arr},  "
			+ "(convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)), "
			+ "#{write_id})"
			+ "</script>")
	public void insertCounsel(CrmTreatementCounselVO vo) throws Exception;
	
	@Update("<script>"
			+ "UPDATE crm_treatement_counsel SET "
			+ "counsel_idx = counsel_idx "
			+ "<if test=\"counsel_date != null and counsel_date != ''\">"
			+ ",counsel_date = #{counsel_date} "
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
			+ "<if test=\"therapist_id != null and therapist_id != ''\">"
			+ ",therapist_id = #{therapist_id} "
			+ "</if>"
			+ "<if test=\"doctor_id != null and doctor_id != ''\">"
			+ ",doctor_id = #{doctor_id} "
			+ "</if>"
			+ "<if test=\"counsel_memo != null and counsel_memo != ''\">"
			+ ",counsel_memo = #{counsel_memo} "
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
			+ "AND counsel_idx = #{counsel_idx}"
			+ "</script>")
	public void updateCounsel(CrmTreatementCounselVO vo) throws Exception;
	
	@Delete("<script>"
			+ "UPDATE crm_treatement_counsel SET "
			+ " del_yn = 'Y', "
			+ " del_date = (convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)), "
			+ " del_id = #{del_id}"
			+ "WHERE 0=0  "
			+ "AND counsel_idx=#{counsel_idx} "
			+ "</script>")
	public void deleteCounsel(CrmTreatementCounselVO vo) throws Exception;
}
