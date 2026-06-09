package kr.ondoc.domain.sybase.legacy.penchart;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class InPatientSymptomVO extends CommonVO {
	List<InPatientSymptomDataVO> data = new ArrayList<InPatientSymptomDataVO>();

	public List<InPatientSymptomDataVO> getData() {
		return data;
	}

	public void setData(List<InPatientSymptomDataVO> data) {
		this.data = data;
	}
}
