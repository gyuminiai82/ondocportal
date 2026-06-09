package kr.ondoc.domain.sybase.penchart;

public class PatientVO {
	String ptno = "";		//차트번호
	//접수정보
	String regno = "";		//접수일련번호
	String reg_date = "";	//접수일자
	String medroom = "";		//진료실번호
	String jin_gubun = "";	//내원목적-01:일반진료 02:검사결과 03:예방주사 04:물리치료 05:약수령   06:필름복사 07:차트복사 08:진단서발급 09:DC처방   
						//10:신체검사 11:Consult  12:시설내 진료 13:검사접수 14:방사접수 15:건강검진 16:검안접수 17:산재병행 18:수술 20:잠복결핵검사 21:잠복결핵치료
						//22:여성청소년 HPV예방접종 23:일반검진후고혈압당뇨확진진료, 24:맞춤형검진바우처, 25:검진연동접수
						//26:검안접수-산동, 27:검안접수-CR 99:아름누리검진접수
	
	String wait_class = "";	//대기자구분(0:응급, 1:일반, 4:예약, 7:보류완료,8:보류, 9:입원수속대기, E:검안대기(DR연동))M:모바일대기(phone),N:무인접수(pad),D:똑닥접수, H:건강검진(새롬연동), W:웹무인접수(QR접수,코로나QR접수)
	String wait_sclass = "";	//(1:일반  0:대기 2:수정  1:진행 3:협진 4:예약 5:백신 6:낮병동 7:공단검진
	
	//입원정보
	String byungdong = "";	//병실구분
	String room = "";		//병실번호
	String in_date = "";		//입원일자
	String out_date = "";	//퇴원일자
	
	String bmr_code = "";	//진료실번호
	String bmr_name = "";	//진료실명칭
	String bmr_docname = "";	//진료의성명
	String bmr_kwanm = "";	//진료실과명칭
	
	//환자정보
	String cen = "";			//세기
	String resno = "";		//주민등록번호
	String name = ""; 		//환자성명
	String age = "";			//나이-년
	String sex = "";			//성별
	String telno = "";		//전화번호
	String hpno = "";		//핸드폰번호
	
	//기타정보
	String ch_date = "";		//진료일자
	String time = "";		//진료시간
	String enddt = "";		//최종진료일
	String kwa = "";			//진료과번호
	String jogubun = "";		//종별구분-최종
	String flag = "";		//진료중여부(Y)
	
	String bjg_name = "";	//내원구분명칭-내원목적
	String bji_name = "";	//종별명칭-종별정보
	
	public String getPtno() {
		return ptno;
	}
	public void setPtno(String ptno) {
		this.ptno = ptno;
	}
	public String getRegno() {
		return regno;
	}
	public void setRegno(String regno) {
		this.regno = regno;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getMedroom() {
		return medroom;
	}
	public void setMedroom(String medroom) {
		this.medroom = medroom;
	}
	public String getJin_gubun() {
		return jin_gubun;
	}
	public void setJin_gubun(String jin_gubun) {
		this.jin_gubun = jin_gubun;
	}
	public String getWait_class() {
		return wait_class;
	}
	public void setWait_class(String wait_class) {
		this.wait_class = wait_class;
	}
	public String getWait_sclass() {
		return wait_sclass;
	}
	public void setWait_sclass(String wait_sclass) {
		this.wait_sclass = wait_sclass;
	}
	public String getByungdong() {
		return byungdong;
	}
	public void setByungdong(String byungdong) {
		this.byungdong = byungdong;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public String getIn_date() {
		return in_date;
	}
	public void setIn_date(String in_date) {
		this.in_date = in_date;
	}
	public String getOut_date() {
		return out_date;
	}
	public void setOut_date(String out_date) {
		this.out_date = out_date;
	}
	public String getBmr_code() {
		return bmr_code;
	}
	public void setBmr_code(String bmr_code) {
		this.bmr_code = bmr_code;
	}
	public String getBmr_name() {
		return bmr_name;
	}
	public void setBmr_name(String bmr_name) {
		this.bmr_name = bmr_name;
	}
	public String getBmr_docname() {
		return bmr_docname;
	}
	public void setBmr_docname(String bmr_docname) {
		this.bmr_docname = bmr_docname;
	}
	public String getBmr_kwanm() {
		return bmr_kwanm;
	}
	public void setBmr_kwanm(String bmr_kwanm) {
		this.bmr_kwanm = bmr_kwanm;
	}
	public String getCen() {
		return cen;
	}
	public void setCen(String cen) {
		this.cen = cen;
	}
	public String getResno() {
		return resno;
	}
	public void setResno(String resno) {
		this.resno = resno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getTelno() {
		return telno;
	}
	public void setTelno(String telno) {
		this.telno = telno;
	}
	public String getHpno() {
		return hpno;
	}
	public void setHpno(String hpno) {
		this.hpno = hpno;
	}
	public String getCh_date() {
		return ch_date;
	}
	public void setCh_date(String ch_date) {
		this.ch_date = ch_date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getEnddt() {
		return enddt;
	}
	public void setEnddt(String enddt) {
		this.enddt = enddt;
	}
	public String getKwa() {
		return kwa;
	}
	public void setKwa(String kwa) {
		this.kwa = kwa;
	}
	public String getJogubun() {
		return jogubun;
	}
	public void setJogubun(String jogubun) {
		this.jogubun = jogubun;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getBjg_name() {
		return bjg_name;
	}
	public void setBjg_name(String bjg_name) {
		this.bjg_name = bjg_name;
	}
	public String getBji_name() {
		return bji_name;
	}
	public void setBji_name(String bji_name) {
		this.bji_name = bji_name;
	}
}
