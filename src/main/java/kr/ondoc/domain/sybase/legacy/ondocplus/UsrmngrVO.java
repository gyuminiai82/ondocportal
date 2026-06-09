package kr.ondoc.domain.sybase.legacy.ondocplus;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class UsrmngrVO extends CommonVO {
	List<UsrmngrDataVO> data = new ArrayList<UsrmngrDataVO>();

	public List<UsrmngrDataVO> getData() {
		return data;
	}

	public void setData(List<UsrmngrDataVO> data) {
		this.data = data;
	}
}
