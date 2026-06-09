package kr.ondoc.domain.sybase.legacy.penchart;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class LoginVO extends CommonVO {
	List<LoginDataVO> data = new ArrayList<LoginDataVO>();

	public List<LoginDataVO> getData() {
		return data;
	}

	public void setData(List<LoginDataVO> data) {
		this.data = data;
	}
}
