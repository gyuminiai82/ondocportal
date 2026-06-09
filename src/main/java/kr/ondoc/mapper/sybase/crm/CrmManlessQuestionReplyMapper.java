package kr.ondoc.mapper.sybase.crm;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import kr.ondoc.domain.sybase.crm.CrmManlessQuestionReplyVO;

public interface CrmManlessQuestionReplyMapper {
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT TOP 5 * from crm_manless_question_reply WHERE 0=0 "
			+ "]]>"
			+ "<if test=\"name != null and name != ''\">"
			+ "AND name = #{name} "
			+ "</if>"
			+ "<if test=\"birth != null and birth != ''\">"
			+ "AND birth = #{birth} "
			+ "</if>"
			+ "<if test=\"hpno != null and hpno != ''\">"
			+ "AND hpno = #{hpno} "
			+ "</if>"
			+ "<if test=\"ptno != null and ptno != ''\">"
			+ "AND ptno = #{ptno} "
			+ "</if>"
			+ "<if test=\"question_idx != null and question_idx != ''\">"
			+ "AND question_idx = #{question_idx} "
			+ "</if> "
			+ "<if test=\"rev_seq != null and rev_seq != ''\">"
			+ "AND rev_seq = #{rev_seq} "
			+ "</if> "
			+ "ORDER BY question_reply_idx DESC "
			+ "</script>")
	public List<CrmManlessQuestionReplyVO> selectListManlessQuestionReply(CrmManlessQuestionReplyVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT reply from crm_manless_question_reply WHERE 0=0 "
			+ "AND rev_seq = #{rev_seq} "
			+ "]]>"
			+ "</script>")
	public String selectManlessQuestionReply(String rev_seq) throws Exception;
	
	@Insert("<script>"
			+ "INSERT INTO crm_manless_question_reply "
			+ "(rev_seq, name, birth, hpno, ptno, title, reply, write_date) "
			+ "VALUES ( "
			+ "#{rev_seq}, #{name}, #{birth}, #{hpno}, #{ptno}, #{title}, #{reply}, "
			+ "(convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)))"
			+ "</script>")
	public void insertManlessQuestionReply(CrmManlessQuestionReplyVO vo) throws Exception;
}
