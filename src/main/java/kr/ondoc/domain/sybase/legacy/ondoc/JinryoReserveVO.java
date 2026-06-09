package kr.ondoc.domain.sybase.legacy.ondoc;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class JinryoReserveVO extends CommonVO {
	List<JinryoReserveDataVO> data = new ArrayList<JinryoReserveDataVO>();

	public List<JinryoReserveDataVO> getData() {
		return data;
	}

	public void setData(List<JinryoReserveDataVO> data) {
		this.data = data;
	}
}
