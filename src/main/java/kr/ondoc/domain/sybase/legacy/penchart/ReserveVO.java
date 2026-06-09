package kr.ondoc.domain.sybase.legacy.penchart;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class ReserveVO extends CommonVO {
	List<ReserveDataVO> data = new ArrayList<ReserveDataVO>();

	public List<ReserveDataVO> getData() {
		return data;
	}

	public void setData(List<ReserveDataVO> data) {
		this.data = data;
	}
}
