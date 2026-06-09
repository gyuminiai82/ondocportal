package kr.ondoc.domain.sybase.legacy.penchart;

import java.util.ArrayList;
import java.util.List;

public class PatientSearchFormDataVO {
	List<PatientSearchFormDataByungdongVO> byungdong = new ArrayList<PatientSearchFormDataByungdongVO>();
	List<PatientSearchFormDataMedroomVO> medroom = new ArrayList<PatientSearchFormDataMedroomVO>();

	public List<PatientSearchFormDataByungdongVO> getByungdong() {
		return byungdong;
	}

	public void setByungdong(List<PatientSearchFormDataByungdongVO> byungdong) {
		this.byungdong = byungdong;
	}
	

	public List<PatientSearchFormDataMedroomVO> getMedroom() {
		return medroom;
	}

	public void setMedroom(List<PatientSearchFormDataMedroomVO> medroom) {
		this.medroom = medroom;
	}
}
