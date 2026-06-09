package kr.ondoc.mapper.sybase.crm;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.ondoc.domain.sybase.crm.CrmAuthMenuVO;
import kr.ondoc.domain.sybase.crm.CrmOndocGroupVO;
import kr.ondoc.domain.sybase.crm.CrmUsrmngrVO;

public interface CrmOndocGroupMapper {
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT * from crm_ondoc_group WHERE 0=0 "
			+ "]]>"
			+ "<if test=\"del_yn != null and del_yn != ''\">"
			+ "AND del_yn = #{del_yn} "
			+ "</if>"
			+ "</script>")
	public List<CrmOndocGroupVO> selectListGroup(CrmOndocGroupVO vo) throws Exception;
	
	@Select("<script>"
			+ "SELECT COUNT(*) "
			+ "FROM crm_ondoc_group "
			+ "WHERE 0=0 "
			+ "AND name = #{name}"
			+ "</script>")
	public int checkGroup(String name) throws Exception;
	
	@Insert("<script>"
			+ "INSERT INTO crm_ondoc_group ("
			+ "name, write_date, write_id) "
			+ "VALUES ( "
			+ "#{name}, "
			+ "(convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)), "
			+ "#{write_id})"
			+ "</script>")
	public void insertInitGroup(CrmOndocGroupVO vo) throws Exception;
	
	@Insert("<script>"
			+ "INSERT INTO crm_ondoc_group ("
			+ "name, write_date, write_id) "
			+ "VALUES ( "
			+ "#{name}, "
			+ "(convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)), "
			+ "#{write_id})"
			+ "</script>")
	public void insertGroup(CrmOndocGroupVO vo) throws Exception;
	
	@Update("<script>"
			+ "UPDATE crm_ondoc_group SET "
			+ "group_idx = group_idx "
			+ "<if test=\"name != null and name != ''\">"
			+ ",name = #{name} "
			+ "</if>"
			+ "<if test=\"modify_id != null and modify_id != ''\">"
			+ ",modify_date = (convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)) "
			+ ",modify_id = #{modify_id} "
			+ "</if>"
			+ "WHERE 0=0 "
			+ "AND group_idx = #{group_idx}"
			+ "</script>")
	public void updateGroup(CrmOndocGroupVO vo) throws Exception;
	
	@Delete("<script>"
			+ "UPDATE crm_ondoc_group SET "
			+ " del_yn = 'Y', "
			+ " del_date = (convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)), "
			+ " del_id = #{del_id}"
			+ "WHERE 0=0  "
			+ "AND group_idx=#{group_idx} "
			+ "</script>")
	public void deleteGroup(CrmOndocGroupVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT distinct menu_code FROM crm_usrmngr_group_relation AS gr\r\n"
			+ "INNER JOIN crm_ondoc_group_menu_relation AS mr\r\n"
			+ "ON gr.group_idx=mr.group_idx AND gr.del_yn='N' AND mr.del_yn='N' \r\n"
			+ "WHERE 0=0  "
			+ "]]>"
			+ "<if test=\"id != null and id != ''\">"
			+ "AND gr.xur_id=#{id} "
			+ "</if>"
			+ "</script>")
	public List<CrmAuthMenuVO> selectListAuthMenu(String id) throws Exception;
}
