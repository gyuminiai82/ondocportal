package kr.ondoc.domain.sybase.legacy.penchart;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class EmrAuthVO extends CommonVO {
	List<EmrAuthDataVO> data = new ArrayList<EmrAuthDataVO>();

	public List<EmrAuthDataVO> getData() {
		return data;
	}

	public void setData(List<EmrAuthDataVO> data) {
		this.data = data;
	}
}
