package kr.ondoc.domain.sybase.healthcheck;

public class HealthCheckParamVO {
	String start_date = "";
	String end_date = "";
	String ptno = "";
	String regno = "";
	String ch_date = "";
	String yyyy = "";
	
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getPtno() {
		return ptno;
	}
	public void setPtno(String ptno) {
		this.ptno = ptno;
	}
	public String getRegno() {
		return regno;
	}
	public void setRegno(String regno) {
		this.regno = regno;
	}
	public String getCh_date() {
		return ch_date;
	}
	public void setCh_date(String ch_date) {
		this.ch_date = ch_date;
	}
	public String getYyyy() {
		return yyyy;
	}
	public void setYyyy(String yyyy) {
		this.yyyy = yyyy;
	}
}
