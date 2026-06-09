package kr.ondoc.mapper.sybase.common;

import org.apache.ibatis.annotations.Select;

import kr.ondoc.domain.sybase.common.UsrmngrVO;

public interface InitMapper {
	@Select("<script>"
			+ "SELECT bhi_hosnum "
			+ "FROM hosinfo "
			+ "WHERE bhi_usechk='Y'"
			+ "</script>")
	public String selectHosnum() throws Exception;
}
