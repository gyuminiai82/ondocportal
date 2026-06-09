package kr.ondoc.domain.sybase.legacy.penchart;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class InPatientCheobangVO extends CommonVO {
	List<InPatientCheobangDataVO> data = new ArrayList<InPatientCheobangDataVO>();

	public List<InPatientCheobangDataVO> getData() {
		return data;
	}

	public void setData(List<InPatientCheobangDataVO> data) {
		this.data = data;
	}
}
