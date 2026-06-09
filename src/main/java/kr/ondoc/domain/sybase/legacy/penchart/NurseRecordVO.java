package kr.ondoc.domain.sybase.legacy.penchart;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class NurseRecordVO extends CommonVO {
	List<NurseRecordDataVO> data = new ArrayList<NurseRecordDataVO>();

	public List<NurseRecordDataVO> getData() {
		return data;
	}

	public void setData(List<NurseRecordDataVO> data) {
		this.data = data;
	}
}
