package kr.ondoc.domain.sybase.legacy.ondocplus;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class SignpenChartVO extends CommonVO{
	List<SignpenChartDataVO> data = new ArrayList<SignpenChartDataVO>();

	public List<SignpenChartDataVO> getData() {
		return data;
	}

	public void setData(List<SignpenChartDataVO> data) {
		this.data = data;
	}
}
