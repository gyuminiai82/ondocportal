package kr.ondoc.domain.sybase.crm;

public class CrmSendSmsBoilerplateGroupVO {
	String sms_boilerplate_group_idx = "";
	String group_title = "";
	String sort = "";
	String use_yn = "";
	String del_yn = "";
	String del_date = "";
	String del_id = "";
	String write_date = "";
	String write_id = "";
	String modify_date = "";
	String modify_id = "";
	
	public void setInitInsert(String group_title, String use_yn, String del_yn, String write_id) {
		this.group_title = group_title;
		this.use_yn = use_yn;
		this.del_yn = del_yn;
		this.write_id = write_id;
	}
	
	public String getSms_boilerplate_group_idx() {
		return sms_boilerplate_group_idx;
	}
	public void setSms_boilerplate_group_idx(String sms_boilerplate_group_idx) {
		this.sms_boilerplate_group_idx = sms_boilerplate_group_idx;
	}
	public String getGroup_title() {
		return group_title;
	}
	public void setGroup_title(String group_title) {
		this.group_title = group_title;
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
