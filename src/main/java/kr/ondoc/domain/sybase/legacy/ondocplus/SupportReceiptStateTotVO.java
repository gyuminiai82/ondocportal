package kr.ondoc.domain.sybase.legacy.ondocplus;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class SupportReceiptStateTotVO extends CommonVO {
	List<SupportReceiptStateTotDataVO> data = new ArrayList<SupportReceiptStateTotDataVO>();

	public List<SupportReceiptStateTotDataVO> getData() {
		return data;
	}

	public void setData(List<SupportReceiptStateTotDataVO> data) {
		this.data = data;
	}
}
