package kr.ondoc.domain.sybase.legacy.penchart;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class DrVitalFieldVO extends CommonVO {
	List<DrVitalFieldDataVO> data = new ArrayList<DrVitalFieldDataVO>();

	public List<DrVitalFieldDataVO> getData() {
		return data;
	}

	public void setData(List<DrVitalFieldDataVO> data) {
		this.data = data;
	}
}