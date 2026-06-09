package kr.ondoc.domain.sybase.legacy.penchart;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class TprVitalHangVO extends CommonVO {
	List<TprVitalHangDataVO> data = new ArrayList<TprVitalHangDataVO>();

	public List<TprVitalHangDataVO> getData() {
		return data;
	}

	public void setData(List<TprVitalHangDataVO> data) {
		this.data = data;
	}
}
