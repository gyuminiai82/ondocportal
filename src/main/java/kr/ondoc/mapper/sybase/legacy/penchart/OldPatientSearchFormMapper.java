package kr.ondoc.mapper.sybase.legacy.penchart;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import kr.ondoc.domain.sybase.legacy.SystemOptionDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.PatientSearchFormDataByungdongVO;
import kr.ondoc.domain.sybase.legacy.penchart.PatientSearchFormDataMedroomVO;

public interface OldPatientSearchFormMapper {
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT DISTINCT brm_byungdong AS code, brm_byungdong + '병동' AS name FROM room WHERE brm_bedcheck<> '2' "
			+ "]]>"
			+ "</script>")
	public List<PatientSearchFormDataByungdongVO> selectByungdong() throws Exception;	
				
	@Select("<script>"
			+ "SELECT bmr_code as code, bmr_name as name, bmr_docname as docname, bmr_kwanm as kwaname FROM medroom WHERE 0=0 AND bmr_use='Y' AND bmr_hosnum=(SELECT TOP 1 bhi_hosnum FROM hosinfo WHERE bhi_usechk='Y') "
			+ "</script>")
	public List<PatientSearchFormDataMedroomVO> selectMedroom() throws Exception;
}
