package kr.ondoc.domain.sybase.legacy.penchart;

public class NurseRecordDataVO {
	String bpt_ptno = "";
	String bpt_name = "";
	String NSRDT = "";
	String NSRTM = "";
	String TXTVL = "";
	
	public String getBpt_ptno() {
		return bpt_ptno;
	}
	public void setBpt_ptno(String bpt_ptno) {
		this.bpt_ptno = bpt_ptno;
	}
	public String getBpt_name() {
		return bpt_name;
	}
	public void setBpt_name(String bpt_name) {
		this.bpt_name = bpt_name;
	}
	public String getNSRDT() {
		return NSRDT;
	}
	public void setNSRDT(String nSRDT) {
		NSRDT = nSRDT;
	}
	public String getNSRTM() {
		return NSRTM;
	}
	public void setNSRTM(String nSRTM) {
		NSRTM = nSRTM;
	}
	public String getTXTVL() {
		return TXTVL;
	}
	public void setTXTVL(String tXTVL) {
		TXTVL = tXTVL;
	}
}
