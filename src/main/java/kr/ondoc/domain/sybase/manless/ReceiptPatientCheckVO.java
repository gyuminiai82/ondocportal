package kr.ondoc.domain.sybase.manless;

public class ReceiptPatientCheckVO {
	//대기중인지 체크 대기중이면 true
	boolean checkStandBy = false;
	
	//같은 사람이 차트번호가 2개인 경우는 다른 종별로 접수한 경우인데 옵션에 따라 가장최근 접수한 종별로 접수하거나 접수실로 보냄
	int cntPtno = 0;
	
	//연락처로 검색시 차트번호가 2개이상인 경우 한사람인지 그 이상인지 확인 2명이상이면 true
	boolean checkPhoneManOneGreaterThan = false;

	public boolean isCheckStandBy() {
		return checkStandBy;
	}

	public void setCheckStandBy(boolean checkStandBy) {
		this.checkStandBy = checkStandBy;
	}

	public int getCntPtno() {
		return cntPtno;
	}

	public void setCntPtno(int cntPtno) {
		this.cntPtno = cntPtno;
	}

	public boolean isCheckPhoneManOneGreaterThan() {
		return checkPhoneManOneGreaterThan;
	}

	public void setCheckPhoneManOneGreaterThan(boolean checkPhoneManOneGreaterThan) {
		this.checkPhoneManOneGreaterThan = checkPhoneManOneGreaterThan;
	}
}
