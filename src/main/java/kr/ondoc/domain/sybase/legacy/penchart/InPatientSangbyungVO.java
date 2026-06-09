package kr.ondoc.domain.sybase.legacy.penchart;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class InPatientSangbyungVO extends CommonVO {
	List<InPatientSangbyungDataVO> data = new ArrayList<InPatientSangbyungDataVO>();

	public List<InPatientSangbyungDataVO> getData() {
		return data;
	}

	public void setData(List<InPatientSangbyungDataVO> data) {
		this.data = data;
	}
}
