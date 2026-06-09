package kr.ondoc.domain.sybase.legacy.ondoc;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class ReservePhysicalVO extends CommonVO {
	List<ReservePhysicalDataVO> data = new ArrayList<ReservePhysicalDataVO>();

	public List<ReservePhysicalDataVO> getData() {
		return data;
	}

	public void setData(List<ReservePhysicalDataVO> data) {
		this.data = data;
	}
}
