package kr.ondoc.domain.sybase.legacy.ondocplus;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class ManlessSunapVO extends CommonVO {
	List<ManlessSunapDataVO> data = new ArrayList<ManlessSunapDataVO>();

	public List<ManlessSunapDataVO> getData() {
		return data;
	}

	public void setData(List<ManlessSunapDataVO> data) {
		this.data = data;
	}
}
