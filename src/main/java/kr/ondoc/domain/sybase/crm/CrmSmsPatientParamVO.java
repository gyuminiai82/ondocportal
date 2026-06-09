package kr.ondoc.domain.sybase.crm;

public class CrmSmsPatientParamVO {
	String ptno = "";
	String name = "";
	String hpno = "";
	String sex = "";
	String birthday = "";
	
	String scheduleStartDate = "";
	String scheduleEndDate = "";
	
	String department_idx = "";
	
	String item_idx = "";
	String item_detail_idx = "";
	
	String del_yn = "";

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

	public String getHpno() {
		return hpno;
	}

	public void setHpno(String hpno) {
		this.hpno = hpno;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getScheduleStartDate() {
		return scheduleStartDate;
	}

	public void setScheduleStartDate(String scheduleStartDate) {
		this.scheduleStartDate = scheduleStartDate;
	}

	public String getScheduleEndDate() {
		return scheduleEndDate;
	}

	public void setScheduleEndDate(String scheduleEndDate) {
		this.scheduleEndDate = scheduleEndDate;
	}

	public String getDepartment_idx() {
		return department_idx;
	}

	public void setDepartment_idx(String department_idx) {
		this.department_idx = department_idx;
	}

	public String getItem_idx() {
		return item_idx;
	}

	public void setItem_idx(String item_idx) {
		this.item_idx = item_idx;
	}

	public String getItem_detail_idx() {
		return item_detail_idx;
	}

	public void setItem_detail_idx(String item_detail_idx) {
		this.item_detail_idx = item_detail_idx;
	}

	public String getDel_yn() {
		return del_yn;
	}

	public void setDel_yn(String del_yn) {
		this.del_yn = del_yn;
	}
}
