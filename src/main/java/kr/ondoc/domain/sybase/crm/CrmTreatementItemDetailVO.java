package kr.ondoc.domain.sybase.crm;

public class CrmTreatementItemDetailVO {
	String treatement_item_detail_idx = "";
	String treatement_item_idx = "";
	String code = "";
	String name = "";
	String price = "";
	String sale_price = "";
	String item_count = "";
	String sort = "";
	String use_yn = "";
	String del_yn ="";
	String write_date = "";
	String write_id = "";
	String modify_date = "";
	String modify_id = "";
	String del_date = "";
	String del_id = "";
	
	public void setInitInsert(String treatement_item_idx, String name, String price, String use_yn, String del_yn, String write_id) {
		this.treatement_item_idx = treatement_item_idx;
		this.name = name;
		this.price = price;
		this.use_yn = use_yn;
		this.del_yn = del_yn;
		this.write_id = write_id;
	}
	
	public String getTreatement_item_detail_idx() {
		return treatement_item_detail_idx;
	}
	public void setTreatement_item_detail_idx(String treatement_item_detail_idx) {
		this.treatement_item_detail_idx = treatement_item_detail_idx;
	}
	public String getTreatement_item_idx() {
		return treatement_item_idx;
	}
	public void setTreatement_item_idx(String treatement_item_idx) {
		this.treatement_item_idx = treatement_item_idx;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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

	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
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
}
