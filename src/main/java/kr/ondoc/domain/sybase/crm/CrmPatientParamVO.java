package kr.ondoc.domain.sybase.crm;

public class CrmPatientParamVO extends PagingParamVO {
	String jogubun = "";
	String ptno = "";
	String name = "";
	String resno = "";
	String hpno = "";
	String jubsumemo = "";
	String sex = "";
	String birthday = "";
	String searchType = "";
	String searchWord = "";
	
	public String getJogubun() {
		return jogubun;
	}
	public void setJogubun(String jogubun) {
		this.jogubun = jogubun;
	}
	public String getPtno() {
		return ptno;
	}
	public void setPtno(String ptno) {
		this.ptno = ptno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getResno() {
		return resno;
	}
	public void setResno(String resno) {
		this.resno = resno;
	}
	public String getHpno() {
		return hpno;
	}
	public void setHpno(String hpno) {
		this.hpno = hpno;
	}
	public String getJubsumemo() {
		return jubsumemo;
	}
	public void setJubsumemo(String jubsumemo) {
		this.jubsumemo = jubsumemo;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getSearchWord() {
		return searchWord;
	}
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}
}
