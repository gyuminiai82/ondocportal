package kr.ondoc.domain.sybase.legacy.penchart;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class InPatientNaewonVO extends CommonVO {
	List<InPatientNaewonDataVO> data = new ArrayList<InPatientNaewonDataVO>();

	public List<InPatientNaewonDataVO> getData() {
		return data;
	}

	public void setData(List<InPatientNaewonDataVO> data) {
		this.data = data;
	}
}
