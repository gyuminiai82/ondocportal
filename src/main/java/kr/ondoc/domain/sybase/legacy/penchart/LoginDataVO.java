package kr.ondoc.domain.sybase.legacy.penchart;

public class LoginDataVO {
	String id = "";
	String check = "false";
	String medroom = "";
	String name = "";
	String resno = "";
	String pass = "";
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCheck() {
		return check;
	}
	public void setCheck(String check) {
		this.check = check;
	}
	public String getMedroom() {
		return medroom;
	}
	public void setMedroom(String medroom) {
		this.medroom = medroom;
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
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
}
