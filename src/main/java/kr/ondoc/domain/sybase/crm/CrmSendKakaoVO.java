package kr.ondoc.domain.sybase.crm;

public class CrmSendKakaoVO {
	String kakao_idx = "";
	String action = "";
	String send_time = "";		//발송시간
	String sent_ago_day = "";	//발송전(일)
	String template_code = "";
	String template_message = "";
	
	String use_yn = "";
	String del_yn = "";
	String del_date = "";
	String del_id = "";
	String write_date = "";
	String write_id = "";
	String modify_date = "";
	String modify_id = "";
	public String getKakao_idx() {
		return kakao_idx;
	}
	public void setKakao_idx(String kakao_idx) {
		this.kakao_idx = kakao_idx;
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
	public String getTemplate_code() {
		return template_code;
	}
	public void setTemplate_code(String template_code) {
		this.template_code = template_code;
	}
	public String getTemplate_message() {
		return template_message;
	}
	public void setTemplate_message(String template_message) {
		this.template_message = template_message;
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
