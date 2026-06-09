package kr.ondoc.domain.sybase.legacy.ondoc;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class WorkPlanVO extends CommonVO {
	List<WorkPlanDataVO> data = new ArrayList<WorkPlanDataVO>();

	public List<WorkPlanDataVO> getData() {
		return data;
	}

	public void setData(List<WorkPlanDataVO> data) {
		this.data = data;
	}
}
