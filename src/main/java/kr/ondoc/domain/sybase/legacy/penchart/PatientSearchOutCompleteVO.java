package kr.ondoc.domain.sybase.legacy.penchart;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class PatientSearchOutCompleteVO extends CommonVO {
	List<PatientSearchOutCompleteDataVO> data = new ArrayList<PatientSearchOutCompleteDataVO>();

	public List<PatientSearchOutCompleteDataVO> getData() {
		return data;
	}

	public void setData(List<PatientSearchOutCompleteDataVO> data) {
		this.data = data;
	}
}
