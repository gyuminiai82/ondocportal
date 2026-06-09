package kr.ondoc.domain.sybase.legacy.ondoc;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class WorkPlanDataVO {
	String reserveDate = "";
	String usableTime = "";
	String reserveUsableCount = "";
	
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
}
