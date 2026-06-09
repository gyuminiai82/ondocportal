package kr.ondoc.domain.sybase.crm;

public class CrmOperationScheduleItemRelationVO {
	String operation_schedule_item_idx = "";
	String operation_schedule_idx = "";
	String item_idx = "";
	String item_detail_idx = "";
	
	public String getOperation_schedule_item_idx() {
		return operation_schedule_item_idx;
	}
	public void setOperation_schedule_item_idx(String operation_schedule_item_idx) {
		this.operation_schedule_item_idx = operation_schedule_item_idx;
	}
	public String getOperation_schedule_idx() {
		return operation_schedule_idx;
	}
	public void setOperation_schedule_idx(String operation_schedule_idx) {
		this.operation_schedule_idx = operation_schedule_idx;
	}
	public String getItem_idx() {
		return item_idx;
	}
	public void setItem_idx(String item_idx) {
		this.item_idx = item_idx;
	}
	public String getItem_detail_idx() {
		return item_detail_idx;
	}
	public void setItem_detail_idx(String item_detail_idx) {
		this.item_detail_idx = item_detail_idx;
	}
}