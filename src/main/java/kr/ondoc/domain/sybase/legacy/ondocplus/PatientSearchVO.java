package kr.ondoc.domain.sybase.legacy.ondocplus;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.PatientSearchDataVO;

public class PatientSearchVO extends CommonVO {
	List<PatientSearchDataVO> data = new ArrayList<PatientSearchDataVO>();

	public List<PatientSearchDataVO> getData() {
		return data;
	}

	public void setData(List<PatientSearchDataVO> data) {
		this.data = data;
	}
}
