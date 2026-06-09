package kr.ondoc.domain.sybase.legacy.ondocplus;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class SupportStateTotVO extends CommonVO {
	List<SupportStateTotDataVO> data = new ArrayList<SupportStateTotDataVO>();

	public List<SupportStateTotDataVO> getData() {
		return data;
	}

	public void setData(List<SupportStateTotDataVO> data) {
		this.data = data;
	}
}
