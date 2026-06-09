package kr.ondoc.mapper.sybase.crm;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.ondoc.domain.sybase.crm.CrmTreatementItemRelationVO;
import kr.ondoc.domain.sybase.crm.CrmTreatementVO;

public interface CrmTreatementMapper {
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT * FROM crm_treatement WHERE 0=0 "
			+ "]]>"
			+ "<if test=\"treatement_idx != null and treatement_idx != ''\">"
			+ "AND treatement_idx = #{treatement_idx} "
			+ "</if>"
			+ "<if test=\"ptno != null and ptno != ''\">"
			+ "AND ptno = #{ptno} "
			+ "</if>"
			+ "<if test=\"treatement_date != null and treatement_date != ''\">"
			+ "AND treatement_date = #{treatement_date} "
			+ "</if>"
			+ "<if test=\"del_yn != null and del_yn != ''\">"
			+ "AND del_yn = #{del_yn} "
			+ "</if>"
			+ "ORDER BY treatement_date DESC, treatement_idx DESC"
			+ "</script>")
	public List<CrmTreatementVO> selectListTreatement(CrmTreatementVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT cti.name AS item_name, ctid.name AS item_detail_name, * FROM crm_treatement AS ct "
			+ "INNER JOIN crm_treatement_item_relation AS ctir "
			+ "ON ct.treatement_idx=ctir.treatement_idx AND ct.ptno=#{ptno} "
			+ "INNER JOIN crm_treatement_item AS cti "
			+ "ON ctir.item_idx=cti.treatement_item_idx "
			+ "INNER JOIN crm_treatement_item_detail AS ctid "
			+ "ON ctir.item_detail_idx=ctid.treatement_item_detail_idx "
			+ "AND ctir.payment_detail_idx=0 "
			+ "]]>"
			+ "AND ct.del_yn = 'N' "
			+ ""
			+ "</script>")
	public List<CrmTreatementItemRelationVO> selectListTreatementPaymentItem(String ptno) throws Exception;
	
	@Select("<script>"
			+ "SELECT MAX(treatement_idx) FROM crm_treatement"
			+ "</script>")
	public String selectMaxIdx() throws Exception;
	
	@Insert("<script>"
			+ "INSERT INTO crm_treatement ("
			+ "operation_schedule_idx, treatement_date, ptno, receptionist_id, counselor_id, therapist_id, doctor_id, "
			+ "treatement_memo, item_arr, "
			+ "write_date, "
			+ "write_id) "
			+ "VALUES ( "
			+ "#{operation_schedule_idx}, #{treatement_date}, #{ptno}, #{receptionist_id}, #{counselor_id}, #{therapist_id}, #{doctor_id}, "
			+ "#{treatement_memo}, #{item_arr},  "
			+ "(convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)), "
			+ "#{write_id})"
			+ "</script>")
	public void insertTreatement(CrmTreatementVO vo) throws Exception;
	
	@Update("<script>"
			+ "UPDATE crm_treatement SET "
			+ "treatement_idx = treatement_idx "
			+ "<if test=\"treatement_date != null and treatement_date != ''\">"
			+ ",treatement_date = #{treatement_date} "
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
			+ "<if test=\"treatement_memo != null and treatement_memo != ''\">"
			+ ",treatement_memo = #{treatement_memo} "
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
			+ "AND treatement_idx = #{treatement_idx}"
			+ "</script>")
	public void updateTreatement(CrmTreatementVO vo) throws Exception;
	
	@Delete("<script>"
			+ "UPDATE crm_treatement SET "
			+ " del_yn = 'Y', "
			+ " del_date = (convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)), "
			+ " del_id = #{del_id}"
			+ "WHERE 0=0  "
			+ "AND treatement_idx=#{treatement_idx} "
			+ "</script>")
	public void deleteTreatement(CrmTreatementVO vo) throws Exception;
}
