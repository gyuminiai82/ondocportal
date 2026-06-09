package kr.ondoc.domain.sybase.legacy.ondoc;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class JinryoVO extends CommonVO {
	List<JinryoDataVO> data = new ArrayList<JinryoDataVO>();

	public List<JinryoDataVO> getData() {
		return data;
	}

	public void setData(List<JinryoDataVO> data) {
		this.data = data;
	}
}
