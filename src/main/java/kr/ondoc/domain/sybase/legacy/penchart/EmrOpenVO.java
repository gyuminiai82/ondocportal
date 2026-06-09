package kr.ondoc.domain.sybase.legacy.penchart;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class EmrOpenVO extends CommonVO {
	List<EmrOpenDataVO> data = new ArrayList<EmrOpenDataVO>();

	public List<EmrOpenDataVO> getData() {
		return data;
	}

	public void setData(List<EmrOpenDataVO> data) {
		this.data = data;
	}
}
