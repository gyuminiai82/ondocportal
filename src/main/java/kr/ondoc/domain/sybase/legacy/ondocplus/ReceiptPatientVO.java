package kr.ondoc.domain.sybase.legacy.ondocplus;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class ReceiptPatientVO extends CommonVO {
	String cntPtno = "0";
	String cntPhoneMan = "0";
	
	List<ReceiptPatientDataVO> data = new ArrayList<ReceiptPatientDataVO>();

	public List<ReceiptPatientDataVO> getData() {
		return data;
	}

	public void setData(List<ReceiptPatientDataVO> data) {
		this.data = data;
	}

	public String getCntPtno() {
		return cntPtno;
	}

	public void setCntPtno(String cntPtno) {
		this.cntPtno = cntPtno;
	}

	public String getCntPhoneMan() {
		return cntPhoneMan;
	}

	public void setCntPhoneMan(String cntPhoneMan) {
		this.cntPhoneMan = cntPhoneMan;
	}
}
