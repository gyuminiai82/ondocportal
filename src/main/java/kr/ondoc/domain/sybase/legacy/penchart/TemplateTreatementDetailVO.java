package kr.ondoc.domain.sybase.legacy.penchart;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class TemplateTreatementDetailVO extends CommonVO {
	List<TemplateTreatementDetailDataVO> data = new ArrayList<TemplateTreatementDetailDataVO>();

	public List<TemplateTreatementDetailDataVO> getData() {
		return data;
	}

	public void setData(List<TemplateTreatementDetailDataVO> data) {
		this.data = data;
	}
}
