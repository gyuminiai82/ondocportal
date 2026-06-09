package kr.ondoc.mapper.sybase.penchart;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import kr.ondoc.domain.sybase.manless.ReceiptPatientParamVO;
import kr.ondoc.domain.sybase.manless.ReceiptPatientTempVO;
import kr.ondoc.domain.sybase.penchart.PatientDetailVO;
import kr.ondoc.domain.sybase.penchart.PatientNewParamVO;
import kr.ondoc.domain.sybase.penchart.PatientParamVO;
import kr.ondoc.domain.sybase.penchart.PatientRoomSymptomVO;
import kr.ondoc.domain.sybase.penchart.PatientVO;

public interface PatientMapper {
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT "
			+ "rwj_resno AS resno, rwj_regno AS regno, rwj_reg_date AS reg_date, rwj_ptno AS ptno, rwj_medroom AS medroom, "
			+ "rwj_name AS name, rwj_yage AS age, rwj_sex AS sex, rwj_flag AS flag, rwj_time AS 'time', "
			+ "(SELECT bjg_name FROM jin_gubun WHERE bjg_code=rwj_jin_gubun), "
			+ "(SELECT bpt_hpno FROM patient WHERE bpt_ptno=rwj_ptno), "
			+ "(SELECT bji_name FROM jonginfo WHERE bji_code=rwj_jogubun), "
			+ "bmr_name,bmr_docname,bmr_kwanm,rwj_wait_class AS wait_class, rwj_wait_sclass AS wait_sclass,rwj_jin_gubun AS jin_gubun "
			+ "FROM waitjin LEFT OUTER JOIN "
			+ "(SELECT bmr_code,bmr_name,bmr_docname,bmr_kwanm,bmr_hosnum,bmr_stdate FROM medroom "
			+ "WHERE bmr_hosnum = (SELECT bhi_hosnum FROM hosinfo WHERE bmr_hosnum=bhi_hosnum and bhi_usechk='Y')) AS med( bmr_code,bmr_name ,bmr_docname,bmr_kwanm,bmr_hosnum,bmr_stdate ) "
			+ "ON ( bmr_code=rwj_medroom AND bmr_stdate=(SELECT MAX(bmr_stdate) FROM medroom "
			+ "WHERE bmr_code=rwj_medroom  AND bmr_stdate<=rwj_reg_date AND bmr_hosnum = (SELECT bhi_hosnum FROM hosinfo WHERE bmr_hosnum=bhi_hosnum and bhi_usechk='Y'))) "
			+ "WHERE 0=0 AND rwj_wait_class !='E' "
			+ "]]>"
			
			+ "<if test=\"date == null or date == ''\">"
			+ "AND rwj_reg_date=convert(char(8), getdate() ,112) "
			+ "</if>"
			+ "<if test=\"date != null and date != ''\">"
			+ "AND rwj_reg_date=#{date} "
			+ "</if>"
			
			+ "<if test=\"room_name != null and room_name != ''\">"
			+ "AND bmr_name=#{room_name} "
			+ "</if>"
			
			+ "<if test=\"name != null and name != ''\">"
			+ "AND rwj_name LIKE '%${name}%' "
			+ "</if>"
			
			+ "ORDER BY rwj_time ASC "
			+ "</script>")
	public List<PatientVO> patientOutStandByList(PatientParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT onw_resno AS resno, onw_regno AS regno, onw_ptno AS ptno, onw_name AS name, onw_sex AS sex,"
			+ "onw_yage AS age, onw_ch_date AS ch_date, onw_time AS 'time', "
			+ "bmr_name, bmr_docname, bmr_kwanm, "
			+ "(SELECT bjg_name FROM jin_gubun WHERE bjg_code=onw_jin_gubun), "
			+ "(SELECT bji_name FROM jonginfo WHERE bji_code=onw_jogubun) FROM naewon LEFT OUTER JOIN "
			+ "(SELECT bmr_code,bmr_name,bmr_docname,bmr_kwanm,bmr_hosnum,bmr_stdate FROM medroom "
			+ "WHERE bmr_hosnum = (SELECT bhi_hosnum FROM hosinfo WHERE bmr_hosnum=bhi_hosnum and bhi_usechk='Y') ) AS med (bmr_code,bmr_name ,bmr_docname,bmr_kwanm,bmr_hosnum,bmr_stdate) "
			+ "ON (onw_medroom=bmr_code AND bmr_stdate=(SELECT MAX(bmr_stdate) FROM medroom "
			+ "WHERE bmr_code=onw_medroom AND bmr_stdate<=onw_ch_date) AND bmr_hosnum = "
			+ "(SELECT bhi_hosnum FROM hosinfo WHERE bmr_hosnum=bhi_hosnum and bhi_usechk='Y') ) "
			+ "WHERE 0=0 "
			+ "]]>"
			
			+ "<if test=\"date == null or date == ''\">"
			+ "AND onw_ch_date=convert(char(8), getdate() ,112) "
			+ "</if>"
			+ "<if test=\"date != null and date != ''\">"
			+ "AND onw_ch_date=#{date} "
			+ "</if>"
			
			+ "AND NOT(onw_kind LIKE '9%') AND onw_class LIKE '1%' AND (onw_kind LIKE '1%' OR onw_kind LIKE '3%' OR onw_kind LIKE '5%' OR onw_kind LIKE '_3') "
			
			+ "<if test=\"room_name != null and room_name != ''\">"
			+ "AND bmr_name=#{room_name} "
			+ "</if>"
			
			+ "<if test=\"name != null and name != ''\">"
			+ "AND onw_name LIKE '%${name}%' "
			+ "</if>"
			
			+ "<if test=\"sort != null and sort != '' and sort == 'name-asc'\">"
			+ "ORDER BY onw_name ASC "
			+ "</if>"
			+ "<if test=\"sort != null and sort != '' and sort == 'name-desc'\">"
			+ "ORDER BY onw_name DESC "
			+ "</if>"
			+ "<if test=\"sort != null and sort != '' and sort == 'time-asc'\">"
			+ "ORDER BY onw_time ASC "
			+ "</if>"
			+ "<if test=\"sort != null and sort != '' and sort == 'time-desc'\">"
			+ "ORDER BY onw_time DESC "
			+ "</if>"
			+ "<if test=\"sort == null or sort == ''\">"
			+ "ORDER BY onw_time ASC "
			+ "</if>"
			+ "</script>")
	public List<PatientVO> patientOutCompleteList(PatientParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT ois_resno AS resno, ois_byungdong AS byungdong, ois_room AS room, ois_regno AS regno, "
			+ "oip_in_date AS in_date, ois_ptno AS ptno, ois_name AS name, "
			+ "ois_age AS age, ois_sex AS sex, ois_reg_ref AS reg_ref, ois_medroom AS medroom, ois_kwa AS kwa, "
			+ "(SELECT bji_name FROM jonginfo WHERE bji_code=ois_jogubun ), bmr_name, bmr_docname, bmr_kwanm "
			+ "FROM ipwon, ipst LEFT OUTER JOIN "
			+ "(SELECT bmr_code,bmr_name,bmr_docname,bmr_kwanm,bmr_hosnum,bmr_stdate FROM medroom WHERE bmr_hosnum = (SELECT bhi_hosnum FROM hosinfo WHERE bmr_hosnum=bhi_hosnum and bhi_usechk='Y') ) AS med "
			+ "( bmr_code,bmr_name ,bmr_docname,bmr_kwanm,bmr_hosnum,bmr_stdate ) ON (bmr_code=ois_medroom "
			+ "AND bmr_stdate= (SELECT MAX(bmr_stdate) FROM medroom "
			+ "WHERE bmr_code=ois_medroom AND bmr_stdate<=DATEFORMAT(GETDATE(), 'yyyymmdd')) AND bmr_hosnum = (SELECT bhi_hosnum FROM hosinfo WHERE bmr_hosnum=bhi_hosnum and bhi_usechk='Y') ) "
			+ "]]>"
			+ "WHERE oip_regno=ois_regno AND oip_ptno=ois_ptno "
			
			+ "<if test=\"room_name != null and room_name != ''\">"
			+ "AND bmr_name=#{room_name} "
			+ "</if>"
			
			+ "<if test=\"byungdong != null and byungdong != ''\">"
			+ "AND ois_byungdong=#{byungdong} "
			+ "</if>"
			
			+ "<if test=\"name != null and name != ''\">"
			+ "AND ois_name LIKE '%${name}%' "
			+ "</if>"
			
			+ "ORDER BY ois_byungdong, ois_room "
			+ "</script>")
	public List<PatientVO> patientInJaewonList(PatientParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT och_byungdong AS byungdong, och_room AS room, oip_regno AS regno, oip_in_date AS in_date, oip_out_date AS out_date, oip_out_time, "
			+ "oip_ptno AS ptno, oip_name AS name,oip_resno AS resno, oip_age AS age, oip_sex AS sex,oip_medroom AS medroom, oip_kwa AS kwa, och_jogubun AS jogubun, "
			+ "(SELECT bji_name FROM jonginfo WHERE bji_code=och_jogubun), bmr_name,bmr_docname,bmr_kwanm  FROM ipwon LEFT OUTER JOIN "
			+ "(SELECT TOP 1 och_regno,och_seq,och_byungdong,och_room,och_jogubun,bmr_name,bmr_docname,bmr_kwanm  FROM changehis LEFT OUTER JOIN "
			+ "(SELECT bmr_code,bmr_name,bmr_docname,bmr_kwanm,bmr_hosnum,bmr_stdate FROM medroom "
			+ "WHERE bmr_hosnum = (SELECT bhi_hosnum FROM hosinfo WHERE bmr_hosnum=bhi_hosnum and bhi_usechk='Y') ) AS med (bmr_code,bmr_name ,bmr_docname,bmr_kwanm,bmr_hosnum,bmr_stdate ) "
			+ "ON (bmr_code=och_medroom AND bmr_stdate= (SELECT MAX(bmr_stdate) FROM medroom "
			+ "WHERE bmr_code=och_medroom AND bmr_stdate<=och_st_date)) AND bmr_hosnum = (SELECT bhi_hosnum FROM hosinfo "
			+ "WHERE bmr_hosnum=bhi_hosnum and bhi_usechk='Y') ) AS chgroom (och_regno,och_seq,och_byungdong,och_room,och_jogubun,bmr_name,bmr_docname,bmr_kwanm ) "
			+ "ON (och_regno=oip_regno AND och_seq=(SELECT MAX(och_seq) "
			+ "FROM changehis WHERE och_regno=oip_regno)) "
			+ "WHERE 0=0 "
			+ "]]>"
			
			+ "<if test=\"start_date != null and start_date != '' and end_date != null and end_date != ''\">"
			+ "AND oip_out_date BETWEEN #{start_date} AND #{end_date} "
			+ "</if>"
			
			+ "<if test=\"room_name != null and room_name != ''\">"
			+ "AND bmr_name=#{room_name} "
			+ "</if>"
			
			+ "<if test=\"byungdong != null and byungdong != ''\">"
			+ "AND och_byungdong=#{byungdong} "
			+ "</if>"
			
			+ "<if test=\"name != null and name != ''\">"
			+ "AND oip_name LIKE '%${name}%' "
			+ "</if>"
			
			+ "ORDER BY och_byungdong, och_room "
			+ "</script>")
	public List<PatientVO> patientInToewonList(PatientParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT DISTINCT bpt_ptno AS ptno, bpt_name AS name, bpt_resno AS resno, "
			+ "bpt_telno AS telno, bpt_hpno AS hpno, bpt_cen AS cen, bpt_jogubun AS jogubun, bpt_sex AS sex, "
			+ "bpt_mihonmo, bpt_enddt, onw_ch_date, onw_medroom, bmr_docname, "
			+ "bpt_yage as age, "
			+ "(SELECT bji_name FROM jonginfo WHERE bji_code=bpt_jogubun),bmr_code, bmr_name FROM patient LEFT OUTER JOIN "
			+ "(SELECT onw_ptno,onw_ch_date,onw_medroom FROM naewon a WHERE onw_ch_date=(SELECT MAX(onw_ch_date) FROM naewon WHERE onw_ptno=a.onw_ptno)) AS "
			+ "NW(onw_ptno,onw_ch_date,onw_medroom) ON (onw_ptno=bpt_ptno) LEFT OUTER JOIN "
			+ "(SELECT bmr_code,bmr_name,bmr_docname,bmr_kwanm,bmr_hosnum,bmr_stdate FROM medroom "
			+ "WHERE bmr_hosnum = (SELECT bhi_hosnum FROM hosinfo WHERE bmr_hosnum=bhi_hosnum and bhi_usechk='Y') ) AS med "
			+ "(bmr_code,bmr_name ,bmr_docname,bmr_kwanm,bmr_hosnum,bmr_stdate) ON (onw_medroom=bmr_code AND bmr_stdate=(SELECT MAX(bmr_stdate) FROM medroom "
			+ "WHERE bmr_code=onw_medroom AND bmr_stdate<=onw_ch_date) AND bmr_hosnum = (SELECT bhi_hosnum FROM hosinfo WHERE bmr_hosnum=bhi_hosnum and bhi_usechk='Y')) "
			+ "WHERE 0=0  "
			+ "]]>"

			//ptno/name/hpno/resno
			//임용태님 요청
			+ "<if test=\"gubun == 'ptno' and name != null and name != ''\">"
			+ "AND bpt_ptno=#{name} "
			+ "</if>"
			
			+ "<if test=\"gubun == 'name' and name != null and name != ''\">"
			+ "AND bpt_name LIKE '%${name}%' "
			+ "</if>"
			
			//한진님 요청
			+ "<if test=\"gubun == 'hpno' and name != null and name != ''\">"
			+ "AND (substr(bpt_hpno,1,3)||substr(bpt_hpno,5,4)||substr(bpt_hpno,10,4) LIKE '%${name}%') OR (substr(bpt_telno,1,3)||substr(bpt_telno,5,4)||substr(bpt_telno,10,4) LIKE '%${name}%') "
			+ "</if>"
			
			//임용태님 요청
			+ "<if test=\"gubun == 'resno' and name != null and name != ''\">"
			+ "AND bpt_resno LIKE '${name}%' "
			+ "</if>"
			
			+ "<if test=\"name == null or name == ''\">"
			+ "AND bpt_ptno='0000000000' "
			+ "</if>"
			+ "<![CDATA["
			+ "AND bpt_resno != '******' "
			+ "]]>"
			+ "ORDER BY bpt_name "
			+ "</script>")
	public List<PatientVO> patientSearchList(PatientParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT (SELECT bmr_name FROM medroom WHERE bmr_code=ors_medroom AND bmr_use='Y' AND bmr_hosnum=(SELECT bhi_hosnum FROM hosinfo WHERE bhi_usechk='Y')) as medroom, "
			+ "ors_symptom "
			+ "from room_symptom "
			+ "where ors_ptno=#{ptno} ORDER BY ors_seq  "
			+ "]]>"
			+ "</script>")
	public List<PatientRoomSymptomVO> selectPatientRoomSymptom(String ptno) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "select (SELECT bmr_name FROM medroom WHERE bmr_code=ors_medroom AND bmr_stdate=(SELECT MAX(bmr_stdate) FROM medroom "
			+ "WHERE bmr_code=ors_medroom AND bmr_stdate <= DATEFORMAT( getdate(*), 'yyyymmdd') )) as medroom, ors_symptom from room_symptom_rtf where ors_ptno=#{ptno} "
			+ "]]>"
			+ "</script>")
	public List<PatientRoomSymptomVO> selectPatientRoomSymptomRtf(String ptno) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT osp_symptom FROM special WHERE osp_ptno=#{ptno} "
			+ "]]>"
			+ "</script>")
	public List<PatientRoomSymptomVO> selectPatientSymptom(String ptno) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT bpt_ptno as ptno, bpt_name as name, bpt_resno as resno,"
			+ "bpt_yage as yage, bpt_sex as sex, bpt_telno as telno, bpt_hpno as hpno, bpt_addr as addr, "
			+ "bpt_addr2 as addr2, bpt_jogubun,TRIM(bpt_jubsumemo) AS bpt_jubsumemo, "
			+ "(SELECT bji_name FROM jonginfo WHERE bji_code=bpt_jogubun) as bji_name, "
			+ "(SELECT rcm_memo FROM receipt_memo WHERE rcm_ptno=bpt_ptno) as rcm_memo, "
			+ "(SELECT TOP 1 xso_base35 as opt FROM systemoption) as opt, "
			+ "(SELECT TOP 1 osp_symptom as symptom_rtf FROM special_rtf WHERE osp_ptno=bpt_ptno) as symptom_rtf, "
			+ "calage(bpt_cen + substr(bpt_resno, 1, 6),today()) as age, "
			+ "(SELECT TOP 1 rwj_regno FROM waitjin WHERE 0=0 AND rwj_wait_class!='E'  AND rwj_reg_date=DATEFORMAT(now(), 'YYYYMMDD') AND rwj_ptno=bpt_ptno) AS daegi_regno, "
			+ "(SELECT TOP 1 onw_regno FROM naewon WHERE onw_ptno=bpt_ptno AND onw_reg_date=(SELECT MAX(onw_reg_date) FROM naewon WHERE onw_ptno=bpt_ptno)) AS complete_regno, "
			+ "(SELECT TOP 1 ois_regno FROM ipst WHERE ois_ptno=bpt_ptno) AS jaewon_regno,  "
			+ "(SELECT TOP 1 och_regno FROM changehis WHERE och_ptno=bpt_ptno AND och_reg_date=(SELECT MAX(och_reg_date) FROM changehis WHERE och_ptno=bpt_ptno)) AS toewon_regno "
			+ "FROM patient "
			+ "WHERE 0=0  "
			+ "AND bpt_ptno=#{ptno} "
			+ "]]>"
			+ "</script>")
	public PatientDetailVO patientDetail(String ptno) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT TOP 1 bpt_ptno,bpt_hpno,bpt_jogubun,bpt_name "
			+ "FROM patient "
			+ "WHERE 0=0 "
			+ "]]>"
			+ "<if test=\"jumin1 != null and jumin1 != ''\">"
			+ "AND SUBSTR(bpt_resno, 1, 7) = SUBSTR(#{jumin1}, 1, 7) "
			+ "</if>"
			+ "<if test=\"jumin2 != null and jumin2 != ''\">"
			+ "AND bpt_resno3 = #{jumin2} "
			+ "</if>"
			//+ "AND bpt_name = #{name} AND bpt_resno LIKE '%${jumin1Front}%' "
			//+ "<if test=\"hpno != null and hpno != ''\">"
			//+ "AND (bpt_hpno = #{hpno} OR bpt_hpno = #{hpno1}) "
			//+ "</if>"
			+ "ORDER BY bpt_enddt DESC "
			+ "</script>")
	public ReceiptPatientTempVO selectPatientTemp(PatientNewParamVO vo) throws Exception;
	
	@Insert("<script>"
			+ "<![CDATA["
			+ "INSERT INTO patient(bpt_ptno, bpt_name, bpt_resno, bpt_resno2,bpt_resno3,"
			+ "bpt_birth, bpt_hpno, bpt_jogubun,bpt_sex,"
			+ "bpt_yage,bpt_mage, bpt_cen, bpt_initdt,"
			+ "bpt_zip,bpt_addr,bpt_addr2, bpt_jubsumemo) "
			+ "VALUES "
			+ "(#{ptno},#{name},#{jumin1},'',#{jumin2},"
			+ "#{birth},#{hpno},'91',#{sex},"
			+ "#{sYAge},#{sMAge},#{sCen},DATEFORMAT(GETDATE(), 'yyyymmdd'),"
			+ "#{zip},#{addr1},#{addr2}, #{jubsumemo}"
			+ ") "
			+ "]]>"
			+ "</script>")
	public void insertPatient(PatientNewParamVO vo) throws Exception;
}
