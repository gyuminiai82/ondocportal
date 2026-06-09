package kr.ondoc.mapper.sybase.crm;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.ondoc.domain.sybase.crm.CrmTreatementPaymentDetailVO;
import kr.ondoc.domain.sybase.crm.CrmTreatementPaymentVO;

public interface CrmTreatementPaymentDetailMapper {
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT * FROM crm_treatement_payment_detail WHERE 0=0 "
			+ "]]>"
			+ "<if test=\"ptno != null and ptno != ''\">"
			+ "AND ptno = #{ptno} "
			+ "</if>"
			+ "<if test=\"payment_idx != null and payment_idx != ''\">"
			+ "AND payment_idx = #{payment_idx} "
			+ "</if>"
			+ "<if test=\"del_yn != null and del_yn != ''\">"
			+ "AND del_yn = #{del_yn} "
			+ "</if>"
			+ "ORDER BY payment_date DESC, payment_detail_idx DESC"
			+ "</script>")
	public List<CrmTreatementPaymentDetailVO> selectListPaymentDetail(CrmTreatementPaymentDetailVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT * FROM crm_treatement_payment_detail WHERE 0=0 "
			+ "]]>"
			+ "<if test=\"ptno != null and ptno != ''\">"
			+ "AND ptno = #{ptno} "
			+ "</if>"
			+ "AND item_count - item_complete_count > 0 "
			+ "AND del_yn = 'N' "
			+ "ORDER BY payment_date DESC, payment_detail_idx DESC"
			+ "</script>")
	public List<CrmTreatementPaymentDetailVO> selectListPaymentDetailItem(CrmTreatementPaymentDetailVO vo) throws Exception;
	
	@Insert("<script>"
			+ "INSERT INTO crm_treatement_payment_detail ("
			+ "operation_schedule_idx, payment_idx, payment_date, ptno, "
			+ "receptionist_id, counselor_id, therapist_id, doctor_id, "
			+ "item_idx, item_name, item_detail_idx, item_name_detail, "
			+ "item_count, item_complete_count, tax_yn, "
			+ "charge_price, sale_price, sale_after_price, "
			+ "write_date, "
			+ "write_id) "
			+ "VALUES ( "
			+ "#{operation_schedule_idx}, #{payment_idx}, #{payment_date}, #{ptno}, "
			+ "#{receptionist_id}, #{counselor_id}, #{therapist_id}, #{doctor_id}, "
			+ "#{item_idx}, #{item_name}, #{item_detail_idx}, #{item_name_detail}, "
			+ "#{item_count}, #{item_complete_count}, #{tax_yn},"
			+ "#{charge_price}, #{sale_price}, #{sale_after_price},  "
			+ "(convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)), "
			+ "#{write_id})"
			+ "</script>")
	public void insertPaymentDetail(CrmTreatementPaymentDetailVO vo) throws Exception;
	
	@Select("<script>"
			+ "SELECT MAX(payment_detail_idx) FROM crm_treatement_payment_detail"
			+ "</script>")
	public String selectMaxIdx() throws Exception;

	@Update("<script>"
			+ "UPDATE crm_treatement_payment_detail SET "
			+ "payment_detail_idx = payment_detail_idx "
			+ "<if test=\"payment_idx != null and payment_idx != ''\">"
			+ ",payment_idx = #{payment_idx} "
			+ "</if>"
			+ "<if test=\"payment_date != null and payment_date != ''\">"
			+ ",payment_date = #{payment_date} "
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
			+ "<if test=\"item_idx != null and item_idx != ''\">"
			+ ",item_idx = #{item_idx} "
			+ "</if>"
			+ "<if test=\"item_name != null and item_name != ''\">"
			+ ",item_name = #{item_name} "
			+ "</if>"
			+ "<if test=\"item_detail_idx != null and item_detail_idx != ''\">"
			+ ",item_detail_idx = #{item_detail_idx} "
			+ "</if>"
			+ "<if test=\"item_name_detail != null and item_name_detail != ''\">"
			+ ",item_name_detail = #{item_name_detail} "
			+ "</if>"
			+ "<if test=\"item_count != null and item_count != ''\">"
			+ ",item_count = #{item_count} "
			+ "</if>"
			+ "<if test=\"item_complete_count != null and item_complete_count != ''\">"
			+ ",item_complete_count = #{item_complete_count} "
			+ "</if>"
			+ "<if test=\"tax_yn != null and tax_yn != ''\">"
			+ ",tax_yn = #{tax_yn} "
			+ "</if>"
			+ "<if test=\"charge_price != null and charge_price != ''\">"
			+ ",charge_price = #{charge_price} "
			+ "</if>"
			+ "<if test=\"sale_price != null and sale_price != ''\">"
			+ ",sale_price = #{sale_price} "
			+ "</if>"
			+ "<if test=\"sale_after_price != null and sale_after_price != ''\">"
			+ ",sale_after_price = #{sale_after_price} "
			+ "</if>"
			+ "<if test=\"del_yn != null and del_yn != ''\">"
			+ ",del_yn = #{del_yn} "
			+ "</if>"
			+ "<if test=\"modify_id != null and modify_id != ''\">"
			+ ",modify_date = (convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)) "
			+ ",modify_id = #{modify_id} "
			+ "</if>"
			+ "WHERE 0=0 "
			+ "AND payment_detail_idx = #{payment_detail_idx}"
			+ "</script>")
	public void updatePaymentDetail(CrmTreatementPaymentDetailVO vo) throws Exception;
	
	@Delete("<script>"
			+ "UPDATE crm_treatement_payment_detail SET "
			+ " del_yn = 'Y', "
			+ " del_date = (convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)), "
			+ " del_id = #{del_id} "
			+ "WHERE 0=0  "
			+ "<if test=\"payment_idx != null and payment_idx != ''\">"
			+ "AND payment_idx = #{payment_idx} "
			+ "</if>"
			+ "<if test=\"payment_detail_idx != null and payment_detail_idx != ''\">"
			+ "AND payment_detail_idx = #{payment_detail_idx} "
			+ "</if>"
			+ "</script>")
	public void deletePaymentDetail(CrmTreatementPaymentDetailVO vo) throws Exception;
	
	@Update("<script>"
			+ "UPDATE crm_treatement_payment_detail SET "
			+ "item_complete_count = item_complete_count + 1 "
			+ "WHERE 0=0 "
			+ "AND payment_detail_idx = #{payment_detail_idx}"
			+ "</script>")
	public void updatePaymentDetailCountUp(String payment_detail_idx) throws Exception;
	
	@Update("<script>"
			+ "UPDATE crm_treatement_payment_detail SET "
			+ "item_complete_count = item_complete_count - 1 "
			+ "WHERE 0=0 "
			+ "AND payment_detail_idx = #{payment_detail_idx}"
			+ "</script>")
	public void updatePaymentDetailCountDown(String payment_detail_idx) throws Exception;
}
