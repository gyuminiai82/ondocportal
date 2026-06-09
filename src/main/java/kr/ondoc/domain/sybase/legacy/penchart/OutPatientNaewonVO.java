package kr.ondoc.domain.sybase.legacy.penchart;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class OutPatientNaewonVO  extends CommonVO {
	List<OutPatientNaewonDataVO> data = new ArrayList<OutPatientNaewonDataVO>();

	public List<OutPatientNaewonDataVO> getData() {
		return data;
	}

	public void setData(List<OutPatientNaewonDataVO> data) {
		this.data = data;
	}
}
