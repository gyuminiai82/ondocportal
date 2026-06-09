package kr.ondoc.domain.sybase.legacy.penchart;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class TreatementResultDetailVO extends CommonVO {
	List<TreatementResultDetailDataVO> data = new ArrayList<TreatementResultDetailDataVO>();

	public List<TreatementResultDetailDataVO> getData() {
		return data;
	}

	public void setData(List<TreatementResultDetailDataVO> data) {
		this.data = data;
	}
}
