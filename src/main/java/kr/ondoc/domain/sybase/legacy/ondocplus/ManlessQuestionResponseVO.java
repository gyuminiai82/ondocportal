package kr.ondoc.domain.sybase.legacy.ondocplus;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class ManlessQuestionResponseVO extends CommonVO {
	List<ManlessQuestionResponseDataVO> data = new ArrayList<ManlessQuestionResponseDataVO>();

	public List<ManlessQuestionResponseDataVO> getData() {
		return data;
	}

	public void setData(List<ManlessQuestionResponseDataVO> data) {
		this.data = data;
	}

}
