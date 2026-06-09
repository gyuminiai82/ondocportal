package kr.ondoc.mapper.sybase.legacy.ondocplus;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import kr.ondoc.domain.sybase.legacy.ondocplus.IpwonToewonStateDataVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.IpwonToewonStateParamVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.SupportReceiptStateDataVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.SupportReceiptStateParamVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.SupportReceiptStateTotDataVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.SupportStateDataVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.SupportStateParamVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.SupportStateTotDataVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.UsrmngrDataVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.UsrmngrParamVO;

public interface OldOndocplusManageMapper {
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT onw_regno,onw_kind,onw_class,onw_ch_date,onw_ptno,onw_name,onw_yage,onw_sex,onw_time,onw_jogubun, bmr_name "
			+ "FROM naewon "
			+ "LEFT OUTER JOIN (SELECT bmr_code,bmr_name,bmr_docname,bmr_kwanm,bmr_hosnum,bmr_stdate "
			+ "					FROM medroom WHERE bmr_hosnum = (SELECT bhi_hosnum FROM hosinfo WHERE bmr_hosnum=bhi_hosnum and bhi_usechk='Y') ) "
			+ "AS med( bmr_code,bmr_name ,bmr_docname,bmr_kwanm,bmr_hosnum,bmr_stdate ) "
			+ "ON( onw_medroom=bmr_code AND bmr_stdate=(SELECT MAX(bmr_stdate) "
			+ "											FROM medroom WHERE bmr_code=onw_medroom AND bmr_stdate<=onw_ch_date) "
			+ "											AND bmr_hosnum = (SELECT bhi_hosnum FROM hosinfo WHERE bmr_hosnum=bhi_hosnum and bhi_usechk='Y') ) "
			+ "WHERE 0=0  "
			+ "]]>"
			+ "<if test=\"date != null and date != ''\">"
			+ "AND onw_ch_date=#{date} "
			+ "</if>"
			+ "AND NOT(onw_kind LIKE '9%') AND onw_class LIKE '1%'  AND (onw_kind LIKE '1%' OR onw_kind LIKE '3%' OR onw_kind LIKE '5%' OR onw_kind LIKE '_3')  "
			+ "<if test=\"room_name != null and room_name != ''\">"
			+ "AND bmr_name=#{room_name} "
			+ "</if>"
			+ "ORDER BY onw_time DESC "
			+ "</script>")
	public List<SupportStateDataVO> selectSupportState(SupportStateParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT onw_ch_date,  COUNT(onw_ptno) as tot, "
			+ "COUNT( (CASE WHEN (onw_jin_kind='0' OR onw_jin_kind='1' OR onw_jin_kind = '2') THEN onw_ptno END)) as cho, "
			+ "COUNT( (CASE WHEN onw_jin_kind='5' THEN onw_ptno END)) as jae, "
			+ "COUNT( (CASE WHEN onw_jin_kind='6' THEN onw_ptno END)) as muli, "
			+ "COUNT( (CASE WHEN(onw_jin_kind='3' OR onw_jin_kind='7' OR onw_jin_kind='8' OR onw_jin_kind='9') THEN onw_ptno end)) as etcjin, "
			+ "COUNT( (CASE WHEN (SUBSTR(onw_jogubun,1,1)='2') THEN onw_ptno END)) as kukmin, "
			+ "COUNT( (CASE WHEN (SUBSTR(onw_jogubun,1,1)='3') THEN onw_ptno END)) as boho, "
			+ "COUNT( (CASE WHEN (SUBSTR(onw_jogubun,1,1)='5') THEN onw_ptno END)) as sanjae, "
			+ "COUNT( (CASE WHEN (SUBSTR(onw_jogubun,1,1)='6') THEN onw_ptno END)) as jabo,  "
			+ "COUNT( (CASE WHEN (SUBSTR(onw_jogubun,1,1)<>'2') AND (SUBSTR(onw_jogubun,1,1)<>'3') AND (SUBSTR(onw_jogubun,1,1)<>'5') AND (SUBSTR(onw_jogubun,1,1)<>'6') THEN onw_ptno END)) as etcjong "
			+ "FROM naewon WHERE 0=0  "
			+ "]]>"
			+ "<if test=\"date != null and date != ''\">"
			+ "AND onw_ch_date=#{date} "
			+ "</if>"
			+ "<if test=\"room_code != null and room_code != ''\">"
			+ "AND onw_medroom=#{room_code} "
			+ "</if>"
			+ "AND NOT(onw_kind LIKE '9%') AND (onw_kind LIKE '1%' OR onw_kind LIKE '3%' OR onw_kind LIKE '5%' OR onw_kind LIKE '_3') "
			+ "GROUP BY onw_ch_date  "
			+ "</script>")
	public List<SupportStateTotDataVO> selectSupportStateTot(SupportStateParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT och_byungdong,och_room,oip_regno,oip_in_date,oip_out_date,oip_ptno, oip_name,oip_resno,oip_age,oip_sex,oip_medroom,oip_kwa,och_jogubun, "
			+ "(SELECT bji_name FROM jonginfo WHERE bji_code=och_jogubun), bmr_name,bmr_docname,bmr_kwanm "
			+ "FROM ipwon "
			+ "LEFT OUTER JOIN  (SELECT och_regno,och_seq,och_byungdong,och_room,och_jogubun,bmr_name,bmr_docname,bmr_kwanm "
			+ "FROM changehis "
			+ "LEFT OUTER JOIN (SELECT bmr_code,bmr_name,bmr_docname,bmr_kwanm,bmr_hosnum,bmr_stdate FROM medroom "
			+ "					WHERE bmr_hosnum = (SELECT bhi_hosnum FROM hosinfo WHERE bmr_hosnum=bhi_hosnum and bhi_usechk='Y') ) "
			+ "AS med( bmr_code,bmr_name ,bmr_docname,bmr_kwanm,bmr_hosnum,bmr_stdate ) "
			+ "ON (bmr_code=och_medroom AND bmr_stdate= (SELECT MAX(bmr_stdate) FROM medroom WHERE bmr_code=och_medroom AND bmr_stdate<=och_st_date) "
			+ "AND bmr_hosnum = (SELECT bhi_hosnum FROM hosinfo WHERE bmr_hosnum=bhi_hosnum and bhi_usechk='Y') )) "
			+ "AS chgroom (och_regno,och_seq,och_byungdong,och_room,och_jogubun,bmr_name,bmr_docname,bmr_kwanm) "
			+ "ON ( och_regno=oip_regno AND och_seq=(SELECT MAX(och_seq) FROM changehis WHERE och_regno=oip_regno))"
			+ " WHERE 0=0  "
			+ "]]>"
			+ "<if test=\"byungdong != null and byungdong != ''\">"
			+ "AND och_byungdong=#{byungdong} "
			+ "</if>"
			+ "<if test=\"room_name != null and room_name != ''\">"
			+ "AND bmr_name=#{room_name} "
			+ "</if>"
			
			+ "<if test=\"gubun != null and gubun != '' and gubun == '0'\">"
			+ "AND oip_in_date BETWEEN #{start_date} AND #{end_date} "
			+ "</if>"
			
			+ "<if test=\"gubun != null and gubun != '' and gubun == '1'\">"
			+ "AND oip_out_date BETWEEN #{start_date} AND #{end_date} "
			+ "</if>"
			
			+ "<if test=\"gubun != null and gubun != '' and gubun == '2'\">"
			+ "<![CDATA["
			+ "oip_in_date <= #{end_date} "
			+ "AND (oip_out_date='' OR oip_out_date > #{start_date} "
			+ "]]>"
			+ "</if>"
			
			+ "<if test=\"gubun != null and gubun != '' and (gubun == '0' or gubun == '1' or gubun == '2')\">"
			+ "ORDER BY oip_in_date, oip_out_date, oip_name "
			+ "</if>"

			+ "</script>")
	public List<IpwonToewonStateDataVO> selectIpwonToewonState(IpwonToewonStateParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT ppt_paydate,ppt_iwgu,ppt_ptno,ppt_name, ppt_totkum+ppt_bikum+ppt_bo100kum as totkum, ppt_billkum+ppt_jangkum as billkum, "
			+ "ppt_boninkum, ppt_bikum+ppt_bo100kum as bikum, ppt_paykum, ppt_cardkum, ppt_cashkum "
			+ "FROM payment "
			+ "WHERE 0=0 "
			+ "]]>"
			+ "<if test=\"date != null and date != ''\">"
			+ "AND ppt_paydate=#{date} "
			+ "</if>"
			+ "<if test=\"room_code != null and room_code != ''\">"
			+ "AND ppt_medroom=#{room_code} "
			+ "</if>"
			+ "<if test=\"gubun != null and gubun != '' and gubun == '0'\">"
			+ "AND ppt_iwgu = '1'  "
			+ "</if>"
			+ "<if test=\"gubun != null and gubun != '' and gubun == '1'\">"
			+ "AND ppt_iwgu = '2'  "
			+ "</if>"
			+ "</script>")
	public List<SupportReceiptStateDataVO> selectSupportReceiptState(SupportReceiptStateParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT ppt_paydate,ppt_iwgu, COUNT(ppt_ptno) as cnt, "
			+ "SUM(ppt_totkum+ppt_bikum+ppt_bo100kum) as totkum, "
			+ "SUM(ppt_billkum+ppt_jangkum) as billkum, "
			+ "SUM(ppt_boninkum) as bonkum, "
			+ "SUM(ppt_bikum+ppt_bo100kum) as bikum, "
			+ "SUM(ppt_paykum) as paykum, "
			+ "SUM(ppt_cardkum) as cardkum, "
			+ "SUM(ppt_cashkum) as cashkum "
			+ "FROM payment "
			+ "WHERE 0=0  "
			+ "]]>"
			+ "<if test=\"date != null and date != ''\">"
			+ "AND ppt_paydate=#{date} "
			+ "</if>"
			+ "<if test=\"room_code != null and room_code != ''\">"
			+ "AND ppt_medroom=#{room_code} "
			+ "</if>"
			+ "<if test=\"gubun != null and gubun != '' and gubun == '0'\">"
			+ "AND ppt_iwgu = '1'  "
			+ "</if>"
			+ "<if test=\"gubun != null and gubun != '' and gubun == '1'\">"
			+ "AND ppt_iwgu = '2'  "
			+ "</if>"
			
			+ "GROUP BY ppt_paydate,ppt_iwgu "
			+ "</script>")
	public List<SupportReceiptStateTotDataVO> selectSupportReceiptStateTot(SupportReceiptStateParamVO vo) throws Exception;
}
