package kr.ondoc.mapper.sybase.crm;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.ondoc.domain.sybase.crm.CrmTreatementCounselItemRelationVO;
import kr.ondoc.domain.sybase.crm.CrmTreatementItemRelationVO;

public interface CrmTreatementItemRelationMapper {
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT * FROM crm_treatement_item_relation WHERE 0=0 "
			+ "]]>"
			+ "<if test=\"treatement_idx != null and treatement_idx != ''\">"
			+ "AND treatement_idx = #{treatement_idx} "
			+ "</if>"
			+ "</script>")
	public List<CrmTreatementItemRelationVO> selectListTreatementItemRelation(CrmTreatementItemRelationVO vo) throws Exception;
	
	@Insert("<script>"
			+ "INSERT INTO crm_treatement_item_relation ("
			+ "treatement_idx, item_idx, item_detail_idx, payment_detail_idx"
			+ ") VALUES ( "
			+ "#{treatement_idx}, #{item_idx}, #{item_detail_idx}, #{payment_detail_idx})"
			+ "</script>")
	public void insertTreatementItemRelation(CrmTreatementItemRelationVO vo) throws Exception;
	
	@Update("<script>"
			+ "UPDATE crm_treatement_item_relation "
			+ "SET payment_detail_idx = #{payment_detail_idx} "
			+ "WHERE treatement_item_idx=#{treatement_item_idx} "
			+ "</script>")
	public void updateTreatementItemPaymentIdx(String treatement_item_idx, String payment_detail_idx) throws Exception;
	
	@Delete("<script>"
			+ "DELETE from crm_treatement_item_relation "
			+ "WHERE 0=0  "
			+ "AND treatement_idx=#{treatement_idx} "
			+ "</script>")
	public void deleteTreatementItemRelation(String treatement_idx) throws Exception;
}
