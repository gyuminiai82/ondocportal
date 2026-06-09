package kr.ondoc.domain.sybase.crm;

public class CrmSmsLogVO {
	String log_idx = "";
	String reserved_id = "";
	String status = "";	//register, move, cancel, delete
	String send_datetime = "";
	String sms_message = "";
	String receive_phone = "";
	String receive_name = "";
	
	String write_date = "";
	String write_id = "";
	String cancel_date = "";
	String cancel_id = "";
	
	public String getLog_idx() {
		return log_idx;
	}
	public void setLog_idx(String log_idx) {
		this.log_idx = log_idx;
	}
	public String getReserved_id() {
		return reserved_id;
	}
	public void setReserved_id(String reserved_id) {
		this.reserved_id = reserved_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSend_datetime() {
		return send_datetime;
	}
	public void setSend_datetime(String send_datetime) {
		this.send_datetime = send_datetime;
	}
	public String getSms_message() {
		return sms_message;
	}
	public void setSms_message(String sms_message) {
		this.sms_message = sms_message;
	}
	public String getReceive_phone() {
		return receive_phone;
	}
	public void setReceive_phone(String receive_phone) {
		this.receive_phone = receive_phone;
	}
	public String getReceive_name() {
		return receive_name;
	}
	public void setReceive_name(String receive_name) {
		this.receive_name = receive_name;
	}
	public String getWrite_date() {
		return write_date;
	}
	public void setWrite_date(String write_date) {
		this.write_date = write_date;
	}
	public String getWrite_id() {
		return write_id;
	}
	public void setWrite_id(String write_id) {
		this.write_id = write_id;
	}
	public String getCancel_date() {
		return cancel_date;
	}
	public void setCancel_date(String cancel_date) {
		this.cancel_date = cancel_date;
	}
	public String getCancel_id() {
		return cancel_id;
	}
	public void setCancel_id(String cancel_id) {
		this.cancel_id = cancel_id;
	}
}
