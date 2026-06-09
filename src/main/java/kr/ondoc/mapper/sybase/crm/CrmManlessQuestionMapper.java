package kr.ondoc.mapper.sybase.crm;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.ondoc.domain.sybase.crm.CrmManlessQuestionVO;


public interface CrmManlessQuestionMapper {
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT * from crm_manless_question WHERE 0=0 "
			+ "]]>"
			+ "<if test=\"use_yn != null and use_yn != ''\">"
			+ "AND use_yn = #{use_yn} "
			+ "</if>"
			+ "<if test=\"del_yn != null and del_yn != ''\">"
			+ "AND del_yn = #{del_yn} "
			+ "</if>"
			+ "ORDER BY sort ASC "
			+ "</script>")
	public List<CrmManlessQuestionVO> selectListManlessQuestion(CrmManlessQuestionVO vo) throws Exception;
	
	
	@Insert("<script>"
			+ "INSERT INTO crm_manless_question (question_title, question_content, sort, use_yn, write_date, write_id) "
			+ "VALUES ( "
			+ "#{question_title}, #{question_content}, "
			+ "ISNULL((SELECT MAX(sort) FROM crm_manless_question), 0)+1, #{use_yn}, "
			+ "(convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)), "
			+ "#{write_id})"
			+ "</script>")
	public void insertManlessQuestion(CrmManlessQuestionVO vo) throws Exception;
	
	@Update("<script>"
			+ "UPDATE crm_manless_question SET "
			+ "question_idx = question_idx "
			+ "<if test=\"question_title != null and question_title != ''\">"
			+ ",question_title = #{question_title} "
			+ "</if>"
			+ "<if test=\"question_content != null and question_content != ''\">"
			+ ",question_content = #{question_content} "
			+ "</if>"
			+ "<if test=\"sort != null and sort != ''\">"
			+ ",sort = #{sort} "
			+ "</if>"
			+ "<if test=\"use_yn != null and use_yn != ''\">"
			+ ",use_yn = #{use_yn} "
			+ "</if>"
			+ "<if test=\"modify_id != null and modify_id != ''\">"
			+ ",modify_date = (convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)) "
			+ ",modify_id = #{modify_id} "
			+ "</if>"
			+ "WHERE 0=0 "
			+ "AND question_idx = #{question_idx}"
			+ "</script>")
	public void updateManlessQuestion(CrmManlessQuestionVO vo) throws Exception;
	
	@Delete("<script>"
			+ "UPDATE crm_manless_question SET "
			+ " del_yn = 'Y', "
			+ " del_date = (convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)), "
			+ " del_id = #{del_id}"
			+ "WHERE 0=0  "
			+ "AND question_idx=#{question_idx} "
			+ "</script>")
	public void deleteManlessQuestion(CrmManlessQuestionVO vo) throws Exception;
}
