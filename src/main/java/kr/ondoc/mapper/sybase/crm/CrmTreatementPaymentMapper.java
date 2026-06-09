package kr.ondoc.mapper.sybase.crm;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.ondoc.domain.sybase.crm.CrmMemoVO;
import kr.ondoc.domain.sybase.crm.CrmTreatementPaymentVO;

public interface CrmTreatementPaymentMapper {
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT * FROM crm_treatement_payment WHERE 0=0 "
			+ "]]>"
			+ "AND payment_idx = #{payment_idx} "
			+ "</script>")
	public CrmTreatementPaymentVO selectPayment(String payment_idx) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT * FROM crm_treatement_payment WHERE 0=0 "
			+ "]]>"
			+ "<if test=\"ptno != null and ptno != ''\">"
			+ "AND ptno = #{ptno} "
			+ "</if>"
			+ "<if test=\"payment_date != null and payment_date != ''\">"
			+ "AND payment_date = #{payment_date} "
			+ "</if>"
			+ "<if test=\"del_yn != null and del_yn != ''\">"
			+ "AND del_yn = #{del_yn} "
			+ "</if>"
			+ "ORDER BY payment_date DESC, payment_idx DESC"
			+ "</script>")
	public List<CrmTreatementPaymentVO> selectListPayment(CrmTreatementPaymentVO vo) throws Exception;

	@Select("<script>"
			+ "SELECT MAX(payment_idx) FROM crm_treatement_payment"
			+ "</script>")
	public String selectMaxIdx() throws Exception;
	
	@Insert("<script>"
			+ "INSERT INTO crm_treatement_payment ("
			+ "operation_schedule_idx, payment_date, ptno, charge_price, sale_price, sale_after_price, "
			+ "payment_price, unpaid_price, card_price, card_gubun, card_installment_period, "
			+ "cash_price, cash_transfer_price, cash_receipt_price, cash_receipt_yn, payment_memo, status, "
			+ "write_date, "
			+ "write_id) "
			+ "VALUES ( "
			+ "#{operation_schedule_idx}, #{payment_date}, #{ptno}, #{charge_price}, #{sale_price}, #{sale_after_price}, "
			+ "#{payment_price}, #{unpaid_price}, #{card_price}, #{card_gubun}, #{card_installment_period}, "
			+ "#{cash_price}, #{cash_transfer_price}, #{cash_receipt_price}, #{cash_receipt_yn}, #{payment_memo}, #{status}, "
			+ "(convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)), "
			+ "#{write_id})"
			+ "</script>")
	public void insertPayment(CrmTreatementPaymentVO vo) throws Exception;
	
	@Update("<script>"
			+ "UPDATE crm_treatement_payment SET "
			+ "payment_idx = payment_idx "
			+ "<if test=\"payment_date != null and payment_date != ''\">"
			+ ",payment_date = #{payment_date} "
			+ "</if>"
			+ "<if test=\"ptno != null and ptno != ''\">"
			+ ",ptno = #{ptno} "
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
			+ "<if test=\"payment_price != null and payment_price != ''\">"
			+ ",payment_price = #{payment_price} "
			+ "</if>"
			+ "<if test=\"unpaid_price != null and unpaid_price != ''\">"
			+ ",unpaid_price = #{unpaid_price} "
			+ "</if>"
			+ "<if test=\"unpaid_payment_price != null and unpaid_payment_price != ''\">"
			+ ",unpaid_payment_price = #{unpaid_payment_price} "
			+ "</if>"
			+ "<if test=\"card_price != null and card_price != ''\">"
			+ ",card_price = #{card_price} "
			+ "</if>"
			+ "<if test=\"card_gubun != null and card_gubun != ''\">"
			+ ",card_gubun = #{card_gubun} "
			+ "</if>"
			+ "<if test=\"card_installment_period != null and card_installment_period != ''\">"
			+ ",card_installment_period = #{card_installment_period} "
			+ "</if>"
			+ "<if test=\"cash_price != null and cash_price != ''\">"
			+ ",cash_price = #{cash_price} "
			+ "</if>"
			+ "<if test=\"cash_transfer_price != null and cash_transfer_price != ''\">"
			+ ",cash_transfer_price = #{cash_transfer_price} "
			+ "</if>"
			+ "<if test=\"cash_receipt_price != null and cash_receipt_price != ''\">"
			+ ",cash_receipt_price = #{cash_receipt_price} "
			+ "</if>"
			+ "<if test=\"cash_receipt_yn != null and cash_receipt_yn != ''\">"
			+ ",cash_receipt_yn = #{cash_receipt_yn} "
			+ "</if>"
			+ "<if test=\"payment_memo != null and payment_memo != ''\">"
			+ ",payment_memo = #{payment_memo} "
			+ "</if>"
			+ "<if test=\"status != null and status != ''\">"
			+ ",status = #{status} "
			+ "</if>"
			+ "<if test=\"del_yn != null and del_yn != ''\">"
			+ ",del_yn = #{del_yn} "
			+ "</if>"
			+ "<if test=\"modify_id != null and modify_id != ''\">"
			+ ",modify_date = (convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)) "
			+ ",modify_id = #{modify_id} "
			+ "</if>"
			+ "WHERE 0=0 "
			+ "AND payment_idx = #{payment_idx}"
			+ "</script>")
	public void updatePayment(CrmTreatementPaymentVO vo) throws Exception;
	
	@Delete("<script>"
			+ "UPDATE crm_treatement_payment SET "
			+ " del_yn = 'Y', "
			+ " del_date = (convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)), "
			+ " del_id = #{del_id}"
			+ "WHERE 0=0  "
			+ "AND payment_idx=#{payment_idx} "
			+ "</script>")
	public void deletePayment(CrmTreatementPaymentVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT * FROM crm_treatement_payment AS ctp "
			+ "LEFT OUTER JOIN patient AS p ON ctp.ptno = p.bpt_ptno WHERE 0=0 "
			+ "]]>"
			+ "AND unpaid_price > 0 "
			+ "<if test=\"ptno != null and ptno != ''\">"
			+ "AND ptno = #{ptno} "
			+ "</if>"
			+ "<if test=\"del_yn != null and del_yn != ''\">"
			+ "AND del_yn = #{del_yn} "
			+ "</if>"
			+ "ORDER BY payment_date DESC, payment_idx DESC"
			+ "</script>")
	public List<CrmTreatementPaymentVO> selectListPaymentUnpaidCheck(CrmTreatementPaymentVO vo) throws Exception;
//	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT TOP ${listNum} "
			+ "* FROM "
			+ "( "
			+ "SELECT *, ROW_NUMBER( ) OVER ( ORDER BY ${sort} ${sortDirection} ) AS RowNum "
			+ "FROM (SELECT * FROM crm_treatement_payment AS ctp "
			+ "LEFT OUTER JOIN patient AS p ON ctp.ptno = p.bpt_ptno "
			+ "WHERE 0=0 AND unpaid_price > 0 "
			+ "]]>"
			+ "<if test=\"search_word != null and search_word != '' and search_type == 'ptno'\">"
			+ "AND ptno = #{search_word} "
			+ "</if>"
			+ "<if test=\"search_word != null and search_word != '' and search_type == 'name'\">"
			+ "AND bpt_name = #{search_word} "
			+ "</if>"
			+ "<if test=\"del_yn != null and del_yn != ''\">"
			+ "AND del_yn = #{del_yn} "
			+ "</if>"
			+ ") AS temp) AS t "
			+ "<![CDATA["
			+ " WHERE RowNum>=${first}"
			+ "]]>"
			+ "</script>")
	public List<CrmTreatementPaymentVO> selectListPaymentUnpaid(CrmTreatementPaymentVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT COUNT(*) "
			+ "]]>"
			+ "FROM (SELECT * FROM crm_treatement_payment AS ctp "
			+ "LEFT OUTER JOIN patient AS p ON ctp.ptno = p.bpt_ptno "
			+ "WHERE 0=0 "
			+ "AND unpaid_price > 0 "
			+ "<if test=\"search_word != null and search_word != '' and search_type == 'ptno'\">"
			+ "AND ptno = #{search_word} "
			+ "</if>"
			+ "<if test=\"search_word != null and search_word != '' and search_type == 'name'\">"
			+ "AND bpt_name = #{search_word} "
			+ "</if>"
			+ "<if test=\"del_yn != null and del_yn != ''\">"
			+ "AND del_yn = #{del_yn} "
			+ "</if>"
			+ ") AS temp"
			+ "</script>")
	public int countPaymentUnpaid(CrmTreatementPaymentVO vo) throws Exception;
}
