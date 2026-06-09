package kr.ondoc.mapper.sybase.legacy.penchart;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import kr.ondoc.domain.sybase.legacy.penchart.LoginDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.LoginVO;
import kr.ondoc.domain.sybase.legacy.penchart.PatientSearchFormDataByungdongVO;

public interface OldLoginMapper {
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT xur_id as id, xur_pass as pass, xur_medroom as medroom, xur_name as name, xur_resno as resno, xur_inday, xur_outday FROM usrmngr WHERE 0=0 AND xur_id=#{id} "
			+ "]]>"
			+ "</script>")
	public List<LoginDataVO> selectLogin(String id) throws Exception;	
}
