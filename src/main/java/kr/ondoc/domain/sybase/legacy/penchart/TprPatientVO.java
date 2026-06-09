package kr.ondoc.domain.sybase.legacy.penchart;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class TprPatientVO extends CommonVO {
	List<TprPatientDataVO> data = new ArrayList<TprPatientDataVO>();

	public List<TprPatientDataVO> getData() {
		return data;
	}

	public void setData(List<TprPatientDataVO> data) {
		this.data = data;
	}
}
