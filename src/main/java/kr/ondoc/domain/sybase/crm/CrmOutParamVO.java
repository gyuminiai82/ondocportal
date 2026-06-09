package kr.ondoc.domain.sybase.crm;

public class CrmOutParamVO extends PagingParamVO {
	String ptno = "";
	String regno = "";
	String medroom = "";
	String year = "";
	String date = "";
	String period = "";
	String kwa = "";
	
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
	public String getMedroom() {
		return medroom;
	}
	public void setMedroom(String medroom) {
		this.medroom = medroom;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getKwa() {
		return kwa;
	}
	public void setKwa(String kwa) {
		this.kwa = kwa;
	}
}
