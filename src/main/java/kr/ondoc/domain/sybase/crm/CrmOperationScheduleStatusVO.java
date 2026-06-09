package kr.ondoc.domain.sybase.crm;

public class CrmOperationScheduleStatusVO {
	String status_idx = "";
	String name = "";
	String background_color = "";
	String color = "";
	String sort = "";
	String use_yn = "";
	String del_yn = "";
	String write_date = "";
	String write_id = "";
	String modify_date = "";
	String modify_id = "";
	String del_date = "";
	String del_id = "";

	public void setInitInsert(String name, String background_color, String color, String sort, String use_yn, String write_id) {
		this.name = name;
		this.background_color = background_color;
		this.color = color;
		this.sort = sort;
		this.use_yn = use_yn;
		this.write_id = write_id;
	}
	
	public String getStatus_idx() {
		return status_idx;
	}
	public void setStatus_idx(String status_idx) {
		this.status_idx = status_idx;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBackground_color() {
		return background_color;
	}
	public void setBackground_color(String background_color) {
		this.background_color = background_color;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
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
