package kr.ondoc.mapper.sybase.legacy.ondocplus;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import kr.ondoc.domain.sybase.legacy.ondocplus.ByungdongDataVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.IpwonPatientCheobangDataVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.IpwonPatientParamVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.IpwonPatientSangByungDataVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.IpwonPatientSymptomDataVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.MedroomDataVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.PatientSearchDataVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.PatientSearchParamVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.UsrmngrDataVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.UsrmngrParamVO;

public interface OldOndocplusMapper {
	@Select("<script>"
			+ "  SELECT xur_id, xur_pass, LENGTH(xur_pass) AS length, xur_name, xur_resno, xur_inday, xur_outday "
			+ "FROM usrmngr "
			+ "WHERE 0=0 AND xur_id=#{xur_id} "
			+ "</script>")
	public List<UsrmngrDataVO> selectUsrmngr(UsrmngrParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "SELECT bmr_code,bmr_name,bmr_docname,bmr_kwanm FROM medroom "
			+ "WHERE bmr_use='Y' AND bmr_hosnum=(SELECT bhi_hosnum FROM hosinfo WHERE bhi_usechk='Y') "
			+ "ORDER BY bmr_code "
			+ "</script>")
	public List<MedroomDataVO> selectMedroom() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT DISTINCT(brm_byungdong) AS brm_byungdong FROM room WHERE brm_bedcheck<> '2'  "
			+ "]]>"
			+ "</script>")
	public List<ByungdongDataVO> selectByungdong() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ " SELECT DISTINCT bpt_ptno,bpt_name,bpt_resno,bpt_telno,"
			+ "		bpt_hpno,bpt_cen,bpt_jogubun,bpt_sex, bpt_mihonmo,"
			+ "		bpt_enddt,onw_ch_date,onw_medroom, bmr_docname, "
			+ "calage(bpt_cen + substr(bpt_resno, 1, 6),today()) as bpt_age, "
			+ "		(SELECT bji_name FROM jonginfo WHERE bji_code=bpt_jogubun),bmr_code,bmr_name "
			+ "FROM patient "
			+ "LEFT OUTER JOIN "
			+ "(SELECT onw_ptno,onw_ch_date,onw_medroom FROM naewon a WHERE onw_ch_date=(SELECT MAX(onw_ch_date) "
			+ "																				FROM naewon WHERE onw_ptno=a.onw_ptno)) "
			+ "AS NW(onw_ptno,onw_ch_date,onw_medroom) "
			+ "ON (onw_ptno=bpt_ptno) "
			+ "LEFT OUTER JOIN (SELECT bmr_code,bmr_name,bmr_docname,bmr_kwanm,bmr_hosnum,bmr_stdate "
			+ "					FROM medroom "
			+ "					WHERE bmr_hosnum = (SELECT bhi_hosnum FROM hosinfo WHERE bmr_hosnum=bhi_hosnum and bhi_usechk='Y') ) "
			+ "AS med( bmr_code,bmr_name ,bmr_docname,bmr_kwanm,bmr_hosnum,bmr_stdate ) "
			+ "ON( onw_medroom=bmr_code AND bmr_stdate=(SELECT MAX(bmr_stdate) FROM medroom "
			+ "											WHERE bmr_code=onw_medroom AND bmr_stdate<=onw_ch_date) "
			+ "											AND bmr_hosnum = (SELECT bhi_hosnum FROM hosinfo WHERE bmr_hosnum=bhi_hosnum and bhi_usechk='Y')) "
			+ "]]>"
			+ "WHERE 0=0  "
			+ "<if test=\"name != null and name != ''\">"
			+ "AND (bpt_name LIKE '%${name}%' "
			+ "OR substr(bpt_hpno,1,3)||substr(bpt_hpno,5,4)||substr(bpt_hpno,10,4) like '%${name}%' "
			+ "OR substr(bpt_telno,1,3)||substr(bpt_telno,5,4)||substr(bpt_telno,10,4) like '%${name}%' "
			+ "OR bpt_ptno = #{name} "
			+ "OR bpt_resno like '%${name}%' "
			+ ") AND onw_medroom is NOT NULL "
			+ "</if>"
			+ "ORDER BY bpt_name "
			+ "</script>")
	public List<PatientSearchDataVO> selectPatientSearch(PatientSearchParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ " SELECT DISTINCT bpt_ptno,bpt_name,bpt_resno,bpt_telno,"
			+ "		bpt_hpno,bpt_cen,bpt_jogubun,bpt_sex, bpt_mihonmo,"
			+ "		bpt_enddt,onw_ch_date,onw_medroom, bmr_docname, "
			+ "calage(bpt_cen + substr(bpt_resno, 1, 6),today()) as bpt_age, "
			+ "		(SELECT bji_name FROM jonginfo WHERE bji_code=bpt_jogubun),bmr_code,bmr_name "
			+ "FROM patient "
			+ "LEFT OUTER JOIN "
			+ "(SELECT onw_ptno,onw_ch_date,onw_medroom FROM naewon a WHERE onw_ch_date=(SELECT MAX(onw_ch_date) "
			+ "																				FROM naewon WHERE onw_ptno=a.onw_ptno)) "
			+ "AS NW(onw_ptno,onw_ch_date,onw_medroom) "
			+ "ON (onw_ptno=bpt_ptno) "
			+ "LEFT OUTER JOIN (SELECT bmr_code,bmr_name,bmr_docname,bmr_kwanm,bmr_hosnum,bmr_stdate "
			+ "					FROM medroom "
			+ "					WHERE bmr_hosnum = (SELECT bhi_hosnum FROM hosinfo WHERE bmr_hosnum=bhi_hosnum and bhi_usechk='Y') ) "
			+ "AS med( bmr_code,bmr_name ,bmr_docname,bmr_kwanm,bmr_hosnum,bmr_stdate ) "
			+ "ON( onw_medroom=bmr_code AND bmr_stdate=(SELECT MAX(bmr_stdate) FROM medroom "
			+ "											WHERE bmr_code=onw_medroom AND bmr_stdate<=onw_ch_date) "
			+ "											AND bmr_hosnum = (SELECT bhi_hosnum FROM hosinfo WHERE bmr_hosnum=bhi_hosnum and bhi_usechk='Y')) "
			+ "]]>"
			+ "WHERE 0=0  "
			+ "<if test=\"search_word != null and search_word != '' and search_type == 'name'\">"
			+ "AND bpt_name like '%${search_word}%' "
			+ "</if>"
			+ "<if test=\"search_word != null and search_word != '' and search_type == 'ptno'\">"
			+ "AND bpt_ptno = #{search_word} "
			+ "</if>"
			+ "AND onw_medroom is NOT NULL "
			+ "ORDER BY bpt_name "
			+ "</script>")
	public List<PatientSearchDataVO> selectPatientSearchType(PatientSearchParamVO vo) throws Exception;

	//v2 신병동
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT oib_regno,oib_kind,oib_ch_date,oib_kwa,oib_seq,oib_edi_code,oib_name,oib_main_sang "
			+ "FROM ocsib1${reg_year} "
			+ "WHERE 0=0 "
			+ "]]>"
			+ "<if test=\"regno != null and regno != ''\">"
			+ "AND oib_regno=#{regno} "
			+ "</if>"
			+ "<if test=\"kind != null and kind != ''\">"
			+ "AND oib_kind=#{kind} "
			+ "</if>"
			+ "<if test=\"date != null and date != ''\">"
			+ "AND oib_ch_date=#{date} "
			+ "</if>"
			+ "<if test=\"medroom != null and medroom != ''\">"
			+ "AND oib_medroom=#{medroom} "
			+ "</if>"
			+ "</script>")
	public List<IpwonPatientSangByungDataVO> selectIpwonPatientSangByung(IpwonPatientParamVO vo) throws Exception;
	
	//v2 신병동
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT oio_regno,oio_kind,oio_ch_date,oio_kwa,oio_name,oio_ptno "
			+ "FROM ocsico${reg_year} "
			+ "WHERE 0=0 "
			+ "]]>"
			+ "<if test=\"regno != null and regno != ''\">"
			+ "AND oio_regno=#{regno} "
			+ "</if>"
			+ "<if test=\"kind != null and kind != ''\">"
			+ "AND oio_kind=#{kind} "
			+ "</if>"
			+ "<if test=\"date != null and date != ''\">"
			+ "AND oio_ch_date=#{date} "
			+ "</if>"
			+ "<if test=\"medroom != null and medroom != ''\">"
			+ "AND oio_medroom=#{medroom} "
			+ "</if>"
			+ "</script>")
	public List<IpwonPatientSymptomDataVO> selectIpwonPatientSymptom(IpwonPatientParamVO vo) throws Exception;
	
	//v2 신병동
	@Select("<script>"
			+ "<![CDATA["
			+ " SELECT oic_regno,oic_kind,oic_ch_date,oic_medroom,oic_kwa,oic_seq,oic_code,oic_name,oic_gubun2,oic_gubun4,oic_hang, "
			+ "oic_via_code, oic_via_time, oic_div, oic_dose, oic_day, oic_jogubun, oic_gubun3, oic_gubun33, oic_gubun35, oic_gubun36, "
			+ "oic_gubun39, oic_chkind, oic_chstate, oic_jiwon_class, oic_jiwon_kind, oic_jiwon_code, oic_jiwon_detail, oic_doc "
			+ "from ocsich${reg_year} "
			+ "WHERE 0=0 "
			+ "]]>"
			+ "<if test=\"regno != null and regno != ''\">"
			+ "AND oic_regno=#{regno} "
			+ "</if>"
			+ "<if test=\"kind != null and kind != ''\">"
			+ "AND oic_kind=#{kind} "
			+ "</if>"
			+ "<if test=\"date != null and date != ''\">"
			+ "AND oic_ch_date=#{date} "
			+ "</if>"
			+ "<if test=\"medroom != null and medroom != ''\">"
			+ "AND oic_medroom=#{medroom} "
			+ "</if>"
			+ "<![CDATA["
			+ "and (oic_cancel_date is NULL AND NOT(oic_code LIKE '@%')) "
			+ "ORDER BY oic_regno,oic_ch_date,oic_kind,oic_save_seq,oic_ch_seq "
			+ "]]>"
			+ "</script>")
	public List<IpwonPatientCheobangDataVO> selectIpwonPatientCheobang(IpwonPatientParamVO vo) throws Exception;
}
