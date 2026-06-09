package kr.ondoc.mapper.sybase.common;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import kr.ondoc.domain.sybase.common.UsrmngrVO;

public interface UsrmngrMapper {
	@Select("<script>"
			+ "SELECT xur_id, xur_pass, xur_medroom, xur_name, xur_resno, "
			+ "(SELECT  TOP 1 bhi_hos07 FROM hosinfo WHERE bhi_usechk='Y') as business_number "
			+ "FROM usrmngr "
			+ "WHERE 0=0 "
			+ "<if test=\"xur_id != null and xur_id != ''\">"
			+ "AND xur_id = #{xur_id}"
			+ "</if>"
			+ "</script>")
	public UsrmngrVO select(UsrmngrVO vo) throws Exception;
}
