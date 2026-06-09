package kr.ondoc.mapper.sybase.crm;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.ondoc.domain.sybase.crm.CrmTreatementItemSearchVO;
import kr.ondoc.domain.sybase.crm.CrmTreatementItemVO;

public interface CrmTreatementItemMapper {
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT *, (select count(*) from crm_treatement_item_detail WHERE treatement_item_idx=cti.treatement_item_idx) as sub_cnt from crm_treatement_item AS cti WHERE 0=0 "
			+ "]]>"
			+ "<if test=\"use_yn != null and use_yn != ''\">"
			+ "AND cti.use_yn = #{use_yn} "
			+ "</if>"
			+ "<if test=\"del_yn != null and del_yn != ''\">"
			+ "AND cti.del_yn = #{del_yn} "
			+ "</if>"
			+ "ORDER BY cti.sort ASC "
			+ "</script>")
	public List<CrmTreatementItemVO> selectListTreatementItem(CrmTreatementItemVO vo) throws Exception;
	
	@Insert("<script>"
			+ "INSERT INTO crm_treatement_item ("
			+ "code, name, sort, use_yn, write_date, write_id) "
			+ "VALUES ( "
			+ "#{code}, #{name}, "
			+ "ISNULL((SELECT MAX(sort) FROM crm_treatement_item), 0)+1, #{use_yn}, "
			+ "(convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)), "
			+ "#{write_id})"
			+ "</script>")
	public void insertTreatementItem(CrmTreatementItemVO vo) throws Exception;
	
	@Update("<script>"
			+ "UPDATE crm_treatement_item SET "
			+ "treatement_item_idx = treatement_item_idx "
			+ "<if test=\"code != null and code != ''\">"
			+ ",code = #{code} "
			+ "</if>"
			+ "<if test=\"name != null and name != ''\">"
			+ ",name = #{name} "
			+ "</if>"
			+ "<if test=\"sort != null and sort != ''\">"
			+ ",sort = #{sort} "
			+ "</if>"
			+ "<if test=\"use_yn != null and use_yn != ''\">"
			+ ",use_yn = #{use_yn} "
			+ "</if>"
			+ "<if test=\"modify_id != null and modify_id != ''\">"
			+ ",modify_date = (convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)) "
			+ ",modify_id = #{modify_id} "
			+ "</if>"
			+ "WHERE 0=0 "
			+ "AND treatement_item_idx = #{treatement_item_idx}"
			+ "</script>")
	public void updateTreatementItem(CrmTreatementItemVO vo) throws Exception;
	
	@Delete("<script>"
			+ "UPDATE crm_treatement_item SET "
			+ " del_yn = 'Y', "
			+ " del_date = (convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)), "
			+ " del_id = #{del_id}"
			+ "WHERE 0=0  "
			+ "AND treatement_item_idx=#{treatement_item_idx} "
			+ "</script>")
	public void deleteTreatementItem(CrmTreatementItemVO vo) throws Exception;
	
	
	@Select("<script>"
			+ "SELECT * FROM ( "
			+ "	SELECT cti.treatement_item_idx AS item_idx, cti.name AS item_name, ctid.treatement_item_detail_idx AS item_detail_idx, "
			+ " ctid.name AS item_detail_name, ctid.price AS price, ctid.sale_price AS sale_price, ctid.item_count AS item_count, cti.sort AS sort, ctid.sort AS detail_sort FROM  "
			+ "	(SELECT * FROM crm_treatement_item WHERE del_yn='N') AS cti  "
			+ "	LEFT OUTER JOIN crm_treatement_item_detail AS ctid  "
			+ "	ON cti.treatement_item_idx=ctid.treatement_item_idx AND ctid.del_yn='N' "
			+ ") AS temp "
			+ "WHERE item_detail_name IS NOT NULL AND item_name LIKE '%${name}%' AND item_detail_name LIKE '%${name}%' "
			+ "ORDER BY sort ASC, detail_sort ASC "
			+ "</script>")
	public List<CrmTreatementItemSearchVO> selectListSearchTreatementItem(String name) throws Exception;
}
