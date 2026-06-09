package kr.ondoc.domain.sybase.penchart;

public class EmrAttachVO {
	String bef_no = "";
	String bes_name = "";
	String key = "";
	String date = "";
	String time = "";
	String bef_form = "";
	String[] form = null;
	
	String xer_view = "";
	String xer_edit = "";
	String xer_print = "";
	
	public String getBef_no() {
		return bef_no;
	}
	public void setBef_no(String bef_no) {
		this.bef_no = bef_no;
	}
	public String getBes_name() {
		return bes_name;
	}
	public void setBes_name(String bes_name) {
		this.bes_name = bes_name;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String[] getForm() {
		return form;
	}
	public void setForm(String[] form) {
		this.form = form;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getBef_form() {
		return bef_form;
	}
	public void setBef_form(String bef_form) {
		this.bef_form = bef_form;
	}
	public String getXer_view() {
		return xer_view;
	}
	public void setXer_view(String xer_view) {
		this.xer_view = xer_view;
	}
	public String getXer_edit() {
		return xer_edit;
	}
	public void setXer_edit(String xer_edit) {
		this.xer_edit = xer_edit;
	}
	public String getXer_print() {
		return xer_print;
	}
	public void setXer_print(String xer_print) {
		this.xer_print = xer_print;
	}
	
}
