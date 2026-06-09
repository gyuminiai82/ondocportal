package kr.ondoc.domain.sybase.crm.detail;

import java.util.ArrayList;
import java.util.List;

import kr.ondoc.domain.sybase.crm.PagingVO;

public class CrmReservePagingVO extends PagingVO {
	List<CrmReserveVO> data = new ArrayList<CrmReserveVO>();

	public List<CrmReserveVO> getData() {
		return data;
	}

	public void setData(List<CrmReserveVO> data) {
		this.data = data;
	}
}
