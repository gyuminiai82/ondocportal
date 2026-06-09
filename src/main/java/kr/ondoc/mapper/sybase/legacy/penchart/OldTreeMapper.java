package kr.ondoc.mapper.sybase.legacy.penchart;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import kr.ondoc.domain.sybase.legacy.penchart.PatientRoomSymptomVO;
import kr.ondoc.domain.sybase.legacy.penchart.SavedTreeDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.SavedTreeVO;
import kr.ondoc.domain.sybase.legacy.penchart.SheetTreeDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.TreeParamVO;

public interface OldTreeMapper {
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT bec_parid AS 'depth1_id',"
			+ "(SELECT TOP 1 bec_name FROM emrchart_list WHERE bec_id=a.bec_parid) AS 'depth1_name', "
			+ "bec_id AS 'depth2_id',"
			+ "bec_name AS 'depth2_name',"
			+ "oec_seq AS 'depth3_id',"
			+ "oec_date AS 'depth3_name',"
			+ "oec_time AS 'depth3_time',   "
			+ "oec_wdate,   "
			+ "(CASE WHEN oec_time is NULL then SUBSTRING(oec_cdate, 12, 9) Else oec_time END) as oec_time,"
			+ "oec_state, GETDATE() AS 'now_time',"
			+ "	(	SELECT xeo_cnt "
			+ "		FROM emrchart_open "
			+ "		WHERE xeo_id=bec_id "
			+ "		AND xeo_uid=#{uid} AND xeo_inout=#{inout}) as o_cnt,   "
			+ "(CASE WHEN o_cnt is NULL then 0 Else o_cnt END) as open_cnt,   "
			+ "(SELECT xur_name FROM usrmngr WHERE xur_id=oec_lockid),   "
			+ "(SELECT bec_seq FROM emrchart_list WHERE bec_id=a.bec_parid) as parseq,   "
			+ "(CASE WHEN parseq is NULL then bec_seq Else parseq END) as par_seq,   "
			+ "oec_lockyn FROM emrchart_list a, emrchart "
			+ "LEFT OUTER JOIN (SELECT xer_uid,xer_id,xer_inout,xer_view,xer_editf FROM emrchart_right) "
			+ "AS emrright(xer_uid,xer_id,xer_inout,xer_view,xer_editf)"
			+ "ON (xer_uid=#{uid} AND xer_id=oec_cid AND xer_inout=oec_inout) "
			+ "WHERE bec_id=oec_cid AND oec_use='1' AND xer_view='1' "
			+ "]]>"
			+ "<![CDATA["
			+ "AND (oec_state<>'N'"
			+ "]]>"
			+ "<if test=\"seq != null and seq != ''\">"
			+ "OR oec_seq in (${seq}) "
			+ "</if>"
			+ ")"
			+ "AND oec_ptno=#{ptno} "
			+ "AND oec_inout=#{inout}  "
			+ "<if test=\"inout != null and inout != '' and inout == '2' and regno != null and regno != ''\">"
			+ "AND oec_regno=#{regno}  "
			+ "</if>"
			+ "<if test=\"start_date != null and start_date != ''\">"
			+ "<![CDATA["
			+ "AND (#{start_date} <= oec_date "
			+ "]]>"
			+ "<if test=\"seq != null and seq != ''\">"
			+ "OR oec_seq in (${seq}) "
			+ "</if>"
			+ ")  "
			+ "</if>"
			+ "<if test=\"end_date != null and end_date != ''\">"
			+ "<![CDATA["
			+ "AND (#{end_date} >= oec_date "
			+ "]]>"
			+ "<if test=\"seq != null and seq != ''\">"
			+ "OR oec_seq in (${seq}) "
			+ "</if>"
			+ ") "
			+ "</if>"
			+ "ORDER BY par_seq,bec_depth,bec_seq,oec_date DESC,oec_time DESC "
			+ "</script>")
	public List<SavedTreeDataVO> selectSavedTree(TreeParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT (SELECT bes_seq FROM emrsheet_list WHERE bes_id=a.bes_parid) as parseq,"
			+ "(CASE WHEN parseq is NULL then bes_seq Else parseq END) as par_seq,  bes_id as id, bes_name as name, bes_parid as parent_id, bes_depth as depth "
			+ "FROM emrsheet_list a "
			+ "LEFT OUTER JOIN (SELECT be2_no,xer_edit FROM emrchart_list2, emrchart_right "
			+ "WHERE xer_inout = #{inout} AND xer_uid = #{uid} AND xer_id=be2_id) AS emrright(be2_no,xer_edit) "
			+ "ON (be2_no= bes_id) WHERE bes_inout = #{inout} AND bes_use = '1' "
			+ "AND bes_kind <> '80' AND bes_kind <> '99'"
			+ "AND (xer_edit = '1' OR (xer_edit is NULL and bes_parid = 0)) "
			+ "ORDER BY par_seq, bes_depth, bes_seq "
			+ "]]>"
			+ "</script>")
	public List<SheetTreeDataVO> selectSheetTree(TreeParamVO vo) throws Exception;
}
