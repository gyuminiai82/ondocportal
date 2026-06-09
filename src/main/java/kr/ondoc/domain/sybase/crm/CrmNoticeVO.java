package kr.ondoc.domain.sybase.crm;

public class CrmNoticeVO {
	String cno_seq = "";
	String cno_content = "";
	String cno_uid = "";
	String cno_name = "";
	String cno_del_yn = "";
	String cno_del_uid = "";
	String cno_del_date = "";
	String cno_int_date = "";
	String datetime= "";
	
	public String getCno_seq() {
		return cno_seq;
	}
	public void setCno_seq(String cno_seq) {
		this.cno_seq = cno_seq;
	}
	public String getCno_content() {
		return cno_content;
	}
	public void setCno_content(String cno_content) {
		this.cno_content = cno_content;
	}
	public String getCno_uid() {
		return cno_uid;
	}
	public void setCno_uid(String cno_uid) {
		this.cno_uid = cno_uid;
	}
	public String getCno_name() {
		return cno_name;
	}
	public void setCno_name(String cno_name) {
		this.cno_name = cno_name;
	}
	public String getCno_del_yn() {
		return cno_del_yn;
	}
	public void setCno_del_yn(String cno_del_yn) {
		this.cno_del_yn = cno_del_yn;
	}
	public String getCno_del_uid() {
		return cno_del_uid;
	}
	public void setCno_del_uid(String cno_del_uid) {
		this.cno_del_uid = cno_del_uid;
	}
	public String getCno_del_date() {
		return cno_del_date;
	}
	public void setCno_del_date(String cno_del_date) {
		this.cno_del_date = cno_del_date;
	}
	public String getCno_int_date() {
		return cno_int_date;
	}
	public void setCno_int_date(String cno_int_date) {
		this.cno_int_date = cno_int_date;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	
}
