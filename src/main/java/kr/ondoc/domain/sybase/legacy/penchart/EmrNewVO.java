package kr.ondoc.domain.sybase.legacy.penchart;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class EmrNewVO extends CommonVO {
	List<EmrNewDataVO> data = new ArrayList<EmrNewDataVO>();

	public List<EmrNewDataVO> getData() {
		return data;
	}

	public void setData(List<EmrNewDataVO> data) {
		this.data = data;
	}
}
