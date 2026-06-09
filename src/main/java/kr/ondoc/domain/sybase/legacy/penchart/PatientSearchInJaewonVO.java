package kr.ondoc.domain.sybase.legacy.penchart;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class PatientSearchInJaewonVO extends CommonVO {
	List<PatientSearchInJaewonDataVO> data = new ArrayList<PatientSearchInJaewonDataVO>();

	public List<PatientSearchInJaewonDataVO> getData() {
		return data;
	}

	public void setData(List<PatientSearchInJaewonDataVO> data) {
		this.data = data;
	}
}
