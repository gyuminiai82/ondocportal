package kr.ondoc.mapper.sybase.crm;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.ondoc.domain.sybase.crm.CrmTreatementPaymentUnpaidLogVO;
import kr.ondoc.domain.sybase.crm.CrmTreatementPaymentVO;

public interface CrmTreatementPaymentUnpaidLogMapper {
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT COUNT(*) "
			+ "]]>"
			+ "FROM (SELECT * FROM crm_treatement_payment_unpaid_log AS ctpul "
			+ "LEFT OUTER JOIN crm_treatement_payment AS ctp ON ctpul.payment_idx = ctp.payment_idx "
			+ "LEFT OUTER JOIN patient AS p ON ctp.ptno=p.bpt_ptno "
			+ "WHERE 0=0 "
			+ "<if test=\"search_word != null and search_word != '' and search_type == 'ptno'\">"
			+ "AND bpt_ptno = #{search_word} "
			+ "</if>"
			+ "<if test=\"search_word != null and search_word != '' and search_type == 'name'\">"
			+ "AND bpt_name = #{search_word} "
			+ "</if>"
			+ "<if test=\"del_yn != null and del_yn != ''\">"
			+ "AND ctpul.del_yn = #{del_yn} "
			+ "</if>"
			+ ") AS temp"
			+ "</script>")
	public int countPaymentUnpaidLog(CrmTreatementPaymentUnpaidLogVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT TOP ${listNum} "
			+ "* FROM "
			+ "( "
			+ "SELECT *, ROW_NUMBER( ) OVER ( ORDER BY ${sort} ${sortDirection} ) AS RowNum "
			+ "FROM (SELECT  ctpul.*, p.* FROM crm_treatement_payment_unpaid_log AS ctpul "
			+ "LEFT OUTER JOIN crm_treatement_payment AS ctp ON ctpul.payment_idx = ctp.payment_idx "
			+ "LEFT OUTER JOIN patient AS p ON ctp.ptno=p.bpt_ptno "
			+ "WHERE 0=0 "
			+ "]]>"
			+ "<if test=\"search_word != null and search_word != '' and search_type == 'ptno'\">"
			+ "AND bpt_ptno = #{search_word} "
			+ "</if>"
			+ "<if test=\"search_word != null and search_word != '' and search_type == 'name'\">"
			+ "AND bpt_name = #{search_word} "
			+ "</if>"
			+ "<if test=\"del_yn != null and del_yn != ''\">"
			+ "AND ctpul.del_yn = #{del_yn} "
			+ "</if>"
			+ ") AS temp) AS t "
			+ "<![CDATA["
			+ " WHERE RowNum>=${first}"
			+ "]]>"
			+ "</script>")
	public List<CrmTreatementPaymentUnpaidLogVO> selectListTreatementPaymentUnpaidLog(CrmTreatementPaymentUnpaidLogVO vo) throws Exception;

	@Insert("<script>"
			+ "INSERT INTO crm_treatement_payment_unpaid_log ("
			+ "payment_idx, payment_price, payment_type, card_gubun, "
			+ "write_date, "
			+ "write_id) "
			+ "VALUES ( "
			+ "#{payment_idx}, #{payment_price}, #{payment_type}, #{card_gubun}, "
			+ "(convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)), "
			+ "#{write_id})"
			+ "</script>")
	public void insertTreatementPaymentUnpaidLog(CrmTreatementPaymentUnpaidLogVO vo) throws Exception;
	
	@Update("<script>"
			+ "UPDATE crm_treatement_payment_unpaid_log SET "
			+ "log_idx = log_idx "
			+ "<if test=\"payment_price != null and payment_price != ''\">"
			+ ",payment_price = #{payment_price} "
			+ "</if>"
			+ "<if test=\"modify_id != null and modify_id != ''\">"
			+ ",modify_date = (convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)) "
			+ ",modify_id = #{modify_id} "
			+ "</if>"
			+ "WHERE 0=0 "
			+ "AND log_idx = #{log_idx}"
			+ "</script>")
	public void updateTreatementPaymentUnpaidLog(CrmTreatementPaymentUnpaidLogVO vo) throws Exception;
	
	@Delete("<script>"
			+ "UPDATE crm_treatement_payment_unpaid_log SET "
			+ " del_yn = 'Y', "
			+ " del_date = (convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)), "
			+ " del_id = #{del_id}"
			+ "WHERE 0=0  "
			+ "<if test=\"log_idx != null and log_idx != ''\">"
			+ "AND log_idx = #{log_idx} "
			+ "</if>"
			+ "<if test=\"payment_idx != null and payment_idx != ''\">"
			+ "AND payment_idx = #{payment_idx} "
			+ "</if>"
			+ "</script>")
	public void deleteTreatementPaymentUnpaidLog(CrmTreatementPaymentUnpaidLogVO vo) throws Exception;
}
