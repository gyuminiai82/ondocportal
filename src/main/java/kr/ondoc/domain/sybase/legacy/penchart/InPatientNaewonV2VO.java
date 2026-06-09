package kr.ondoc.domain.sybase.legacy.penchart;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class InPatientNaewonV2VO extends CommonVO {
	List<InPatientNaewonV2DataVO> data = new ArrayList<InPatientNaewonV2DataVO>();

	public List<InPatientNaewonV2DataVO> getData() {
		return data;
	}

	public void setData(List<InPatientNaewonV2DataVO> data) {
		this.data = data;
	}
}
