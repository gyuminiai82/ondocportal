package kr.ondoc.domain.sybase.legacy.penchart;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class OcsViaVO extends CommonVO {
	List<OcsViaDataVO> data = new ArrayList<OcsViaDataVO>();

	public List<OcsViaDataVO> getData() {
		return data;
	}

	public void setData(List<OcsViaDataVO> data) {
		this.data = data;
	}
}
