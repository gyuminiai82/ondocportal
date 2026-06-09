package kr.ondoc.mapper.sybase.crm;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import kr.ondoc.domain.sybase.crm.CrmTreatementCounselItemRelationVO;

public interface CrmTreatementCounselItemRelationMapper {
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT * FROM crm_treatement_counsel_item_relation WHERE 0=0 "
			+ "]]>"
			+ "<if test=\"counsel_idx != null and counsel_idx != ''\">"
			+ "AND counsel_idx = #{counsel_idx} "
			+ "</if>"
			+ "</script>")
	public List<CrmTreatementCounselItemRelationVO> selectListTreatementCounselItemRelation(CrmTreatementCounselItemRelationVO vo) throws Exception;
	
	@Insert("<script>"
			+ "INSERT INTO crm_treatement_counsel_item_relation ("
			+ "counsel_idx, item_idx, item_detail_idx"
			+ ") VALUES ( "
			+ "#{counsel_idx}, #{item_idx}, #{item_detail_idx})"
			+ "</script>")
	public void insertTreatementCounselItemRelation(CrmTreatementCounselItemRelationVO vo) throws Exception;
	
	@Delete("<script>"
			+ "DELETE from crm_treatement_counsel_item_relation "
			+ "WHERE 0=0  "
			+ "AND counsel_idx=#{counsel_idx} "
			+ "</script>")
	public void deleteTreatementCounselItemRelation(String counsel_idx) throws Exception;
}
