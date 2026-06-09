package kr.ondoc.domain.sybase.healthcheck;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class HealthCheckVO extends CommonVO {
	List<HealthCheckDataVO> data = new ArrayList<HealthCheckDataVO>();

	public List<HealthCheckDataVO> getData() {
		return data;
	}

	public void setData(List<HealthCheckDataVO> data) {
		this.data = data;
	}
}
