package kr.ondoc.mapper.sybase.crm;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.ondoc.domain.sybase.crm.CrmLoginParamVO;
import kr.ondoc.domain.sybase.crm.CrmLoginVO;
import kr.ondoc.domain.sybase.crm.CrmTreatementVO;
import kr.ondoc.domain.sybase.crm.CrmUsrmngrVO;

public interface CrmUsrmngrMapper {
	@Select("<script>"
			+ "SELECT xur_id, xur_pass, xur_medroom, xur_name, xur_resno, xur_grade,  "
			+ "(SELECT  TOP 1 bhi_hos07 FROM hosinfo WHERE bhi_usechk='Y') as business_number "
			+ "FROM usrmngr "
			+ "WHERE 0=0 "
			+ "<if test=\"xur_id != null and xur_id != ''\">"
			+ "AND xur_id = #{xur_id}"
			+ "</if>"
			+ "</script>")
	public CrmLoginVO selectLogin(CrmLoginParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "SELECT COUNT(*) "
			+ "FROM usrmngr "
			+ "WHERE 0=0 "
			+ "<if test=\"xur_id != null and xur_id != ''\">"
			+ "AND xur_id = #{xur_id}"
			+ "</if>"
			+ "</script>")
	public int checkUsrmngr(CrmUsrmngrVO vo) throws Exception;
	
	@Select("<script>"
			+ "SELECT xur_id, xur_name, xur_right, xur_grade, xur_stop FROM usrmngr "
			+ "WHERE 0=0 "
			+ "<if test='xur_stop != null and xur_stop == \"Y\"'>"
			+ "AND xur_stop = #{xur_stop} "
			+ "</if>"
			+ "<if test='xur_stop != null and xur_stop == \"N\"'>"
			+ "AND (xur_stop = #{xur_stop} OR xur_stop = '') "
			+ "</if>"
			+ "</script>")
	public List<CrmUsrmngrVO> selectUsrmngr(CrmUsrmngrVO vo) throws Exception;
	
	@Select("<script>"
			+ "SELECT xur_id, xur_name, xur_right, xur_grade, xur_stop FROM usrmngr "
			+ "WHERE 0=0 "
			+ "<if test='xur_stop != null and xur_stop == \"Y\"'>"
			+ "AND xur_stop = #{xur_stop} "
			+ "</if>"
			+ "<if test='xur_stop != null and xur_stop == \"N\"'>"
			+ "AND (xur_stop = #{xur_stop} OR xur_stop = '') "
			+ "</if>"
			+ "ORDER BY xur_create DESC"
			+ "</script>")
	public List<CrmUsrmngrVO> selectListUsrmngr(CrmUsrmngrVO vo) throws Exception;
	
	@Insert("<script>"
			+ "INSERT INTO usrmngr ("
			+ "xur_id, xur_pass, xur_name, xur_outday, xur_stop, "
			+ "xur_right, xur_grade, xur_uid, xur_ip) "
			+ "VALUES ( "
			+ "#{xur_id}, #{xur_pass}, #{xur_name}, '', #{xur_stop}, "
			+ "'1', '01', #{xur_uid}, '127.0.0.1'"
			+ ")"
			+ "</script>")
	public void insertUsrmngr(CrmUsrmngrVO vo) throws Exception;
	
	@Update("<script>"
			+ "UPDATE usrmngr SET "
			+ "xur_id = xur_id "
			+ "<if test=\"xur_pass != null and xur_pass != ''\">"
			+ ",xur_pass = #{xur_pass} "
			+ "</if>"
			+ "<if test=\"xur_name != null and xur_name != ''\">"
			+ ",xur_name = #{xur_name} "
			+ "</if>"
			+ "<if test=\"xur_stop != null and xur_stop != ''\">"
			+ ",xur_stop = #{xur_stop} "
			+ "</if>"
			+ "<if test=\"xur_right != null and xur_right != ''\">"
			+ ",xur_right = #{xur_right} "
			+ "</if>"
			+ "<if test=\"xur_grade != null and xur_grade != ''\">"
			+ ",xur_grade = #{xur_grade} "
			+ "</if>"
			+ "<if test=\"xur_uid != null and xur_uid != ''\">"
			+ ",xur_uid = #{xur_uid} "
			+ "</if>"
			+ "WHERE 0=0 "
			+ "AND xur_id = #{xur_id}"
			+ "</script>")
	public void updateUsrmngr(CrmUsrmngrVO vo) throws Exception;
}
