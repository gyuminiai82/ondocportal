package kr.ondoc.domain.sybase.legacy.ondoc;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class WorkPlanParamVO extends CommonVO {
	String code = "";
	String reserveDate = "";
	String flagCheck = "";
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getReserveDate() {
		return reserveDate;
	}
	public void setReserveDate(String reserveDate) {
		this.reserveDate = reserveDate;
	}
	public String getFlagCheck() {
		return flagCheck;
	}
	public void setFlagCheck(String flagCheck) {
		this.flagCheck = flagCheck;
	}
}
