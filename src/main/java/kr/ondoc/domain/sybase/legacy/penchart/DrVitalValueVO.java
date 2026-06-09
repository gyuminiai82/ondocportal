package kr.ondoc.domain.sybase.legacy.penchart;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class DrVitalValueVO extends CommonVO {
	List<DrVitalValueDataVO> data = new ArrayList<DrVitalValueDataVO>();

	public List<DrVitalValueDataVO> getData() {
		return data;
	}

	public void setData(List<DrVitalValueDataVO> data) {
		this.data = data;
	}
}
