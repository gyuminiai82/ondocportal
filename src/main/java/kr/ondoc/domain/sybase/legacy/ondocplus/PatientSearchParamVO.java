package kr.ondoc.domain.sybase.legacy.ondocplus;

public class PatientSearchParamVO {
	String search_type;
	String search_word;
	String name;
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}