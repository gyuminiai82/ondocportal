package kr.ondoc.domain.sybase.legacy.penchart;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class NaewonYearVO extends CommonVO{
	List<NaewonYearDataVO> data = new ArrayList<NaewonYearDataVO>();

	public List<NaewonYearDataVO> getData() {
		return data;
	}

	public void setData(List<NaewonYearDataVO> data) {
		this.data = data;
	}
}
