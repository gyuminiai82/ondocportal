package kr.ondoc.domain.sybase.legacy;

import java.util.ArrayList;
import java.util.List;

public class HosinfoVO extends CommonVO {
	List<HosinfoDataVO> data = new ArrayList<HosinfoDataVO>();

	public List<HosinfoDataVO> getData() {
		return data;
	}

	public void setData(List<HosinfoDataVO> data) {
		this.data = data;
	}
}
