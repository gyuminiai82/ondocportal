package kr.ondoc.mapper.sybase.crm;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.ondoc.domain.sybase.crm.CrmPatientDetailVO;
import kr.ondoc.domain.sybase.crm.CrmPatientJonginfoVO;
import kr.ondoc.domain.sybase.crm.CrmPatientParamVO;
import kr.ondoc.domain.sybase.crm.CrmPatientRoomSymptomVO;
import kr.ondoc.domain.sybase.crm.CrmPatientVO;
import kr.ondoc.domain.sybase.manless.ReceiptPatientParamVO;

public interface CrmPatientMapper {
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT bpt_ptno,bpt_name,bpt_resno,bpt_yage,bpt_sex,bpt_telno,bpt_hpno, bpt_addr,bpt_addr2, bpt_birth, bpt_zip, "
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
	public CrmPatientDetailVO selectPatient(CrmPatientParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT (SELECT bmr_name FROM medroom WHERE bmr_code=ors_medroom AND bmr_use='Y' AND bmr_hosnum=(SELECT bhi_hosnum FROM hosinfo WHERE bhi_usechk='Y')) as medroom, "
			+ "ors_symptom "
			+ "from room_symptom "
			+ "where ors_ptno=#{ptno} ORDER BY ors_seq "
			+ "]]>"
			+ "</script>")
	public List<CrmPatientRoomSymptomVO> selectPatientRoomSymptom(String ptno) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "select (SELECT bmr_name FROM medroom WHERE bmr_code=ors_medroom AND bmr_stdate=(SELECT MAX(bmr_stdate) FROM medroom "
			+ "WHERE bmr_code=ors_medroom AND bmr_stdate <= DATEFORMAT( getdate(*), 'yyyymmdd') )) as medroom, ors_symptom from room_symptom_rtf where ors_ptno=#{ptno} "
			+ "]]>"
			+ "</script>")
	public List<CrmPatientRoomSymptomVO> selectPatientRoomSymptomRtf(String ptno) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT osp_symptom FROM special WHERE osp_ptno=#{ptno} "
			+ "]]>"
			+ "</script>")
	public List<CrmPatientRoomSymptomVO> selectPatientSymptom(String ptno) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT TOP ${listNum} "
			//+ "(SELECT bjk_kwanm FROM yjinkwa WHERE bjk_kwa=(SELECT TOP 1 onw_kwa from naewon WHERE onw_ptno=t.bpt_ptno ORDER BY onw_regno DESC)), "
			+ "* FROM "
			+ "( "
			+ "SELECT *, ROW_NUMBER( ) OVER ( ORDER BY ${sort} ${sortDirection} ) AS RowNum "
			+ "FROM patient WHERE 0=0 "
			+ "]]>"
			+ "<if test=\"ptno != null and ptno != ''\">"
			+ "AND bpt_ptno like '%${ptno}%' "
			+ "</if>"
			+ "<if test=\"name != null and name != ''\">"
			+ "AND bpt_name like '%${name}%' "
			+ "</if>"
			+ "<if test=\"resno != null and resno != ''\">"
			+ "AND bpt_resno like '%${resno}%' "
			+ "</if>"
			+ "<if test=\"birthday != null and birthday != ''\">"
			+ "AND bpt_birth like '%${birthday}%' "
			+ "</if>"
			+ "<if test=\"jogubun != null and jogubun != ''\">"
			+ "AND bpt_jogubun=#{jogubun} "
			+ "</if>"
			+ "<if test=\"hpno != null and hpno != ''\">"
			+ "AND REPLACE(bpt_hpno, '-', '') like '%${hpno}%' "
			+ "</if>"
			+ "<if test=\"jubsumemo != null and jubsumemo != ''\">"
			+ "AND bpt_jubsumemo like '%${jubsumemo}%' "
			+ "</if>"
			+ "<if test=\"searchType != null and searchType != ''\">"
			+ "AND bpt_${searchType} like '%${searchWord}%' "
			+ "</if>"
			
			+ ") AS t "
			+ "<![CDATA["
			+ " WHERE RowNum>=${first}"
			+ "]]>"
			+ "</script>")
	public List<CrmPatientVO> selectPatientList(CrmPatientParamVO vo) throws Exception;
	//SELECT  TOP 15  * FROM ( SELECT *, ROW_NUMBER( ) OVER ( ORDER BY bpt_initdt DESC ) AS RowNum FROM patient ) AS t WHERE RowNum>=1
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT COUNT(*) "
			+ "]]>"
			+ "FROM patient WHERE 0=0 "
			+ "<if test=\"ptno != null and ptno != ''\">"
			+ "AND bpt_ptno like '%${ptno}%' "
			+ "</if>"
			+ "<if test=\"name != null and name != ''\">"
			+ "AND bpt_name like '%${name}%' "
			+ "</if>"
			+ "<if test=\"resno != null and resno != ''\">"
			+ "AND bpt_resno like '%${resno}%' "
			+ "</if>"
			+ "<if test=\"birthday != null and birthday != ''\">"
			+ "AND bpt_birth like '%${birthday}%' "
			+ "</if>"
			+ "<if test=\"jogubun != null and jogubun != ''\">"
			+ "AND bpt_jogubun=#{jogubun} "
			+ "</if>"
			+ "<if test=\"hpno != null and hpno != ''\">"
			+ "AND REPLACE(bpt_hpno, '-', '') like '%${hpno}%' "
			+ "</if>"
			+ "<if test=\"jubsumemo != null and jubsumemo != ''\">"
			+ "AND bpt_jubsumemo like '%${jubsumemo}%' "
			+ "</if>"
			+ "<if test=\"searchType != null and searchType != ''\">"
			+ "AND bpt_${searchType} like '%${searchWord}%' "
			+ "</if>"
			+ "</script>")
	public int countPatient(CrmPatientParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT * FROM jonginfo "
			+ "WHERE bji_usechk='Y'"
			+ ""
			+ "]]>"
			+ "</script>")
	public List<CrmPatientJonginfoVO> selectPatientJonginfo() throws Exception;
	
	@Insert("<script>"
			+ "<![CDATA["
			+ "INSERT INTO patient(bpt_ptno, bpt_name, bpt_resno, bpt_resno2,bpt_resno3,"
			+ "bpt_birth, bpt_hpno, bpt_jogubun,bpt_sex,"
			+ "bpt_yage,bpt_mage, bpt_cen, bpt_initdt,bpt_zip,bpt_addr,bpt_addr2, bpt_channel, bpt_jubsumemo) "
			+ "VALUES "
			+ "(#{bpt_ptno},#{bpt_name},#{bpt_resno},'','',"
			+ "#{bpt_birth},#{bpt_hpno},'91',#{bpt_sex},"
			+ "#{bpt_yage},#{bpt_mage},#{bpt_cen},DATEFORMAT(GETDATE(), 'yyyymmdd'),#{bpt_zip},#{bpt_addr},#{bpt_addr2}, #{bpt_channel}, #{bpt_jubsumemo}"
			+ ") "
			+ "]]>"
			+ "</script>")
	public void insertPatient(CrmPatientVO vo) throws Exception;
	
	@Update("<script>"
			+ "UPDATE patient SET "
			+ "bpt_ptno = bpt_ptno "
			+ ",bpt_name = #{bpt_name} "
			+ "<if test=\"bpt_resno != null and bpt_resno != ''\">"
			+ ",bpt_resno = #{bpt_resno} "
			+ "</if>"
			+ ",bpt_birth = #{bpt_birth} "
			+ "<if test=\"bpt_hpno != null and bpt_hpno != ''\">"
			+ ",bpt_hpno = #{bpt_hpno} "
			+ "</if>"
			+ ",bpt_sex = #{bpt_sex} "
			+ ",bpt_yage = #{bpt_yage} "
			+ ",bpt_mage = #{bpt_mage} "
			+ "<if test=\"bpt_cen != null and bpt_cen != ''\">"
			+ ",bpt_cen = #{bpt_cen} "
			+ "</if>"
			+ ",bpt_zip = #{bpt_zip} "
			+ ",bpt_addr = #{bpt_addr} "
			+ ",bpt_addr2 = #{bpt_addr2} "
			+ ",bpt_jubsumemo = #{bpt_jubsumemo} "
			+ "WHERE 0=0 "
			+ "AND bpt_ptno = #{bpt_ptno}"
			+ "</script>")
	public void updatePatient(CrmPatientVO vo) throws Exception;
	
	@Update("<script>"
			+ "UPDATE patient SET "
			+ "bpt_yage = #{bpt_yage} "
			+ ",bpt_mage = #{bpt_mage} "
			+ "WHERE 0=0 "
			+ "AND bpt_ptno = #{bpt_ptno}"
			+ "</script>")
	public void updatePatientAge(CrmPatientVO vo) throws Exception;
}
