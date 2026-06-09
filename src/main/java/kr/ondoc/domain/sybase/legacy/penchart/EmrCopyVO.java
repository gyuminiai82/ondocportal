package kr.ondoc.domain.sybase.legacy.penchart;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class EmrCopyVO extends CommonVO {
	List<EmrCopyDataVo> data = new ArrayList<EmrCopyDataVo>();

	public List<EmrCopyDataVo> getData() {
		return data;
	}

	public void setData(List<EmrCopyDataVo> data) {
		this.data = data;
	}
}
