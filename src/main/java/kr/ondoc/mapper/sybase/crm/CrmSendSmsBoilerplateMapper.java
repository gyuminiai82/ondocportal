package kr.ondoc.mapper.sybase.crm;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.ondoc.domain.sybase.crm.CrmPatientParamVO;
import kr.ondoc.domain.sybase.crm.CrmPatientVO;
import kr.ondoc.domain.sybase.crm.CrmSendSmsBoilerplateVO;
import kr.ondoc.domain.sybase.crm.CrmSmsPatientParamVO;

public interface CrmSendSmsBoilerplateMapper {
	@Select("<script>"
			+ "SELECT * from crm_send_sms_boilerplate "
			+ "WHERE 0=0 "
			+ "AND sms_boilerplate_idx = #{sms_boilerplate_idx}"
			+ "</script>")
	public CrmSendSmsBoilerplateVO selectSendSmsBoilerplate(CrmSendSmsBoilerplateVO vo) throws Exception;
	
	@Select("<script>"
			+ "SELECT * from crm_send_sms_boilerplate "
			+ "WHERE 0=0 "
			+ "<if test=\"sms_boilerplate_group_idx != null and sms_boilerplate_group_idx != ''\">"
			+ "AND sms_boilerplate_group_idx = #{sms_boilerplate_group_idx} "
			+ "</if>"
			+ "<if test=\"use_yn != null and use_yn != ''\">"
			+ "AND use_yn = #{use_yn} "
			+ "</if>"
			+ "<if test=\"del_yn != null and del_yn != ''\">"
			+ "AND del_yn = #{del_yn} "
			+ "</if>"
			+ "ORDER BY sort"
			+ "</script>")
	public List<CrmSendSmsBoilerplateVO> selectListSendSmsBoilerplate(CrmSendSmsBoilerplateVO vo) throws Exception;
	
	@Insert("<script>"
			+ "INSERT INTO crm_send_sms_boilerplate ("
			+ "sms_boilerplate_group_idx, sms_title, sms_message, sort, use_yn, "
			+ "write_date,"
			+ "write_id) "
			+ "VALUES ( "
			+ "#{sms_boilerplate_group_idx}, #{sms_title}, #{sms_message}, "
			+ "ISNULL((SELECT MAX(sort) FROM crm_send_sms_boilerplate), 0)+1, "
			+ "#{use_yn}, "
			+ "(convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)), "
			+ "#{write_id}"
			+ ")"
			+ "</script>")
	public void insertSendSmsBoilerplate(CrmSendSmsBoilerplateVO vo) throws Exception;
	
	@Update("<script>"
			+ "UPDATE crm_send_sms_boilerplate SET "
			+ "sms_boilerplate_idx = sms_boilerplate_idx "
			+ "<if test=\"sms_boilerplate_group_idx != null and sms_boilerplate_group_idx != ''\">"
			+ ",sms_boilerplate_group_idx = #{sms_boilerplate_group_idx} "
			+ "</if>"
			+ "<if test=\"sms_title != null and sms_title != ''\">"
			+ ",sms_title = #{sms_title} "
			+ "</if>"
			+ "<if test=\"sms_message != null and sms_message != ''\">"
			+ ",sms_message = #{sms_message} "
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
			+ "AND sms_boilerplate_idx = #{sms_boilerplate_idx}"
			+ "</script>")
	public void updateSendSmsBoilerplate(CrmSendSmsBoilerplateVO vo) throws Exception;
	
	@Delete("<script>"
			+ "UPDATE crm_send_sms_boilerplate SET "
			+ " del_yn = 'Y', "
			+ " del_date = (convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)), "
			+ " del_id = #{del_id}"
			+ "WHERE 0=0  "
			+ "AND sms_boilerplate_idx=#{sms_boilerplate_idx} "
			+ "</script>")
	public void deleteSendSmsBoilerplate(CrmSendSmsBoilerplateVO vo) throws Exception;
	
	//문자발송을 위한 환자검색
	@Select("<script>"
			+ "SELECT DISTINCT pa.* FROM patient AS pa LEFT OUTER JOIN crm_operation_schedule cos "
			+ "ON pa.bpt_ptno=cos.ptno "
			+ "LEFT OUTER JOIN crm_treatement ct "
			+ "ON cos.operation_schedule_idx=ct.operation_schedule_idx "
			+ "LEFT OUTER JOIN crm_treatement_item_relation ctir "
			+ "ON ct.treatement_idx=ctir.treatement_idx "
			+ "WHERE 0=0 "
			+ "<if test=\"del_yn != null and del_yn != ''\">"
			+ "AND cos.del_yn = #{del_yn} "
			+ "</if>"
			+ "<if test=\"scheduleStartDate != null and scheduleStartDate != ''\">"
			+ "AND cos.schedule_date BETWEEN #{scheduleStartDate} AND #{scheduleEndDate} "
			+ "</if>"
			+ "<if test=\"department_idx != null and department_idx != ''\">"
			+ "AND cos.department_idx = #{department_idx} "
			+ "</if>"
			+ "<if test=\"ptno != null and ptno != ''\">"
			+ "AND pa.bpt_ptno = #{ptno} "
			+ "</if>"
			+ "<if test=\"name != null and name != ''\">"
			+ "AND pa.bpt_name LIKE '%${name}%' "
			+ "</if>"
			+ "<if test=\"hpno != null and hpno != ''\">"
			+ "AND pa.bpt_hpno LIKE '%${hpno}%' "
			+ "</if>"
			+ "<if test=\"sex != null and sex != ''\">"
			+ "AND pa.bpt_sex = #{sex} "
			+ "</if>"
			+ "<if test=\"birthday != null and birthday != ''\">"
			+ "AND pa.bpt_birth = #{birthday} "
			+ "</if>"
			+ "<if test=\"item_idx != null and item_idx != ''\">"
			+ "AND ctir.item_idx = #{item_idx} "
			+ "</if>"
			+ "<if test=\"item_detail_idx != null and item_detail_idx != ''\">"
			+ "AND ctir.item_detail_idx = #{item_detail_idx} "
			+ "</if>"
			+ "<if test=\"birthday != null and birthday != ''\">"
			+ "AND pa.bpt_birth = #{birthday} "
			+ "</if>"
			+ "</script>")
	public List<CrmPatientVO> selectListSmsPatientList(CrmSmsPatientParamVO vo) throws Exception;
}
