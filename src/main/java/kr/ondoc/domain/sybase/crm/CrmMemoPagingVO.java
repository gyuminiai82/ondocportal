package kr.ondoc.domain.sybase.crm;

import java.util.ArrayList;
import java.util.List;

public class CrmMemoPagingVO extends PagingVO {
	List<CrmMemoVO> data = new ArrayList<CrmMemoVO>();

	public List<CrmMemoVO> getData() {
		return data;
	}

	public void setData(List<CrmMemoVO> data) {
		this.data = data;
	}
}
