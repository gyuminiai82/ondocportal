package kr.ondoc.domain.sybase.legacy.ondocplus;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class SignpenCategoryVO extends CommonVO {
	List<SignpenCategoryDataVO> data = new ArrayList<SignpenCategoryDataVO>();

	public List<SignpenCategoryDataVO> getData() {
		return data;
	}

	public void setData(List<SignpenCategoryDataVO> data) {
		this.data = data;
	}
}