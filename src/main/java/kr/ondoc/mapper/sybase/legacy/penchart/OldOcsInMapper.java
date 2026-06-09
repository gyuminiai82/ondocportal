package kr.ondoc.mapper.sybase.legacy.penchart;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import kr.ondoc.domain.sybase.legacy.penchart.NaewonParamVO;
import kr.ondoc.domain.sybase.legacy.penchart.OutPatientSangbyungDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.InPatientCheobangDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.InPatientNaewonDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.InPatientNaewonV2DataVO;
import kr.ondoc.domain.sybase.legacy.penchart.InPatientSangbyungDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.InPatientSymptomDataVO;

public interface OldOcsInMapper {
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT oip_regno AS onw_regno, oip_medroom AS onw_medroom, oip_in_date AS onw_start_date, oip_out_date AS onw_end_date, bmr_name, bmr_docname FROM ipwon "
			+ "LEFT OUTER JOIN(SELECT bmr_code,bmr_name,bmr_docname,bmr_kwanm,bmr_hosnum,bmr_stdate "
			+ "					FROM medroom WHERE bmr_hosnum = (SELECT bhi_hosnum FROM hosinfo WHERE bmr_hosnum=bhi_hosnum and bhi_usechk='Y') ) AS medroom "
			+ "ON oip_medroom=medroom.bmr_code AND medroom.bmr_stdate=(SELECT MAX(bmr_stdate) FROM medroom WHERE bmr_code=oip_medroom AND bmr_stdate<=oip_in_date) "
			+ "WHERE oip_ptno=#{ptno} ORDER BY oip_in_date DESC "
			+ "]]>"
			+ "</script>")
	public List<InPatientNaewonDataVO> selectInPatientNaewonPtno(NaewonParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT *, (SELECT bji_name FROM jonginfo WHERE bji_code=onw_jogubun) "
			+ "FROM (SELECT onw_regno, onw_medroom, onw_jogubun, onw_jin_kind, onw_jin_time, onw_kwa, onw_ch_date "
			+ "			FROM naewon "
			+ "			WHERE 0=0 AND (onw_kind LIKE '2%' OR onw_kind LIKE '4%' OR onw_kind LIKE '6%' OR onw_kind LIKE '7%' OR onw_kind LIKE '8%') "
			+ "			AND onw_ptno=#{ptno} AND onw_regno=#{regno} AND onw_medroom=#{medroom} "
			+ "			GROUP BY onw_regno, onw_medroom, onw_jogubun, onw_jin_kind, onw_jin_time, onw_kwa, onw_ch_date) AS naewon "
			+ "LEFT OUTER JOIN (SELECT bmr_code,bmr_name,bmr_docname,bmr_kwanm,bmr_hosnum,bmr_stdate FROM medroom) AS medroom "
			+ "ON naewon.onw_medroom=medroom.bmr_code AND medroom.bmr_stdate=(SELECT MAX(bmr_stdate) FROM medroom WHERE bmr_code=onw_medroom AND bmr_stdate<=onw_ch_date) "
			+ "ORDER BY onw_ch_date DESC "
			+ "]]>"
			+ "</script>")
	public List<InPatientNaewonDataVO> selectInPatientNaewonRegno(NaewonParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT oip_regno as onw_regno, oip_medroom as onw_medroom, oip_in_date as onw_start_date, oip_out_date as onw_end_date, bmr_name, bmr_docname FROM ipwon "
			+ "LEFT OUTER JOIN(SELECT bmr_code,bmr_name,bmr_docname,bmr_kwanm,bmr_hosnum,bmr_stdate FROM medroom         "
			+ "WHERE bmr_hosnum = (SELECT bhi_hosnum FROM hosinfo WHERE bmr_hosnum=bhi_hosnum and bhi_usechk='Y') ) AS medroom "
			+ "ON oip_medroom=medroom.bmr_code AND medroom.bmr_stdate=(SELECT MAX(bmr_stdate) "
			+ "															FROM medroom "
			+ "															WHERE bmr_code=oip_medroom AND bmr_stdate<=oip_in_date) "
			+ "WHERE oip_ptno=#{ptno} "
			+ "ORDER BY oip_in_date DESC "
			+ "]]>"
			+ "</script>")
	public List<InPatientNaewonV2DataVO> selectInPatientNaewonV2Ptno(NaewonParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT oin_regno,oin_kind, oin_medroom, oin_ch_date,oin_kwa ,oin_reg_date, "
			+ "(SELECT bmr_name FROM medroom WHERE bmr_code=oin_medroom AND bmr_hosnum=(SELECT bhi_hosnum FROM hosinfo WHERE bmr_hosnum=bhi_hosnum and bhi_usechk='Y')    "
			+ "AND bmr_stdate=(SELECT MAX(bmr_stdate) "
			+ "					FROM medroom "
			+ "					WHERE bmr_code=oin_medroom AND bmr_hosnum=(SELECT bhi_hosnum FROM hosinfo "
			+ "																WHERE bmr_hosnum=bhi_hosnum and bhi_usechk='Y') "
			+ "					AND bmr_stdate<=oin_ch_date)) FROM inaewon "
			+ "WHERE oin_regno=#{regno} AND oin_medroom=#{medroom} AND oin_class<>'0' "
			+ "]]>"
			+ "<if test=\"period != null and period != ''\">"
			+ "AND oin_ch_date LIKE '%${period}%' "
			+ "</if>"
			+ "ORDER BY oin_ch_date DESC "
			+ "</script>")
	public List<InPatientNaewonV2DataVO> selectInPatientNaewonV2Regno(NaewonParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT icb_regno,icb_kind,icb_ch_date,icb_kwa,icb_seq,icb_edi_code,icb_name,icb_main_sang "
			+ "FROM cib1${year} WHERE 0=0 AND icb_regno=#{regno} AND icb_ch_date=#{date}  "
			+ "]]>"
			+ "</script>")
	public List<InPatientSangbyungDataVO> selectInPatientSangbyung(NaewonParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT oio_regno,oio_kind,oio_ch_date,oio_kwa,oio_name,oio_ptno "
			+ "FROM cichco${year} "
			+ "WHERE 0=0 AND oio_regno=#{regno} AND oio_kwa=#{kwa} AND oio_ch_date=#{date} "
			+ "]]>"
			+ "</script>")
	public List<InPatientSymptomDataVO> selectInPatientSymptom(NaewonParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT ici_regno,ici_kind,ici_ch_date,ici_kwa,ici_seq,ici_code,ici_name,ici_gubun2,ici_hang,ici_dose,ici_div,ici_day,ici_via_code "
			+ "FROM cich${year} "
			+ "WHERE (ici_cancel_date is NULL AND NOT(ici_code LIKE '@%')) AND ici_regno=#{regno} AND ici_ch_date=#{date} AND ici_kwa=#{kwa} ORDER BY ici_ch_date DESC  "
			+ "]]>"
			+ "</script>")
	public List<InPatientCheobangDataVO> selectInPatientCheobang(NaewonParamVO vo) throws Exception;
}
