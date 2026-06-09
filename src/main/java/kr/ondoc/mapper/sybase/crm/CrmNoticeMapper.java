package kr.ondoc.mapper.sybase.crm;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Delete;

import kr.ondoc.domain.sybase.crm.CrmNoticeVO;

public interface CrmNoticeMapper {
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT *, convert(char,cno_int_date,8) AS datetime from crm_notice WHERE 0=0 "
			+ "]]>"
			+ "<if test=\"cno_int_date != null and cno_int_date != ''\">"
			+ "AND convert(char,cno_int_date,112) = #{cno_int_date} "
			+ "</if> "
			+ "AND cno_del_yn = 'N' "
			+ "ORDER BY cno_int_date DESC "
			+ "</script>")
	public List<CrmNoticeVO> selectNotice(CrmNoticeVO vo) throws Exception;
	
	@Insert("<script>"
			+ "INSERT INTO crm_notice ("
			+ "cno_content, cno_uid, cno_name, cno_int_date) "
			+ "VALUES ( "
			+ "#{cno_content}, #{cno_uid}, #{cno_name}, GETDATE())"
			+ "</script>")
	public void insertNotice(CrmNoticeVO vo) throws Exception;

	
	@Delete("<script>"
			+ "UPDATE crm_notice SET "
			+ "cno_del_yn = 'Y', "
			+ "cno_del_date = GETDATE() "
			+ "WHERE 0=0 "
			+ "AND cno_seq = #{cno_seq}"
			+ "</script>")
	public void deleteNotice(CrmNoticeVO vo) throws Exception;
}
