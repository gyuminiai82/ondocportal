package kr.ondoc.mapper.sybase.common;

import org.apache.ibatis.annotations.Select;

import kr.ondoc.domain.sybase.common.OptionInfo2VO;
import kr.ondoc.domain.sybase.common.OptionInfoVO;

public interface OptionInfoMapper {
	@Select("<script>"
			+ "SELECT xso_base72 FROM systemoption"
			+ "</script>")
	public String selectBase72() throws Exception;
	
	@Select("<script>"
			+ "SELECT "
			+ "(SELECT ISNULL(xso_base08, '0') AS xso_base08 FROM systemoption WHERE xso_date=(SELECT MAX(xso_date) FROM optionInfo)) AS xso_base08, "
			+ "(SELECT TOP 1 ISNULL(xpt_jiwn12, '0') AS xpt_jiwn12 FROM optioninfo WHERE xpt_date=(SELECT MAX(xpt_date) FROM optionInfo)) AS xpt_jiwn12 "
			+ "</script>")
	public OptionInfoVO select() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT TOP 1 ISNULl(xpt_reg67, '20240520') AS xpt_reg67 FROM optioninfo2 WHERE xpt_mngrno = "
			+ "(SELECT TOP 1 bhi_mngrno FROM hosinfo WHERE bhi_usechk='Y' AND bhi_stdate<=convert(char,GETDATE(),112)) "
			+ "AND xpt_date < convert(char,GETDATE(),112) "
			+ "ORDER BY xpt_date DESC"
			+ "]]>"
			+ "</script>")
	public OptionInfo2VO selectOptioninfo2() throws Exception;
}
