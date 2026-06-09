package kr.ondoc.domain.sybase.crm;

public class CrmStatisticsWeekdaySalesVO {
	String weekday = "";
	String reserve_count = "0";
	String reserve_cancel_count = "0";
	String reserve_no_visit = "0";
	String reserve_visit = "0";
	String sale_count = "0";
	String charge_price = "0";
	String sale_after_price = "0";
	String cnt_price = "0";
	
	public String getWeekday() {
		return weekday;
	}
	public void setWeekday(String weekday) {
		this.weekday = weekday;
	}
	public String getReserve_count() {
		return reserve_count;
	}
	public void setReserve_count(String reserve_count) {
		this.reserve_count = reserve_count;
	}
	public String getReserve_cancel_count() {
		return reserve_cancel_count;
	}
	public void setReserve_cancel_count(String reserve_cancel_count) {
		this.reserve_cancel_count = reserve_cancel_count;
	}
	public String getReserve_no_visit() {
		return reserve_no_visit;
	}
	public void setReserve_no_visit(String reserve_no_visit) {
		this.reserve_no_visit = reserve_no_visit;
	}
	public String getReserve_visit() {
		return reserve_visit;
	}
	public void setReserve_visit(String reserve_visit) {
		this.reserve_visit = reserve_visit;
	}
	public String getSale_count() {
		return sale_count;
	}
	public void setSale_count(String sale_count) {
		this.sale_count = sale_count;
	}
	public String getCharge_price() {
		return charge_price;
	}
	public void setCharge_price(String charge_price) {
		this.charge_price = charge_price;
	}
	public String getSale_after_price() {
		return sale_after_price;
	}
	public void setSale_after_price(String sale_after_price) {
		this.sale_after_price = sale_after_price;
	}
	public String getCnt_price() {
		return cnt_price;
	}
	public void setCnt_price(String cnt_price) {
		this.cnt_price = cnt_price;
	}
}
