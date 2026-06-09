package kr.ondoc.domain.sybase.crm;

import java.util.List;

public class CrmCounselSubReserveVO {
	List<CrmReserveVO> reserveList = null;
	List<CrmReserveOperationVO> reserveOperationList = null;
	
	public List<CrmReserveVO> getReserveList() {
		return reserveList;
	}
	public void setReserveList(List<CrmReserveVO> reserveList) {
		this.reserveList = reserveList;
	}
	public List<CrmReserveOperationVO> getReserveOperationList() {
		return reserveOperationList;
	}
	public void setReserveOperationList(List<CrmReserveOperationVO> reserveOperationList) {
		this.reserveOperationList = reserveOperationList;
	}
}
