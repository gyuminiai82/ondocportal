package kr.ondoc.domain.sybase.crm;

import java.util.ArrayList;
import java.util.List;

public class CrmOutPagingVO extends PagingVO {
	List<CrmOutVO> data = new ArrayList<CrmOutVO>();

	public List<CrmOutVO> getData() {
		return data;
	}

	public void setData(List<CrmOutVO> data) {
		this.data = data;
	}
}
