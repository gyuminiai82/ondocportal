package kr.ondoc.domain.sybase.legacy.penchart;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class TprVitalVO extends CommonVO {
	List<TprVitalDataVO> data = new ArrayList<TprVitalDataVO>();

	public List<TprVitalDataVO> getData() {
		return data;
	}

	public void setData(List<TprVitalDataVO> data) {
		this.data = data;
	}
}
