package kr.ondoc.domain.sybase.legacy.ondoc;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class MedroomVO extends CommonVO {
	List<MedroomDataVO> data = new ArrayList<MedroomDataVO>();

	public List<MedroomDataVO> getData() {
		return data;
	}

	public void setData(List<MedroomDataVO> data) {
		this.data = data;
	}
}
