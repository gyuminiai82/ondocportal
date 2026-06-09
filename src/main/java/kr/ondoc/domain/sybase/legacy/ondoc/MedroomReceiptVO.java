package kr.ondoc.domain.sybase.legacy.ondoc;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class MedroomReceiptVO extends CommonVO {
	List<MedroomReceiptDataVO> data = new ArrayList<MedroomReceiptDataVO>();

	public List<MedroomReceiptDataVO> getData() {
		return data;
	}

	public void setData(List<MedroomReceiptDataVO> data) {
		this.data = data;
	}
}
