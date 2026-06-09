package kr.ondoc.mapper.sybase.legacy.penchart;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import kr.ondoc.domain.sybase.legacy.penchart.NaewonParamVO;
import kr.ondoc.domain.sybase.legacy.penchart.NaewonYearDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.OutPatientCheobangDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.OutPatientNaewonDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.OutPatientSangbyungDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.OutPatientSymptomDataVO;

public interface OldOcsOutMapper {
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT substring(onw_ch_date, 1, 4) as year "
			+ "FROM naewon "
			+ "WHERE 0=0 AND (onw_kind LIKE '1%' OR onw_kind LIKE '3%' OR onw_kind LIKE '5%' OR onw_kind LIKE '_3') AND NOT(onw_kind LIKE '9%') AND onw_ptno=#{ptno} "
			+ "GROUP BY substring(onw_ch_date, 1, 4) "
			+ "ORDER BY year desc "
			+ "]]>"
			+ "</script>")
	public List<NaewonYearDataVO> selectNaewonYear(String ptno) throws Exception;	
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT onw_regno,onw_kind,onw_ch_date,onw_kwa,onw_ptno,onw_medroom,onw_jin_kind,onw_jin_time,onw_time, "
			+ "(SELECT bji_name FROM jonginfo WHERE bji_code=onw_jogubun), bmr_name,bmr_docname,bmr_kwanm FROM naewon "
			+ "LEFT OUTER JOIN medroom "
			+ "ON bmr_code=onw_medroom AND bmr_hosnum= (SELECT bhi_hosnum "
			+ "											FROM hosinfo WHERE bmr_hosnum=bhi_hosnum and bhi_usechk='Y') "
			+ "AND bmr_stdate=(SELECT MAX(bmr_stdate) FROM medroom WHERE bmr_code=onw_medroom AND bmr_stdate<=onw_ch_date) "
			+ "					WHERE (onw_kind LIKE '1%' OR onw_kind LIKE '3%' OR onw_kind LIKE '5%' OR onw_kind LIKE '_3') "
			+ "					AND NOT(onw_kind LIKE '9%') AND onw_ptno=#{ptno} AND substring(onw_ch_date, 1, 4)=#{year} "
			+ "ORDER BY onw_ch_date desc "
			+ "]]>"
			+ "</script>")
	public List<OutPatientNaewonDataVO> selectOutPatientNaewon(NaewonParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT owb_regno,owb_seq,owb_edi_code,owb_name,owb_main_sang FROM ocswb1${year} "
			+ "WHERE 0 = 0 AND owb_regno=#{regno}  "
			+ "]]>"
			+ "</script>")
	public List<OutPatientSangbyungDataVO> selectOutPatientSangbyung(NaewonParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT owo_regno,owo_name,owo_ptno FROM ocswco${year} "
			+ "WHERE 0 = 0 AND owo_regno=#{regno}   "
			+ "]]>"
			+ "</script>")
	public List<OutPatientSymptomDataVO> selectOutPatientSymptom(NaewonParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT owc_regno,owc_seq,owc_code,owc_name,owc_gubun2, owc_gubun4,owc_hang,owc_dose,owc_div,owc_day,owc_via_code,owc_jogubun,owc_gubun3,owc_gubun33,owc_gubun35,owc_gubun36,owc_gubun39,owc_chkind "
			+ "FROM ocswch${year} WHERE (owc_cancel_date is NULL AND NOT(owc_code LIKE '@%')) AND owc_regno=#{regno} "
			+ "AND owc_chkind <> '9'   "
			+ "]]>"
			+ "</script>")
	public List<OutPatientCheobangDataVO> selectOutPatientCheobang(NaewonParamVO vo) throws Exception;
}
