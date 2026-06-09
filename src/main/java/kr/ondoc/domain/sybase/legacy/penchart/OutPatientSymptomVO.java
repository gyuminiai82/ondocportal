package kr.ondoc.domain.sybase.legacy.penchart;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class OutPatientSymptomVO extends CommonVO {
	List<OutPatientSymptomDataVO> data = new ArrayList<OutPatientSymptomDataVO>();

	public List<OutPatientSymptomDataVO> getData() {
		return data;
	}

	public void setData(List<OutPatientSymptomDataVO> data) {
		this.data = data;
	}
}
