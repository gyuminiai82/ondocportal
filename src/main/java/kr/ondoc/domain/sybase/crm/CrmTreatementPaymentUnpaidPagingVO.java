package kr.ondoc.domain.sybase.crm;

import java.util.ArrayList;
import java.util.List;

public class CrmTreatementPaymentUnpaidPagingVO extends PagingVO {
	List<CrmTreatementPaymentVO> data = new ArrayList<CrmTreatementPaymentVO>();

	public List<CrmTreatementPaymentVO> getData() {
		return data;
	}

	public void setData(List<CrmTreatementPaymentVO> data) {
		this.data = data;
	}
}
