package kr.ondoc.domain.sybase.legacy.penchart;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class TreatementResultVO extends CommonVO {
	List<TreatementResultDataVO> data = new ArrayList<TreatementResultDataVO>();

	public List<TreatementResultDataVO> getData() {
		return data;
	}

	public void setData(List<TreatementResultDataVO> data) {
		this.data = data;
	}
}
