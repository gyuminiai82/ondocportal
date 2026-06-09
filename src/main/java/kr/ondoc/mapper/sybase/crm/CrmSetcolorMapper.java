package kr.ondoc.mapper.sybase.crm;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.ondoc.domain.sybase.crm.CrmSetcolorVO;

public interface CrmSetcolorMapper {
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT * from crm_setcolor WHERE 0=0 "
			+ "]]>"
			+ "<if test=\"use_yn != null and use_yn != ''\">"
			+ "AND use_yn = #{use_yn} "
			+ "</if>"
			+ "<if test=\"del_yn != null and del_yn != ''\">"
			+ "AND del_yn = #{del_yn} "
			+ "</if>"
			+ "ORDER BY sort ASC "
			+ "</script>")
	public List<CrmSetcolorVO> selectListSetcolor(CrmSetcolorVO vo) throws Exception;
	
	@Insert("<script>"
			+ "INSERT INTO crm_setcolor ("
			+ "name, background_color, color, sort, use_yn, write_date, write_id) "
			+ "VALUES ( "
			+ "#{name}, #{background_color}, #{color}, "
			+ "ISNULL((SELECT MAX(sort) FROM crm_setcolor), 0)+1, #{use_yn}, "
			+ "(convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)), "
			+ "#{write_id})"
			+ "</script>")
	public void insertSetcolor(CrmSetcolorVO vo) throws Exception;
	
	@Update("<script>"
			+ "UPDATE crm_setcolor SET "
			+ "setcolor_idx = setcolor_idx "
			+ "<if test=\"name != null and name != ''\">"
			+ ",name = #{name} "
			+ "</if>"
			+ "<if test=\"background_color != null and background_color != ''\">"
			+ ",background_color = #{background_color} "
			+ "</if>"
			+ "<if test=\"color != null and color != ''\">"
			+ ",color = #{color} "
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
			+ "AND setcolor_idx = #{setcolor_idx}"
			+ "</script>")
	public void updateSetcolor(CrmSetcolorVO vo) throws Exception;
	
	@Delete("<script>"
			+ "UPDATE crm_setcolor SET "
			+ " del_yn = 'Y', "
			+ " del_date = (convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)), "
			+ " del_id = #{del_id}"
			+ "WHERE 0=0  "
			+ "AND setcolor_idx=#{setcolor_idx} "
			+ "</script>")
	public void deleteSetcolor(CrmSetcolorVO vo) throws Exception;
}
