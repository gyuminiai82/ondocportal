package kr.ondoc.mapper.sybase.crm;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import kr.ondoc.domain.sybase.crm.CrmUsrmngrSelectRelationVO;

public interface CrmUsrmngrSelectRelationMapper {
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT select_idx, gubun, usrmngr_id, usrmngr_id AS xur_id, xur_name, xur_name AS user_name FROM crm_usrmngr_select_relation AS cusr "
			+ "INNER JOIN usrmngr AS usr "
			+ "ON cusr.del_yn='N' AND usr.xur_stop<>'Y' AND cusr.usrmngr_id=usr.xur_id "
			+ "]]>"
			+ "</script>")
	public List<CrmUsrmngrSelectRelationVO> selectListUsrmngrSelectRelation(CrmUsrmngrSelectRelationVO vo) throws Exception;
	
	@Insert("<script>"
			+ "INSERT INTO crm_usrmngr_select_relation ("
			+ "gubun, usrmngr_id, write_date, write_id) "
			+ "VALUES ( "
			+ "#{gubun}, #{usrmngr_id}, "
			+ "(convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)), "
			+ "#{write_id})"
			+ "</script>")
	public void insertUsrmngrSelectRelation(CrmUsrmngrSelectRelationVO vo) throws Exception;
	
	@Delete("<script>"
			+ "UPDATE crm_usrmngr_select_relation SET "
			+ " del_yn = 'Y', "
			+ " del_date = (convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)), "
			+ " del_id = #{del_id}"
			+ "WHERE 0=0  "
			+ "<if test=\"gubun != null and gubun != ''\">"
			+ "AND gubun = #{gubun} "
			+ "</if>"
			+ "<if test=\"usrmngr_id != null and usrmngr_id != ''\">"
			+ "AND usrmngr_id = #{usrmngr_id} "
			+ "</if>"
			+ "</script>")
	public void deleteUsrmngrSelectRelation(CrmUsrmngrSelectRelationVO vo) throws Exception;
}
