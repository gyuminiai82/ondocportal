package kr.ondoc.domain.sybase.legacy.penchart;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class VitalValueVO extends CommonVO {
	List<VitalValueDataVO> data = new ArrayList<VitalValueDataVO>();

	public List<VitalValueDataVO> getData() {
		return data;
	}

	public void setData(List<VitalValueDataVO> data) {
		this.data = data;
	}
}
