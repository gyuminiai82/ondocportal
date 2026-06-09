package kr.ondoc.domain.sybase.legacy.penchart;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class EmrAttachVO extends CommonVO {
	List<EmrAttachDataVO> data = new ArrayList<EmrAttachDataVO>();

	public List<EmrAttachDataVO> getData() {
		return data;
	}

	public void setData(List<EmrAttachDataVO> data) {
		this.data = data;
	}
}