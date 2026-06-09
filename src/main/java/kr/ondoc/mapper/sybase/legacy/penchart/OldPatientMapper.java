package kr.ondoc.mapper.sybase.legacy.penchart;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import kr.ondoc.domain.sybase.legacy.ondocplus.PatientObgyDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.PatientDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.PatientRoomSymptomVO;
import kr.ondoc.domain.sybase.legacy.penchart.PatientSearchOutDaegiDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.PatientSearchParamVO;

public interface OldPatientMapper {
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
			+ "SELECT bpt_ptno as ptno,bpt_name as name,bpt_resno as resno,"
			+ "bpt_yage as yage,bpt_sex as sex,bpt_telno as telno,bpt_hpno as hpno,bpt_addr as addr,bpt_addr2 as addr2,bpt_jogubun,TRIM(bpt_jubsumemo) AS bpt_jubsumemo, "
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
	public List<PatientDataVO> selectPatient(String ptno) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT bpt_ptno,bpt_name,bpt_resno,bpt_yage,bpt_sex,bpt_telno,bpt_hpno, bpt_addr,bpt_addr2, "
			+ "TRIM(bpt_jubsumemo) AS bpt_jubsumemo, "
			+ "(SELECT bji_name FROM jonginfo WHERE bji_code=bpt_jogubun) as bji_name, "
			+ "(SELECT rcm_memo FROM receipt_memo WHERE rcm_ptno=bpt_ptno) as rcm_memo, "
			+ "(SELECT TOP 1 xso_base35 as opt FROM systemoption) as opt, "
			+ "(SELECT TOP 1 osp_symptom as symptom_rtf FROM special_rtf WHERE osp_ptno=bpt_ptno) as symptom_rtf, "
			+ "calage(bpt_cen + substr(bpt_resno, 1, 6),today()) as bpt_age "
			+ "FROM patient "
			+ "WHERE 0=0   "
			+ "AND bpt_ptno=#{ptno} "
			+ "]]>"
			+ "</script>")
	public List<kr.ondoc.domain.sybase.legacy.ondocplus.PatientDataVO> selectOnDocPlusPatient(String ptno) throws Exception;
	
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT * "
			+ "FROM patient_obgy "
			+ "WHERE 0=0   "
			+ "AND rpo_ptno=#{ptno} "
			+ "]]>"
			+ "</script>")
	public List<kr.ondoc.domain.sybase.legacy.ondocplus.PatientObgyDataVO> selectOnDocPlusPatientObgy(String ptno) throws Exception;
	 
}
