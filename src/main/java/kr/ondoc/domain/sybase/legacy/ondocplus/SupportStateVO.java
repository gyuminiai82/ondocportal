package kr.ondoc.domain.sybase.legacy.ondocplus;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class SupportStateVO extends CommonVO {
	List<SupportStateDataVO> data = new ArrayList<SupportStateDataVO>();

	public List<SupportStateDataVO> getData() {
		return data;
	}

	public void setData(List<SupportStateDataVO> data) {
		this.data = data;
	}
}
