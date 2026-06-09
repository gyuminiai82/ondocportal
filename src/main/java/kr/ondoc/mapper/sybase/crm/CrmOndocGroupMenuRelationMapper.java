package kr.ondoc.mapper.sybase.crm;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import kr.ondoc.domain.sybase.crm.CrmOndocGroupMenuRelationVO;
import kr.ondoc.domain.sybase.crm.CrmSetcolorVO;
import kr.ondoc.domain.sybase.crm.CrmUsrmngrSelectRelationVO;

public interface CrmOndocGroupMenuRelationMapper {
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT * from crm_ondoc_group_menu_relation WHERE 0=0 "
			+ "]]>"
			+ "<if test=\"group_idx != null and group_idx != ''\">"
			+ "AND group_idx = #{group_idx} "
			+ "</if>"
			+ "<if test=\"del_yn != null and del_yn != ''\">"
			+ "AND del_yn = #{del_yn} "
			+ "</if>"
			+ "</script>")
	public List<CrmOndocGroupMenuRelationVO> selectList(CrmOndocGroupMenuRelationVO vo) throws Exception;
	
	@Insert("<script>"
			+ "INSERT INTO crm_ondoc_group_menu_relation ("
			+ "group_idx, menu_code, write_date, write_id) "
			+ "VALUES ( "
			+ "#{group_idx}, #{menu_code}, "
			+ "(convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)), "
			+ "#{write_id})"
			+ "</script>")
	public void insert(CrmOndocGroupMenuRelationVO vo) throws Exception;
	
	@Insert("<script>"
			+ "INSERT INTO crm_ondoc_group_menu_relation (group_menu_idx, "
			+ "group_idx, menu_code, write_date, write_id) "
			+ "VALUES (#{group_menu_idx},  "
			+ "#{group_idx}, #{menu_code}, "
			+ "(convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)), "
			+ "#{write_id})"
			+ "</script>")
	public void initInsert(CrmOndocGroupMenuRelationVO vo) throws Exception;
	
	@Delete("<script>"
			+ "UPDATE crm_ondoc_group_menu_relation SET "
			+ " del_yn = 'Y', "
			+ " del_date = (convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)), "
			+ " del_id = #{del_id}"
			+ "WHERE 0=0  "
			+ "<if test=\"group_idx != null and group_idx != ''\">"
			+ "AND group_idx = #{group_idx} "
			+ "</if>"
			+ "<if test=\"menu_code != null and menu_code != ''\">"
			+ "AND menu_code = #{menu_code} "
			+ "</if>"
			+ "<if test=\"group_menu_idx != null and group_menu_idx != ''\">"
			+ "AND group_menu_idx = #{group_menu_idx} "
			+ "</if>"
			+ "</script>")
	public void delete(CrmOndocGroupMenuRelationVO vo) throws Exception;
}
