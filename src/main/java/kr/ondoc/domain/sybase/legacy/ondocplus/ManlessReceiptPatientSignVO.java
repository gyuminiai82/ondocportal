package kr.ondoc.domain.sybase.legacy.ondocplus;

public class ManlessReceiptPatientSignVO {
	String ptno = "";
	String name = "";
	byte[] psign = null;
	
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
	public byte[] getPsign() {
		return psign;
	}
	public void setPsign(byte[] psign) {
		this.psign = psign;
	}
}
