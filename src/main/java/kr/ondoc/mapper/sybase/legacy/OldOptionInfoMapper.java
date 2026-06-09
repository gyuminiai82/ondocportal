package kr.ondoc.mapper.sybase.legacy;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import kr.ondoc.domain.sybase.legacy.HosinfoDataVO;
import kr.ondoc.domain.sybase.legacy.IdentificationDataVO;
import kr.ondoc.domain.sybase.legacy.Optioninfo2DavaVO;
import kr.ondoc.domain.sybase.legacy.SystemOptionDataVO;

public interface OldOptionInfoMapper {
	@Select("<script>"
			+ "SELECT xso_base72 FROM systemoption"
			+ "</script>")
	public String selectBase72() throws Exception;
	
	@Select("<script>"
			+ "SELECT (SELECT xso_base08 FROM systemoption) AS xso_base08, (SELECT xso_base72 FROM systemoption) AS xso_base72, (SELECT TOP 1 ISNULL(xpt_jiwn12, '''') FROM optioninfo WHERE xpt_date=(SELECT MAX(xpt_date) FROM optionInfo)) AS xpt_jiwn12"
			+ "</script>")
	public List<SystemOptionDataVO> select() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT TOP 1 bhi_mngrno, bhi_hosnum, bhi_stdate, bhi_eddate, bhi_usechk , "
			+ "bhi_hos03, bhi_hos04, bhi_hos05, bhi_hos06, bhi_hos07 , "
			+ "bhi_hos08, bhi_hos09, bhi_hos10, bhi_hos11, bhi_hos12 , "
			+ "bhi_hos13, bhi_hos14, bhi_hos15, bhi_hos16, bhi_hos17 "
			+ "FROM hosinfo "
			+ "WHERE 0=0 AND bhi_usechk='Y'"
			+ "AND bhi_stdate<=convert(char,GETDATE(),112) ORDER BY bhi_stdate DESC"
			+ "]]>"
			+ "</script>")
	public List<HosinfoDataVO> selectHosinfo() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT TOP 1 ISNULl(xpt_reg67, '20240520') AS xpt_reg67 FROM optioninfo2 WHERE xpt_mngrno = "
			+ "(SELECT TOP 1 bhi_mngrno FROM hosinfo WHERE bhi_usechk='Y' AND bhi_stdate<=convert(char,GETDATE(),112)) "
			+ "AND xpt_date < convert(char,GETDATE(),112) "
			+ "ORDER BY xpt_date DESC"
			+ "]]>"
			+ "</script>")
	public List<Optioninfo2DavaVO> selectOptioninfo2() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT TOP 1 ISNULl(bpt2_sinbundate, '') AS bpt2_sinbundate FROM patient2 WHERE bpt2_ptno = ${ptno}"
			+ "]]>"
			+ "</script>")
	public List<IdentificationDataVO> selectIdentification(String ptno) throws Exception;
}
