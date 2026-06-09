package kr.ondoc.mapper.sybase.crm;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import kr.ondoc.domain.sybase.crm.CrmUsrmngrGroupRelationVO;

public interface CrmUsrmngrGroupRelationMapper {
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT cog.group_idx, cog.name as group_name, cugr.xur_id from crm_usrmngr_group_relation AS cugr "
			+ "INNER JOIN crm_ondoc_group AS cog "
			+ "ON cugr.del_yn='N' AND cugr.group_idx=cog.group_idx "
			+ "]]>"
			+ "<if test=\"xur_id != null and xur_id != ''\">"
			+ "AND cugr.xur_id = #{xur_id} "
			+ "</if>"
			+ "</script>")
	public List<CrmUsrmngrGroupRelationVO> selectList(CrmUsrmngrGroupRelationVO vo) throws Exception;
	
	@Insert("<script>"
			+ "INSERT INTO crm_usrmngr_group_relation ("
			+ "xur_id, group_idx, write_date, write_id) "
			+ "VALUES ( "
			+ "#{xur_id}, #{group_idx}, "
			+ "(convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)), "
			+ "#{write_id})"
			+ "</script>")
	public void insert(CrmUsrmngrGroupRelationVO vo) throws Exception;
	
	@Insert("<script>"
			+ "INSERT INTO crm_usrmngr_group_relation (user_group_idx, "
			+ "xur_id, group_idx, write_date, write_id) "
			+ "VALUES ( "
			+ "#{user_group_idx}, #{xur_id}, #{group_idx}, "
			+ "(convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)), "
			+ "#{write_id})"
			+ "</script>")
	public void initInsert(CrmUsrmngrGroupRelationVO vo) throws Exception;
	
	@Delete("<script>"
			+ "UPDATE crm_usrmngr_group_relation SET "
			+ " del_yn = 'Y', "
			+ " del_date = (convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)), "
			+ " del_id = #{del_id}"
			+ "WHERE 0=0  "
			+ "<if test=\"user_group_idx != null and user_group_idx != ''\">"
			+ "AND user_group_idx = #{user_group_idx} "
			+ "</if>"
			+ "<if test=\"xur_id != null and xur_id != ''\">"
			+ "AND xur_id = #{xur_id} "
			+ "</if>"
			+ "<if test=\"group_idx != null and group_idx != ''\">"
			+ "AND group_idx = #{group_idx} "
			+ "</if>"
			+ "</script>")
	public void delete(CrmUsrmngrGroupRelationVO vo) throws Exception;
}
