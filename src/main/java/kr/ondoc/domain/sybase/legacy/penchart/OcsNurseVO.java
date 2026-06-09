package kr.ondoc.domain.sybase.legacy.penchart;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class OcsNurseVO extends CommonVO {
	List<OcsNurseDataVO> data = new ArrayList<OcsNurseDataVO>();

	public List<OcsNurseDataVO> getData() {
		return data;
	}

	public void setData(List<OcsNurseDataVO> data) {
		this.data = data;
	}
}
