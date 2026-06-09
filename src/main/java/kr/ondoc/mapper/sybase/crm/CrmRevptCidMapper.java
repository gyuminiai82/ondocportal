package kr.ondoc.mapper.sybase.crm;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.ondoc.domain.sybase.crm.CrmRevptCidVO;
import kr.ondoc.domain.sybase.crm.CrmRevptNaverVO;

public interface CrmRevptCidMapper {
	@Select("<script>"
			+ "SELECT * from revpt_cid "
			+ "WHERE 0=0 "
			+ "<if test=\"cid_no != null and cid_no != ''\">"
			+ "AND cid_no = #{cid_no} "
			+ "</if>"
			+ "</script>")
	public CrmRevptCidVO select(CrmRevptCidVO vo) throws Exception;
	
	@Select("<script>"
			+ "SELECT Top 10 * from revpt_cid "
			+ "WHERE 0=0 "
			+ "</script>")
	public List<CrmRevptCidVO> selectList(CrmRevptCidVO vo) throws Exception;
	
	@Update("<script>"
			+ "UPDATE revpt_cid SET "
			+ "cid_susin_date = cid_susin_date "
			+ "<if test=\"cid_action != null and cid_action != ''\">"
			+ ",cid_action = #{cid_action} "
			+ "</if>"
			+ "<if test=\"cid_uid != null and cid_uid != ''\">"
			+ ",cid_uid = #{cid_uid} "
			+ "</if>"
			+ "<if test=\"cid_unm != null and cid_unm != ''\">"
			+ ",cid_unm = #{cid_unm} "
			+ "</if>"
			+ "<if test=\"cid_memo != null and cid_memo != ''\">"
			+ ",cid_memo = #{cid_memo} "
			+ "</if>"
			+ "WHERE 0=0 "
			+ "<if test=\"cid_no != null and cid_no != ''\">"
			+ "AND cid_no = #{cid_no} "
			+ "</if>"
			+ "</script>")
	public void update(CrmRevptCidVO vo) throws Exception;
}
