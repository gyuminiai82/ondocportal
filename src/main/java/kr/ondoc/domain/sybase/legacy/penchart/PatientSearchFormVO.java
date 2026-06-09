package kr.ondoc.domain.sybase.legacy.penchart;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;
import kr.ondoc.domain.sybase.legacy.SystemOptionDataVO;

public class PatientSearchFormVO extends CommonVO {
	PatientSearchFormDataVO data = new PatientSearchFormDataVO();

	public PatientSearchFormDataVO getData() {
		return data;
	}

	public void setData(PatientSearchFormDataVO data) {
		this.data = data;
	}
}
