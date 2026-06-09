package kr.ondoc.domain.sybase.penchart;

public class ChartTreeParamVO {
	String seq = "";
	String seqs = "";
	String oec_ptno = "";
	String oec_inout = "";
	String oec_regno = "";
	String xer_uid = "";
	
	String bes_kind = ""; //80정신과동의서,99동의서, 기타값:펜차트
	
	String depth1_id = "";
	String depth1_name = "";
	String depth2_id = "";
	String depth2_name = "";
	String depth3_id = "";
	String depth3_name = "";
	
	String start_date = "";
	String end_date = "";
		
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getSeqs() {
		return seqs;
	}
	public void setSeqs(String seqs) {
		this.seqs = seqs;
	}
	public String getOec_ptno() {
		return oec_ptno;
	}
	public void setOec_ptno(String oec_ptno) {
		this.oec_ptno = oec_ptno;
	}
	public String getOec_inout() {
		return oec_inout;
	}
	public void setOec_inout(String oec_inout) {
		this.oec_inout = oec_inout;
	}
	public String getOec_regno() {
		return oec_regno;
	}
	public void setOec_regno(String oec_regno) {
		this.oec_regno = oec_regno;
	}
	public String getXer_uid() {
		return xer_uid;
	}
	public void setXer_uid(String xer_uid) {
		this.xer_uid = xer_uid;
	}
	public String getBes_kind() {
		return bes_kind;
	}
	public void setBes_kind(String bes_kind) {
		this.bes_kind = bes_kind;
	}
	public String getDepth1_id() {
		return depth1_id;
	}
	public void setDepth1_id(String depth1_id) {
		this.depth1_id = depth1_id;
	}
	public String getDepth1_name() {
		return depth1_name;
	}
	public void setDepth1_name(String depth1_name) {
		this.depth1_name = depth1_name;
	}
	public String getDepth2_id() {
		return depth2_id;
	}
	public void setDepth2_id(String depth2_id) {
		this.depth2_id = depth2_id;
	}
	public String getDepth2_name() {
		return depth2_name;
	}
	public void setDepth2_name(String depth2_name) {
		this.depth2_name = depth2_name;
	}
	public String getDepth3_id() {
		return depth3_id;
	}
	public void setDepth3_id(String depth3_id) {
		this.depth3_id = depth3_id;
	}
	public String getDepth3_name() {
		return depth3_name;
	}
	public void setDepth3_name(String depth3_name) {
		this.depth3_name = depth3_name;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
}
