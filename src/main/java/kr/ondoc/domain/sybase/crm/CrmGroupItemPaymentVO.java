package kr.ondoc.domain.sybase.crm;

public class CrmGroupItemPaymentVO {
	String item_name = "";
	String item_idx = "";
	String item_name_detail = "";
	String item_detail_idx = "";
	
	String count = "";
	String charge_price = "";
	String sale_price = "";
	String sale_after_price = "";
	
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public String getItem_idx() {
		return item_idx;
	}
	public void setItem_idx(String item_idx) {
		this.item_idx = item_idx;
	}
	public String getItem_name_detail() {
		return item_name_detail;
	}
	public void setItem_name_detail(String item_name_detail) {
		this.item_name_detail = item_name_detail;
	}
	public String getItem_detail_idx() {
		return item_detail_idx;
	}
	public void setItem_detail_idx(String item_detail_idx) {
		this.item_detail_idx = item_detail_idx;
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
