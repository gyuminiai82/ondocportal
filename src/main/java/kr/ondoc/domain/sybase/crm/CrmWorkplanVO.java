package kr.ondoc.domain.sybase.crm;

public class CrmWorkplanVO {
	String reserveDate = "";
	String usableTime = "";
	String reserveUsableCount = "";
	String mobileCount = "";
	String code = "";
	
	public String getReserveDate() {
		return reserveDate;
	}
	public void setReserveDate(String reserveDate) {
		this.reserveDate = reserveDate;
	}
	public String getUsableTime() {
		return usableTime;
	}
	public void setUsableTime(String usableTime) {
		this.usableTime = usableTime;
	}
	public String getReserveUsableCount() {
		return reserveUsableCount;
	}
	public void setReserveUsableCount(String reserveUsableCount) {
		this.reserveUsableCount = reserveUsableCount;
	}
	public String getMobileCount() {
		return mobileCount;
	}
	public void setMobileCount(String mobileCount) {
		this.mobileCount = mobileCount;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
