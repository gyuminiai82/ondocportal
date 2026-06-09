package kr.ondoc.domain.sybase.legacy.penchart;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class EmrSavedVO extends CommonVO {
	List<EmrSavedDataVO> data = new ArrayList<EmrSavedDataVO>();

	public List<EmrSavedDataVO> getData() {
		return data;
	}

	public void setData(List<EmrSavedDataVO> data) {
		this.data = data;
	}
}
