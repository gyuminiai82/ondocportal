package kr.ondoc.domain.sybase.legacy.penchart;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class PatientVO extends CommonVO {
	List<PatientDataVO> data = new ArrayList<PatientDataVO>();

	public List<PatientDataVO> getData() {
		return data;
	}

	public void setData(List<PatientDataVO> data) {
		this.data = data;
	}
}
