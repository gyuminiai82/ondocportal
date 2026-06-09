package kr.ondoc.mapper.sybase.crm;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import kr.ondoc.domain.sybase.crm.CrmMemoVO;

public interface CrmMemoMapper {
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT TOP ${listNum} "
			//+ "(SELECT bjk_kwanm FROM yjinkwa WHERE bjk_kwa=(SELECT TOP 1 onw_kwa from naewon WHERE onw_ptno=t.bpt_ptno ORDER BY onw_regno DESC)), "
			+ "* FROM "
			+ "( "
			+ "SELECT *, ROW_NUMBER( ) OVER ( ORDER BY ${sort} ${sortDirection} ) AS RowNum "
			+ "FROM crm_memo WHERE 0=0 "
			+ "]]>"
			+ "<if test=\"notice_yn != null and notice_yn != ''\">"
			+ "AND notice_yn = #{notice_yn} "
			+ "</if>"
			+ "<if test=\"del_yn != null and del_yn != ''\">"
			+ "AND del_yn = #{del_yn} "
			+ "</if>"
			+ "<if test=\"memo != null and memo != ''\">"
			+ "AND memo like '%${memo}%' "
			+ "</if>"
			+ ") AS t "
			+ "<![CDATA["
			+ " WHERE RowNum>=${first}"
			+ "]]>"
			+ "</script>")
	public List<CrmMemoVO> selectMemoList(CrmMemoVO vo) throws Exception;
	//SELECT  TOP 15  * FROM ( SELECT *, ROW_NUMBER( ) OVER ( ORDER BY bpt_initdt DESC ) AS RowNum FROM patient ) AS t WHERE RowNum>=1
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT COUNT(*) "
			+ "]]>"
			+ "FROM crm_memo WHERE 0=0 "
			+ "<if test=\"notice_yn != null and notice_yn != ''\">"
			+ "AND notice_yn = #{notice_yn} "
			+ "</if>"
			+ "<if test=\"del_yn != null and del_yn != ''\">"
			+ "AND del_yn = #{del_yn} "
			+ "</if>"
			+ "<if test=\"memo != null and memo != ''\">"
			+ "AND memo like '%${memo}%' "
			+ "</if>"
			+ "</script>")
	public int countMemo(CrmMemoVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT * "
			+ "]]>"
			+ "FROM crm_memo WHERE 0=0 "
			+ "<if test=\"memo_date != null and memo_date != ''\">"
			+ "AND SUBSTR(memo_date, 1, 6) = #{memo_date} "
			+ "</if>"
			+ "<if test=\"del_yn != null and del_yn != ''\">"
			+ "AND del_yn = #{del_yn} "
			+ "</if>"
			+ "<if test=\"notice_yn != null and notice_yn != ''\">"
			+ "AND notice_yn = #{notice_yn} "
			+ "</if>"
			+ "ORDER BY notice_yn DESC, memo_idx DESC"
			+ "</script>")
	public List<CrmMemoVO> selectMemoMonthList(CrmMemoVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT * "
			+ "]]>"
			+ "FROM crm_memo WHERE 0=0 "
			+ "<if test=\"memo_date != null and memo_date != ''\">"
			+ "AND memo_date = #{memo_date} "
			+ "</if>"
			+ "<if test=\"del_yn != null and del_yn != ''\">"
			+ "AND del_yn = #{del_yn} "
			+ "</if>"
			+ "<if test=\"notice_yn != null and notice_yn != ''\">"
			+ "AND notice_yn = #{notice_yn} "
			+ "</if>"
			+ "ORDER BY notice_yn DESC, memo_idx ASC"
			+ "</script>")
	public List<CrmMemoVO> selectMemoDayList(CrmMemoVO vo) throws Exception;

	@Insert("<script>"
			+ "<![CDATA["
			+ "INSERT INTO crm_memo(notice_yn, memo, memo_image, memo_date, "
			+ "write_date, "
			+ "write_id,"
			+ "write_name) "
			+ "VALUES "
			+ "(#{notice_yn}, #{memo}, #{memo_image}, #{memo_date},"
			+ "(convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)), "
			+ "#{write_id},"
			+ "#{write_name})"
			+ "]]>"
			+ "</script>")
	public void insertMemo(CrmMemoVO vo) throws Exception;
	
	@Delete("<script>"
			+ "UPDATE crm_memo SET "
			+ " del_yn = 'Y', "
			+ " del_date = (convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)), "
			+ " del_id = #{del_id}"
			+ "WHERE 0=0  "
			+ "AND memo_idx=#{memo_idx} "
			+ "</script>")
	public void deleteMemo(CrmMemoVO vo) throws Exception;
}
