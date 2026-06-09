package kr.ondoc.domain.sybase.legacy.ondoc;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class WorkDateVO extends CommonVO {
	List<WorkDateDataVO> data = new ArrayList<WorkDateDataVO>();

	public List<WorkDateDataVO> getData() {
		return data;
	}

	public void setData(List<WorkDateDataVO> data) {
		this.data = data;
	}
}
