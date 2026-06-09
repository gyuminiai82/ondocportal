package kr.ondoc.domain.sybase.crm;

public class CrmSendSmsVO {
	String sms_idx = "";
	String action = "";
	String send_time = "";		//발송시간
	String sent_ago_day = "";	//발송전(일)
	String sms_message = "";
	
	String use_yn = "";
	String del_yn = "";
	String del_date = "";
	String del_id = "";
	String write_date = "";
	String write_id = "";
	String modify_date = "";
	String modify_id = "";
	
	public void setInitInsert(String action, String send_time, String sent_ago_day, String sms_message, String use_yn, String del_yn, String write_id) {
		this.action = action;
		this.send_time = send_time;
		this.sent_ago_day = sent_ago_day;
		this.sms_message = sms_message;
		this.use_yn = use_yn;
		this.del_yn = del_yn;
		this.write_id = write_id;
	}
	
	public String getSms_idx() {
		return sms_idx;
	}
	public void setSms_idx(String sms_idx) {
		this.sms_idx = sms_idx;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getSend_time() {
		return send_time;
	}
	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}
	public String getSent_ago_day() {
		return sent_ago_day;
	}
	public void setSent_ago_day(String sent_ago_day) {
		this.sent_ago_day = sent_ago_day;
	}
	public String getSms_message() {
		return sms_message;
	}
	public void setSms_message(String sms_message) {
		this.sms_message = sms_message;
	}
	public String getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}
	public String getDel_yn() {
		return del_yn;
	}
	public void setDel_yn(String del_yn) {
		this.del_yn = del_yn;
	}
	public String getDel_date() {
		return del_date;
	}
	public void setDel_date(String del_date) {
		this.del_date = del_date;
	}
	public String getDel_id() {
		return del_id;
	}
	public void setDel_id(String del_id) {
		this.del_id = del_id;
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
	public String getModify_date() {
		return modify_date;
	}
	public void setModify_date(String modify_date) {
		this.modify_date = modify_date;
	}
	public String getModify_id() {
		return modify_id;
	}
	public void setModify_id(String modify_id) {
		this.modify_id = modify_id;
	}
}
