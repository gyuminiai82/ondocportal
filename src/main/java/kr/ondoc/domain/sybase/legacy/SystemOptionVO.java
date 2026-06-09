package kr.ondoc.domain.sybase.legacy;

import java.util.ArrayList;
import java.util.List;

public class SystemOptionVO extends CommonVO {
	List<SystemOptionDataVO> data = new ArrayList<SystemOptionDataVO>();

	public List<SystemOptionDataVO> getData() {
		return data;
	}

	public void setData(List<SystemOptionDataVO> data) {
		this.data = data;
	}
}
