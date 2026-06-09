package kr.ondoc.domain.sybase.legacy.penchart;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class OutPatientCheobangVO extends CommonVO {
	List<OutPatientCheobangDataVO> data = new ArrayList<OutPatientCheobangDataVO>();

	public List<OutPatientCheobangDataVO> getData() {
		return data;
	}

	public void setData(List<OutPatientCheobangDataVO> data) {
		this.data = data;
	}
}
