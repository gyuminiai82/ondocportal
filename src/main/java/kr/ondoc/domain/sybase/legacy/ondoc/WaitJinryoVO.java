package kr.ondoc.domain.sybase.legacy.ondoc;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ByungdongDataVO;

public class WaitJinryoVO extends CommonVO {
	List<WaitJinryoDataVO> data = new ArrayList<WaitJinryoDataVO>();

	public List<WaitJinryoDataVO> getData() {
		return data;
	}

	public void setData(List<WaitJinryoDataVO> data) {
		this.data = data;
	}
}
