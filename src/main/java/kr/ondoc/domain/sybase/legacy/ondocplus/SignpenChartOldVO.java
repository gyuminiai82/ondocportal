package kr.ondoc.domain.sybase.legacy.ondocplus;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class SignpenChartOldVO extends CommonVO {
	List<SignpenChartOldDataVO> data = new ArrayList<SignpenChartOldDataVO>();

	public List<SignpenChartOldDataVO> getData() {
		return data;
	}

	public void setData(List<SignpenChartOldDataVO> data) {
		this.data = data;
	}
}
