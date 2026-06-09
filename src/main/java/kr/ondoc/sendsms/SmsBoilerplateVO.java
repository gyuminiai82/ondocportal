package kr.ondoc.sendsms;

import java.util.List;

import kr.ondoc.domain.sybase.crm.CrmReserveVO;

public class SmsBoilerplateVO {
	List<SmsBoilerplatePatientVO> toPatient = null;
	String datetime = "";
	String message = "";
	
	public List<SmsBoilerplatePatientVO> getToPatient() {
		return toPatient;
	}
	public void setToPatient(List<SmsBoilerplatePatientVO> toPatient) {
		this.toPatient = toPatient;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
