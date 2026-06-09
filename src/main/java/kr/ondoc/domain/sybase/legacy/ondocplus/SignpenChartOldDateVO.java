package kr.ondoc.domain.sybase.legacy.ondocplus;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class SignpenChartOldDateVO extends CommonVO {
	List<SignpenChartOldDateDataVO> data = new ArrayList<SignpenChartOldDateDataVO>();

	public List<SignpenChartOldDateDataVO> getData() {
		return data;
	}

	public void setData(List<SignpenChartOldDateDataVO> data) {
		this.data = data;
	}
}
