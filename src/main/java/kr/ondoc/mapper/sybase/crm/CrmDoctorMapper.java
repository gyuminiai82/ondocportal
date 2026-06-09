package kr.ondoc.mapper.sybase.crm;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.ondoc.domain.sybase.crm.CrmDoctorVO;

public interface CrmDoctorMapper {
	@Select("<script>"
			+ "SELECT bdt_license, bdt_docname, bdt_kwa, bdt_kwaname, bdt_drno FROM doctor WHERE 0=0 "
			+ "AND bdt_hosnum=(SELECT bhi_hosnum FROM hosinfo WHERE bhi_usechk='Y') "
			+ "ORDER BY bdt_hosdate DESC"
			+ "</script>")
	public List<CrmDoctorVO> selectDoctor() throws Exception;
	
	
	@Insert("<script>"
			+ "INSERT INTO doctor (bdt_license, bdt_hosnum, bdt_hosname, "
			+ " bdt_docjumin, bdt_docname, bdt_kwa, bdt_kwaname, bdt_tel, bdt_addr, "
			+ "	bdt_drno, bdt_mngrno, bdt_docename, bdt_hosdate) VALUES "
			+ "	(#{bdt_license}, (SELECT TOP 1 bhi_hosnum FROM hosinfo WHERE  bhi_usechk='Y' ORDER BY bhi_stdate DESC), "
			+ " (SELECT TOP 1 bhi_hos03 FROM hosinfo WHERE  bhi_usechk='Y' ORDER BY bhi_stdate DESC), "
			+ " #{bdt_docjumin}, #{bdt_docname}, #{bdt_kwa}, #{bdt_kwaname}, #{bdt_tel}, #{bdt_addr}, "
			+ "	#{bdt_drno}, #{bdt_mngrno}, #{bdt_docename}, (SELECT TOP 1 bhi_stdate FROM hosinfo WHERE  bhi_usechk='Y' ORDER BY bhi_stdate DESC)"
			+ ")"
			+ "</script>")
	public void insertDoctor(CrmDoctorVO vo) throws Exception;
	
	@Update("<script>"
			+ "UPDATE doctor SET "
			+ "bdt_license = bdt_license "
			+ "<if test=\"bdt_docjumin != null and bdt_docjumin != ''\">"
			+ ",bdt_docjumin = #{bdt_docjumin} "
			+ "</if>"
			+ "<if test=\"bdt_docname != null and bdt_docname != ''\">"
			+ ",bdt_docname = #{bdt_docname} "
			+ "</if>"
			+ "<if test=\"bdt_drno != null and bdt_drno != ''\">"
			+ ",bdt_drno = #{bdt_drno} "
			+ "</if>"
			+ "<if test=\"bdt_kwa != null and bdt_kwa != ''\">"
			+ ",bdt_kwa = #{bdt_kwa} "
			+ "</if>"
			+ "<if test=\"bdt_kwaname != null and bdt_kwaname != ''\">"
			+ ",bdt_kwaname = #{bdt_kwaname} "
			+ "</if>"
			+ "<if test=\"bdt_tel != null and bdt_tel != ''\">"
			+ ",bdt_tel = #{bdt_tel} "
			+ "</if>"
			+ "<if test=\"bdt_addr != null and bdt_addr != ''\">"
			+ ",bdt_addr = #{bdt_addr} "
			+ "</if>"
			+ "WHERE 0=0 "
			+ "AND bdt_license = #{bdt_license}"
			+ "</script>")
	public void updateDoctor(CrmDoctorVO vo) throws Exception;
	
	@Delete("<script>"
			+ "DELETE FROM doctor "
			+ "WHERE 0=0 "
			+ "AND bdt_license = #{bdt_license}"
			+ "</script>")
	public void deleteDoctor(CrmDoctorVO vo) throws Exception;
}
