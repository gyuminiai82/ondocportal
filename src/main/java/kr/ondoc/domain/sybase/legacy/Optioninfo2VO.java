package kr.ondoc.domain.sybase.legacy;

import java.util.ArrayList;
import java.util.List;

public class Optioninfo2VO extends CommonVO {
	List<Optioninfo2DavaVO> data = new ArrayList<Optioninfo2DavaVO>();

	public List<Optioninfo2DavaVO> getData() {
		return data;
	}

	public void setData(List<Optioninfo2DavaVO> data) {
		this.data = data;
	}
}
