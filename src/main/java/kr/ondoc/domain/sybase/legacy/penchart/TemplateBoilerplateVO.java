package kr.ondoc.domain.sybase.legacy.penchart;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class TemplateBoilerplateVO extends CommonVO {
	List<TemplateBoilerplateDataVO> data = new ArrayList<TemplateBoilerplateDataVO>();

	public List<TemplateBoilerplateDataVO> getData() {
		return data;
	}

	public void setData(List<TemplateBoilerplateDataVO> data) {
		this.data = data;
	}
}
