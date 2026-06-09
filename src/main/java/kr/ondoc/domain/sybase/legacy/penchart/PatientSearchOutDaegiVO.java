package kr.ondoc.domain.sybase.legacy.penchart;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class PatientSearchOutDaegiVO extends CommonVO {
	List<PatientSearchOutDaegiDataVO> data = new ArrayList<PatientSearchOutDaegiDataVO>();

	public List<PatientSearchOutDaegiDataVO> getData() {
		return data;
	}

	public void setData(List<PatientSearchOutDaegiDataVO> data) {
		this.data = data;
	}
}
