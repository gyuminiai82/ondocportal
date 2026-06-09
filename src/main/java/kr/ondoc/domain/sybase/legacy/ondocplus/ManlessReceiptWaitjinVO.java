package kr.ondoc.domain.sybase.legacy.ondocplus;

public class ManlessReceiptWaitjinVO {
	String sRegCnt = "";	/* 접수일련번호 */
	String sPtno = "";		/* 차트번호 */
	String medroom = "";	/* 진료실번호 */
	String sKwa = "";		/* 진료과번호 */
	String name = "";		/* 환자성명 */
	String jumin1 = "";		/* 주민번호 */
	String sYage = "";		/* 나이-년 */
	String sMage = "";		/* 나이-월 */
	String sex = "";		/* 성별(M,F) */
	String jin_gubun = "";	/* 내원목적 */
	String jin_kind = "";	/* 내원구분1(초,재) */
	String jin_time = "1";	/* 0:신환,1:과초진,2:초진,5:재진,6:물리치료,7:가족내원 */
	String sClass = "";		/* 대기자구분(0:응급, 1:일반, 8:보류, M:모바일-201701추가, N:무인접수(스마트데스크), W:웹간편접수(온닥데스크) ) */
	String wait_sclass = "";//rwj_wait_sclass
	String other_room = "";	/* 타과협진 진료실번호 */
	String sJogubun = "";	/* 자격구분 */
	String qua_seq = "1";	/* 자격일련번호 */
	String salecd = "";		/* 할인코드 */
	String salerate = "";	/* 할인률 */
	String excp = "";		/* 환자예외코드 */
	String jin_ref = "";	/* 진료실 참조 */
	String reserve_memo = "";/* 진료보류 메모 */
	String findchart = "";	/* 의무기록지 검색완료여부 */
	String mngrno = "";		/* 요양기관기호 */
	String flag = "";		/* 진료중 여부(Y) */
	String nextchoday = "";	
	String nanchicode = "";
	String pregnant = "";
	String nosound = "";
	String classNWanja = "";//rwj_tmp3  모바일접수시 내방여부:OY, 미내방:N
	String company = "";	//* 현대아주 - 소속사관리(온닥앱에서 접수시)*/
	
	public String getsRegCnt() {
		return sRegCnt;
	}
	public void setsRegCnt(String sRegCnt) {
		this.sRegCnt = sRegCnt;
	}
	public String getsPtno() {
		return sPtno;
	}
	public void setsPtno(String sPtno) {
		this.sPtno = sPtno;
	}
	public String getMedroom() {
		return medroom;
	}
	public void setMedroom(String medroom) {
		this.medroom = medroom;
	}
	public String getsKwa() {
		return sKwa;
	}
	public void setsKwa(String sKwa) {
		this.sKwa = sKwa;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getJumin1() {
		return jumin1;
	}
	public void setJumin1(String jumin1) {
		this.jumin1 = jumin1;
	}
	public String getsYage() {
		return sYage;
	}
	public void setsYage(String sYage) {
		this.sYage = sYage;
	}
	public String getsMage() {
		return sMage;
	}
	public void setsMage(String sMage) {
		this.sMage = sMage;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getJin_gubun() {
		return jin_gubun;
	}
	public void setJin_gubun(String jin_gubun) {
		this.jin_gubun = jin_gubun;
	}
	public String getJin_kind() {
		return jin_kind;
	}
	public void setJin_kind(String jin_kind) {
		this.jin_kind = jin_kind;
	}
	public String getJin_time() {
		return jin_time;
	}
	public void setJin_time(String jin_time) {
		this.jin_time = jin_time;
	}
	public String getsClass() {
		return sClass;
	}
	public void setsClass(String sClass) {
		this.sClass = sClass;
	}
	public String getWait_sclass() {
		return wait_sclass;
	}
	public void setWait_sclass(String wait_sclass) {
		this.wait_sclass = wait_sclass;
	}
	public String getOther_room() {
		return other_room;
	}
	public void setOther_room(String other_room) {
		this.other_room = other_room;
	}
	public String getsJogubun() {
		return sJogubun;
	}
	public void setsJogubun(String sJogubun) {
		this.sJogubun = sJogubun;
	}
	public String getQua_seq() {
		return qua_seq;
	}
	public void setQua_seq(String qua_seq) {
		this.qua_seq = qua_seq;
	}
	public String getSalecd() {
		return salecd;
	}
	public void setSalecd(String salecd) {
		this.salecd = salecd;
	}
	public String getSalerate() {
		return salerate;
	}
	public void setSalerate(String salerate) {
		this.salerate = salerate;
	}
	public String getExcp() {
		return excp;
	}
	public void setExcp(String excp) {
		this.excp = excp;
	}
	public String getJin_ref() {
		return jin_ref;
	}
	public void setJin_ref(String jin_ref) {
		this.jin_ref = jin_ref;
	}
	public String getReserve_memo() {
		return reserve_memo;
	}
	public void setReserve_memo(String reserve_memo) {
		this.reserve_memo = reserve_memo;
	}
	public String getFindchart() {
		return findchart;
	}
	public void setFindchart(String findchart) {
		this.findchart = findchart;
	}
	public String getMngrno() {
		return mngrno;
	}
	public void setMngrno(String mngrno) {
		this.mngrno = mngrno;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getNextchoday() {
		return nextchoday;
	}
	public void setNextchoday(String nextchoday) {
		this.nextchoday = nextchoday;
	}
	public String getNanchicode() {
		return nanchicode;
	}
	public void setNanchicode(String nanchicode) {
		this.nanchicode = nanchicode;
	}
	public String getPregnant() {
		return pregnant;
	}
	public void setPregnant(String pregnant) {
		this.pregnant = pregnant;
	}
	public String getNosound() {
		return nosound;
	}
	public void setNosound(String nosound) {
		this.nosound = nosound;
	}
	public String getClassNWanja() {
		return classNWanja;
	}
	public void setClassNWanja(String classNWanja) {
		this.classNWanja = classNWanja;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
}
