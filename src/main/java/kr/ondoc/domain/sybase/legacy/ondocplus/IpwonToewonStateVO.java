package kr.ondoc.domain.sybase.legacy.ondocplus;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.legacy.CommonVO;

public class IpwonToewonStateVO extends CommonVO {
	List<IpwonToewonStateDataVO> data = new ArrayList<IpwonToewonStateDataVO>();

	public List<IpwonToewonStateDataVO> getData() {
		return data;
	}

	public void setData(List<IpwonToewonStateDataVO> data) {
		this.data = data;
	}
}
