package kr.ondoc.domain.sybase.crm;

import java.util.ArrayList;
import java.util.List;

public class CrmTreatementPaymentUnpaidLogPagingVO extends PagingVO {
	List<CrmTreatementPaymentUnpaidLogVO> data = new ArrayList<CrmTreatementPaymentUnpaidLogVO>();

	public List<CrmTreatementPaymentUnpaidLogVO> getData() {
		return data;
	}

	public void setData(List<CrmTreatementPaymentUnpaidLogVO> data) {
		this.data = data;
	}
}
