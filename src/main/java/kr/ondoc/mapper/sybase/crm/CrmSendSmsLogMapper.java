package kr.ondoc.mapper.sybase.crm;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import kr.ondoc.domain.sybase.crm.CrmSmsLogVO;

public interface CrmSendSmsLogMapper {
	@Select("<script>"
			+ "SELECT * from crm_send_sms_log "
			+ "WHERE 0=0 "
			+ "AND log_idx = #{log_idx}"
			+ "</script>")
	public CrmSmsLogVO selectSmsLog(CrmSmsLogVO vo) throws Exception;
	
	@Select("<script>"
			+ "SELECT * from crm_send_sms_log "
			+ "WHERE 0=0 "
			+ "<if test=\"status != null and status != ''\">"
			+ "AND status = #{status} "
			+ "</if>"
			+ "ORDER BY sms_idx DESC"
			+ "</script>")
	public List<CrmSmsLogVO> selectListSmsLog(CrmSmsLogVO vo) throws Exception;
	
	@Insert("<script>"
			+ "INSERT INTO crm_send_sms_log ("
			+ "reserved_id, status, send_datetime, sms_message, receive_phone, receive_name, "
			+ "write_date,"
			+ "write_id) "
			+ "VALUES ( "
			+ "#{reserved_id}, #{status}, #{send_datetime}, #{sms_message}, #{receive_phone}, #{receive_name}, "
			+ "(convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)), "
			+ "#{write_id}"
			+ ")"
			+ "</script>")
	public void insertSmsLog(CrmSmsLogVO vo) throws Exception;
	
	@Delete("<script>"
			+ "UPDATE crm_send_sms_log SET "
			+ " cancel_date = (convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)), "
			+ " cancel_id = #{cancel_id}"
			+ "WHERE 0=0  "
			+ "AND log_idx=#{log_idx} "
			+ "</script>")
	public void cancelSmsLog(CrmSmsLogVO vo) throws Exception;
}
