package kr.ondoc.domain.sybase.legacy.ondocplus;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class ManlessQuestionVO extends CommonVO {
	List<ManlessQuestionDataVO> data = new ArrayList<ManlessQuestionDataVO>();

	public List<ManlessQuestionDataVO> getData() {
		return data;
	}

	public void setData(List<ManlessQuestionDataVO> data) {
		this.data = data;
	}

}
