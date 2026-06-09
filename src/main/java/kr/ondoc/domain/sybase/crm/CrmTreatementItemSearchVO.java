package kr.ondoc.domain.sybase.crm;

public class CrmTreatementItemSearchVO {
	String item_idx = "";
	String item_name = "";
	String item_detail_idx = "";
	String item_detail_name = "";
	String price = "";
	String sale_price = "";
	String item_count = "";
	
	public String getItem_idx() {
		return item_idx;
	}
	public void setItem_idx(String item_idx) {
		this.item_idx = item_idx;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public String getItem_detail_idx() {
		return item_detail_idx;
	}
	public void setItem_detail_idx(String item_detail_idx) {
		this.item_detail_idx = item_detail_idx;
	}
	public String getItem_detail_name() {
		return item_detail_name;
	}
	public void setItem_detail_name(String item_detail_name) {
		this.item_detail_name = item_detail_name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getSale_price() {
		return sale_price;
	}
	public void setSale_price(String sale_price) {
		this.sale_price = sale_price;
	}
	public String getItem_count() {
		return item_count;
	}
	public void setItem_count(String item_count) {
		this.item_count = item_count;
	}
}
