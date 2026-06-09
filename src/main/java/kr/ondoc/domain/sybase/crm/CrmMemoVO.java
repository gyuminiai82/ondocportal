package kr.ondoc.domain.sybase.crm;

public class CrmMemoVO extends PagingParamVO {
	String memo_idx = "";
	String notice_yn = "";
	String memo = "";
	String memo_image = "";
	String memo_date = "";
	String del_yn = "";
	String write_date = "";
	String write_id = "";
	String write_name = "";
	String modify_date = "";
	String modify_id = "";
	String del_date = "";
	String del_id = "";
	
	public String getMemo_idx() {
		return memo_idx;
	}
	public void setMemo_idx(String memo_idx) {
		this.memo_idx = memo_idx;
	}
	public String getNotice_yn() {
		return notice_yn;
	}
	public void setNotice_yn(String notice_yn) {
		this.notice_yn = notice_yn;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getMemo_image() {
		return memo_image;
	}
	public void setMemo_image(String memo_image) {
		this.memo_image = memo_image;
	}
	public String getMemo_date() {
		return memo_date;
	}
	public void setMemo_date(String memo_date) {
		this.memo_date = memo_date;
	}
	public String getDel_yn() {
		return del_yn;
	}
	public void setDel_yn(String del_yn) {
		this.del_yn = del_yn;
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
	public String getWrite_name() {
		return write_name;
	}
	public void setWrite_name(String write_name) {
		this.write_name = write_name;
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
}
