package kr.ondoc.domain.sybase.legacy.penchart;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class SavedTreeVO extends CommonVO {
	List<SavedTreeDataVO> data = new ArrayList<SavedTreeDataVO>();

	public List<SavedTreeDataVO> getData() {
		return data;
	}

	public void setData(List<SavedTreeDataVO> data) {
		this.data = data;
	}
}
