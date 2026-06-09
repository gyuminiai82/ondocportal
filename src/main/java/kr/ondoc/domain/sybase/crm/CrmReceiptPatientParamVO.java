package kr.ondoc.domain.sybase.crm;

public class CrmReceiptPatientParamVO {
	String date;
	String name;
	String room_name;
	
	String byungdong;
	String start_date;
	String end_date;
	
	String sort;
	
	String gubun;	//전체조회시 ptno/name/hpno/resno

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getGubun() {
		return gubun;
	}

	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
}
