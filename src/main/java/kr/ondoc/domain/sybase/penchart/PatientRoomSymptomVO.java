package kr.ondoc.domain.sybase.penchart;

public class PatientRoomSymptomVO {
	String medroom = "";
	String ors_symptom = "";
	String osp_symptom = "";
	
	public String getMedroom() {
		return medroom;
	}
	public void setMedroom(String medroom) {
		this.medroom = medroom;
	}
	public String getOrs_symptom() {
		return ors_symptom;
	}
	public void setOrs_symptom(String ors_symptom) {
		this.ors_symptom = ors_symptom;
	}
	public String getOsp_symptom() {
		return osp_symptom;
	}
	public void setOsp_symptom(String osp_symptom) {
		this.osp_symptom = osp_symptom;
	}
}