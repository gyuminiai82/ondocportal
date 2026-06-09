package kr.ondoc.domain.sybase.crm;

import java.util.ArrayList;
import java.util.List;

public class CrmIpwonPagingVO extends PagingVO {
	List<CrmIpwonVO> data = new ArrayList<CrmIpwonVO>();

	public List<CrmIpwonVO> getData() {
		return data;
	}

	public void setData(List<CrmIpwonVO> data) {
		this.data = data;
	}
}
