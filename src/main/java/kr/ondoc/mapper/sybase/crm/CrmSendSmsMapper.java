package kr.ondoc.mapper.sybase.crm;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.ondoc.domain.sybase.crm.CrmSendSmsVO;

public interface CrmSendSmsMapper {
	@Select("<script>"
			+ "SELECT * from crm_send_sms "
			+ "WHERE 0=0 "
			+ "AND sms_idx = #{sms_idx}"
			+ "</script>")
	public CrmSendSmsVO selectSendSms(CrmSendSmsVO vo) throws Exception;
	
	@Select("<script>"
			+ "SELECT * from crm_send_sms "
			+ "WHERE 0=0 "
			+ "<if test=\"action != null and action != ''\">"
			+ "AND action = #{action} "
			+ "</if>"
			+ "<if test=\"use_yn != null and use_yn != ''\">"
			+ "AND use_yn = #{use_yn} "
			+ "</if>"
			+ "<if test=\"del_yn != null and del_yn != ''\">"
			+ "AND del_yn = #{del_yn} "
			+ "</if>"
			+ "ORDER BY sms_idx DESC"
			+ "</script>")
	public List<CrmSendSmsVO> selectListSendSms(CrmSendSmsVO vo) throws Exception;
	
	@Insert("<script>"
			+ "INSERT INTO crm_send_sms ("
			+ "action, send_time, sent_ago_day, sms_message, "
			+ "write_date,"
			+ "write_id) "
			+ "VALUES ( "
			+ "#{action}, #{send_time}, #{sent_ago_day}, #{sms_message}, "
			+ "(convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)), "
			+ "#{write_id}"
			+ ")"
			+ "</script>")
	public void insertSendSms(CrmSendSmsVO vo) throws Exception;
	
	@Update("<script>"
			+ "UPDATE crm_send_sms SET "
			+ "sms_idx = sms_idx "
			+ "<if test=\"action != null and action != ''\">"
			+ ",action = #{action} "
			+ "</if>"
			+ "<if test=\"send_time != null and send_time != ''\">"
			+ ",send_time = #{send_time} "
			+ "</if>"
			+ "<if test=\"sent_ago_day != null\">"
			+ ",sent_ago_day = #{sent_ago_day} "
			+ "</if>"
			+ "<if test=\"sms_message != null and sms_message != ''\">"
			+ ",sms_message = #{sms_message} "
			+ "</if>"
			+ "<if test=\"use_yn != null and use_yn != ''\">"
			+ ",use_yn = #{use_yn} "
			+ "</if>"
			+ "<if test=\"modify_id != null and modify_id != ''\">"
			+ ",modify_date = (convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)) "
			+ ",modify_id = #{modify_id} "
			+ "</if>"
			+ "WHERE 0=0 "
			+ "AND sms_idx = #{sms_idx}"
			+ "</script>")
	public void updateSendSms(CrmSendSmsVO vo) throws Exception;
	
	@Delete("<script>"
			+ "UPDATE crm_send_sms SET "
			+ " del_yn = 'Y', "
			+ " del_date = (convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)), "
			+ " del_id = #{del_id}"
			+ "WHERE 0=0  "
			+ "AND sms_idx=#{sms_idx} "
			+ "</script>")
	public void deleteSendSms(CrmSendSmsVO vo) throws Exception;
}
