package kr.ondoc.domain.sybase.crm;

public class PagingParamVO {
	String fnName = "";
	String page = "1";
	String listNum = "15";
	String blockNum = "10";
	
	String first = "1";
	String sort = "bpt_enddt";
	String sortDirection = "DESC";
	String search_type = "";
	String search_word = "";
	
	public String getFnName() {
		return fnName;
	}
	public void setFnName(String fnName) {
		this.fnName = fnName;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getListNum() {
		return listNum;
	}
	public void setListNum(String listNum) {
		this.listNum = listNum;
	}
	public String getBlockNum() {
		return blockNum;
	}
	public void setBlockNum(String blockNum) {
		this.blockNum = blockNum;
	}
	public String getFirst() {
		return first;
	}
	public void setFirst(String first) {
		this.first = first;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getSortDirection() {
		return sortDirection;
	}
	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}
	public String getSearch_type() {
		return search_type;
	}
	public void setSearch_type(String search_type) {
		this.search_type = search_type;
	}
	public String getSearch_word() {
		return search_word;
	}
	public void setSearch_word(String search_word) {
		this.search_word = search_word;
	}
}
