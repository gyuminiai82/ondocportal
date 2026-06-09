package kr.ondoc.domain.sybase.legacy.ondocplus;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class IpwonPatientSymptomVO extends CommonVO  {
	List<IpwonPatientSymptomDataVO> data = new ArrayList<IpwonPatientSymptomDataVO>();

	public List<IpwonPatientSymptomDataVO> getData() {
		return data;
	}

	public void setData(List<IpwonPatientSymptomDataVO> data) {
		this.data = data;
	}
}
