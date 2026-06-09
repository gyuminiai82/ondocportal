package kr.ondoc.domain.sybase.legacy.penchart;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class TemplateBoilerplateDetailVO extends CommonVO {
	List<TemplateBoilerplateDetailDataVO> data = new ArrayList<TemplateBoilerplateDetailDataVO>();

	public List<TemplateBoilerplateDetailDataVO> getData() {
		return data;
	}

	public void setData(List<TemplateBoilerplateDetailDataVO> data) {
		this.data = data;
	}
}
