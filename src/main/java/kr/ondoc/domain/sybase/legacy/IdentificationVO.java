package kr.ondoc.domain.sybase.legacy;

import java.util.ArrayList;
import java.util.List;

public class IdentificationVO extends CommonVO {
	List<IdentificationDataVO> data = new ArrayList<IdentificationDataVO>();

	public List<IdentificationDataVO> getData() {
		return data;
	}

	public void setData(List<IdentificationDataVO> data) {
		this.data = data;
	}
}