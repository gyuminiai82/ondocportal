package kr.ondoc.mapper.sybase.common;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import kr.ondoc.domain.sybase.common.UsrmngrVO;
import kr.ondoc.domain.sybase.penchart.TempGroupAuthVO;

public interface GroupAuthMapper {
	@Select("<script>"
			+ "SELECT DISTINCT(xpg_file) AS xpg_file, xpg_class FROM pg_id, workgroup "
			+ "WHERE xpg_class+xpg_sclass=xwg_class AND xwg_code=(SELECT xur_right FROM usrmngr WHERE xur_id=#{xur_id}) ORDER BY xpg_class "
			+ "</script>")
	public List<TempGroupAuthVO> list(String xur_id) throws Exception;
}
