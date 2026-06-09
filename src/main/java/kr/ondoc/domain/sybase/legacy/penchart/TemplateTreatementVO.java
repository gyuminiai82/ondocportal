package kr.ondoc.domain.sybase.legacy.penchart;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class TemplateTreatementVO extends CommonVO {
	List<TemplateTreatementDataVO> data = new ArrayList<TemplateTreatementDataVO>();

	public List<TemplateTreatementDataVO> getData() {
		return data;
	}

	public void setData(List<TemplateTreatementDataVO> data) {
		this.data = data;
	}
}
