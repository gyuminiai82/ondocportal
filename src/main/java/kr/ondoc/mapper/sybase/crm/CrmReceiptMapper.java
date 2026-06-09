package kr.ondoc.mapper.sybase.crm;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import kr.ondoc.domain.sybase.crm.CrmReceiptPatientParamVO;
import kr.ondoc.domain.sybase.crm.CrmReceiptPatientVO;

public interface CrmReceiptMapper {
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT "
			+ "rwj_resno AS resno, rwj_regno AS regno, rwj_reg_date AS reg_date, rwj_ptno AS ptno, rwj_medroom AS medroom, "
			+ "rwj_name AS name, rwj_yage AS age, rwj_sex AS sex, rwj_flag AS flag, rwj_time AS 'time', "
			+ "(SELECT bjg_name FROM jin_gubun WHERE bjg_code=rwj_jin_gubun),"
			+ "(SELECT bpt_telno FROM patient WHERE bpt_ptno=rwj_ptno), "
			+ "(SELECT bpt_hpno FROM patient WHERE bpt_ptno=rwj_ptno), "
			+ "(SELECT bji_name FROM jonginfo WHERE bji_code=rwj_jogubun), "
			+ "bmr_name,bmr_docname,bmr_kwanm,rwj_wait_class AS wait_class, rwj_wait_sclass AS wait_sclass,rwj_jin_gubun AS jin_gubun, rwj_jin_kind "
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
	public List<CrmReceiptPatientVO> selectReceipt(CrmReceiptPatientParamVO vo) throws Exception;
}
