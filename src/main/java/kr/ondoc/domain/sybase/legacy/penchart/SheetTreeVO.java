package kr.ondoc.domain.sybase.legacy.penchart;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class SheetTreeVO extends CommonVO {
	List<SheetTreeDataVO> data = new ArrayList<SheetTreeDataVO>();

	public List<SheetTreeDataVO> getData() {
		return data;
	}

	public void setData(List<SheetTreeDataVO> data) {
		this.data = data;
	}
}
