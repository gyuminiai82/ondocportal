package kr.ondoc.domain.sybase.crm;

import java.util.List;

public class CrmStatisticsPeriodPaymentVO {
	String count = "";
	String bpt_name = "";
	String bpt_yage = "";
	String bpt_birth = "";
	
	String payment_idx = "";
	String payment_date = "";
	String ptno = "";
	String charge_price = "";
	String sale_price = "";
	String sale_after_price = "";
	String payment_price = "";
	String unpaid_price = "";
	String card_price = "";
	String card_gubun = "";
	String card_installment_period = "";
	String cash_price = "";
	String cash_transfer_price = "";
	String cash_receipt_price = "";
	String cash_receipt_yn = "";
	String payment_memo = "";
	String status = "ing";
	String payment_time = "";
	String detail_arr = "";
	String del_yn = "";
	String write_date = "";
	String write_id = "";
	String modify_date = "";
	String modify_id = "";
	String del_date = "";
	String del_id = "";
	
	List<CrmTreatementPaymentDetailVO> detailList = null;
	
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getBpt_name() {
		return bpt_name;
	}
	public void setBpt_name(String bpt_name) {
		this.bpt_name = bpt_name;
	}
	public String getBpt_yage() {
		return bpt_yage;
	}
	public void setBpt_yage(String bpt_yage) {
		this.bpt_yage = bpt_yage;
	}
	public String getBpt_birth() {
		return bpt_birth;
	}
	public void setBpt_birth(String bpt_birth) {
		this.bpt_birth = bpt_birth;
	}
	public String getPayment_idx() {
		return payment_idx;
	}
	public void setPayment_idx(String payment_idx) {
		this.payment_idx = payment_idx;
	}
	public String getPayment_date() {
		return payment_date;
	}
	public void setPayment_date(String payment_date) {
		this.payment_date = payment_date;
	}
	public String getPtno() {
		return ptno;
	}
	public void setPtno(String ptno) {
		this.ptno = ptno;
	}
	public String getCharge_price() {
		return charge_price;
	}
	public void setCharge_price(String charge_price) {
		this.charge_price = charge_price;
	}
	public String getSale_price() {
		return sale_price;
	}
	public void setSale_price(String sale_price) {
		this.sale_price = sale_price;
	}
	public String getSale_after_price() {
		return sale_after_price;
	}
	public void setSale_after_price(String sale_after_price) {
		this.sale_after_price = sale_after_price;
	}
	public String getPayment_price() {
		return payment_price;
	}
	public void setPayment_price(String payment_price) {
		this.payment_price = payment_price;
	}
	public String getUnpaid_price() {
		return unpaid_price;
	}
	public void setUnpaid_price(String unpaid_price) {
		this.unpaid_price = unpaid_price;
	}
	public String getCard_price() {
		return card_price;
	}
	public void setCard_price(String card_price) {
		this.card_price = card_price;
	}
	public String getCard_gubun() {
		return card_gubun;
	}
	public void setCard_gubun(String card_gubun) {
		this.card_gubun = card_gubun;
	}
	public String getCard_installment_period() {
		return card_installment_period;
	}
	public void setCard_installment_period(String card_installment_period) {
		this.card_installment_period = card_installment_period;
	}
	public String getCash_price() {
		return cash_price;
	}
	public void setCash_price(String cash_price) {
		this.cash_price = cash_price;
	}
	public String getCash_transfer_price() {
		return cash_transfer_price;
	}
	public void setCash_transfer_price(String cash_transfer_price) {
		this.cash_transfer_price = cash_transfer_price;
	}
	public String getCash_receipt_price() {
		return cash_receipt_price;
	}
	public void setCash_receipt_price(String cash_receipt_price) {
		this.cash_receipt_price = cash_receipt_price;
	}
	public String getCash_receipt_yn() {
		return cash_receipt_yn;
	}
	public void setCash_receipt_yn(String cash_receipt_yn) {
		this.cash_receipt_yn = cash_receipt_yn;
	}
	public String getPayment_memo() {
		return payment_memo;
	}
	public void setPayment_memo(String payment_memo) {
		this.payment_memo = payment_memo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPayment_time() {
		return payment_time;
	}
	public void setPayment_time(String payment_time) {
		this.payment_time = payment_time;
	}
	public String getDetail_arr() {
		return detail_arr;
	}
	public void setDetail_arr(String detail_arr) {
		this.detail_arr = detail_arr;
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
	public String getModify_date() {
		return modify_date;
	}
	public void setModify_date(String modify_date) {
		this.modify_date = modify_date;
	}
	public String getModify_id() {
		return modify_id;
	}
	public void setModify_id(String modify_id) {
		this.modify_id = modify_id;
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
	public List<CrmTreatementPaymentDetailVO> getDetailList() {
		return detailList;
	}
	public void setDetailList(List<CrmTreatementPaymentDetailVO> detailList) {
		this.detailList = detailList;
	}
}
