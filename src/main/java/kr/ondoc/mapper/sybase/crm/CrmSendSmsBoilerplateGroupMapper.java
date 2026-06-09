package kr.ondoc.mapper.sybase.crm;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.ondoc.domain.sybase.crm.CrmSendSmsBoilerplateGroupVO;
import kr.ondoc.domain.sybase.crm.CrmSendSmsBoilerplateVO;

public interface CrmSendSmsBoilerplateGroupMapper {
	@Select("<script>"
			+ "SELECT * from crm_send_sms_boilerplate_group "
			+ "WHERE 0=0 "
			+ "<if test=\"use_yn != null and use_yn != ''\">"
			+ "AND use_yn = #{use_yn} "
			+ "</if>"
			+ "<if test=\"del_yn != null and del_yn != ''\">"
			+ "AND del_yn = #{del_yn} "
			+ "</if>"
			+ "ORDER BY sort"
			+ "</script>")
	public List<CrmSendSmsBoilerplateGroupVO> selectListSendSmsBoilerplateGroup(CrmSendSmsBoilerplateGroupVO vo) throws Exception;
	
	@Insert("<script>"
			+ "INSERT INTO crm_send_sms_boilerplate_group ("
			+ "group_title, sort, use_yn, "
			+ "write_date,"
			+ "write_id) "
			+ "VALUES ( "
			+ "#{group_title}, "
			+ "ISNULL((SELECT MAX(sort) FROM crm_send_sms_boilerplate_group), 0)+1, "
			+ "#{use_yn}, "
			+ "(convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)), "
			+ "#{write_id}"
			+ ")"
			+ "</script>")
	public void insertSendSmsBoilerplateGroup(CrmSendSmsBoilerplateGroupVO vo) throws Exception;
	
	@Update("<script>"
			+ "UPDATE crm_send_sms_boilerplate_group SET "
			+ "sms_boilerplate_group_idx = sms_boilerplate_group_idx "
			+ "<if test=\"group_title != null and group_title != ''\">"
			+ ",group_title = #{group_title} "
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
			+ "AND sms_boilerplate_group_idx = #{sms_boilerplate_group_idx}"
			+ "</script>")
	public void updateSendSmsBoilerplateGroup(CrmSendSmsBoilerplateGroupVO vo) throws Exception;
	
	@Delete("<script>"
			+ "UPDATE crm_send_sms_boilerplate_group SET "
			+ " del_yn = 'Y', "
			+ " del_date = (convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)), "
			+ " del_id = #{del_id}"
			+ "WHERE 0=0  "
			+ "AND sms_boilerplate_group_idx=#{sms_boilerplate_group_idx} "
			+ "</script>")
	public void deleteSendSmsBoilerplateGroup(CrmSendSmsBoilerplateGroupVO vo) throws Exception;
}
