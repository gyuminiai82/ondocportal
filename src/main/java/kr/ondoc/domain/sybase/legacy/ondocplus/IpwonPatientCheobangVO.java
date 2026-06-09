package kr.ondoc.domain.sybase.legacy.ondocplus;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class IpwonPatientCheobangVO extends CommonVO  {
	List<IpwonPatientCheobangDataVO> data = new ArrayList<IpwonPatientCheobangDataVO>();

	public List<IpwonPatientCheobangDataVO> getData() {
		return data;
	}

	public void setData(List<IpwonPatientCheobangDataVO> data) {
		this.data = data;
	}
}
