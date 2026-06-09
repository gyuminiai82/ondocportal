package kr.ondoc.mapper.sybase.crm;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import kr.ondoc.domain.sybase.crm.CrmIpwonCategoryParamVO;
import kr.ondoc.domain.sybase.crm.CrmIpwonCategoryVO;
import kr.ondoc.domain.sybase.crm.CrmIpwonParamVO;
import kr.ondoc.domain.sybase.crm.CrmIpwonVO;
import kr.ondoc.domain.sybase.crm.CrmOutParamVO;
import kr.ondoc.domain.sybase.crm.CrmOutVO;
import kr.ondoc.domain.sybase.crm.CrmPatientParamVO;
import kr.ondoc.domain.sybase.crm.detail.CrmReserveVO;
import kr.ondoc.domain.sybase.legacy.penchart.InPatientNaewonDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.InPatientNaewonV2DataVO;
import kr.ondoc.domain.sybase.legacy.penchart.NaewonParamVO;
import kr.ondoc.domain.sybase.legacy.penchart.OutPatientNaewonDataVO;

public interface CrmPatientDetailMapper {
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT TOP ${listNum} "
			+ "* FROM "
			+ "( "
			+ "SELECT *, ROW_NUMBER( ) OVER ( ORDER BY ${sort} ${sortDirection} ) AS RowNum "
			+ "FROM revpt WHERE 0=0 "
			+ "]]>"
			+ "<if test=\"ptno != null and ptno != ''\">"
			+ "AND rev_ptno = #{ptno} "
			+ "</if>"
			+ ") AS t "
			+ "<![CDATA["
			+ " WHERE RowNum>=${first}"
			+ "]]>"
			+ "</script>")
	public List<CrmReserveVO> selectReserveList(CrmPatientParamVO vo) throws Exception;


	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT COUNT(*) "
			+ "]]>"
			+ "FROM revpt WHERE 0=0 "
			+ "<if test=\"ptno != null and ptno != ''\">"
			+ "AND rev_ptno = #{ptno} "
			+ "</if>"
			+ "</script>")
	public int countReserve(CrmPatientParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT TOP ${listNum} "
			+ "* FROM "
			+ "( "
			+ "SELECT *, ROW_NUMBER( ) OVER ( ORDER BY onw_ch_date desc ) AS RowNum "
			+ "FROM ("
			+ "SELECT onw_regno,onw_kind,onw_ch_date,onw_kwa,onw_ptno,onw_medroom,onw_jin_kind,onw_jin_time,onw_time, "
			+ "(SELECT bji_name FROM jonginfo WHERE bji_code=onw_jogubun) AS bji_name, bmr_name,bmr_docname,bmr_kwanm FROM naewon "
			+ "LEFT OUTER JOIN medroom "
			+ "ON bmr_code=onw_medroom AND bmr_hosnum= (SELECT bhi_hosnum "
			+ "											FROM hosinfo WHERE bmr_hosnum=bhi_hosnum and bhi_usechk='Y') "
			+ "AND bmr_stdate=(SELECT MAX(bmr_stdate) FROM medroom WHERE bmr_code=onw_medroom AND bmr_stdate<=onw_ch_date) "
			+ "					WHERE (onw_kind LIKE '1%' OR onw_kind LIKE '3%' OR onw_kind LIKE '5%' OR onw_kind LIKE '_3') "
			+ "					AND NOT(onw_kind LIKE '9%') AND onw_ptno=#{ptno} "
			+ ") AS temp WHERE 0=0 "
			+ "]]>"
			+ ") AS t "
			+ "<![CDATA["
			+ " WHERE RowNum>=${first}"
			+ "]]>"
			+ "</script>")
	public List<CrmOutVO> selectOutList(CrmOutParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT COUNT(*) "
			+ "FROM naewon  "
			+ "LEFT OUTER JOIN medroom "
			+ "ON bmr_code=onw_medroom AND bmr_hosnum= (SELECT bhi_hosnum "
			+ "											FROM hosinfo WHERE bmr_hosnum=bhi_hosnum and bhi_usechk='Y') "
			+ "AND bmr_stdate=(SELECT MAX(bmr_stdate) FROM medroom WHERE bmr_code=onw_medroom AND bmr_stdate<=onw_ch_date) "
			+ "					WHERE (onw_kind LIKE '1%' OR onw_kind LIKE '3%' OR onw_kind LIKE '5%' OR onw_kind LIKE '_3') "
			+ "					AND NOT(onw_kind LIKE '9%') AND onw_ptno=#{ptno} "
			+ "]]>"
			+ "</script>")
	public int countOut(CrmOutParamVO vo) throws Exception;
	
	//신병동 미사용시 입원정보 리스트
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT oip_regno AS regno, oip_medroom AS medroom, oip_in_date AS start_date, oip_out_date AS end_date, bmr_name, bmr_docname FROM ipwon "
			+ "LEFT OUTER JOIN(SELECT bmr_code,bmr_name,bmr_docname,bmr_kwanm,bmr_hosnum,bmr_stdate "
			+ "					FROM medroom WHERE bmr_hosnum = (SELECT bhi_hosnum FROM hosinfo WHERE bmr_hosnum=bhi_hosnum and bhi_usechk='Y') ) AS medroom "
			+ "ON oip_medroom=medroom.bmr_code AND medroom.bmr_stdate=(SELECT MAX(bmr_stdate) FROM medroom WHERE bmr_code=oip_medroom AND bmr_stdate<=oip_in_date) "
			+ "WHERE oip_ptno=#{ptno} ORDER BY oip_in_date DESC "
			+ "]]>"
			+ "</script>")
	public List<CrmIpwonCategoryVO> selectIpwonCategoryList(CrmIpwonCategoryParamVO vo) throws Exception;
	
	//신병동 사용시 입원정보 리스트
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT oip_regno as regno, oip_medroom as medroom, oip_in_date as start_date, oip_out_date as end_date, bmr_name, bmr_docname FROM ipwon "
			+ "LEFT OUTER JOIN(SELECT bmr_code,bmr_name,bmr_docname,bmr_kwanm,bmr_hosnum,bmr_stdate FROM medroom         "
			+ "WHERE bmr_hosnum = (SELECT bhi_hosnum FROM hosinfo WHERE bmr_hosnum=bhi_hosnum and bhi_usechk='Y') ) AS medroom "
			+ "ON oip_medroom=medroom.bmr_code AND medroom.bmr_stdate=(SELECT MAX(bmr_stdate) "
			+ "															FROM medroom "
			+ "															WHERE bmr_code=oip_medroom AND bmr_stdate<=oip_in_date) "
			+ "WHERE oip_ptno=#{ptno} "
			+ "ORDER BY oip_in_date DESC "
			+ "]]>"
			+ "</script>")
	public List<CrmIpwonCategoryVO> selectIpwonCategoryV2List(CrmIpwonCategoryParamVO vo) throws Exception;
	
	//신병동 미사용시 입원진료 리스트
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT TOP ${listNum} "
			+ "*, (SELECT bji_name FROM jonginfo WHERE bji_code=onw_jogubun) FROM "
			+ "( "
			+ "SELECT *, ROW_NUMBER( ) OVER ( ORDER BY onw_ch_date DESC ) AS RowNum "
			+ "FROM ("
			
			+ "SELECT * "
			+ "FROM (SELECT onw_regno AS regno, onw_medroom AS medroom, onw_jogubun AS jogubun, onw_jin_kind AS jin_kind, onw_jin_time AS jin_time, onw_kwa AS kwa, onw_ch_date AS ch_date "
			+ "			FROM naewon "
			+ "			WHERE 0=0 AND (onw_kind LIKE '2%' OR onw_kind LIKE '4%' OR onw_kind LIKE '6%' OR onw_kind LIKE '7%' OR onw_kind LIKE '8%') "
			+ "			AND onw_ptno=#{ptno} AND onw_regno=#{regno} AND onw_medroom=#{medroom} "
			+ "			GROUP BY onw_regno, onw_medroom, onw_jogubun, onw_jin_kind, onw_jin_time, onw_kwa, onw_ch_date) AS naewon "
			+ "LEFT OUTER JOIN (SELECT bmr_code,bmr_name,bmr_docname,bmr_kwanm,bmr_hosnum,bmr_stdate FROM medroom) AS medroom "
			+ "ON naewon.onw_medroom=medroom.bmr_code AND medroom.bmr_stdate=(SELECT MAX(bmr_stdate) "
			+ "FROM medroom WHERE bmr_code=onw_medroom AND bmr_stdate<=onw_ch_date) "
			
			+ ") AS temp WHERE 0=0 "
			+ "]]>"
			+ ") AS t "
			+ "<![CDATA["
			+ " WHERE RowNum>=${first}"
			+ "]]>"
			+ "</script>")
	public List<CrmIpwonVO> selectIpwonList(CrmIpwonParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT COUNT(*) "
			+ "FROM (SELECT onw_regno, onw_medroom, onw_jogubun, onw_jin_kind, onw_jin_time, onw_kwa, onw_ch_date "
			+ "			FROM naewon "
			+ "			WHERE 0=0 AND (onw_kind LIKE '2%' OR onw_kind LIKE '4%' OR onw_kind LIKE '6%' OR onw_kind LIKE '7%' OR onw_kind LIKE '8%') "
			+ "			AND onw_ptno=#{ptno} AND onw_regno=#{regno} AND onw_medroom=#{medroom} "
			+ "			GROUP BY onw_regno, onw_medroom, onw_jogubun, onw_jin_kind, onw_jin_time, onw_kwa, onw_ch_date) AS naewon "
			+ "LEFT OUTER JOIN (SELECT bmr_code,bmr_name,bmr_docname,bmr_kwanm,bmr_hosnum,bmr_stdate FROM medroom) AS medroom "
			+ "ON naewon.onw_medroom=medroom.bmr_code AND medroom.bmr_stdate=(SELECT MAX(bmr_stdate) FROM medroom WHERE bmr_code=onw_medroom AND bmr_stdate<=onw_ch_date) "
			+ "]]>"
			+ "</script>")
	public int countIpwon(CrmIpwonParamVO vo) throws Exception;
	
	//신병동 사용시 입원진료 리스트
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT TOP ${listNum} "
			+ "oin_regno AS regno, oin_kind AS kind, oin_medroom AS medroom, oin_ch_date AS ch_date, oin_kwa AS kwa "
			+ ", (SELECT bmr_name FROM medroom WHERE bmr_code=oin_medroom AND bmr_hosnum=(SELECT bhi_hosnum FROM hosinfo WHERE bmr_hosnum=bhi_hosnum and bhi_usechk='Y')    "
			+ "AND bmr_stdate=(SELECT MAX(bmr_stdate) "
			+ "					FROM medroom "
			+ "					WHERE bmr_code=oin_medroom AND bmr_hosnum=(SELECT bhi_hosnum FROM hosinfo "
			+ "																WHERE bmr_hosnum=bhi_hosnum and bhi_usechk='Y') "
			+ "					AND bmr_stdate<=oin_ch_date))"
			
			+ ", (SELECT bji_name FROM jonginfo WHERE bji_code=(SELECT oip_ejo_gubun FROM ipwon WHERE oip_regno=oin_regno)) AS bji_name "
			//+ ", (SELECT oip_reg_ref FROM ipwon WHERE oip_regno=oin_regno) AS reg_ref "
			
			+ "FROM "
			+ "( "
			
			+ "SELECT *, ROW_NUMBER( ) OVER ( ORDER BY oin_ch_date DESC ) AS RowNum "
			+ "FROM ("
			+ "SELECT oin_regno,oin_kind, oin_medroom, oin_ch_date,oin_kwa ,oin_reg_date "
			
			+ "FROM inaewon "
			+ "WHERE oin_regno=#{regno} AND oin_medroom=#{medroom} AND oin_class<>'0' "
			+ "]]>"
			
			+ ") AS temp WHERE 0=0 "
			+ ") AS t "
			+ "<![CDATA["
			+ " WHERE RowNum>=${first}"
			+ "]]>"
			+ "</script>")
	public List<CrmIpwonVO> selectIpwonV2List(CrmIpwonParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT COUNT(*) FROM inaewon "
			+ "WHERE oin_regno=#{regno} AND oin_medroom=#{medroom} AND oin_class<>'0' "
			+ "]]>"
			+ "<if test=\"period != null and period != ''\">"
			+ "AND oin_ch_date LIKE '%${period}%' "
			+ "</if>"
			+ "</script>")
	public int countIpwonV2(CrmIpwonParamVO vo) throws Exception;
}
