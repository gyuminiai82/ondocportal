package kr.ondoc.mapper.sybase.legacy.ondocplus;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.ondoc.domain.sybase.legacy.ondoc.JinryoReceiveParamVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ManlessQuestionDataVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ManlessQuestionParamVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ManlessQuestionResponseDataVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ManlessQuestionResponseParamVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ManlessReceiptAgeVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ManlessReceiptLastVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ManlessReceiptMedroomVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ManlessReceiptOptioninfoVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ManlessReceiptParamVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ManlessReceiptPatientSignVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ManlessReceiptPatientVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ManlessSunapDataVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.OptionVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.PatientSearchDataVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.PatientSearchParamVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ReceiptPatientDataVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ReceiptPatientParamVO;

public interface OldManlessReceiptMapper {
	@Select("<script>"
			+ "<![CDATA["
			+ " SELECT mmq_seq, mmq_title, mmq_question FROM mob_manless_question "
			+ "]]>"
			+ "WHERE 0=0  "
			+ "<if test=\"seq != null and seq != ''\">"
			+ "AND mmq_seq=#{seq} "
			+ "</if>"
			+ "ORDER BY mmq_seq ASC "
			+ "</script>")
	public List<ManlessQuestionDataVO> selectManlessQuestion(ManlessQuestionParamVO vo) throws Exception;
	
	@Insert("<script>"
			+ "INSERT mob_manless_question (mmq_title, mmq_question) "
			+ "VALUES ( "
			+ "#{title}, "
			+ "#{questions}"
			+ ")"
			+ "</script>")
	public void insertManlessQuestion(ManlessQuestionParamVO vo) throws Exception;
	
	@Update("<script>"
			+ "UPDATE mob_manless_question SET "
			+ "mmq_title = #{title}, "
			+ "mmq_question = #{questions} "
			+ "WHERE 0=0  "
			+ "AND mmq_seq=#{seq} "
			+ "</script>")
	public void updateManlessQuestion(ManlessQuestionParamVO vo) throws Exception;
	
	@Delete("<script>"
			+ "DELETE FROM mob_manless_question "
			+ "WHERE 0=0  "
			+ "AND mmq_seq=#{seq} "
			+ "</script>")
	public void deleteManlessQuestion(ManlessQuestionParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT TOP 100 bpt_ptno, bpt_resno2, bpt_resno3, * FROM patient WHERE bpt_resno2 <> '' AND bpt_resno3 = '' "
			+ "]]>"
			+ "</script>")
	public List<ReceiptPatientDataVO> selectconvertPatient() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT COUNT(*) FROM patient WHERE 0=0 "
			+ "]]>"
			+ "AND SUBSTR(bpt_resno, 1, 7) = SUBSTR(#{jumin}, 1, 7) "
			+ "AND bpt_resno3 = #{jumin2} "
			+ "</script>")
	public Integer chkPatient(ReceiptPatientParamVO vo) throws Exception; 
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT COUNT(*) FROM patient WHERE 0=0 "
			+ "]]>"
			+ "AND SUBSTR(bpt_resno, 1, 7) = SUBSTR(#{jumin}, 1, 7) "
			+ "AND bpt_resno2 = #{jumin2_old} "
			+ "AND bpt_resno3 = '' "
			+ "</script>")
	public Integer chkPatientEnc(ReceiptPatientParamVO vo) throws Exception; 
	
	@Update("<script>"
			+ "UPDATE patient SET "
			+ "bpt_resno3=#{jumin2} "
			+ "WHERE 0=0  "
			+ "AND SUBSTR(bpt_resno, 1, 7) = SUBSTR(#{jumin}, 1, 7) "
			+ "AND bpt_resno2 = #{jumin2_old} "
			+ "AND bpt_resno3 = '' "
			+ "</script>")
	public void updatePatientResno3(ReceiptPatientParamVO vo) throws Exception;
		
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT COUNT(*) AS cnt FROM waitjin wj "
			+ "LEFT OUTER JOIN patient p "
			+ "ON wj.rwj_ptno = p.bpt_ptno "
			+ "WHERE 0=0 "
			+ "]]>"
			+ "AND wj.rwj_reg_date = DATEFORMAT(GETDATE(), 'yyyymmdd') "
			+ "<if test=\"phone != null and phone != ''\">"
			+ "AND (p.bpt_hpno = #{phone} OR p.bpt_hpno = #{phone1}) "
			+ "</if>"
			+ "<if test=\"jumin != null and jumin != ''\">"
			+ "AND SUBSTR(bpt_resno, 1, 7) = SUBSTR(#{jumin}, 1, 7) AND p.bpt_resno3 = #{jumin2} "
			+ "</if>"
			+ "</script>")
	public Integer selectReceiptPatient(ReceiptPatientParamVO vo) throws Exception; 
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT COUNT(*) AS cnt FROM patient "
			+ "WHERE 0=0 "
			+ "]]>"
			+ "<if test=\"phone != null and phone != ''\">"
			+ "AND (bpt_hpno = #{phone} OR bpt_hpno = #{phone1}) "
			+ "</if>"
			+ "<if test=\"jumin != null and jumin != ''\">"
			+ "AND SUBSTR(bpt_resno, 1, 7) = SUBSTR(#{jumin}, 1, 7) AND bpt_resno3 = #{jumin2} "
			+ "</if>"
			+ "</script>")
	public Integer selectReceiptPatient2(ReceiptPatientParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT bpt_resno, bpt_resno2, bpt_resno3, bpt_hpno FROM patient "
			+ "GROUP BY bpt_resno, bpt_resno2, bpt_resno3, bpt_hpno "
			+ "HAVING bpt_hpno = #{phone} OR bpt_hpno = #{phone1} "
			+ "]]>"
			+ "</script>")
	public List<ReceiptPatientDataVO> selectReceiptPatient3(ReceiptPatientParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT bpt_ptno, bpt_name, bpt_hpno, bpt_birth, bpt_sex, bpt_resno, bpt_resno3 "
			+ "FROM patient "
			+ "WHERE 0=0 AND bpt_jogubun BETWEEN 21 AND 39 "
			+ "]]>"
			+ "<if test=\"phone != null and phone != ''\">"
			+ "AND (bpt_hpno = #{phone} OR bpt_hpno = #{phone1}) "
			+ "</if>"
			+ "<if test=\"jumin != null and jumin != ''\">"
			+ "AND SUBSTR(bpt_resno, 1, 7) = SUBSTR(#{jumin}, 1, 7) AND bpt_resno3 = #{jumin2} "
			+ "</if>"
			+ "ORDER BY bpt_enddt DESC "
			+ "</script>")
	public List<ReceiptPatientDataVO> selectReceiptPatient4(ReceiptPatientParamVO vo) throws Exception;	
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT bpt_ptno, bpt_name, bpt_hpno, bpt_birth, bpt_sex, bpt_resno, bpt_resno3 "
			+ "FROM patient "
			+ "WHERE 0=0 "
			+ "]]>"
			+ "<if test=\"phone != null and phone != ''\">"
			+ "AND (bpt_hpno = #{phone} OR bpt_hpno = #{phone1}) "
			+ "</if>"
			+ "<if test=\"jumin != null and jumin != ''\">"
			+ "AND SUBSTR(bpt_resno, 1, 7) = SUBSTR(#{jumin}, 1, 7) AND bpt_resno3 = #{jumin2} "
			+ "</if>"
			+ "ORDER BY bpt_enddt DESC "
			+ "</script>")
	public List<ReceiptPatientDataVO> selectReceiptPatient5(ReceiptPatientParamVO vo) throws Exception;	
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT TOP 1 onw_medroom FROM naewon "
			+ "WHERE onw_ptno = #{ptno} "
			+ "ORDER BY onw_ch_date DESC, onw_time DESC "
			+ "]]>"
			+ "</script>")
	public String selectReceiptPatientMedroom(String ptno) throws Exception;
	
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT TOP 1 * FROM optioninfo where xpt_date<DATEFORMAT(GETDATE(), 'yyyymmdd') ORDER BY xpt_date DESC "
			+ "]]>"
			+ "</script>")
	public ManlessReceiptOptioninfoVO selectOption() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT * FROM last "
			+ "]]>"
			+ "</script>")
	public ManlessReceiptLastVO selectLast() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT TOP 1 bpt_ptno,bpt_hpno,bpt_jogubun "
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
	public ManlessReceiptPatientVO selectPatient(ManlessReceiptParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT TOP 1 * FROM medroom "
			+ "WHERE 0=0 AND bmr_use='Y' AND bmr_mobuse='Y' "
			+ "AND bmr_hosnum=(SELECT bhi_hosnum FROM hosinfo WHERE bhi_usechk='Y') AND bmr_code=#{medroom} "
			+ "ORDER BY bmr_stdate DESC "
			+ "]]>"
			+ "</script>")
	public ManlessReceiptMedroomVO selectMedroom(ManlessReceiptParamVO vo) throws Exception;
	
	@Insert("<script>"
			+ "<![CDATA["
			+ "INSERT INTO patient(bpt_ptno, bpt_name, bpt_resno, bpt_resno2,bpt_resno3,"
			+ "bpt_birth, bpt_hpno, bpt_jogubun,bpt_sex,bpt_email,"
			+ "bpt_yage,bpt_mage, bpt_cen, bpt_initdt,bpt_zip,bpt_addr,bpt_addr2) "
			+ "VALUES "
			+ "(#{ptno},#{name},#{jumin1},'',#{jumin2},"
			+ "#{birth},#{hpno},'21',#{sex},#{idno},#{sYAge},#{sMAge},#{sCen},DATEFORMAT(GETDATE(), 'yyyymmdd'),#{zip},#{addr1},#{addr2}"
			+ ") "
			+ "]]>"
			+ "</script>")
	public void insertPatient(ManlessReceiptParamVO vo) throws Exception;
	
	@Insert("<script>"
			+ "<![CDATA["
			+ "INSERT INTO patient_sign (bps_code,bps_ptno,bps_name,bps_pname,bps_relat,bps_sign,bps_date) VALUES ("
			+ "'001',"
			+ "#{ptno},"
			+ "#{name},"
			+ "#{name},"
			+ "'본인',"
			+ "#{psign},"
			+ "DATEFORMAT(GETDATE(), 'yyyymmdd')"
			+ ") "
			+ "]]>"
			+ "</script>")
	public void insertPatientSign(ManlessReceiptPatientSignVO vo) throws Exception;
	
	@Update("<script>"
			+ "UPDATE patient SET "
			+ "bpt_jubsumemo='동의일자:'||DATEFORMAT(GETDATE(), 'yyyy-mm-dd'), bpt_ptinfo='010000000' "
			+ "WHERE 0=0  "
			+ "AND bpt_ptno=#{ptno} "
			+ "</script>")
	public void updatePatient(String ptno) throws Exception;
	
	@Insert("<script>"
			+ "<![CDATA["
			+ "UPDATE patient_sign SET bps_sign=#{psign} "
			+ "WHERE bps_ptno=#{ptno}"
			+ "]]>"
			+ " "
			+ "</script>")
	public void updatePatientSign(ManlessReceiptPatientSignVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "select calage(#{birth},today()) AS yAge, calage2(#{birth},today()) AS mAge "
			+ "]]>"
			+ "</script>")
	public ManlessReceiptAgeVO selectAge(String birth) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT COUNT(*) FROM patient WHERE 0=0 "
			+ "AND bpt_ptno=#{ptno}"
			+ "]]>"
			+ "</script>")
	public int selectPatientCnt(String ptno) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT @@identity AS regno"
			+ "]]>"
			+ "</script>")
	public String selectRegno() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT MAX(rrg_regno) from reg "
			+ " where rrg_reg_date = CONVERT(CHAR(8), GETDATE(), 112) "
			+ " and rrg_ptno = #{ptno} "
			+ "]]>"
			+ "<if test=\"medroom != null and medroom != ''\">"
			+ "AND rrg_medroom=#{medroom} "
			+ "</if>"
			+ "</script>")
	public String selectRegno2(ManlessReceiptParamVO vo) throws Exception;
	
	@Update("<script>"
			+ "UPDATE last SET rla_nptno=#{ptno}"
			+ "</script>")
	public void updateLastNptno(String ptno) throws Exception;
	
	@Update("<script>"
			+ "UPDATE last SET rla_mptno=#{ptno}"
			+ "</script>")
	public void updateLastMptno(String ptno) throws Exception;
		
	@Insert("<script>"
			+ "<![CDATA["
			+ "INSERT INTO reg(rrg_reg_date,rrg_ptno,rrg_medroom,rrg_kwa,rrg_kind,rrg_class,rrg_qua_seq ) "
			+ "VALUES "
			+ "(DATEFORMAT(GETDATE(), 'yyyymmdd'), #{ptno}, #{medroom}, #{sKwa}, 'ON','10', '1'"
			+ ")  "
			+ "]]>"
			+ "</script>")
	public void insertReg(ManlessReceiptParamVO vo) throws Exception;
	
	@Insert("<script>"
			+ "INSERT INTO waitjin ("
			+ "rwj_regno,rwj_reg_date,rwj_ptno,rwj_medroom,rwj_kwa,"
			+ "rwj_name,rwj_resno, rwj_yage,rwj_mage,rwj_sex,"
			+ "rwj_jin_gubun,rwj_jin_kind,rwj_jin_time, rwj_wait_class,rwj_wait_sclass,"
			+ "rwj_other_room,rwj_jogubun,rwj_qua_seq, rwj_salecd,rwj_salerate,"
			+ "rwj_excp,rwj_jin_ref,rwj_reserve_memo, rwj_findchart,rwj_mngrno,"
			+ "rwj_flag, rwj_nextchoday, rwj_nanchicode, rwj_pregnant, rwj_nosound, "
			+ "rwj_tmp3, rwj_tmp6"
			+ ") "
			+ "VALUES "
			+ "( "
			+ "#{sRegCnt}, DATEFORMAT(GETDATE(), 'yyyymmdd'), #{ptno}, #{medroom}, #{sKwa}, "
			+ "#{name}, #{jumin1}, #{sYAge}, #{sMAge}, #{sex},"
			+ "#{jin_gubun}, "
			+ "#{jin_kind}, "
			+ "'1', #{sClass}, "
			+ "#{wait_sclass}, "
			+ "'', #{sJogubun}, '1', '', '', "
			+ "'', '', '', '', '1', "
			+ "'', '', '', '','',"
			+ "#{tmp3},#{company})  "
			+ "</script>")
	public void insertWaitjin(ManlessReceiptParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT TOP 1 "
			+ "mmqr_seq, mmqr_reg_date, mmqr_medroom, mmqr_name, mmqr_birth, "
			+ "mmqr_hpno, mmqr_title, mmqr_reply "
			+ "FROM mob_manless_question_reply "
			+ "WHERE 0=0 "
			+ "]]>"
			+ "<if test=\"seq != null and seq != ''\">"
			+ "AND mmqr_seq=#{seq} "
			+ "</if>"
			+ "<if test=\"reg_date != null and reg_date != ''\">"
			+ "AND mmqr_reg_date=#{reg_date} "
			+ "</if>"
			+ "<if test=\"medroom != null and medroom != ''\">"
			+ "AND mmqr_medroom=#{medroom} "
			+ "</if>"
			+ "<if test=\"name != null and name != ''\">"
			+ "AND mmqr_name=#{name} "
			+ "</if>"
			+ "<if test=\"birth != null and birth != ''\">"
			+ "AND mmqr_birth=#{birth} "
			+ "</if>"
			+ "<if test=\"hpno != null and hpno != ''\">"
			+ "AND mmqr_hpno=#{hpno} "
			+ "</if>"
			+ "ORDER BY mmqr_seq DESC"
			+ "</script>")
	public List<ManlessQuestionResponseDataVO> selectManlessQuestionReply(ManlessQuestionResponseParamVO vo) throws Exception;
	
	@Insert("<script>"
			+ "<![CDATA["
			+ "INSERT mob_manless_question_reply ("
			+ "mmqr_reg_date, mmqr_medroom, mmqr_name, mmqr_birth, mmqr_hpno, mmqr_title, mmqr_reply"
			+ ") VALUES ( "
			+ "DATEFORMAT(GETDATE(), 'yyyymmdd'),"
			+ "#{medroom},"
			+ "#{name},"
			+ "#{birth},"
			+ "#{hpno},"
			+ "#{title},"
			+ "#{reply}"
			+ ")  "
			+ "]]>"
			+ "</script>")
	public void insertManlessQuestionReply(ManlessQuestionResponseParamVO vo) throws Exception;
	
	@Select("<script>"
			+ " SELECT pwt_ptno, pwt_name, pwt_medroom, (pwt_boninkum+pwt_bikum+pwt_100kum-pwt_dc1kum-pwt_dc2kum-pwt_nanchikum-pwt_bohunboninkum-pwt_bonin100_underkum) AS sunapkum "
			+ "FROM paywait "
			+ "WHERE 0=0 AND pwt_waitdate = convert(varchar, GETDATE(), 112) "
			+ "ORDER BY pwt_waitdate, pwt_waittime "
			+ "</script>")
	public List<ManlessSunapDataVO> selectManlessSunap() throws Exception;
}
