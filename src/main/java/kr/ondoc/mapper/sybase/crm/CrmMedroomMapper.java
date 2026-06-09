package kr.ondoc.mapper.sybase.crm;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import kr.ondoc.domain.sybase.crm.CrmMedroomVO;

public interface CrmMedroomMapper {
	//진
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT DISTINCT bmr_code,bmr_name,bmr_docname,bmr_kwanm, bmr_kwa FROM medroom AS med "
			+ "INNER JOIN doc_workplan AS workplan "
			+ "ON med.bmr_code=workplan.bdw_code "
			+ "WHERE med.bmr_use='Y' AND med.bmr_hosnum=(SELECT bhi_hosnum FROM hosinfo WHERE bhi_usechk='Y') ORDER BY bmr_code "
			+ "]]>"
			+ "</script>")
	public List<CrmMedroomVO> selectMedroom() throws Exception;
}
