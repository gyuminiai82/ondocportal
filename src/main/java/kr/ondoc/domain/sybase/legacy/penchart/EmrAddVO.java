package kr.ondoc.domain.sybase.legacy.penchart;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class EmrAddVO extends CommonVO{
	List<EmrAddDataVO> data = new ArrayList<EmrAddDataVO>();

	public List<EmrAddDataVO> getData() {
		return data;
	}

	public void setData(List<EmrAddDataVO> data) {
		this.data = data;
	}
}
