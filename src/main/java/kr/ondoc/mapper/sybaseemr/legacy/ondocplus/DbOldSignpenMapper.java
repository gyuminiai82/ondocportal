package kr.ondoc.mapper.sybaseemr.legacy.ondocplus;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import kr.ondoc.domain.sybase.legacy.ondocplus.SignpenCategoryDataVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.SignpenCategoryParamVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.SignpenChartDataVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.SignpenChartOldDataVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.SignpenChartOldDateDataVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.SignpenChartOldDateParamVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.SignpenChartOldParamVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.SignpenChartParamVO;

public interface DbOldSignpenMapper {
	@Select("<script>"
			+ "SELECT DISTINCT SUBSTR(bes_name, 0, CHARINDEX(')', bes_name)) AS 'cate_name' "
			+ "FROM emrsheet_list "
			+ "WHERE bes_name like '(%)%' AND bes_kind=#{bes_kind} AND bes_inout=#{bes_inout} "
			+ "</script>")
	public List<SignpenCategoryDataVO> selectSignpenCategory(SignpenCategoryParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "SELECT (SELECT bes_seq FROM emrsheet_list WHERE bes_id=a.bes_parid) as parseq, "
			+ "(CASE WHEN parseq is NULL then bes_seq Else parseq END) as par_seq, * "
			+ "FROM emrsheet_list a "
			+ "WHERE 0=0 AND parseq IS NOT NULL AND bes_kind in (${arr_kind}) AND bes_use  = '1'  "
			+ "<if test=\"bes_inout != null and bes_inout != ''\">"
			+ "AND bes_inout=#{bes_inout} "
			+ "</if> "
			+ "<if test=\"cate != null and cate != ''\">"
			+ "AND bes_name LIKE '${cate}%' "
			+ "</if>"
			+ "ORDER BY bes_kind, par_seq, bes_depth, bes_seq"
			+ "</script>")
	public List<SignpenChartDataVO> selectSignpenChart(SignpenChartParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "SELECT distinct oec_date FROM emrchart AS ec "
			+ "LEFT OUTER JOIN emrsheet_list AS esl "
			+ "ON ec.oec_sid=esl.bes_id "
			+ "WHERE 0=0 AND esl.bes_kind in (80,99) "
			+ "AND esl.bes_use='1' AND (ec.oec_state='S' OR ec.oec_state='E') "
			+ "<if test=\"inout != null and inout != ''\">"
			+ "AND esl.bes_inout=#{inout} AND ec.oec_inout=#{inout} "
			+ "</if>"
			+ "<if test=\"ptno != null and ptno != ''\">"
			+ "AND ec.oec_ptno=#{ptno} "
			+ "</if>"
			+ "ORDER BY oec_date DESC  "
			+ "</script>")
	public List<SignpenChartOldDateDataVO> selectSignpenChartOldDate(SignpenChartOldDateParamVO vo) throws Exception;
	
	@Select("<script>"
			+ " SELECT * FROM emrchart AS ec "
			+ "LEFT OUTER JOIN emrsheet_list AS esl "
			+ "ON ec.oec_sid=esl.bes_id "
			+ "WHERE 0=0 AND esl.bes_kind in (${arr_kind}) "
			+ "AND esl.bes_use='1' AND (ec.oec_state='S' OR ec.oec_state='E') "
			+ "<if test=\"inout != null and inout != ''\">"
			+ "AND esl.bes_inout=#{inout} AND ec.oec_inout=#{inout} "
			+ "</if>"
			+ "AND ec.oec_ptno=#{ptno} "
			+ "AND ec.oec_date=#{date} "
			+ "ORDER BY oec_date DESC, oec_time DESC, oec_cid ASC "
			+ "</script>")
	public List<SignpenChartOldDataVO> selectSignpenChartOld(SignpenChartOldParamVO vo) throws Exception;
}
