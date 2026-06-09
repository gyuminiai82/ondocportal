package kr.ondoc.domain.sybase.crm;

public class CrmTreatementPaymentUnpaidLogVO extends PagingParamVO {
	String log_idx = "";
	String payment_idx = "";
	String payment_price = "";
	String payment_type = "";
	String card_gubun = "";
	String del_yn = "";
	String write_date = "";
	String write_id = "";
	String del_date = "";
	String del_id = "";
	
	String bpt_ptno = "";
	String bpt_name = "";
	String bpt_hpno = "";
	
	public String getLog_idx() {
		return log_idx;
	}
	public void setLog_idx(String log_idx) {
		this.log_idx = log_idx;
	}
	public String getPayment_idx() {
		return payment_idx;
	}
	public void setPayment_idx(String payment_idx) {
		this.payment_idx = payment_idx;
	}
	public String getPayment_price() {
		return payment_price;
	}
	public void setPayment_price(String payment_price) {
		this.payment_price = payment_price;
	}
	public String getPayment_type() {
		return payment_type;
	}
	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}
	public String getCard_gubun() {
		return card_gubun;
	}
	public void setCard_gubun(String card_gubun) {
		this.card_gubun = card_gubun;
	}
	public String getDel_yn() {
		return del_yn;
	}
	public void setDel_yn(String del_yn) {
		this.del_yn = del_yn;
	}
	public String getWrite_date() {
		return write_date;
	}
	public void setWrite_date(String write_date) {
		this.write_date = write_date;
	}
	public String getWrite_id() {
		return write_id;
	}
	public void setWrite_id(String write_id) {
		this.write_id = write_id;
	}
	public String getDel_date() {
		return del_date;
	}
	public void setDel_date(String del_date) {
		this.del_date = del_date;
	}
	public String getDel_id() {
		return del_id;
	}
	public void setDel_id(String del_id) {
		this.del_id = del_id;
	}
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
	public String getBpt_hpno() {
		return bpt_hpno;
	}
	public void setBpt_hpno(String bpt_hpno) {
		this.bpt_hpno = bpt_hpno;
	}
}
