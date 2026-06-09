package kr.ondoc.domain.sybase.legacy.penchart;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class PatientSearchInToewonVO extends CommonVO {
	List<PatientSearchInToewonDataVO> data = new ArrayList<PatientSearchInToewonDataVO>();

	public List<PatientSearchInToewonDataVO> getData() {
		return data;
	}

	public void setData(List<PatientSearchInToewonDataVO> data) {
		this.data = data;
	}
}