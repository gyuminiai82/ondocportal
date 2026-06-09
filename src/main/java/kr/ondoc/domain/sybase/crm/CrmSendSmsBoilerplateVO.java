package kr.ondoc.domain.sybase.crm;

public class CrmSendSmsBoilerplateVO {
	String sms_boilerplate_idx = "";
	String sms_boilerplate_group_idx = "";
	String sms_title = "";
	String sms_message = "";
	String sort = "";
	String use_yn = "";
	String del_yn = "";
	String del_date = "";
	String del_id = "";
	String write_date = "";
	String write_id = "";
	String modify_date = "";
	String modify_id = "";
	
	public void setInitInsert(String sms_boilerplate_group_idx, String sms_title, String sms_message, String use_yn, String del_yn, String write_id) {
		this.sms_boilerplate_group_idx = sms_boilerplate_group_idx;
		this.sms_title = sms_title;
		this.sms_message = sms_message;
		this.use_yn = use_yn;
		this.del_yn = del_yn;
		this.write_id = write_id;
	}
	
	public String getSms_boilerplate_idx() {
		return sms_boilerplate_idx;
	}
	public void setSms_boilerplate_idx(String sms_boilerplate_idx) {
		this.sms_boilerplate_idx = sms_boilerplate_idx;
	}
	public String getSms_boilerplate_group_idx() {
		return sms_boilerplate_group_idx;
	}
	public void setSms_boilerplate_group_idx(String sms_boilerplate_group_idx) {
		this.sms_boilerplate_group_idx = sms_boilerplate_group_idx;
	}
	public String getSms_title() {
		return sms_title;
	}
	public void setSms_title(String sms_title) {
		this.sms_title = sms_title;
	}
	public String getSms_message() {
		return sms_message;
	}
	public void setSms_message(String sms_message) {
		this.sms_message = sms_message;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
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
