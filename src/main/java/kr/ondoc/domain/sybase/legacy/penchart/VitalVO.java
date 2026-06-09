package kr.ondoc.domain.sybase.legacy.penchart;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class VitalVO extends CommonVO {
	List<VitalDataVO> data = new ArrayList<VitalDataVO>();

	public List<VitalDataVO> getData() {
		return data;
	}

	public void setData(List<VitalDataVO> data) {
		this.data = data;
	}
}
