package kr.ondoc.mapper.sybase.penchart;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.ondoc.domain.sybase.penchart.EmrAddParamVO;
import kr.ondoc.domain.sybase.penchart.EmrAddVO;
import kr.ondoc.domain.sybase.penchart.EmrAttachCopyParamVO;
import kr.ondoc.domain.sybase.penchart.EmrAttachCopyTempVO;
import kr.ondoc.domain.sybase.penchart.EmrAttachVO;
import kr.ondoc.domain.sybase.penchart.EmrAuthVO;
import kr.ondoc.domain.sybase.penchart.EmrDateParamVO;
import kr.ondoc.domain.sybase.penchart.EmrDateVO;
import kr.ondoc.domain.sybase.penchart.EmrDeleteParamVO;
import kr.ondoc.domain.sybase.penchart.EmrLockClearVO;
import kr.ondoc.domain.sybase.penchart.EmrNewPageVO;
import kr.ondoc.domain.sybase.penchart.EmrOpenParamVO;
import kr.ondoc.domain.sybase.penchart.EmrOpenVO;
import kr.ondoc.domain.sybase.penchart.EmrReplaceVO;
import kr.ondoc.domain.sybase.penchart.EmrSavedVO;
import kr.ondoc.domain.sybase.penchart.EmrSheetVO;
import kr.ondoc.domain.sybase.penchart.EmrTempVO;
import kr.ondoc.domain.sybase.penchart.EmrTimeParamVO;
import kr.ondoc.domain.sybase.penchart.FormVO;
import kr.ondoc.domain.sybase.penchart.TemplateBoilerplateCateVO;
import kr.ondoc.domain.sybase.penchart.TemplateBoilerplateDetailVO;
import kr.ondoc.domain.sybase.penchart.TemplateTreatementCateVO;
import kr.ondoc.domain.sybase.penchart.TemplateTreatementDetailVO;

public interface EmrMapper {
	@Select("<script>"
			+ "SELECT oec_lockyn, oec_lockdt, oec_loichost, DATEDIFF(HOUR, oec_lockdt, GETDATE()) AS 'lock_hour' "
			+ "FROM emrchart WHERE 0=0 "
			+ "<if test=\"oec_ptno != null and oec_ptno != ''\">"
			+ "AND oec_ptno = #{oec_ptno}"
			+ "</if>"
			+ "<if test=\"oec_seq != null and oec_seq != ''\">"
			+ "AND oec_seq = #{oec_seq}"
			+ "</if>"
			+ "</script>")
	public EmrSavedVO selectLockCheck(EmrSavedVO vo) throws Exception;
	
	@Select("<script>"
			+ "SELECT oec_ptno, oec_seq, oec_lockyn, oec_lockdt, "
			+ "oec_loichost, SUBSTRING(oec_date, 1, 4) AS 'year', oec_date, oec_time, oec_file "
			+ "FROM emrchart WHERE 0=0 "
			+ "<if test=\"oec_ptno != null and oec_ptno != ''\">"
			+ "AND oec_ptno = #{oec_ptno}"
			+ "</if>"
			+ "<if test=\"oec_seq != null and oec_seq != ''\">"
			+ "AND oec_seq = #{oec_seq}"
			+ "</if>"
			+ "</script>")
	public EmrSavedVO selectSavedEmr(EmrSavedVO vo) throws Exception;
	
	@Update("<script>"
			+ "UPDATE emrchart SET "
			+ "oec_seq=oec_seq "
			+ ",oec_lockyn = '0' "
			+ ",oec_lockid = '' "
			+ ",oec_lockdt = null "
			+ ",oec_loichost = null "
			+ "WHERE 0=0 "
			+ "<if test=\"oec_ptno != null and oec_ptno != ''\">"
			+ "AND oec_ptno = #{oec_ptno}"
			+ "</if>"
			+ "<if test=\"oec_seq != null and oec_seq != ''\">"
			+ "AND oec_seq = #{oec_seq}"
			+ "</if>"
			+ "</script>")
	public void updateSavedEmr(EmrSavedVO vo) throws Exception; 
	
	@Insert("<script>"
			+ "INSERT INTO emrchart (oec_regno, oec_ptno, oec_seq, oec_inout, oec_sid, oec_cid, oec_kind, oec_wdate, oec_create_uid) "
			+ "VALUES ( "
			+ "#{regno}, "
			+ "#{ptno}, "
			+ "(SELECT (CASE WHEN MAX(oec_seq)+1 is NULL THEN 1 ELSE MAX(oec_seq)+1 END) as cnt FROM emrchart WHERE oec_ptno=#{ptno}), "
			+ "#{inout}, "
			+ "#{bes_id}, "
			+ "(SELECT be2_id FROM emrchart_list ecl LEFT OUTER JOIN emrchart_list2 ecl2 ON ecl.bec_id=ecl2.be2_id AND ecl.bec_inout=#{inout} WHERE 0=0 "
			+ " AND ecl.bec_use='1' "
			+ "<if test=\"kind == null or kind == ''\">" 
			+ "AND ecl.bec_kind not in ('80', '99') " 
			+ "</if>"
			+ "<if test=\"kind != null and kind != ''\">"
			+ "AND ecl.bec_kind in (${kind}) "
			+ "</if>"
			
			+ "AND be2_no=#{bes_id}), "
			+ "#{kind},"
			+ "convert(char,GETDATE(), 112), "
			+ "#{id}"
			+ ")"
			+ "</script>")
	public void insertNewEmr(EmrNewPageVO vo) throws Exception;
	
	@Select("<script>"
			+ "SELECT TOP 1 "
			+ "oec_seq, "
			+ "SUBSTRING(oec_cdate, 1, 10) AS 'date', "
			+ "SUBSTRING(oec_cdate, 12, 8) AS 'time' "
			+ "FROM emrchart "
			+ "WHERE oec_ptno=#{ptno} AND oec_create_uid=#{id} ORDER BY oec_seq DESC"
			+ "</script>")
	public EmrTempVO selectTempEmr(EmrNewPageVO vo) throws Exception;	
	
	@Select("<script>"
			+ "SELECT bef_no, bef_form, (SELECT bes_name FROM emrsheet_list WHERE bes_id=#{bes_id}) AS 'bes_name' "
			+ "FROM emrform_list WHERE 0=0 "
			+ "AND bef_no=(SELECT bes_no FROM emrsheet_list WHERE bes_id=#{bes_id})"
			+ "</script>")
	public EmrNewPageVO selectPage(EmrNewPageVO vo) throws Exception;
	
	@Select("<script>"
			+ "SELECT bef_no, bef_form, (SELECT bes_name FROM emrsheet_list WHERE bes_id=#{bes_id}) AS 'bes_name' "
			+ "FROM emrform_list WHERE 0=0 "
			+ "AND bef_no=(SELECT bes_no FROM emrsheet_list WHERE bes_id=#{bes_id})"
			+ "</script>")
	public EmrNewPageVO selectPageSid(String bes_id) throws Exception;
	
	@Select("<script>"
			+ "SELECT emrform_qry(bef_no) as query, bef_no, bef_form "
			+ "FROM emrform_list WHERE 0=0 "
			+ "AND bef_no=#{bef_no}"
			+ "</script>")
	public EmrNewPageVO selectPanel(EmrNewPageVO vo) throws Exception;
	
	@Select("<script>"
			+ "SELECT emrform_qry(bef_no) as query "
			+ "FROM emrform_list WHERE 0=0 "
			+ "AND bef_no=#{bef_no}"
			+ "</script>")
	public String selectPanelQuery(String bef_no) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT * FROM (${query}) AS temp ORDER BY idx "
			+ "]]>"
			+ "</script>")
	public List<FormVO> selectForm(String query) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT TOP 1 usr.xur_name, emr.oec_seq, emr.oec_lockyn, emr.oec_lockid, emr.oec_lockdt, emr.oec_loichost, xer_uid, xer_id, xer_inout, xer_view, xer_print, xer_edit "
			+ "FROM emrchart emr "
			+ "INNER JOIN emrchart_right emrr "
			+ "ON emr.oec_cid=emrr.xer_id "
			+ "LEFT OUTER JOIN usrmngr usr "
			+ "ON emr.oec_lockid=usr.xur_id "
			+ "WHERE 0=0 AND emr.oec_seq = #{seq} AND emr.oec_ptno = #{ptno} "
			+ "]]>"
			+ "<if test=\"uid != null and uid != ''\">"
			+ "AND emrr.xer_uid = #{uid}"
			+ "</if>"
			+ "</script>")
	public EmrAuthVO selectAuth(String seq, String ptno, String uid) throws Exception;
	
	@Update("<script>"
			+ "UPDATE emrchart SET "
			+ "oec_lockyn = '1' "
			+ ",oec_lockid = #{id} "
			+ ",oec_lockdt = GETDATE() "
			+ ",oec_loichost = #{uuid} "
			+ "WHERE 0=0 "
			+ "<if test=\"oec_ptno != null and oec_ptno != ''\">"
			+ "AND oec_ptno = #{oec_ptno}"
			+ "</if>"
			+ "<if test=\"oec_seq != null and oec_seq != ''\">"
			+ "AND oec_seq = #{oec_seq}"
			+ "</if>"
			+ "AND oec_lockyn='0'"
			+ "</script>")
	public void updateEmrLock(EmrSavedVO vo) throws Exception;
	
	@Update("<script>"
			+ "UPDATE emrchart SET "
			+ "oec_seq=oec_seq "
			+ ",oec_date = #{date} "
			+ ",oec_time = #{time} "
			+ ",oec_file = #{file_name} "
			+ ",oec_fdate = GETDATE() "
			+ ",oec_udate = GETDATE() "
			+ ",oec_state = #{state} "
			+ ",oec_up_uid = #{id} "
			+ ",oec_lockyn = '0' "
			+ ",oec_lockid = '' "
			+ ",oec_lockdt = null "
			+ ",oec_loichost = null "
			+ "WHERE 0=0 "
			+ "AND oec_ptno = #{ptno}"
			+ "AND oec_seq = #{key}"
			+ "</script>")
	public void updateEmr(EmrSheetVO vo) throws Exception;
	
	@Update("<script>"
			+ "UPDATE emrchart SET "
			+ "oec_lockyn = '0' "
			+ ",oec_lockid = '' "
			+ ",oec_lockdt = null "
			+ ",oec_loichost = null "
			+ "WHERE 0=0 "
			+ "AND oec_lockyn='1' "
			+ "AND oec_ptno = #{ptno}"
			+ "AND oec_loichost= #{uuid} "
			+ "</script>")
	public void lockClearEmr(EmrLockClearVO vo) throws Exception;
	
	//Replace
	@Select("<script>"
			+ "<![CDATA["
			+ " SELECT TOP 1 bmr_docname FROM ( SELECT TOP 1 medroom, reg_date "
			+ "									FROM ( SELECT rwj_medroom AS medroom, rwj_reg_date AS reg_date FROM waitjin WHERE rwj_regno=#{regno} UNION "
			+ "											SELECT onw_medroom AS medroom, onw_ch_date AS reg_date FROM naewon WHERE onw_regno=#{regno} ) tp) temp "
			+ "LEFT OUTER JOIN medroom ON temp.medroom=medroom.bmr_code AND medroom.bmr_stdate<=temp.reg_date "
			+ "ORDER BY bmr_stdate DESC "
			+ "]]>"
			+ "</script>")
	public String selectReplaceIncharge(String regno) throws Exception;	
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT "
			+ "bpt_birth, bpt_ptno, bpt_name, bpt_pname, bpt_email, bpt_yage, bpt_telno, bpt_hpno,  "
			+ "(bpt_addr || ' ' || bpt_addr2) as bpt_addr, "
			+ "(CASE bpt_sex WHEN 'M' THEN 'MAN' ELSE 'WOMAN' END) as bpt_sex, "
			+ "SUBSTRING(bpt_resno, 1, 7) as bpt_resno, bpt_resno2, bpt_resno3 FROM PATIENT "
			+ "WHERE 0=0 AND bpt_ptno=#{ptno} "
			+ "]]>"
			+ "</script>")
	public EmrReplaceVO selectReplacePatient(String ptno) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT ${field} FROM IPST "
			+ "WHERE 0=0 AND ois_ptno=#{ptno} "
			+ "]]>"
			+ "</script>")
	public String selectReplaceIpst(String field, String ptno) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT ${field} FROM IPWON "
			+ "WHERE 0=0 AND oip_ptno=#{ptno} "
			+ "]]>"
			+ "</script>")
	public String selectReplaceIpwon(String field, String ptno) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT TOP 1 ${field} "
			+ "FROM QUALIFY "
			+ "WHERE 0=0 AND rqu_ptno=#{ptno} "
			+ "AND rqu_jogubun="
			+ "	(SELECT bpt_jogubun "
			+ "	FROM patient WHERE bpt_ptno=#{ptno}) "
			+ "ORDER BY rqu_reg_date DESC "
			+ "]]>"
			+ "</script>")
	public String selectReplaceQualify(String field, String ptno) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT TOP 1 rqu_tgso, rqu_tgso3 "
			+ "FROM QUALIFY "
			+ "WHERE 0=0 AND rqu_ptno=#{ptno} "
			+ "AND rqu_jogubun="
			+ "	(SELECT bpt_jogubun "
			+ "	FROM patient WHERE bpt_ptno=#{ptno}) "
			+ "ORDER BY rqu_reg_date DESC "
			+ "]]>"
			+ "</script>")
	public EmrReplaceVO selectReplaceQualify2(String ptno) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT TOP 1 ${field} FROM DOCTOR "
			+ "WHERE 0=0 AND bdt_docname=(SELECT xur_name FROM usrmngr WHERE xur_id=#{id})  "
			+ "]]>"
			+ "</script>")
	public String selectReplaceDoctor(String field, String id) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT TOP 1 ${field} FROM HOSINFO "
			+ "WHERE 0=0 AND bhi_usechk='Y' AND bhi_stdate<=${nowDate} ORDER BY bhi_stdate DESC  "
			+ "]]>"
			+ "</script>")
	public String selectReplaceHosinfo(String field, String id, String nowDate) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT bek_code, bek_mngrno, bek_name FROM sangyongkind WHERE 0=0 "
			+ "]]>"
			+ "</script>")
	public List<TemplateTreatementCateVO> selectTemplateTreatementCate() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT bes_code, bes_mngrno, bes_name, bes_kcode, bes_groupcd, bes_medroom, bes_naeyong "
			+ "FROM sangyonggu "
			+ "WHERE 0=0 "
			+ "]]>"
			+ "<if test=\"kcode != null and kcode != ''\">"
			+ "AND bes_kcode=#{kcode} "
			+ "</if>"
			+ "</script>")
	public List<TemplateTreatementDetailVO> selectTemplateTreatementDetail(String kcode) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT bek2_code, bek2_mngrno, bek2_name FROM sangyongkind2 WHERE 0=0 "
			+ "]]>"
			+ "</script>")
	public List<TemplateBoilerplateCateVO> selectTemplateBoilerplateCate() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT bes2_code, bes2_mngrno, bes2_medroom, bes2_kcode, bes2_naeyong "
			+ "FROM sangyonggu2 "
			+ "]]>"
			+ "<if test=\"kcode != null and kcode != ''\">"
			+ "WHERE 0=0 AND bes2_kcode=#{kcode} "
			+ "</if>"
			+ "</script>")
	public List<TemplateBoilerplateDetailVO> selectTemplateBoilerplateDetail(String kcode) throws Exception;
	
	
	
			
	/*
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT xeo_id, xeo_inout, xeo_cnt "
			+ "FROM emrchart_open "
			+ "WHERE 0=0 AND xeo_uid = #{id} AND xeo_inout = #{inout}  "
			+ "]]>"
			+ "</script>")*/
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT oec_seq, xeo_id, xeo_cnt FROM emrchart AS ec "
			+ "	LEFT OUTER JOIN emrchart_list AS ecl "
			+ "	ON ec.oec_cid=ecl.bec_id "
			+ "	LEFT OUTER JOIN emrchart_right AS ecr "
			+ "	ON ecl.bec_id=ecr.xer_id"
			+ "	LEFT OUTER JOIN (SELECT * FROM emrchart_open WHERE xeo_uid = #{id} AND xeo_inout = #{inout}) AS eco "
			+ "	ON ecl.bec_id=eco.xeo_id "
			+ "	WHERE 0=0 "
			+ " AND oec_use = 1 AND xer_view =  1 AND oec_state <> 'N' "
			+ " AND xeo_cnt <> 0 "
			+ "]]>"
			+ "	AND oec_ptno=#{ptno} AND oec_inout = #{inout} AND xer_uid = #{id}"
			+ "<if test=\"kind == null or kind == ''\">" 
			+ "AND bec_kind not in ('80', '99') " 
			+ "</if>"
			+ "<if test=\"kind != null and kind != ''\">"
			+ "AND bec_kind in (${kind}) "
			+ "</if>"
			+ "	ORDER BY xeo_id DESC, oec_date DESC,oec_time DESC "
			+ "</script>")
	public List<EmrOpenVO> selectOpen(EmrOpenParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT TOP 1 jin_kind  "
			+ "FROM ("
			+ "SELECT 1 AS num, rwj_regno AS regno, rwj_ptno AS ptno, rwj_reg_date AS reg_date, rwj_jin_kind AS jin_kind "
			+ "FROM waitjin  "
			+ "WHERE rwj_ptno=#{ptno}  "
			+ "UNION  "
			+ "SELECT 2 AS num, onw_regno AS regno, onw_ptno AS ptno, onw_reg_date AS reg_date, onw_jin_kind AS jin_kind  "
			+ "FROM naewon WHERE onw_ptno=#{ptno}) AS temp "
			+ "ORDER BY reg_date DESC, num DESC, regno DESC  "
			+ "]]>"
			+ "</script>")
	public String selectAdd1(EmrAddParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT bem_no, bem_inout "
			+ "FROM emrsheet_mngr "
			+ "WHERE 0=0 "
			+ "AND bem_gubun=#{gubun} AND bem_inout=#{inout} AND bem_id=#{id} AND bem_no NOT IN ( SELECT oec_sid FROM emrchart WHERE 0=0  AND oec_ptno=#{ptno} "
			+ "AND oec_state='S' AND oec_wdate=convert(char,GETDATE(),112)) "
			+ "ORDER BY bem_seq DESC "
			+ "]]>"
			+ "</script>")
	public List<EmrAddVO> selectAdd2(EmrAddParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT bes_kind "
			+ "FROM emrsheet_list "
			+ "WHERE 0=0 "
			+ "AND bes_id=#{idx} "
			+ "]]>"
			+ "</script>")
	public String selectKind(String idx) throws Exception;
	
	@Update("<script>"
			+ "<![CDATA["
			+ "UPDATE emrchart SET "
			+ "oec_time = #{time} "
			+ "WHERE 0=0 AND oec_ptno=#{ptno} AND oec_seq=#{seq}  "
			+ "]]>"
			+ "</script>")
	public void updateTime(EmrTimeParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT LEFT(oec_date, 4) AS directory, oec_file FROM emrchart "
			+ "WHERE 0=0 AND oec_ptno=#{ptno} AND oec_seq=#{seq} "
			+ "]]>"
			+ "</script>")
	public EmrDateVO selectDate(EmrDateParamVO vo) throws Exception;
	
	@Update("<script>"
			+ "<![CDATA["
			+ "UPDATE emrchart SET "
			+ "oec_wdate = #{wdate}, "
			+ "oec_date = #{date} "
			+ "WHERE 0=0 AND oec_ptno=#{ptno} AND oec_seq=#{seq}  "
			+ "]]>"
			+ "</script>")
	public void updateDate(EmrDateParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT TOP 1 oec_inout, oec_sid, oec_cid , "
			+ "		(SELECT (CASE WHEN MAX(oec_seq)+1 is NULL THEN 1 ELSE MAX(oec_seq)+1 END) as cnt "
			+ "		FROM emrchart WHERE oec_ptno=#{ptno}) AS seq "
			+ "FROM emrchart "
			+ "WHERE 0=0 AND oec_seq=#{seq} AND oec_ptno=#{ptno} "
			+ "]]>"
			+ "</script>")
	public EmrAttachCopyTempVO selectAttach1(EmrAttachCopyParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT TOP 1 oec_seq, "
			+ "SUBSTRING(oec_cdate, 1, 10) AS 'strDate', "
			+ "SUBSTRING(oec_cdate, 12, 8) AS 'strTime' "
			+ "FROM emrchart "
			+ "WHERE 0=0 AND oec_ptno=#{ptno} "
			+ "ORDER BY oec_seq DESC "
			+ "]]>"
			+ "</script>")
	public EmrAttachCopyTempVO selectAttach2(EmrAttachCopyParamVO vo) throws Exception;
	
	@Insert("<script>"
			+ "<![CDATA["
			+ "INSERT INTO emrchart (oec_regno, oec_ptno, oec_seq, oec_inout, oec_sid, oec_cid, oec_wdate, oec_create_uid) "
			+ "VALUES "
			+ "( #{regno}, #{ptno}, #{seq}, #{inout}, #{sid}, #{cid}, convert(char,GETDATE(),112), #{id})  "
			+ "]]>"
			+ "</script>")
	public void insertAttach2(EmrAttachCopyParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT * , "
			+ "(SELECT bes_name FROM emrsheet_list WHERE bes_id=#{sid}) AS 'bes_name' "
			+ "FROM emrform_list "
			+ "WHERE 0=0 AND bef_no=(SELECT bes_no FROM emrsheet_list WHERE bes_id=#{sid}) "
			+ "]]>"
			+ "</script>")
	public EmrAttachVO selectAttach3(EmrAttachCopyParamVO vo) throws Exception;
	
	@Delete("<script>"
			+ "UPDATE emrchart SET "
			+ "oec_seq=oec_seq "
			+ ",oec_use = '0' "
			+ ",oec_fdate = GETDATE() "
			+ ",oec_udate = GETDATE() "
			+ ",oec_up_uid = #{id} "
			+ "WHERE 0=0 "
			+ "AND oec_ptno = #{ptno}"
			+ "AND oec_seq = #{seq}"
			+ "</script>")
	public void deleteEmr(EmrDeleteParamVO vo) throws Exception; 
}
