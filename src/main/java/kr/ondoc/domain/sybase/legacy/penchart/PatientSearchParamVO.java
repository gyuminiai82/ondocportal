package kr.ondoc.domain.sybase.legacy.penchart;

public class PatientSearchParamVO {
	String date;
	String date2 = "";
	String ptno;
	String name;
	String medroom;
	String room_name;
	
	String byungdong;
	String start_date;
	String end_date;
	
	String type;
	String sort;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDate2() {
		return date2;
	}
	public void setDate2(String date2) {
		this.date2 = date2;
	}
	public String getPtno() {
		return ptno;
	}
	public void setPtno(String ptno) {
		this.ptno = ptno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMedroom() {
		return medroom;
	}
	public void setMedroom(String medroom) {
		this.medroom = medroom;
	}
	public String getRoom_name() {
		return room_name;
	}
	public void setRoom_name(String room_name) {
		this.room_name = room_name;
	}
	public String getByungdong() {
		return byungdong;
	}
	public void setByungdong(String byungdong) {
		this.byungdong = byungdong;
	}
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
}
