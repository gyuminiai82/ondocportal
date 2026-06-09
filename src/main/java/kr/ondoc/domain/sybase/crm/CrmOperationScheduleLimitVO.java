package kr.ondoc.domain.sybase.crm;

public class CrmOperationScheduleLimitVO {
	String schedule_time = "";
	String treatement_time = "";
	
	String department_idx = "";
	String department_number = "";
	String schedule_date = "";
	
	public String getSchedule_time() {
		return schedule_time;
	}
	public void setSchedule_time(String schedule_time) {
		this.schedule_time = schedule_time;
	}
	public String getTreatement_time() {
		return treatement_time;
	}
	public void setTreatement_time(String treatement_time) {
		this.treatement_time = treatement_time;
	}
	public String getDepartment_idx() {
		return department_idx;
	}
	public void setDepartment_idx(String department_idx) {
		this.department_idx = department_idx;
	}
	public String getDepartment_number() {
		return department_number;
	}
	public void setDepartment_number(String department_number) {
		this.department_number = department_number;
	}
	public String getSchedule_date() {
		return schedule_date;
	}
	public void setSchedule_date(String schedule_date) {
		this.schedule_date = schedule_date;
	}
}
