package kr.ondoc.mapper.sybase.legacy.ondoc;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.ondoc.domain.sybase.legacy.ondoc.JinryoDataVO;
import kr.ondoc.domain.sybase.legacy.ondoc.JinryoParamVO;
import kr.ondoc.domain.sybase.legacy.ondoc.JinryoPatientDataVO;
import kr.ondoc.domain.sybase.legacy.ondoc.JinryoReceiveParamVO;
import kr.ondoc.domain.sybase.legacy.ondoc.JinryoReserveDataVO;
import kr.ondoc.domain.sybase.legacy.ondoc.JinryoReserveParamVO;
import kr.ondoc.domain.sybase.legacy.ondoc.JinryoVO;
import kr.ondoc.domain.sybase.legacy.ondoc.MedroomDataVO;
import kr.ondoc.domain.sybase.legacy.ondoc.MedroomReceiptDataVO;
import kr.ondoc.domain.sybase.legacy.ondoc.ReservePhysicalDataVO;
import kr.ondoc.domain.sybase.legacy.ondoc.ReservePhysicalParamVO;
import kr.ondoc.domain.sybase.legacy.ondoc.WaitJinryoDataVO;
import kr.ondoc.domain.sybase.legacy.ondoc.WorkDateDataVO;
import kr.ondoc.domain.sybase.legacy.ondoc.WorkDateExceptDataVO;
import kr.ondoc.domain.sybase.legacy.ondoc.WorkPlanDataVO;
import kr.ondoc.domain.sybase.legacy.ondoc.WorkPlanParamVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ManlessQuestionParamVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ManlessReceiptAgeVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ManlessReceiptLastVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ManlessReceiptMedroomVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ManlessReceiptParamVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ManlessReceiptPatientVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ManlessSunapDataVO;

public interface OldOndocMapper {
	@Select("<script>"
			+ "<![CDATA["
			+ " SELECT bmr_code as medroom,bmr_name as name,bmr_kwanm as kwaname, (SELECT COUNT(rwj_medroom) FROM waitjin "
			+ "											WHERE bmr_code=rwj_medroom "
			+ "											AND rwj_reg_date = DATEFORMAT(GETDATE(), 'yyyymmdd') AND bmr_stdate <= rwj_reg_date AND bmr_use='Y' "
			+ "											AND bmr_hosnum = (SELECT bhi_hosnum FROM hosinfo  WHERE bmr_hosnum=bhi_hosnum and bhi_usechk='Y')) AS awaitercount "
			+ "FROM medroom  "
			+ "WHERE bmr_hosnum = (SELECT bhi_hosnum FROM hosinfo WHERE bhi_usechk='Y') "
			+ "AND bmr_use='Y' "
			+ "]]>"
			+ "</script>")
	public List<WaitJinryoDataVO> selectWaitJinryo() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "select min_time as startTime, max_time as endTime, bmr_code as code, bmr_mobname as name, bmr_docname as docname, bmr_kwanm as kwaname, "
			+ "DATEFORMAT( getdate(*),'HHnn') as currentTime "
			+ "from (SELECT * FROM medroom "
			+ "		WHERE bmr_use='Y'  AND bmr_hosnum=(SELECT bhi_hosnum FROM hosinfo WHERE bhi_usechk='Y')) AS medroom "
			+ "		left outer join (select bdw_code,min(bdw_time), max(bdw_time) FROM doc_workplan a where bdw_date=(select max(bdw_date) "
			+ "						from doc_workplan "
			+ "						WHERE bdw_code= a.bdw_code and (bdw_gubun='0' or bdw_gubun='1')  "
			+ "						and bdw_date<=DATEFORMAT( getdate(*),'YYYYmmdd') and bdw_daygu=(DOW( getdate(*) ) -1) ) "
			+ "						and (bdw_gubun = '0'  or bdw_gubun='1') and bdw_daygu=(DOW( getdate(*) ) -1) "
			+ "						and bdw_hosnum=(select top 1 bhi_hosnum from hosinfo where bhi_usechk='Y') group by bdw_code) as workplan (code1,min_time, max_time) "
			+ "on (code1=bmr_code) "
			+ "left outer join (select bds_code FROM doc_schedule  "
			+ "					where bds_daygu='9' and bds_fdate<=DATEFORMAT( getdate(*),'YYYYmmdd') and bds_tdate>=DATEFORMAT( getdate(*),'YYYYmmdd')) as schedu (code2) "
			+ "on (code2=bmr_code) "
			+ "where bmr_use='Y' and bmr_mobuse='Y'  "
			+ "]]>"
			+ "</script>")
	public List<MedroomReceiptDataVO> selectMedroomReceipt() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT bmr_code as code,bmr_name as name,bmr_docname as docname,bmr_kwanm as kwaname FROM medroom "
			+ "WHERE bmr_use='Y' AND bmr_hosnum=(SELECT bhi_hosnum FROM hosinfo WHERE bhi_usechk='Y') AND bmr_docname <> '' "
			+ "ORDER BY bmr_name, bmr_code "
			+ "]]>"
			+ "</script>")
	public List<MedroomDataVO> selectMedroom() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT bds_fdate, bds_tdate FROM doc_schedule "
			+ "WHERE 0=0 AND bds_code=#{code} "
			+ "AND bds_hosnum=(SELECT bhi_hosnum FROM hosinfo WHERE bhi_usechk='Y') "
			+ "AND bds_fdate IS NOT NULL AND bds_tdate IS NOT NULL  "
			+ "]]>"
			+ "</script>")
	public List<WorkDateExceptDataVO> selectWorkDateExcept(String code) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT * FROM ( "
			+ "SELECT (substring(usableDate, 1, 4)+substring(usableDate, 6, 2)+substring(usableDate, 9, 2)) AS usableDate "
			+ "FROM ( "
			+ "SELECT today()+1 AS day,today()+1 AS usableDate "
			+ "UNION ALL SELECT today()+2 AS day,today()+2 AS usableDate "
			+ "UNION ALL SELECT today()+3 AS day,today()+3 AS usableDate "
			+ "UNION ALL SELECT today()+4 AS day,today()+4 AS usableDate "
			+ "UNION ALL SELECT today()+5 AS day,today()+5 AS usableDate "
			+ "UNION ALL SELECT today()+6 AS day,today()+6 AS usableDate "
			+ "UNION ALL SELECT today()+7 AS day,today()+7 AS usableDate "
			+ "UNION ALL SELECT today()+8 AS day,today()+8 AS usableDate "
			+ "UNION ALL SELECT today()+9 AS day,today()+9 AS usableDate "
			+ "UNION ALL SELECT today()+10 AS day,today()+10 AS usableDate "
			+ "UNION ALL SELECT today()+11 AS day,today()+11 AS usableDate "
			+ "UNION ALL SELECT today()+12 AS day,today()+12 AS usableDate "
			+ "UNION ALL SELECT today()+13 AS day,today()+13 AS usableDate "
			+ "UNION ALL SELECT today()+14 AS day,today()+14 AS usableDate "
			+ "UNION ALL SELECT today()+15	AS day,today()+15 AS usableDate "
			+ "UNION ALL SELECT today()+16	AS day,today()+16 AS usableDate "
			+ "UNION ALL SELECT today()+17	AS day,today()+17 AS usableDate "
			+ "UNION ALL SELECT today()+18	AS day,today()+18 AS usableDate "
			+ "UNION ALL SELECT today()+19	AS day,today()+19 AS usableDate "
			+ "UNION ALL SELECT today()+20	AS day,today()+20 AS usableDate "
			+ "UNION ALL SELECT today()+21	AS day,today()+21 AS usableDate "
			+ "UNION ALL SELECT today()+22	AS day,today()+22 AS usableDate "
			+ "UNION ALL SELECT today()+23	AS day,today()+23 AS usableDate "
			+ "UNION ALL SELECT today()+24	AS day,today()+24 AS usableDate "
			+ "UNION ALL SELECT today()+25	AS day,today()+25 AS usableDate "
			+ "UNION ALL SELECT today()+26	AS day,today()+26 AS usableDate "
			+ "UNION ALL SELECT today()+27	AS day,today()+27 AS usableDate "
			+ "UNION ALL SELECT today()+28	AS day,today()+28 AS usableDate "
			+ "UNION ALL SELECT today()+29	AS day,today()+29 AS usableDate "
			+ "UNION ALL SELECT today()+30	AS day,today()+30 AS usableDate "
			+ "UNION ALL SELECT today()+31	AS day,today()+31 AS usableDate "
			+ "UNION ALL SELECT today()+32	AS day,today()+32 AS usableDate "
			+ "UNION ALL SELECT today()+33	AS day,today()+33 AS usableDate "
			+ "UNION ALL SELECT today()+34	AS day,today()+34 AS usableDate "
			+ "UNION ALL SELECT today()+35	AS day,today()+35 AS usableDate "
			+ "UNION ALL SELECT today()+36	AS day,today()+36 AS usableDate "
			+ "UNION ALL SELECT today()+37	AS day,today()+37 AS usableDate "
			+ "UNION ALL SELECT today()+38	AS day,today()+38 AS usableDate "
			+ "UNION ALL SELECT today()+39	AS day,today()+39 AS usableDate "
			+ "UNION ALL SELECT today()+40	AS day,today()+40 AS usableDate "
			+ "UNION ALL SELECT today()+41	AS day,today()+41 AS usableDate "
			+ "UNION ALL SELECT today()+42	AS day,today()+42 AS usableDate "
			+ "UNION ALL SELECT today()+43	AS day,today()+43 AS usableDate "
			+ "UNION ALL SELECT today()+44	AS day,today()+44 AS usableDate "
			+ "UNION ALL SELECT today()+45	AS day,today()+45 AS usableDate "
			+ "UNION ALL SELECT today()+46	AS day,today()+46 AS usableDate "
			+ "UNION ALL SELECT today()+47	AS day,today()+47 AS usableDate "
			+ "UNION ALL SELECT today()+48	AS day,today()+48 AS usableDate "
			+ "UNION ALL SELECT today()+49	AS day,today()+49 AS usableDate "
			+ "UNION ALL SELECT today()+50	AS day,today()+50 AS usableDate "
			+ "UNION ALL SELECT today()+51	AS day,today()+51 AS usableDate "
			+ "UNION ALL SELECT today()+52	AS day,today()+52 AS usableDate "
			+ "UNION ALL SELECT today()+53	AS day,today()+53 AS usableDate "
			+ "UNION ALL SELECT today()+54	AS day,today()+54 AS usableDate "
			+ "UNION ALL SELECT today()+55	AS day,today()+55 AS usableDate "
			+ "UNION ALL SELECT today()+56	AS day,today()+56 AS usableDate "
			+ "UNION ALL SELECT today()+57	AS day,today()+57 AS usableDate "
			+ "UNION ALL SELECT today()+58	AS day,today()+58 AS usableDate "
			+ "UNION ALL SELECT today()+59	AS day,today()+59 AS usableDate "
			+ "UNION ALL SELECT today()+60	AS day,today()+60 AS usableDate "
			+ "UNION ALL SELECT today()+61	AS day,today()+61 AS usableDate "
			+ "UNION ALL SELECT today()+62	AS day,today()+62 AS usableDate "
			+ "UNION ALL SELECT today()+63	AS day,today()+63 AS usableDate "
			+ "UNION ALL SELECT today()+64	AS day,today()+64 AS usableDate "
			+ "UNION ALL SELECT today()+65	AS day,today()+65 AS usableDate "
			+ "UNION ALL SELECT today()+66	AS day,today()+66 AS usableDate "
			+ "UNION ALL SELECT today()+67	AS day,today()+67 AS usableDate "
			+ "UNION ALL SELECT today()+68	AS day,today()+68 AS usableDate "
			+ "UNION ALL SELECT today()+69	AS day,today()+69 AS usableDate "
			+ "UNION ALL SELECT today()+70	AS day,today()+70 AS usableDate "
			+ "UNION ALL SELECT today()+71	AS day,today()+71 AS usableDate "
			+ "UNION ALL SELECT today()+72	AS day,today()+72 AS usableDate "
			+ "UNION ALL SELECT today()+73	AS day,today()+73 AS usableDate "
			+ "UNION ALL SELECT today()+74	AS day,today()+74 AS usableDate "
			+ "UNION ALL SELECT today()+75	AS day,today()+75 AS usableDate "
			+ "UNION ALL SELECT today()+76	AS day,today()+76 AS usableDate "
			+ "UNION ALL SELECT today()+77	AS day,today()+77 AS usableDate "
			+ "UNION ALL SELECT today()+78	AS day,today()+78 AS usableDate "
			+ "UNION ALL SELECT today()+79	AS day,today()+79 AS usableDate "
			+ "UNION ALL SELECT today()+80	AS day,today()+80 AS usableDate "
			+ "UNION ALL SELECT today()+81	AS day,today()+81 AS usableDate "
			+ "UNION ALL SELECT today()+82	AS day,today()+82 AS usableDate "
			+ "UNION ALL SELECT today()+83	AS day,today()+83 AS usableDate "
			+ "UNION ALL SELECT today()+84	AS day,today()+84 AS usableDate "
			+ "UNION ALL SELECT today()+85	AS day,today()+85 AS usableDate "
			+ "UNION ALL SELECT today()+86	AS day,today()+86 AS usableDate "
			+ "UNION ALL SELECT today()+87	AS day,today()+87 AS usableDate "
			+ "UNION ALL SELECT today()+88	AS day,today()+88 AS usableDate "
			+ "UNION ALL SELECT today()+89	AS day,today()+89 AS usableDate "
			+ "UNION ALL SELECT today()+90	AS day,today()+90 AS usableDate ) AS usableDate "
			+ "WHERE DATEPART(WEEKDAY, day) IN (SELECT bdw_daygu+1 FROM doc_workplan "
			+ "WHERE 0=0 AND bdw_code=#{code} AND bdw_hosnum=(SELECT bhi_hosnum FROM hosinfo WHERE bhi_usechk='Y') GROUP BY bdw_daygu)) AS tempTable "
			+ "WHERE usableDate NOT IN (${except})  ORDER BY usabledate  "
			+ "]]>"
			+ "</script>")
	public List<WorkDateDataVO> selectWorkDate(String code, String except) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT COUNT(*) as cnt FROM doc_workplan "
			+ "WHERE 0=0 AND bdw_code=#{code} "
			+ "AND bdw_gubun='1' "
			+ "AND bdw_date<=#{reserveDate} "
			+ "AND bdw_daygu=(SELECT DATEPART(WEEKDAY, #{reserveDate})-1) "
			+ "AND bdw_hosnum=(SELECT bhi_hosnum FROM hosinfo WHERE bhi_usechk='Y')   "
			+ "]]>"
			+ "</script>")
	public int selectWorkPlanCnt(WorkPlanParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT #{reserveDate} AS reserveDate, bdw_time AS usableTime, (bdw_cnt-bdw_re_cnt-mrv_re_cnt) AS reserveUsableCount "
			+ "FROM (SELECT bdw_date, bdw_time, bdw_cnt, COUNT(rev_time) AS bdw_re_cnt, COUNT(mrv_time) AS mrv_re_cnt "
			+ "			FROM (SELECT WP.bdw_code, WP.bdw_date, WP.bdw_time, WP.bdw_cnt, RV.rev_time, MRV.mrv_time "
			+ "				FROM (SELECT bdw_code, bdw_date, bdw_time, bdw_cnt "
			+ "						FROM doc_workplan "
			+ "						WHERE 0=0 "
			+ " 					AND bdw_code=#{code} "
			+ "						AND bdw_daygu=(SELECT DATEPART(WEEKDAY, #{reserveDate})-1) "
			+ "						AND bdw_hosnum=(SELECT bhi_hosnum FROM hosinfo WHERE bhi_usechk='Y')"
			+ "						AND bdw_date = (SELECT MAX(bdw_date) FROM doc_workplan WHERE ((bdw_daygu=(SELECT DATEPART(WEEKDAY, #{reserveDate})-1) AND bdw_gubun='0' AND bdw_date<=#{reserveDate}) OR (bdw_date = #{reserveDate} AND bdw_gubun = '1')) AND bdw_code=#{code} AND bdw_hosnum=(SELECT bhi_hosnum FROM hosinfo WHERE bhi_usechk='Y')) "
			+ "						AND bdw_gubun = (SELECT MAX(bdw_gubun) FROM doc_workplan WHERE ((bdw_daygu=(SELECT DATEPART(WEEKDAY, #{reserveDate})-1) AND bdw_gubun='0' AND bdw_date<=#{reserveDate}) OR (bdw_date = #{reserveDate} AND bdw_gubun = '1')) AND bdw_code=#{code} AND bdw_hosnum=(SELECT bhi_hosnum FROM hosinfo WHERE bhi_usechk='Y')) "
			+ "						) AS WP "
			+ "						LEFT OUTER JOIN revpt AS RV "
			+ "						ON RV.rev_date=#{reserveDate} AND RV.rev_medroom=#{code} AND WP.bdw_time=RV.rev_time AND RV.rev_canid is null"
			+ "						LEFT OUTER JOIN (SELECT * FROM mob_revpt WHERE mrv_status=0) AS MRV "
			+ "						ON MRV.mrv_date=#{reserveDate} AND wp.bdw_time=MRV.mrv_time AND wp.bdw_code=MRV.mrv_medroom) AS TB "
			+ "						GROUP BY bdw_date, bdw_time, bdw_cnt) AS TB "
			+ "ORDER BY usableTime   "
			+ "]]>"
			+ "</script>")
	public List<WorkPlanDataVO> selectWorkPlan(WorkPlanParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "SELECT "
			+ "mrv_seq as jinryoReserveIdx,"
			+ "mrv_name as name,"
			+ "mrv_hpno as hpno,"
			+ "mrv_sex as sex,"
			+ "mrv_birth as birth,"
			+ "mrv_medroom as medroom,"
			+ "mrv_date as reserveDate,"
			+ "mrv_time as reserveTime,"
			+ "mrv_medroom_com as medroomCom,"
			+ "mrv_date_com as reserveDateCom,"
			+ "mrv_time_com as reserveTimeCom,"
			+ "mrv_cancel_memo as cancelMemo,"
			+ "mrv_status as status"
			+ " FROM mob_revpt "
			+ "WHERE 0=0 "
			+ "<if test=\"name != null and name != ''\">"
			+ "AND mrv_name=#{name} "
			+ "</if>"
			+ "<if test=\"hpno != null and hpno != ''\">"
			+ "AND mrv_hpno=#{hpno} "
			+ "</if>"
			+ "<if test=\"jinryoReserveIdx != null and jinryoReserveIdx != ''\">"
			+ "AND mrv_seq=#{jinryoReserveIdx} "
			+ "</if>"
			+ "</script>")
	public List<JinryoReserveDataVO> selectJinryoReserve(JinryoReserveParamVO vo) throws Exception;
	
	@Insert("<script>"
			+ "INSERT mob_revpt ("
			+ "mrv_seq,mrv_ptno,mrv_medroom,mrv_date,mrv_time,"
			+ "mrv_name,mrv_sex,mrv_birth,mrv_hpno,mrv_memo,"
			+ "mrv_email,mrv_company,mrv_kind,mrv_mngrno,mrv_status"
			+ ") VALUES ( "
			+ "#{jinryoReserveIdx},#{ptno},#{medroom},#{date},#{time}, "
			+ "#{name},#{sex},#{birth},#{hpno},#{memo},"
			+ "#{idno},#{company},#{kind},'1','0'"
			+ ")"
			+ "</script>")
	public void insertJinryoReserve(JinryoReserveParamVO vo) throws Exception;
	
	@Update("<script>"
			+ "UPDATE mob_revpt SET "
			+ "mrv_seq=mrv_seq "
			+ "<if test=\"name != null and name != ''\">"
			+ ", mrv_name=#{name} "
			+ "</if>"
			+ "<if test=\"hpno != null and hpno != ''\">"
			+ ", mrv_hpno=#{hpno} "
			+ "</if>"
			+ "<if test=\"medroom != null and medroom != ''\">"
			+ ", mrv_medroom=#{medroom} "
			+ "</if>"
			+ "<if test=\"sex != null and sex != ''\">"
			+ ", mrv_sex=#{sex} "
			+ "</if>"
			+ "<if test=\"birth != null and birth != ''\">"
			+ ", mrv_birth=#{birth} "
			+ "</if>"
			+ "<if test=\"date != null and date != ''\">"
			+ ", mrv_date=#{date} "
			+ "</if>"
			+ "<if test=\"time != null and time != ''\">"
			+ ", mrv_time=#{time} "
			+ "</if>"
			+ "<if test=\"memo != null and memo != ''\">"
			+ ", mrv_memo=#{memo} "
			+ "</if>"
			+ "<if test=\"idno != null and idno != ''\">"
			+ ", mrv_email=#{idno} "
			+ "</if>"
			+ "<if test=\"company != null and company != ''\">"
			+ ", mrv_company=#{company} "
			+ "</if>"
			+ ",mrv_kind = #{kind} "
			+ "WHERE 0=0  "
			+ "AND mrv_seq=#{jinryoReserveIdx} "
			+ "AND mrv_status='0' "
			+ "</script>")
	public void updateJinryoReserve(JinryoReserveParamVO vo) throws Exception;
	
	@Delete("<script>"
			+ "<![CDATA["
			+ "UPDATE mob_revpt SET mrv_status='2',"
			+ "mrv_cancel_date=DATEFORMAT(GETDATE(), 'yyyymmdd hh:mm:ss'),"
			+ "mrv_cancel_memo='사용자 취소' "
			+ "]]>"
			+ "WHERE 0=0  "
			+ "AND mrv_seq=#{jinryoReserveIdx} "
			+ "</script>")
	public void deleteJinryoReserve(JinryoReserveParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT TOP 1 bpt_ptno,bpt_hpno,bpt_jogubun "
			+ "FROM patient "
			+ "WHERE 0=0 "
			+ "]]>"
			+ "AND bpt_name = #{name} "
			+ "<if test=\"hpno != null and hpno != ''\">"
			+ "<![CDATA["
			+ "AND bpt_hpno <> '' "
			+ "]]>"
			+ "</if>"
			+ "ORDER BY bpt_enddt DESC "
			+ "</script>")
	public ManlessReceiptPatientVO selectPatient(JinryoReceiveParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT bpt_ptno, bpt_hpno "
			+ "FROM patient "
			+ "]]>"
			+ "WHERE 0=0 "
			+ "AND bpt_name=#{name} "
			+ "AND LEFT(bpt_resno, 6)=RIGHT(#{birth}, 6) "
			+ "</script>")
	public List<JinryoPatientDataVO> selectPtno(String name, String birth) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT bpt2_sinbundate "
			+ "FROM patient2 "
			+ "]]>"
			+ "WHERE 0=0 "
			+ "AND bpt2_ptno=#{ptno} "
			+ "</script>")
	public String selectInfoValidCheck(String ptno) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT onw_ch_date, (SELECT bmr_docname FROM medroom "
			+ "						WHERE bmr_code=onw_medroom AND bmr_stdate <= onw_ch_date AND bmr_use='Y' "
			+ "						AND bmr_hosnum=(SELECT bhi_hosnum FROM hosinfo WHERE bhi_usechk='Y')) AS docname, "
			+ "onw_presc_seq, (SELECT SUM(ppt_paykum) FROM payment WHERE ppt_regno=onw_regno) AS paykum FROM naewon "
			+ "]]>"
			+ "WHERE 0=0 AND (onw_kind Like '1%' or onw_kind Like '3%') "
			+ "AND onw_ptno IN (#{ptno}) "
			+ "AND onw_ch_date BETWEEN #{startDate} AND #{endDate} ORDER BY onw_ch_date DESC"
			+ "</script>")
	public List<JinryoDataVO> selectJinryo(JinryoParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT odt_edi_code,odt_edi_name,odt_qty,odt_div,odt_chto FROM chdt${indateLeft} "
			+ "]]>"
			+ "WHERE odt_pub_date = #{indate} AND odt_pub_seq = #{pubSeq} ORDER BY odt_ch_seq "
			
			+ "</script>")
	public List<JinryoDataVO> selectJinryoDetail(JinryoParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT TOP 1 * FROM medroom "
			+ "WHERE 0=0 AND bmr_use='Y' AND bmr_mobuse='Y' "
			+ "AND bmr_hosnum=(SELECT bhi_hosnum FROM hosinfo WHERE bhi_usechk='Y') AND bmr_code=#{medroom} "
			+ "ORDER BY bmr_stdate DESC "
			+ "]]>"
			+ "</script>")
	public ManlessReceiptMedroomVO selectMedrooms(JinryoReceiveParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT * FROM last "
			+ "]]>"
			+ "</script>")
	public ManlessReceiptLastVO selectLast() throws Exception;
	
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
	public void insertPatient(JinryoReceiveParamVO vo) throws Exception;
	
	@Insert("<script>"
			+ "<![CDATA["
			+ "INSERT INTO receipt_memo(rcm_ptno, rcm_memo) "
			+ "VALUES "
			+ "(#{ptno}, #{memo}"
			+ ") "
			+ "]]>"
			+ "</script>")
	public void insertMemo(JinryoReceiveParamVO vo) throws Exception;
	
	@Update("<script>"
			+ "UPDATE last SET rla_nptno=#{ptno}"
			+ "</script>")
	public void updateLastNptno(String ptno) throws Exception;
	
	@Update("<script>"
			+ "UPDATE last SET rla_mptno=#{ptno}"
			+ "</script>")
	public void updateLastMptno(String ptno) throws Exception;
	
	@Update("<script>"
			+ "UPDATE patient SET bpt_email=#{idno}"
			+ "WHERE bpt_ptno=#{ptno}"
			+ "</script>")
	public void updatePatientIdno(JinryoReceiveParamVO vo) throws Exception;
	
	@Insert("<script>"
			+ "<![CDATA["
			+ "INSERT INTO reg(rrg_reg_date,rrg_ptno,rrg_medroom,rrg_kwa,rrg_kind,rrg_class,rrg_qua_seq ) "
			+ "VALUES "
			+ "(DATEFORMAT(GETDATE(), 'yyyymmdd'), #{ptno}, #{medroom}, #{sKwa}, 'ON','10', '1'"
			+ ")  "
			+ "]]>"
			+ "</script>")
	public void insertReg(JinryoReceiveParamVO vo) throws Exception;
	
	@Insert("<script>"
			+ "INSERT INTO waitjin ("
			+ "rwj_regno,rwj_reg_date,rwj_ptno,rwj_medroom,rwj_kwa,"
			+ "rwj_name,rwj_resno, rwj_yage,rwj_mage,rwj_sex,"
			+ "rwj_jin_gubun,rwj_jin_kind,rwj_jin_time, rwj_wait_class,rwj_wait_sclass,"
			
			+ "rwj_other_room,rwj_jogubun,rwj_qua_seq, rwj_salecd,rwj_salerate,"
			+ "rwj_excp,rwj_jin_ref,rwj_reserve_memo, rwj_findchart,rwj_mngrno,"
			+ "rwj_flag, rwj_nextchoday, rwj_nanchicode, rwj_pregnant, rwj_nosound, "
			+ "rwj_tmp3, rwj_tmp5, rwj_tmp6"
			+ ") "
			+ "VALUES "
			+ "( "
			+ "#{sRegCnt}, DATEFORMAT(GETDATE(), 'yyyymmdd'), #{ptno}, #{medroom}, #{sKwa}, "
			+ "#{name}, #{jumin1}, #{sYAge}, #{sMAge}, #{sex},"
			+ "#{gubun},#{jin_kind},'1', 'M','10', "
			+ "'', #{sJogubun}, '1', '', '', "
			+ "'', #{memo}, '', '', '1', "
			+ "'', '', '', '','',"
			+ "#{tmp3}, #{jinryoReceiveIdx}, #{company})  "
			+ "</script>")
	public void insertWaitjin(JinryoReceiveParamVO vo) throws Exception;
	
	@Update("<script>"
			+ "UPDATE waitjin SET "
			+ "rwj_tmp5=rwj_tmp5"
			+ "<if test=\"medroom != null and medroom != ''\">"
			+ ",rwj_medroom=#{medroom}  "
			+ "</if>"
			+ ",rwj_jin_gubun=#{gubun} "
			+ "WHERE 0=0 "
			+ "AND rwj_tmp5=#{jinryoReceiveIdx}"
			+ "</script>")
	public void updateWaitjin(JinryoReceiveParamVO vo) throws Exception;
	
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
	public String selectRegno2(JinryoReceiveParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT TOP 1 bpt_ptno FROM patient "
			+ "WHERE 0=0 "
			+ "AND bpt_enddt <> '' "
			+ "AND bpt_enddt is not null "
			+ "AND bpt_name=#{name} AND bpt_sex=#{sex} "
			+ "AND bpt_birth=#{birthday} "
			+ "AND (bpt_hpno = #{phone} "
			+ "		OR bpt_hpno = #{phone1} "
			+ "		OR replacestr(bpt_hpno, ' ', '') = #{phone1} "
			+ "		OR replacestr(bpt_hpno, '/', '') = #{phone1} "
			+ "		OR replacestr(bpt_hpno, '.', '') = #{phone1}) "
			+ "ORDER BY bpt_enddt DESC"
			+ "]]>"
			+ "</script>")
	public String selectPtnos(ReservePhysicalParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "select "
			+ "(CNT - ( SELECT COUNT(*)  "
			+ "			FROM mob_revpt WHERE mrv_status=0 AND mrv_name=#{name} "
			+ "			AND mrv_sex=#{sex} AND (mrv_hpno = #{phone} OR mrv_hpno = #{phone1})) ) AS cnt "
			+ "FROM ( select isNull(sum(jph_cnt - jph_act), 0) as cnt "
			+ "			from pthistory "
			+ "			where #{date} between jph_ch_date and jph_expire and jph_ptno=#{ptno}  and  jph_cnt > jph_act  and jph_cnt>0) AS temp"
			+ "]]>"
			+ "</script>")
	public List<ReservePhysicalDataVO> selectPhysicalCnt(ReservePhysicalParamVO vo) throws Exception;
}
