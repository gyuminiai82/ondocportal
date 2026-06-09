package kr.ondoc.mapper.sybase.crm;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import kr.ondoc.domain.sybase.crm.CrmDashOutDaegiVO;
import kr.ondoc.domain.sybase.crm.CrmDashReserveVO;
import kr.ondoc.domain.sybase.crm.CrmDashSunapVO;

public interface CrmDashboardMapper {
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT rwj_regno,rwj_reg_date,rwj_ptno,rwj_medroom,rwj_name,rwj_yage,rwj_sex,rwj_flag, DATEFORMAT( rwj_time, 'hh:mm:ss' ) as rwj_time, "
			+ "		(SELECT bjg_name FROM jin_gubun WHERE bjg_code=rwj_jin_gubun), "
			+ "		(SELECT bpt_hpno FROM patient WHERE bpt_ptno=rwj_ptno), "
			+ "		(SELECT bji_name FROM jonginfo WHERE bji_code=rwj_jogubun), "
			+ "]]>"
			+ "<![CDATA["
			+ "		bmr_name,bmr_docname,bmr_kwanm,rwj_wait_class,rwj_wait_sclass,rwj_jin_gubun "
			+ "FROM waitjin LEFT OUTER JOIN "
			+ "	(SELECT bmr_code,bmr_name,bmr_docname,bmr_kwanm,bmr_hosnum,bmr_stdate FROM medroom "
			+ "		WHERE bmr_hosnum = (SELECT bhi_hosnum FROM hosinfo WHERE bmr_hosnum=bhi_hosnum and bhi_usechk='Y')) AS "
			+ "     med( bmr_code,bmr_name ,bmr_docname,bmr_kwanm,bmr_hosnum,bmr_stdate ) "
			+ "	ON ( bmr_code=rwj_medroom AND bmr_stdate=(SELECT MAX(bmr_stdate) FROM medroom "
			+ "	WHERE bmr_code=rwj_medroom  AND bmr_stdate<=rwj_reg_date "
			+ "		AND bmr_hosnum = (SELECT bhi_hosnum FROM hosinfo WHERE bmr_hosnum=bhi_hosnum and bhi_usechk='Y'))) "
			+ "	WHERE 0=0 AND rwj_wait_class!='E' "
			+ "]]>"
			+ "<if test=\"date != null and date != ''\">"
			+ "AND rwj_reg_date=#{date} "
			+ "</if>"
			+ "ORDER BY rwj_time ASC "
			+ "</script>")
	public List<CrmDashOutDaegiVO> selectListOutDaegi(String date) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT bmr_name, bmr_kwanm, bmr_hosname, bmr_docname, rev_seq, rev_ptno, rev_hpno, rev_date, rev_time, rev_name, rev_memo, rev_examgu, rev_yage, rev_sex, rev_color "
			+ "FROM revpt AS RP "
			+ "INNER JOIN "
			+ "		(SELECT * FROM medroom"
			+ "			WHERE 0=0 AND bmr_hosnum = (SELECT bhi_hosnum FROM hosinfo WHERE bhi_usechk='Y') AND bmr_use='Y') AS MD "
			+ "ON RP.rev_medroom=MD.bmr_code  "
			+ "]]>"
			+ "<if test=\"date != null and date != ''\">"
			+ "AND RP.rev_date = #{date} "
			+ "</if>"
			
			+ "AND (RP.rev_canid is null OR RP.rev_canid = '') "
			+ "ORDER BY RP.rev_time ASC "
			+ "</script>")
	public List<CrmDashReserveVO> selectListReserve(String date) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT * FROM ("
			+ "SELECT 'standby' AS type, replace(convert(varchar, pwt_waittime, 108 ), ':', '') AS regtime, pwt_ptno AS ptno, pwt_medroom AS medroom, "
			+ "(pwt_boninkum+pwt_bikum+pwt_100kum-pwt_dc1kum-pwt_dc2kum-pwt_nanchikum-pwt_bohunboninkum-pwt_bonin100_underkum) AS price "
			+ "FROM paywait WHERE 0=0 AND pwt_waitdate = #{date} "
			+ "UNION ALL "
			+ "SELECT 'complete' AS type, ppt_paytime AS regtime, ppt_ptno AS ptno, ppt_medroom AS medroom, ppt_paykum AS price "
			+ "FROM payment WHERE 0=0 AND ppt_paydate = #{date} "
			+ ") AS sunap "
			+ "LEFT OUTER JOIN patient AS p "
			+ "ON sunap.ptno = p.bpt_ptno "
			+ "LEFT OUTER JOIN (SELECT * FROM medroom WHERE 0=0 "
			+ "AND bmr_hosnum = (SELECT bhi_hosnum FROM hosinfo WHERE  bhi_usechk='Y') AND bmr_use='Y') AS m "
			+ "ON sunap.medroom = m.bmr_code"
			+ "]]>"
			+ "</script>")
	public List<CrmDashSunapVO> selectListSunap(String date) throws Exception;
}
