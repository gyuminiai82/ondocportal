package kr.ondoc.domain.sybase.legacy.ondocplus;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class SupportReceiptStateVO extends CommonVO {
	List<SupportReceiptStateDataVO> data = new ArrayList<SupportReceiptStateDataVO>();

	public List<SupportReceiptStateDataVO> getData() {
		return data;
	}

	public void setData(List<SupportReceiptStateDataVO> data) {
		this.data = data;
	}
}
