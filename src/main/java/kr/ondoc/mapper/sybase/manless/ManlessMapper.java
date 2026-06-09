package kr.ondoc.mapper.sybase.manless;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.ondoc.domain.sybase.manless.ReceiptPatientVO;
import kr.ondoc.domain.sybase.manless.ReceiptPtnoLastVO;
import kr.ondoc.domain.sybase.legacy.IdentificationDataVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ManlessQuestionDataVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ManlessQuestionParamVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ManlessQuestionResponseParamVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ManlessReceiptParamVO;
import kr.ondoc.domain.sybase.manless.AgeTempVO;
import kr.ondoc.domain.sybase.manless.IdentificationVO;
import kr.ondoc.domain.sybase.manless.MedroomTempVO;
import kr.ondoc.domain.sybase.manless.MunjinVO;
import kr.ondoc.domain.sybase.manless.OptioninfoVO;
import kr.ondoc.domain.sybase.manless.ReceiptMedroomVO;
import kr.ondoc.domain.sybase.manless.ReceiptPatientParamVO;
import kr.ondoc.domain.sybase.manless.ReceiptPatientSignVO;
import kr.ondoc.domain.sybase.manless.ReceiptPatientTempVO;


public interface ManlessMapper {
	//문진
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
	public List<MunjinVO> selectMunjin(String seq) throws Exception;
	
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
	public void insertMunjinReply(ReceiptPatientParamVO vo) throws Exception;
	
	//진료실
	@Select("<script>"
			+ "SELECT bmr_code,bmr_name,bmr_docname,bmr_kwanm FROM medroom "
			+ "WHERE bmr_use='Y' AND bmr_hosnum=(SELECT bhi_hosnum FROM hosinfo WHERE bhi_usechk='Y') "
			+ "ORDER BY bmr_code "
			+ "</script>")
	public List<ReceiptMedroomVO> selectReceiptMedroom() throws Exception;
	
	//환자검색 Start
	//대기중인 환자 검색
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT COUNT(*) AS cnt FROM waitjin wj "
			+ "LEFT OUTER JOIN patient p "
			+ "ON wj.rwj_ptno = p.bpt_ptno "
			+ "WHERE 0=0 "
			+ "]]>"
			+ "AND wj.rwj_reg_date = DATEFORMAT(GETDATE(), 'yyyymmdd') "
			+ "<if test=\"phoneFront != '' and phoneEnd != ''\">"
			+ "AND bpt_hpno like '010%' AND (bpt_hpno like '%${phoneFront}____' OR bpt_hpno like '%${phoneFront}_____') AND bpt_hpno like '%${phoneEnd}' "
			+ "</if>"
			+ "<if test=\"jumin1 != null and jumin1 != ''\">"
			+ "AND SUBSTR(bpt_resno, 1, 7) = #{jumin1} AND p.bpt_resno3 = #{jumin2} "
			+ "</if>"
			+ "</script>")
	public Integer checkStandBy(String jumin1, String jumin2, String phoneFront, String phoneEnd) throws Exception;
	
	//환자 검색
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT COUNT(*) AS cnt FROM patient "
			+ "WHERE 0=0 "
			+ "]]>"
			+ "<if test=\"phoneFront != '' and phoneEnd != ''\">"
			+ "AND bpt_hpno like '010%' AND (bpt_hpno like '%${phoneFront}____' OR bpt_hpno like '%${phoneFront}_____') AND bpt_hpno like '%${phoneEnd}' "
			+ "</if>"
			+ "<if test=\"jumin1 != null and jumin1 != ''\">"
			+ "AND SUBSTR(bpt_resno, 1, 7) = #{jumin1} AND bpt_resno3 = #{jumin2} "
			+ "</if>"
			+ "</script>")
	public Integer cntReceiptPatient(String jumin1, String jumin2, String phoneFront, String phoneEnd) throws Exception;
	
	//같은 연락처 쓰는 환자 인원수
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT COUNT(*) FROM (SELECT bpt_resno, bpt_hpno FROM patient "
			+ "GROUP BY bpt_resno, bpt_resno2, bpt_resno3, bpt_hpno  "
			+ "HAVING bpt_hpno like '010%' AND (bpt_hpno like '%${phoneFront}____' OR bpt_hpno like '%${phoneFront}_____') AND bpt_hpno like '%${phoneEnd}') AS temp "
			+ "]]>"
			+ "</script>")
	public Integer cntPhoneMan(String phoneFront, String phoneEnd) throws Exception;
	
	
	//가장 최근에 접수한 종별의 환자정보 가져오기
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT TOP 1 bpt_ptno, bpt_name, bpt_hpno, bpt_birth, bpt_sex, bpt_resno, bpt_resno3, bpt_zip, bpt_addr, bpt_addr2 "
			+ "FROM patient "
			+ "WHERE 0=0 "
			+ "]]>"
			+ "<if test=\"jogubun != null and jogubun != ''\">"
			+ "AND bpt_jogubun BETWEEN 21 AND 39 "
			+ "</if>"
			+ "<if test=\"phoneFront != '' and phoneEnd != ''\">"
			+ "AND bpt_hpno like '010%' AND (bpt_hpno like '%${phoneFront}____' OR bpt_hpno like '%${phoneFront}_____') AND bpt_hpno like '%${phoneEnd}' "
			+ "</if>"
			+ "<if test=\"jumin1 != null and jumin1 != ''\">"
			+ "AND SUBSTR(bpt_resno, 1, 7) = #{jumin1} AND bpt_resno3 = #{jumin2} "
			+ "</if>"
			+ "ORDER BY bpt_enddt DESC "
			+ "</script>")
	public List<ReceiptPatientVO> selectReceiptPatient(String jogubun, String jumin1, String jumin2, String phoneFront, String phoneEnd) throws Exception;
	
	//가장 최근에 접수한 진료실 정보 가져오기
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT TOP 1 onw_medroom FROM naewon "
			+ "WHERE onw_ptno = #{ptno} "
			+ "ORDER BY onw_ch_date DESC, onw_time DESC "
			+ "]]>"
			+ "</script>")
	public String selectReceiptPatientMedroom(String ptno) throws Exception;
	//환자검색 End
	
	//접수하기 Start
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT TOP 1 * FROM optioninfo where xpt_date<DATEFORMAT(GETDATE(), 'yyyymmdd') ORDER BY xpt_date DESC "
			+ "]]>"
			+ "</script>")
	public OptioninfoVO selectOption() throws Exception;
	
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
	public ReceiptPatientTempVO selectPatientTemp(ReceiptPatientParamVO vo) throws Exception;
	

	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT TOP 1 * FROM medroom "
			+ "WHERE 0=0 AND bmr_use='Y' "
			+ "AND bmr_hosnum=(SELECT bhi_hosnum FROM hosinfo WHERE bhi_usechk='Y') AND bmr_code=#{medroom} "
			+ "ORDER BY bmr_stdate DESC "
			+ "]]>"
			+ "</script>")
	public MedroomTempVO selectMedroomTemp(ReceiptPatientParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT * FROM last "
			+ "]]>"
			+ "</script>")
	public ReceiptPtnoLastVO selectLast() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "select calage(#{birth},today()) AS yAge, calage2(#{birth},today()) AS mAge "
			+ "]]>"
			+ "</script>")
	public AgeTempVO selectAgeTemp(String birth) throws Exception;
	
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT COUNT(*) FROM patient WHERE 0=0 "
			+ "AND bpt_ptno=#{ptno}"
			+ "]]>"
			+ "</script>")
	public int selectPatientCnt(String ptno) throws Exception;
	
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
	public void insertPatient(ReceiptPatientParamVO vo) throws Exception;
	
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
	public void insertPatientSign(ReceiptPatientSignVO vo) throws Exception;
	
	@Update("<script>"
			+ "UPDATE patient SET "
			+ "bpt_jubsumemo='동의일자:'||DATEFORMAT(GETDATE(), 'yyyy-mm-dd'), bpt_ptinfo='010000000' "
			+ "WHERE 0=0  "
			+ "AND bpt_ptno=#{ptno} "
			+ "</script>")
	public void updatePatient(String ptno) throws Exception;
	
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
	public void insertReg(ReceiptPatientParamVO vo) throws Exception;
	
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
	public String selectRegno2(ReceiptPatientParamVO vo) throws Exception;
	
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
	public void insertWaitjin(ReceiptPatientParamVO vo) throws Exception;
	
	//접수하기 End
	
	
	//본인확인 싸인(6개월마다) 날짜 확인
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT TOP 1 ISNULl(bpt2_sinbundate, '') AS bpt2_sinbundate FROM patient2 WHERE bpt2_ptno = ${ptno}"
			+ "]]>"
			+ "</script>")
	public IdentificationVO selectIdentification(String ptno) throws Exception;
}
