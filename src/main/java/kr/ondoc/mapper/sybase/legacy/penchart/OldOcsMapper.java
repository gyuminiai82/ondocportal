package kr.ondoc.mapper.sybase.legacy.penchart;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import kr.ondoc.domain.sybase.legacy.penchart.DrVitaiParamVO;
import kr.ondoc.domain.sybase.legacy.penchart.DrVitalFieldDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.DrVitalValueDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.InPatientCheobangDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.LoginDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.NaewonParamVO;
import kr.ondoc.domain.sybase.legacy.penchart.NaewonYearDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.NurseRecordDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.NurseRecordParamVO;
import kr.ondoc.domain.sybase.legacy.penchart.OcsNurseDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.OcsNurseParamVO;
import kr.ondoc.domain.sybase.legacy.penchart.OcsViaDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.TprParamVO;
import kr.ondoc.domain.sybase.legacy.penchart.TprPatientDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.TprPatientVO;
import kr.ondoc.domain.sybase.legacy.penchart.TprVitalDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.TprVitalHangDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.TreatementResultDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.TreatementResultDetailDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.TreatementResultParamVO;
import kr.ondoc.domain.sybase.legacy.penchart.VitalDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.VitalParamVO;
import kr.ondoc.domain.sybase.legacy.penchart.VitalValueDataVO;

public interface OldOcsMapper {
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT jew_ch_date, jew_ord_seq "
			+ "FROM waitexam, examch "
			+ "WHERE jec_detail<>'T' AND (jew_waitgu<>'0' AND jew_waitgu<>'8') "
			+ "AND jec_regno=jew_regno AND jec_ch_date=jew_ch_date AND jec_kwa=jew_kwa "
			+ "AND jec_medroom=jew_medroom AND jec_ord_seq=jew_ord_seq AND jew_ptno=#{ptno} AND LEFT(jew_ch_date, 4)=#{year} "
			+ "GROUP BY jew_ch_date, jew_ord_seq ORDER BY jew_ch_date DESC "
			+ "]]>"
			+ "</script>")
	public List<TreatementResultDataVO> selectTreatementResultYear(TreatementResultParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT LEFT(jew_ch_date, 4) AS year "
			+ "FROM waitexam, examch WHERE jec_detail<>'T' AND (jew_waitgu<>'0' AND jew_waitgu<>'8') "
			+ "AND jec_regno=jew_regno AND jec_ch_date=jew_ch_date AND jec_kwa=jew_kwa "
			+ "AND jec_medroom=jew_medroom AND jec_ord_seq=jew_ord_seq AND jew_ptno=#{ptno} "
			+ "GROUP BY year ORDER BY year DESC "
			+ "]]>"
			+ "</script>")
	public List<TreatementResultDataVO> selectTreatementResult(TreatementResultParamVO vo) throws Exception;
	
	
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT jew_regno,jew_ch_date,jew_iwgu,jew_ptno,jew_kwa,jew_medroom,jew_ord_seq,jec_seq,jec_scode,jec_edi_code,jec_routine, jec_name,jec_value,jec_dnvalue,jec_upvalue,jec_gumche,jec_unit "
			+ ",COALESCE((SELECT jer_value FROM exam_sutakresult WHERE jer_regno=jec_regno AND jer_ch_date=jec_ch_date AND jer_ord_seq=jec_ord_seq AND jer_seq=jec_seq), '') AS jer_value "
			+ "FROM waitexam, examch "
			+ "WHERE jec_detail<>'T' AND (jew_waitgu<>'0' AND jew_waitgu<>'8') AND jec_regno =jew_regno "
			+ "AND jec_ch_date=jew_ch_date AND jec_kwa =jew_kwa AND jec_medroom=jew_medroom AND jec_ord_seq=jew_ord_seq "
			+ "AND jew_ptno=#{ptno} AND jew_ch_date=#{date} AND jew_ord_seq=#{ord_seq} "
			+ "ORDER BY jew_ch_date DESC, jec_seq "
			+ "]]>"
			+ "</script>")
	public List<TreatementResultDetailDataVO> selectTreatementResultDetail(TreatementResultParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT jec_edi_code, jew_regno,jew_ch_date, jec_value FROM waitexam, examch "
			+ "WHERE jec_detail<>'T' AND (jew_waitgu<>'0' AND jew_waitgu<>'8') "
			+ "AND jec_regno =jew_regno AND jec_ch_date=jew_ch_date AND jec_kwa =jew_kwa AND jec_medroom=jew_medroom "
			+ "AND jec_ord_seq=jew_ord_seq AND jew_ptno=#{ptno} AND jec_edi_code=#{edi_code} AND jec_scode=#{code} "
			+ "ORDER BY jew_ch_date DESC, jec_seq "
			+ "]]>"
			+ "</script>")
	public List<TreatementResultDetailDataVO> selectTreatementResultDetailCode(TreatementResultParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT bvh_hang01,bvh_hang02,bvh_hang03,bvh_hang04,bvh_hang05,bvh_hang06,bvh_hang07,bvh_hang08,bvh_hang09,bvh_hang10, "
			+ "bvh_hang11,bvh_hang12,bvh_hang13,bvh_hang14,bvh_hang15,bvh_hang16,bvh_hang17,bvh_hang18 "
			+ "FROM vitalhang "
			+ "WHERE bvh_medroom=(SELECT TOP 1 bmr_code "
			+ "						FROM medroom WHERE bmr_use='Y' AND bmr_hosnum=(SELECT TOP 1 bhi_hosnum  "
			+ "																		FROM hosinfo WHERE bhi_stdate=(SELECT MAX(bhi_stdate) as hosdate "
			+ "																										FROM hosinfo WHERE bhi_stdate <= DATEFORMAT(GETDATE(), 'YYYYmmdd'))))"
			+ "]]>"
			+ "</script>")
	public List<VitalDataVO> selectVital(VitalParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT ovt_ptno, ovt_vidate, MAX(ovt_time) AS ovt_time ,AVG(ovt_fil01) AS ovt_fil01,AVG(ovt_fil02) AS ovt_fil02,AVG(ovt_fil03) AS ovt_fil03 ,AVG(ovt_fil04) AS ovt_fil04,AVG(ovt_fil05) AS ovt_fil05,"
			+ "AVG(ovt_fil06) AS ovt_fil06 ,AVG(ovt_fil07) AS ovt_fil07,AVG(ovt_fil08) AS ovt_fil08,AVG(ovt_fil09) AS ovt_fil09 ,AVG(ovt_fil10) AS ovt_fil10,AVG(ovt_fil11) AS ovt_fil11,AVG(ovt_fil12) AS ovt_fil12 ,"
			+ "AVG(ovt_fil13) AS ovt_fil13,AVG(ovt_fil14) AS ovt_fil14,AVG(ovt_fil15) AS ovt_fil15 ,AVG(ovt_fil16) AS ovt_fil16,AVG(ovt_fil17) AS ovt_fil17,AVG(ovt_fil18) AS ovt_fil18 "
			+ "FROM  (SELECT * FROM vital  "
			+ "			WHERE 0=0 AND (ovt_fil01 like '%[0-9]%' OR ovt_fil01 is null OR ovt_fil01='') "
			+ "			AND (ovt_fil02 like '%[0-9]%' OR ovt_fil02 is null OR ovt_fil02='') AND (ovt_fil03 like '%[0-9]%' OR ovt_fil03 is null OR ovt_fil03='') "
			+ "			AND (ovt_fil04 like '%[0-9]%' OR ovt_fil04 is null OR ovt_fil04='') AND (ovt_fil05 like '%[0-9]%' OR ovt_fil05 is null OR ovt_fil05='') "
			+ "			AND (ovt_fil06 like '%[0-9]%' OR ovt_fil06 is null OR ovt_fil06='') AND (ovt_fil07 like '%[0-9]%' OR ovt_fil07 is null OR ovt_fil07='') "
			+ "			AND (ovt_fil08 like '%[0-9]%' OR ovt_fil08 is null OR ovt_fil08='') AND (ovt_fil09 like '%[0-9]%' OR ovt_fil09 is null OR ovt_fil09='') "
			+ "			AND (ovt_fil10 like '%[0-9]%' OR ovt_fil10 is null OR ovt_fil10='') AND (ovt_fil11 like '%[0-9]%' OR ovt_fil11 is null OR ovt_fil11='') "
			+ "			AND (ovt_fil12 like '%[0-9]%' OR ovt_fil12 is null OR ovt_fil12='') AND (ovt_fil13 like '%[0-9]%' OR ovt_fil13 is null OR ovt_fil13='') "
			+ "			AND (ovt_fil14 like '%[0-9]%' OR ovt_fil14 is null OR ovt_fil14='') AND (ovt_fil15 like '%[0-9]%' OR ovt_fil15 is null OR ovt_fil15='') "
			+ "			AND (ovt_fil16 like '%[0-9]%' OR ovt_fil16 is null OR ovt_fil16='') AND (ovt_fil17 like '%[0-9]%' OR ovt_fil17 is null OR ovt_fil17='') "
			+ "			AND (ovt_fil18 like '%[0-9]%' OR ovt_fil18 is null OR ovt_fil18='') AND ovt_ptno=#{ptno}  "
			+ "]]>"			
			+ "<if test=\"start_date != null and start_date != '' and end_date != null and end_date != ''\">"
			+ "			AND ovt_vidate BETWEEN #{start_date} AND #{end_date} ) AS temp "
			+ "</if>"
			+ "			GROUP BY ovt_ptno, ovt_vidate "
			+ "ORDER BY ovt_vidate DESC, ovt_time DESC "
			+ "</script>")
	public List<VitalValueDataVO> selectVitalValue(VitalParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT bpt_ptno, bpt_name, (SUBSTR(NSRDT, 1, 4) + SUBSTR(NSRDT, 6, 2) + SUBSTR(NSRDT, 9, 2)) AS 'NSRDT', NSRTM, TXTVL "
			+ "FROM drsoft.UTCHTNSRM "
			+ "LEFT OUTER JOIN patient "
			+ "ON CHTNO = bpt_ptno  WHERE 0=0  AND CHTNO=#{ptno}  AND NSRDT BETWEEN #{start_date} AND #{end_date} ORDER BY NSRDT, SEQNO  "
			+ "]]>"	
			+ "</script>")
	public List<NurseRecordDataVO> selectNurseRecordV2(NurseRecordParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT TOP 1 * FROM vitalhang WHERE bvh_check='Y' OR bvh_medroom='01'  "
			+ "]]>"	
			+ "</script>")
	public List<TprVitalHangDataVO> selectTprVitalHang(TprParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT v.*, ip.ois_byungdong, ip.ois_room, ip.ois_medroom, ip.ois_bed FROM vital AS v   "
			+ "INNER JOIN ipst AS ip   "
			+ "ON ip.ois_ptno = v.ovt_ptno   "
			+ "WHERE ovt_vidate=#{date} AND ovt_time=#{time} "
			+ "]]>"	
			+ "<if test=\"byungdong != null and byungdong != ''\">"
			+ "AND ois_byungdong = #{byungdong} "
			+ "</if>"
			+ "<if test=\"medroom != null and medroom != ''\">"
			+ "AND ois_medroom = #{medroom} "
			+ "</if>"
			+ "<if test=\"ptno != null and ptno != ''\">" 
			+ "AND ovt_ptno in (${ptno}) " 
			+ "</if>"
			+ "ORDER BY ois_byungdong,ois_room,ois_bed "
			+ "</script>")
	public List<TprVitalDataVO> selectTprVitalDate(TprParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT temp2.* FROM (SELECT v.ovt_ptno AS ptno, MAX(v.ovt_vidate+ovt_time) AS vidate "
			+ "						FROM vital AS v"
			+ "						INNER JOIN ipst AS ip "
			+ "						ON ip.ois_ptno = v.ovt_ptno GROUP BY v.ovt_ptno ) AS temp1 "
			+ "LEFT OUTER JOIN (SELECT v.*, ip.ois_byungdong, ip.ois_room, ip.ois_medroom, ip.ois_bed FROM vital AS v "
			+ "					INNER JOIN ipst AS ip ON ip.ois_ptno = v.ovt_ptno ) AS temp2 "
			+ "ON temp1.ptno=temp2.ovt_ptno AND LEFT(temp1.vidate, 8)=temp2.ovt_vidate AND RIGHT(temp1.vidate, 6)=temp2.ovt_time "
			+ "WHERE 0=0 "
			+ "]]>"	
			+ "<if test=\"byungdong != null and byungdong != ''\">"
			+ "AND ois_byungdong = #{byungdong} "
			+ "</if>"
			+ "<if test=\"medroom != null and medroom != ''\">"
			+ "AND ois_medroom = #{medroom} "
			+ "</if>"
			+ "<if test=\"ptno != null and ptno != ''\">" 
			+ "AND ovt_ptno in (${ptno}) " 
			+ "</if>"
			+ "ORDER BY temp2.ois_byungdong,temp2.ois_room,temp2.ois_bed "
			+ "</script>")
	public List<TprVitalDataVO> selectTprVital(TprParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT ois_room,ois_byungdong,ois_medroom,bpt_ptno,bpt_name,calage(bpt_cen + SUBSTR(bpt_resno,1,6) , DATEFORMAT( now(*), 'yyyymmdd' )) as age ,bpt_sex "
			+ "FROM ipst AS ip "
			+ "INNER JOIN patient AS p "
			+ "ON ip.ois_ptno=p.bpt_ptno "
			+ "]]>"	
			+ "<if test=\"byungdong != null and byungdong != ''\">" 
			+ "AND ip.ois_byungdong=#{byungdong} " 
			+ "</if>"
			+ "<if test=\"medroom != null and medroom != ''\">" 
			+ "AND ip.ois_medroom=#{medroom} "
			+ "</if>"
			+ "ORDER BY ip.ois_byungdong, ip.ois_room, ip.ois_bed "
			+ "</script>")
	public List<TprPatientDataVO> selectTprPatient(TprParamVO vo) throws Exception;
	
	@Delete("<script>"
			+ "DELETE FROM vital WHERE 0=0 "
			+ "AND ovt_mngrno = #{ovt_mngrno} "
			+ "AND ovt_vidate = #{ovt_vidate} "
			+ "AND ovt_time = #{ovt_time} "
			+ "AND ovt_ptno = ${ptno} "
			+ "</script>")
	public void deleteTprVital(TprParamVO vo) throws Exception;
	
	@Insert("<script>"
			+ "INSERT INTO vital (ovt_mngrno, ovt_vidate, ovt_time, ovt_medroom, ovt_uid, ${field}) VALUES ("
			+ "#{ovt_mngrno}, #{ovt_vidate}, #{ovt_time}, #{ovt_medroom}, #{ovt_uid}, ${value})"
			+ "</script>")
	public void insertTprVital(TprParamVO vo) throws Exception;
	
	
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT COLNM, COLTL FROM drsoft.UTLSTCOLM WHERE LSTCD = 'EYERETLT2' AND USEYN = '1' AND COLVW = '1' AND USRID = '' AND EDTYN = '1' ORDER BY SEQVL "
			+ "]]>"	
			+ "</script>")
	public List<DrVitalFieldDataVO> selectVitalField() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT RETDT,FLD07,FLD17,FLD01,FLD02,FLD03,FLD05,FLD04,FLD06,FLD12,FLD13,FLD14,FLD09,FLD11,FLD08,REM01, SEQID "
			+ "FROM drsoft.UTEYERETM "
			+ "WHERE 0=0 AND CHTNO = #{ptno} AND (SUBSTR(RETDT, 1, 4) + SUBSTR(RETDT, 6, 2) + SUBSTR(RETDT, 9, 2)) "
			+ "BETWEEN #{start_date} AND #{end_date} ORDER BY RETDT, SEQID  "
			+ "]]>"	
			+ "</script>")
	public List<DrVitalValueDataVO> selectDrVitalValue(DrVitaiParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT  bvi_code as code, bvi_mngrno as mngrno, bvi_hang as hang, bvi_discript as discript, bvi_explain as explain, bvi_use as use, bvi_zcode as zcode, bvi_div as div, bvi_nurse as nurse "
			+ "FROM via "
			+ "WHERE 0=0 AND bvi_div IS NOT NULL AND bvi_div<>'' AND bvi_use='Y' ORDER BY bvi_code   "
			+ "]]>"	
			+ "</script>")
	public List<OcsViaDataVO> selectVia() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT onr_time, onr_rec, "
			+ "(CASE onr_uid  WHEN 'ACEOFHIS' THEN '관리자'  "
			+ "ELSE (SELECT xur_name FROM usrmngr WHERE xur_id=onr_uid)  "
			+ "END) as nurse_nm  "
			+ "FROM nurse_record  "
			+ "WHERE onr_regno = #{regno}  AND onr_date = #{date}  AND onr_jobgu='0'  AND onr_cancel_date is NULL  ORDER BY onr_time, onr_seq  "
			+ "]]>"	
			+ "</script>")
	public List<OcsNurseDataVO> selectNurse(OcsNurseParamVO vo) throws Exception;
}
