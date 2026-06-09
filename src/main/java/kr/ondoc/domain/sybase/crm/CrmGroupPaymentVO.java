package kr.ondoc.domain.sybase.crm;

public class CrmGroupPaymentVO {
	String id = "";
	String idx = "";
	String count = "";
	String charge_price = "";
	String sale_price = "";
	String sale_after_price = "";
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdx() {
		return idx;
	}
	public void setIdx(String idx) {
		this.idx = idx;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
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
}
