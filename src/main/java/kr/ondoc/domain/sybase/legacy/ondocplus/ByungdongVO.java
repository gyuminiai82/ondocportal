package kr.ondoc.domain.sybase.legacy.ondocplus;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class ByungdongVO extends CommonVO {
	List<ByungdongDataVO> data = new ArrayList<ByungdongDataVO>();

	public List<ByungdongDataVO> getData() {
		return data;
	}

	public void setData(List<ByungdongDataVO> data) {
		this.data = data;
	}
}
