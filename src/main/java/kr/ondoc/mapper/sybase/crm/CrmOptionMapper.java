package kr.ondoc.mapper.sybase.crm;

import org.apache.ibatis.annotations.Select;

import kr.ondoc.domain.sybase.crm.SystemOptionVO;


public interface CrmOptionMapper {
	@Select("<script>"
			+ "SELECT (SELECT xso_base08 FROM systemoption) AS xso_base08, (SELECT TOP 1 ISNULL(xpt_jiwn12, '''') FROM optioninfo WHERE xpt_date=(SELECT MAX(xpt_date) FROM optionInfo)) AS xpt_jiwn12"
			+ "</script>")
	public SystemOptionVO selectOption() throws Exception;
}
