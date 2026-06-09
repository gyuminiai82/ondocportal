package kr.ondoc.mapper.sybase.penchart;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import kr.ondoc.domain.sybase.penchart.SearchSelectByungdongVO;
import kr.ondoc.domain.sybase.penchart.SearchSelectMedroomVO;

public interface SearchSelectMapper {
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT bmr_code,bmr_name,bmr_docname,bmr_kwanm "
			+ "FROM medroom "
			+ "WHERE bmr_use='Y' "
			+ "AND bmr_hosnum=(SELECT bhi_hosnum FROM hosinfo WHERE bhi_usechk='Y') ORDER BY bmr_code "
			+ "]]>"
			+ "</script>")
	public List<SearchSelectMedroomVO> searchSelectMedroomList() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT DISTINCT(brm_byungdong) AS brm_byungdong "
			+ "FROM room "
			+ "WHERE brm_bedcheck<> '2' "
			+ "]]>"
			+ "</script>")
	public List<SearchSelectByungdongVO> searchSelectByungdongList() throws Exception;
}