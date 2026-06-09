package kr.ondoc.domain.sybase.common;

public class GroupAuthVO {
	Boolean penchart = false;	//펜차트
	Boolean consent = false;	//동의서
	Boolean treatement = false;		//진료내역
	Boolean manless = false;	//무인접수
	Boolean checknote = false;	//체크노트
	Boolean reservation = false;	//예약현황
	Boolean healthcheck = false;	//건강검진
	
	public Boolean getPenchart() {
		return penchart;
	}
	public void setPenchart(Boolean penchart) {
		this.penchart = penchart;
	}
	public Boolean getConsent() {
		return consent;
	}
	public void setConsent(Boolean consent) {
		this.consent = consent;
	}
	public Boolean getTreatement() {
		return treatement;
	}
	public void setTreatement(Boolean treatement) {
		this.treatement = treatement;
	}
	public Boolean getManless() {
		return manless;
	}
	public void setManless(Boolean manless) {
		this.manless = manless;
	}
	public Boolean getChecknote() {
		return checknote;
	}
	public void setChecknote(Boolean checknote) {
		this.checknote = checknote;
	}
	public Boolean getReservation() {
		return reservation;
	}
	public void setReservation(Boolean reservation) {
		this.reservation = reservation;
	}
	public Boolean getHealthcheck() {
		return healthcheck;
	}
	public void setHealthcheck(Boolean healthcheck) {
		this.healthcheck = healthcheck;
	}
}
