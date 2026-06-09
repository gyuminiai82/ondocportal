package kr.ondoc.domain.sybase.legacy.ondocplus;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class IpwonPatientSangByungVO extends CommonVO {
	List<IpwonPatientSangByungDataVO> data = new ArrayList<IpwonPatientSangByungDataVO>();

	public List<IpwonPatientSangByungDataVO> getData() {
		return data;
	}

	public void setData(List<IpwonPatientSangByungDataVO> data) {
		this.data = data;
	}
}
