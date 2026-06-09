package kr.ondoc.domain.sybase.legacy.penchart;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class OutPatientSangbyungVO extends CommonVO {
	List<OutPatientSangbyungDataVO> data = new ArrayList<OutPatientSangbyungDataVO>();

	public List<OutPatientSangbyungDataVO> getData() {
		return data;
	}

	public void setData(List<OutPatientSangbyungDataVO> data) {
		this.data = data;
	}
}
