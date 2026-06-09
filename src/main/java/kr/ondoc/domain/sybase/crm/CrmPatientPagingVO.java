package kr.ondoc.domain.sybase.crm;

import java.util.ArrayList;
import java.util.List;

public class CrmPatientPagingVO extends PagingVO {
	List<CrmPatientVO> data = new ArrayList<CrmPatientVO>();

	public List<CrmPatientVO> getData() {
		return data;
	}

	public void setData(List<CrmPatientVO> data) {
		this.data = data;
	}
}
