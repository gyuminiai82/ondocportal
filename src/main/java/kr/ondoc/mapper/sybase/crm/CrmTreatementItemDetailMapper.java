package kr.ondoc.mapper.sybase.crm;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.ondoc.domain.sybase.crm.CrmTreatementItemDetailVO;

public interface CrmTreatementItemDetailMapper {
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT * from crm_treatement_item_detail WHERE 0=0 "
			+ "]]>"
			+ "<if test=\"treatement_item_idx != null and treatement_item_idx != ''\">"
			+ "AND treatement_item_idx = #{treatement_item_idx} "
			+ "</if>"
			+ "<if test=\"use_yn != null and use_yn != ''\">"
			+ "AND use_yn = #{use_yn} "
			+ "</if>"
			+ "<if test=\"del_yn != null and del_yn != ''\">"
			+ "AND del_yn = #{del_yn} "
			+ "</if>"
			+ "ORDER BY sort ASC "
			+ "</script>")
	public List<CrmTreatementItemDetailVO> selectListTreatementItemDetail(CrmTreatementItemDetailVO vo) throws Exception;
	
	@Insert("<script>"
			+ "INSERT INTO crm_treatement_item_detail ("
			+ "treatement_item_idx, code, name, price, sale_price, item_count, sort, use_yn, write_date, write_id) "
			+ "VALUES ( "
			+ "#{treatement_item_idx}, #{code}, #{name}, #{price}, #{sale_price}, #{item_count}, "
			+ "ISNULL((SELECT MAX(sort) FROM crm_treatement_item_detail), 0)+1, #{use_yn}, "
			+ "(convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)), "
			+ "#{write_id})"
			+ "</script>")
	public void insertTreatementItemDetail(CrmTreatementItemDetailVO vo) throws Exception;
	
	@Update("<script>"
			+ "UPDATE crm_treatement_item_detail SET "
			+ "treatement_item_detail_idx = treatement_item_detail_idx "
			+ "<if test=\"code != null and code != ''\">"
			+ ",code = #{code} "
			+ "</if>"
			+ "<if test=\"name != null and name != ''\">"
			+ ",name = #{name} "
			+ "</if>"
			+ "<if test=\"price != null and price != ''\">"
			+ ",price = #{price} "
			+ "</if>"
			+ "<if test=\"sale_price != null and sale_price != ''\">"
			+ ",sale_price = #{sale_price} "
			+ "</if>"
			+ "<if test=\"item_count != null and item_count != ''\">"
			+ ",item_count = #{item_count} "
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
			+ "AND treatement_item_detail_idx = #{treatement_item_detail_idx}"
			+ "</script>")
	public void updateTreatementItemDetail(CrmTreatementItemDetailVO vo) throws Exception;
	
	@Delete("<script>"
			+ "UPDATE crm_treatement_item_detail SET "
			+ " del_yn = 'Y', "
			+ " del_date = (convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)), "
			+ " del_id = #{del_id}"
			+ "WHERE 0=0  "
			+ "AND treatement_item_detail_idx=#{treatement_item_detail_idx} "
			+ "</script>")
	public void deleteTreatementItemDetail(CrmTreatementItemDetailVO vo) throws Exception;
}
