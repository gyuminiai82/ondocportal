package kr.ondoc.mapper.sybase.crm;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import kr.ondoc.domain.sybase.crm.CrmAgeReserveVO;
import kr.ondoc.domain.sybase.crm.CrmAgeSalesVO;
import kr.ondoc.domain.sybase.crm.CrmGroupItemPaymentVO;
import kr.ondoc.domain.sybase.crm.CrmGroupPaymentVO;
import kr.ondoc.domain.sybase.crm.CrmPathReserveVO;
import kr.ondoc.domain.sybase.crm.CrmPathSalesVO;
import kr.ondoc.domain.sybase.crm.CrmStatisticsParamVO;
import kr.ondoc.domain.sybase.crm.CrmStatisticsPeriodPaymentTotalVO;
import kr.ondoc.domain.sybase.crm.CrmStatisticsPeriodPaymentVO;
import kr.ondoc.domain.sybase.crm.CrmStatisticsProgressVO;
import kr.ondoc.domain.sybase.crm.CrmStatisticsRegionVO;
import kr.ondoc.domain.sybase.crm.CrmTimeReserveVO;
import kr.ondoc.domain.sybase.crm.CrmTimeSalesVO;
import kr.ondoc.domain.sybase.crm.CrmTreatementPaymentDetailVO;
import kr.ondoc.domain.sybase.crm.CrmWeekdayReserveVO;
import kr.ondoc.domain.sybase.crm.CrmWeekdaySalesVO;

public interface CrmStatisticsMapper {
	@Select("<script>"
			+ "<![CDATA["
			+ "select addr from ("
			+ ""
			+ "select convert(varchar(255),REPLACE(REPLACE(REPLACE(REPLACE(bpt_addr, '서울 ', '서울특별시'), '인천 ', '인천광역시'), '인천시', '인천광역시'), '경기 ', '경기도')) AS addr, "
			+ "bpt_addr, * FROM patient "
			+ "WHERE bpt_addr <> ''"
			+ ") as t group by addr  "
			+ "]]>"
			+ "</script>")
	public List<CrmStatisticsRegionVO> selectRegionList() throws Exception;
	
	
	//매출 통계(상세)
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT * FROM crm_treatement_payment ctp "
			+ "	LEFT OUTER JOIN patient AS pa "
			+ "	ON pa.bpt_ptno=ctp.ptno"
			+ "	WHERE 0=0 AND del_yn='N'"
			+ "]]>"
			+ "<if test=\"startDate != null and startDate != '' and endDate != null and endDate != ''\">"
			+ "	AND payment_date BETWEEN #{startDate} AND #{endDate}"
			+ "</if>"
			+ "<if test=\"sex != null and sex != ''\">"
			+ "	AND pa.bpt_sex = #{sex} "
			+ "</if>"
			+ "<if test=\"ptno != null and ptno != ''\">"
			+ "AND pa.bpt_ptno like '%${ptno}%' "
			+ "</if>"
			+ "<if test=\"name != null and name != ''\">"
			+ "AND pa.bpt_name like '%${name}%' "
			+ "</if>"
			+ "ORDER BY payment_date DESC, write_date DESC"
			+ "</script>")
	public List<CrmStatisticsPeriodPaymentVO> selectPeriodPaymentList(CrmStatisticsParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT * FROM crm_treatement_payment_detail WHERE 0=0 "
			+ "]]>"
			+ "<if test=\"ptno != null and ptno != ''\">"
			+ "AND ptno = #{ptno} "
			+ "</if>"
			+ "<if test=\"payment_idx != null and payment_idx != ''\">"
			+ "AND payment_idx = #{payment_idx} "
			+ "</if>"
			+ "<if test=\"del_yn != null and del_yn != ''\">"
			+ "AND del_yn = #{del_yn} "
			+ "</if>"
			+ "ORDER BY payment_date DESC, payment_detail_idx DESC"
			+ "</script>")
	public List<CrmTreatementPaymentDetailVO> selectListPaymentDetail(CrmTreatementPaymentDetailVO vo) throws Exception;
	
	//매출 통계(일별)
	@Select("<script>"
			+ "SELECT payment_date, COUNT(*) AS COUNT, "
			+ "	SUM(charge_price) AS charge_price, SUM(sale_price) AS sale_price,"
			+ "	SUM(sale_after_price) AS sale_after_price, SUM(payment_price) AS payment_price,"
			+ "	SUM(unpaid_price) AS unpaid_price, SUM(card_price) AS card_price,"
			+ "	SUM(cash_price) AS cash_price, SUM(cash_transfer_price) AS cash_transfer_price "
			+ "FROM ("
			+ "	SELECT * FROM crm_treatement_payment ctp 	"
			+ "	LEFT OUTER JOIN patient AS pa 	"
			+ "	ON pa.bpt_ptno=ctp.ptno	WHERE 0=0 AND del_yn='N' "
			+ "<if test=\"startDate != null and startDate != '' and endDate != null and endDate != ''\">"
			+ "	AND payment_date BETWEEN #{startDate} AND #{endDate}	"
			+ "</if>"
			+ "<if test=\"sex != null and sex != ''\">"
			+ "	AND pa.bpt_sex = #{sex}"
			+ "</if>"
			+ ") AS temp "
			+ "GROUP BY payment_date "
			+ "ORDER BY payment_date DESC"
			+ "</script>")
	public List<CrmStatisticsPeriodPaymentVO> selectPeriodPaymentDayList(CrmStatisticsParamVO vo) throws Exception;
	
	//매출 통계(월별)
	@Select("<script>"
			+ "SELECT LEFT(payment_date, 6) AS payment_date, COUNT(*) AS COUNT, "
			+ "	SUM(charge_price) AS charge_price, SUM(sale_price) AS sale_price,"
			+ "	SUM(sale_after_price) AS sale_after_price, SUM(payment_price) AS payment_price,"
			+ "	SUM(unpaid_price) AS unpaid_price, SUM(card_price) AS card_price,"
			+ "	SUM(cash_price) AS cash_price, SUM(cash_transfer_price) AS cash_transfer_price "
			+ "FROM ("
			+ "	SELECT * FROM crm_treatement_payment ctp 	"
			+ "	LEFT OUTER JOIN patient AS pa 	"
			+ "	ON pa.bpt_ptno=ctp.ptno	WHERE 0=0 AND del_yn='N' "
			+ "<if test=\"startDate != null and startDate != '' and endDate != null and endDate != ''\">"
			+ "	AND payment_date BETWEEN #{startDate} AND #{endDate}	"
			+ "</if>"
			+ "<if test=\"sex != null and sex != ''\">"
			+ "	AND pa.bpt_sex = #{sex}"
			+ "</if>"
			+ ") AS temp "
			+ "GROUP BY payment_date "
			+ "ORDER BY payment_date DESC"
			+ "</script>")
	public List<CrmStatisticsPeriodPaymentVO> selectPeriodPaymentMonthList(CrmStatisticsParamVO vo) throws Exception;
	
	//매출 통계(상세 합계)
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT"
			+ "		COUNT(*) AS count,"
			+ "		SUM(charge_price) AS charge_price, SUM(sale_price) AS sale_price,"
			+ "		SUM(sale_after_price) AS sale_after_price, SUM(payment_price) AS payment_price,"
			+ "		SUM(unpaid_price) AS unpaid_price, SUM(card_price) AS card_price, SUM(cash_price) AS cash_price, "
			+ "		SUM(cash_transfer_price) AS cash_transfer_price"
			+ "	FROM crm_treatement_payment AS ctp"
			+ "	LEFT OUTER JOIN patient AS pa "
			+ "	ON pa.bpt_ptno=ctp.ptno"
			+ "	WHERE 0=0 "
			+ "	AND del_yn='N'  "
			+ "]]>"
			+ "<if test=\"startDate != null and startDate != '' and endDate != null and endDate != ''\">"
			+ "	AND payment_date BETWEEN #{startDate} AND #{endDate}"
			+ "</if>"
			+ "<if test=\"sex != null and sex != ''\">"
			+ "	AND pa.bpt_sex = #{sex} "
			+ "</if>"
			+ "<if test=\"ptno != null and ptno != ''\">"
			+ "AND pa.bpt_ptno like '%${ptno}%' "
			+ "</if>"
			+ "<if test=\"name != null and name != ''\">"
			+ "AND pa.bpt_name like '%${name}%' "
			+ "</if>"
			+ "</script>")
	public CrmStatisticsPeriodPaymentTotalVO selectPeriodPaymentTotalList(CrmStatisticsParamVO vo) throws Exception;
	
	//예약통계-내원경로(예약건수)
	@Select("<script>"
			+ "SELECT bpt_channel, COUNT(*) AS count, status_idx  FROM ("
			+ "SELECT ptno, del_yn, schedule_date, "
			+ "CASE WHEN status_idx > 1"
			+ "			THEN 2"
			+ "			ELSE status_idx END AS status_idx FROM crm_operation_schedule"
			+ "			) ctp 	"
			+ "			LEFT OUTER JOIN patient AS pa ON pa.bpt_ptno=ctp.ptno	WHERE 0=0 	"
			+ "			AND del_yn='N' "
			+ "<if test=\"startDate != null and startDate != '' and endDate != null and endDate != ''\">"
			+ "	AND schedule_date BETWEEN #{startDate} AND #{endDate}	"
			+ "</if>"
			+ "<if test=\"sex != null and sex != ''\">"
			+ "	AND pa.bpt_sex = #{sex}"
			+ "</if>"
			+ "GROUP BY bpt_channel, status_idx "
			+ "</script>")
	public List<CrmPathReserveVO> selectPathReserveList(CrmStatisticsParamVO vo) throws Exception;
	
	
	//예약통계-내원경로(매출)
	@Select("<script>"
			+ "SELECT bpt_channel, COUNT(*) AS count, SUM(charge_price) AS charge_price, "
			+ "SUM(sale_after_price) AS sale_after_price, sale_after_price/count AS cnt_price "
			+ "FROM crm_treatement_payment ctp 	"
			+ "LEFT OUTER JOIN patient AS pa 	ON pa.bpt_ptno=ctp.ptno	WHERE 0=0 	"
			+ "AND del_yn='N' "
			+ "<if test=\"startDate != null and startDate != '' and endDate != null and endDate != ''\">"
			+ "	AND payment_date BETWEEN #{startDate} AND #{endDate}	"
			+ "</if>"
			+ "<if test=\"sex != null and sex != ''\">"
			+ "	AND pa.bpt_sex = #{sex} "
			+ "</if>"
			+ "GROUP BY bpt_channel "
			+ "</script>")
	public List<CrmPathSalesVO> selectPathSalesList(CrmStatisticsParamVO vo) throws Exception;
	
	//예약통계-요일별(예약건수)
	@Select("<script>"
			+ "SELECT DATEPART(weekday, schedule_date) AS weekday, COUNT(*) AS count, status_idx  FROM ("
			+ "SELECT ptno, del_yn, schedule_date, "
			+ "CASE WHEN status_idx > 1"
			+ "			THEN 2"
			+ "			ELSE status_idx END AS status_idx FROM crm_operation_schedule"
			+ "			) ctp 	"
			+ "			LEFT OUTER JOIN patient AS pa ON pa.bpt_ptno=ctp.ptno	WHERE 0=0 	"
			+ "			AND del_yn='N' "
			+ "<if test=\"startDate != null and startDate != '' and endDate != null and endDate != ''\">"
			+ "	AND schedule_date BETWEEN #{startDate} AND #{endDate}	"
			+ "</if>"
			+ "<if test=\"sex != null and sex != ''\">"
			+ "	AND pa.bpt_sex = #{sex}"
			+ "</if>"
			+ "GROUP BY weekday, status_idx "
			+ "</script>")
	public List<CrmWeekdayReserveVO> selectWeekdayReserveList(CrmStatisticsParamVO vo) throws Exception;
	
	
	//예약통계-요일별(매출)
	@Select("<script>"
			+ "SELECT DATEPART(weekday, payment_date) AS weekday, COUNT(*) AS count, SUM(charge_price) AS charge_price, "
			+ "SUM(sale_after_price) AS sale_after_price, sale_after_price/count AS cnt_price "
			+ "FROM crm_treatement_payment ctp 	"
			+ "LEFT OUTER JOIN patient AS pa 	ON pa.bpt_ptno=ctp.ptno	WHERE 0=0 	"
			+ "AND del_yn='N' "
			+ "<if test=\"startDate != null and startDate != '' and endDate != null and endDate != ''\">"
			+ "	AND payment_date BETWEEN #{startDate} AND #{endDate}	"
			+ "</if>"
			+ "<if test=\"sex != null and sex != ''\">"
			+ "	AND pa.bpt_sex = #{sex} "
			+ "</if>"
			+ "GROUP BY weekday "
			+ "</script>")
	public List<CrmWeekdaySalesVO> selectWeekdaySalesList(CrmStatisticsParamVO vo) throws Exception;
	
	//예약통계-시간대별(예약건수)
	@Select("<script>"
			+ "SELECT LEFT(schedule_time, 2) + '00' AS 'time', COUNT(*) AS count, status_idx  FROM ("
			+ "SELECT ptno, del_yn, schedule_date, schedule_time, "
			+ "CASE WHEN status_idx > 1"
			+ "			THEN 2"
			+ "			ELSE status_idx END AS status_idx FROM crm_operation_schedule"
			+ "			) ctp 	"
			+ "			LEFT OUTER JOIN patient AS pa ON pa.bpt_ptno=ctp.ptno	WHERE 0=0 	"
			+ "			AND del_yn='N' "
			+ "<if test=\"startDate != null and startDate != '' and endDate != null and endDate != ''\">"
			+ "	AND schedule_date BETWEEN #{startDate} AND #{endDate}	"
			+ "</if>"
			+ "<if test=\"sex != null and sex != ''\">"
			+ "	AND pa.bpt_sex = #{sex}"
			+ "</if>"
			+ "GROUP BY LEFT(schedule_time, 2), status_idx "
			+ "ORDER BY LEFT(schedule_time, 2) "
			+ "</script>")
	public List<CrmTimeReserveVO> selectTimeReserveList(CrmStatisticsParamVO vo) throws Exception;
	
	//예약통계-시간대별(매출)
	@Select("<script>"
			+ "SELECT (LEFT(RIGHT(write_date, 4), 2) + '00') AS 'time', COUNT(*) AS count, SUM(charge_price) AS charge_price, "
			+ "SUM(sale_after_price) AS sale_after_price, sale_after_price/count AS cnt_price "
			+ "FROM crm_treatement_payment ctp 	"
			+ "LEFT OUTER JOIN patient AS pa 	ON pa.bpt_ptno=ctp.ptno	WHERE 0=0 	"
			+ "AND del_yn='N' "
			+ "<if test=\"startDate != null and startDate != '' and endDate != null and endDate != ''\">"
			+ "	AND payment_date BETWEEN #{startDate} AND #{endDate}	"
			+ "</if>"
			+ "<if test=\"sex != null and sex != ''\">"
			+ "	AND pa.bpt_sex = #{sex} "
			+ "</if>"
			+ "GROUP BY LEFT(RIGHT(write_date, 4), 2) "
			+ "ORDER BY LEFT(RIGHT(write_date, 4), 2) "
			+ "</script>")
	public List<CrmTimeSalesVO> selectTimeSalesList(CrmStatisticsParamVO vo) throws Exception;
	
	//예약통계-연령대별(예약건수)
		@Select("<script>"
				+ "SELECT CONVERT(varchar(10), (FLOOR(bpt_yage/10) * 10)) AS age_group, COUNT(*) AS count, status_idx  FROM ("
				+ "SELECT ptno, del_yn, schedule_date, schedule_time, "
				+ "CASE WHEN status_idx > 1"
				+ "			THEN 2"
				+ "			ELSE status_idx END AS status_idx FROM crm_operation_schedule"
				+ "			) ctp 	"
				+ "			LEFT OUTER JOIN patient AS pa ON pa.bpt_ptno=ctp.ptno	WHERE 0=0 	"
				+ "			AND del_yn='N' "
				+ "<if test=\"startDate != null and startDate != '' and endDate != null and endDate != ''\">"
				+ "	AND schedule_date BETWEEN #{startDate} AND #{endDate}	"
				+ "</if>"
				+ "<if test=\"sex != null and sex != ''\">"
				+ "	AND pa.bpt_sex = #{sex}"
				+ "</if>"
				+ "GROUP BY FLOOR(bpt_yage/10) * 10, status_idx  "
				+ "ORDER BY FLOOR(bpt_yage/10) * 10 "
				+ "</script>")
		public List<CrmAgeReserveVO> selectAgeReserveList(CrmStatisticsParamVO vo) throws Exception;
		
		//예약통계-연령대별(매출)
		@Select("<script>"
				+ "SELECT CONVERT(varchar(10), (FLOOR(bpt_yage/10) * 10 )) AS age_group, COUNT(*) AS count, SUM(charge_price) AS charge_price, "
				+ "SUM(sale_after_price) AS sale_after_price, sale_after_price/count AS cnt_price "
				+ "FROM crm_treatement_payment ctp 	"
				+ "LEFT OUTER JOIN patient AS pa 	ON pa.bpt_ptno=ctp.ptno	WHERE 0=0 	"
				+ "AND del_yn='N' "
				+ "<if test=\"startDate != null and startDate != '' and endDate != null and endDate != ''\">"
				+ "	AND payment_date BETWEEN #{startDate} AND #{endDate}	"
				+ "</if>"
				+ "<if test=\"sex != null and sex != ''\">"
				+ "	AND pa.bpt_sex = #{sex} "
				+ "</if>"
				+ "GROUP BY FLOOR(bpt_yage/10) * 10  "
				+ "ORDER BY FLOOR(bpt_yage/10) * 10 "
				+ "</script>")
		public List<CrmAgeSalesVO> selectAgeSalesList(CrmStatisticsParamVO vo) throws Exception;
	
	//담당의사별 통계
	@Select("<script>"
			+ "SELECT ISNULL(doctor_id, '') AS id, COUNT(*) AS COUNT, SUM(charge_price) AS charge_price, "
			+ "SUM(sale_price) AS sale_price, SUM(sale_after_price) AS sale_after_price "
			+ "FROM crm_treatement_payment_detail AS ctp LEFT OUTER JOIN patient AS pa ON pa.bpt_ptno=ctp.ptno	 "
			+ "WHERE 0=0 "
			+ "AND del_yn='N' "
			+ "<if test=\"startDate != null and startDate != '' and endDate != null and endDate != ''\">"
			+ "	AND payment_date BETWEEN #{startDate} AND #{endDate}	"
			+ "</if>"
			+ "<if test=\"sex != null and sex != ''\">"
			+ "	AND pa.bpt_sex = #{sex} "
			+ "</if>"
			+ "GROUP BY ISNULL(doctor_id, '') 	"
			+ "ORDER BY id "
			+ "</script>")
	public List<CrmGroupPaymentVO> selectDoctorList(CrmStatisticsParamVO vo) throws Exception;
	
	//상담사별 통계
	@Select("<script>"
			+ "SELECT ISNULL(counselor_id, '') AS id, COUNT(*) AS COUNT, SUM(charge_price) AS charge_price, "
			+ "SUM(sale_price) AS sale_price, SUM(sale_after_price) AS sale_after_price "
			+ "FROM crm_treatement_payment_detail AS ctp LEFT OUTER JOIN patient AS pa ON pa.bpt_ptno=ctp.ptno	 "
			+ "WHERE 0=0 "
			+ "AND del_yn='N' "
			+ "<if test=\"startDate != null and startDate != '' and endDate != null and endDate != ''\">"
			+ "	AND payment_date BETWEEN #{startDate} AND #{endDate}	"
			+ "</if>"
			+ "<if test=\"sex != null and sex != ''\">"
			+ "	AND pa.bpt_sex = #{sex} "
			+ "</if>"
			+ "GROUP BY ISNULL(counselor_id, '') "
			+ "ORDER BY id "
			+ "</script>")
	public List<CrmGroupPaymentVO> selectCounselorList(CrmStatisticsParamVO vo) throws Exception;
	
	//치료사별 통계
	@Select("<script>"
			+ "SELECT ISNULL(therapist_id, '') AS id, COUNT(*) AS COUNT, SUM(charge_price) AS charge_price, "
			+ "SUM(sale_price) AS sale_price, SUM(sale_after_price) AS sale_after_price "
			+ "FROM crm_treatement_payment_detail AS ctp LEFT OUTER JOIN patient AS pa ON pa.bpt_ptno=ctp.ptno	 "
			+ "WHERE 0=0 "
			+ "AND del_yn='N' "
			+ "<if test=\"startDate != null and startDate != '' and endDate != null and endDate != ''\">"
			+ "	AND payment_date BETWEEN #{startDate} AND #{endDate}	"
			+ "</if>"
			+ "<if test=\"sex != null and sex != ''\">"
			+ "	AND pa.bpt_sex = #{sex} "
			+ "</if>"
			+ "GROUP BY ISNULL(therapist_id, '') "
			+ "ORDER BY id "
			+ "</script>")
	public List<CrmGroupPaymentVO> selectTherapistList(CrmStatisticsParamVO vo) throws Exception;
	
	//연령대별 통계
	@Select("<script>"
			+ "SELECT FLOOR(bpt_yage/10) * 10 AS id, COUNT(*) AS COUNT, SUM(charge_price * item_count) AS charge_price, "
			+ "SUM(sale_price * item_count) AS sale_price, SUM(sale_after_price * item_count) AS sale_after_price "
			+ "FROM crm_treatement_payment_detail AS ctp LEFT OUTER JOIN patient AS pa ON pa.bpt_ptno=ctp.ptno	 "
			+ "WHERE 0=0 "
			+ "AND del_yn='N' "
			+ "<if test=\"startDate != null and startDate != '' and endDate != null and endDate != ''\">"
			+ "	AND payment_date BETWEEN #{startDate} AND #{endDate}	"
			+ "</if>"
			+ "<if test=\"sex != null and sex != ''\">"
			+ "	AND pa.bpt_sex = #{sex} "
			+ "</if>"
			+ "GROUP BY FLOOR(bpt_yage/10) * 10 "
			+ "ORDER BY id "
			+ "</script>")
	public List<CrmGroupPaymentVO> selectAgeList(CrmStatisticsParamVO vo) throws Exception;
	
	//진료항목별 통계
	@Select("<script>"
			+ "SELECT item_name, item_idx, COUNT(*) AS COUNT, SUM(charge_price * item_count) AS charge_price, "
			+ "SUM(sale_price * item_count) AS sale_price, SUM(sale_after_price * item_count) AS sale_after_price "
			+ "FROM crm_treatement_payment_detail AS ctp LEFT OUTER JOIN patient AS pa ON pa.bpt_ptno=ctp.ptno	 "
			+ "WHERE 0=0 "
			+ "AND del_yn='N' "
			+ "<if test=\"startDate != null and startDate != '' and endDate != null and endDate != ''\">"
			+ "	AND payment_date BETWEEN #{startDate} AND #{endDate}	"
			+ "</if>"
			+ "<if test=\"sex != null and sex != ''\">"
			+ "	AND pa.bpt_sex = #{sex} "
			+ "</if>"
			+ "GROUP BY item_name, item_idx	"
			+ "ORDER BY item_idx "
			+ "</script>")
	public List<CrmGroupItemPaymentVO> selectItemList(CrmStatisticsParamVO vo) throws Exception;
	
	//담당의사/상담자별 통계 내 진료항목 목록
	@Select("<script>"
			+ "SELECT * FROM crm_treatement_payment_detail AS ctp LEFT OUTER JOIN patient AS pa ON pa.bpt_ptno=ctp.ptno "
			+ "WHERE 0=0 "
			+ "AND del_yn='N' "
			+ "<if test=\"startDate != null and startDate != '' and endDate != null and endDate != ''\">"
			+ "	AND payment_date BETWEEN #{startDate} AND #{endDate}	"
			+ "</if>"
			+ "<if test=\"gubun == 'doctor'\">"
			+ "	AND doctor_id = #{doctor_id} "
			+ "</if>"
			+ "<if test=\"gubun == 'counselor'\">"
			+ "	AND counselor_id = #{counselor_id} "
			+ "</if>"
			+ "<if test=\"gubun == 'therapist'\">"
			+ "	AND therapist_id = #{therapist_id} "
			+ "</if>"
			+ "<if test=\"gubun == 'age'\">"
			+ "	AND (FLOOR(bpt_yage/10) * 10) = #{age} "
			+ "</if>"
			+ "<if test=\"gubun == 'item'\">"
			+ "	AND item_name = #{item_name} "
			+ "</if>"
			+ "<if test=\"sex != null and sex != ''\">"
			+ "	AND pa.bpt_sex = #{sex} "
			+ "</if>"
			+ "ORDER BY write_date DESC, item_idx "
			+ "</script>")
	public List<CrmTreatementPaymentDetailVO> selectItemSearchList(CrmStatisticsParamVO vo) throws Exception;
	
	
	@Select("<script>"
			+ "SELECT item_name, item_name_detail, item_idx, item_detail_idx, COUNT(*) AS COUNT, SUM(charge_price) AS charge_price, "
			+ "SUM(sale_price) AS sale_price, SUM(sale_after_price) AS sale_after_price "
			+ "FROM crm_treatement_payment_detail AS ctp LEFT OUTER JOIN patient AS pa ON pa.bpt_ptno=ctp.ptno	 "
			+ "WHERE 0=0 "
			+ "AND del_yn='N' "
			+ "<if test=\"startDate != null and startDate != '' and endDate != null and endDate != ''\">"
			+ "	AND payment_date BETWEEN #{startDate} AND #{endDate}	"
			+ "</if>"
			+ "<if test=\"sex != null and sex != ''\">"
			+ "	AND pa.bpt_sex = #{sex} "
			+ "</if>"
			+ "GROUP BY item_name, item_name_detail, item_idx, item_detail_idx	"
			+ "ORDER BY item_idx, item_detail_idx "
			+ "</script>")
	public List<CrmGroupItemPaymentVO> selectItemDetailList(CrmStatisticsParamVO vo) throws Exception;

	//담당의사 매출 추이
	@Select("<script>"
			+ "SELECT LEFT(payment_date, 6) AS month, SUM(sale_after_price) AS sale_after_price FROM crm_treatement_payment_detail "
			+ "WHERE 0=0 "
			+ "<if test=\"name != null and name != ''\">"
			+ "AND item_name = #{name}"
			+ "</if>"
			+ "AND doctor_id = #{doctor_id}"
			+ "AND del_yn = 'N' "
			+ "AND LEFT(payment_date, 6) >= LEFT(convert(char,dateadd(month, -6, convert(char,GETDATE(),112)), 112), 6) "
			+ "GROUP BY LEFT(payment_date, 6)"
			+ "</script>")
	public List<CrmStatisticsProgressVO> selectListDoctorProgress(CrmStatisticsParamVO vo) throws Exception;
	
	//상담사 매출 추이
	@Select("<script>"
			+ "SELECT LEFT(payment_date, 6) AS month, SUM(sale_after_price) AS sale_after_price FROM crm_treatement_payment_detail "
			+ "WHERE 0=0 "
			+ "<if test=\"name != null and name != ''\">"
			+ "AND item_name = #{name}"
			+ "</if>"
			+ "AND counselor_id = #{counselor_id}"
			+ "AND del_yn = 'N' "
			+ "AND LEFT(payment_date, 6) >= LEFT(convert(char,dateadd(month, -6, convert(char,GETDATE(),112)), 112), 6) "
			+ "GROUP BY LEFT(payment_date, 6)"
			+ "</script>")
	public List<CrmStatisticsProgressVO> selectListCounselorProgress(CrmStatisticsParamVO vo) throws Exception;
	
	//치료사 매출 추이
	@Select("<script>"
			+ "SELECT LEFT(payment_date, 6) AS month, SUM(sale_after_price) AS sale_after_price FROM crm_treatement_payment_detail "
			+ "WHERE 0=0 "
			+ "<if test=\"name != null and name != ''\">"
			+ "AND item_name = #{name}"
			+ "</if>"
			+ "AND therapist_id = #{therapist_id}"
			+ "AND del_yn = 'N' "
			+ "AND LEFT(payment_date, 6) >= LEFT(convert(char,dateadd(month, -6, convert(char,GETDATE(),112)), 112), 6) "
			+ "GROUP BY LEFT(payment_date, 6)"
			+ "</script>")
	public List<CrmStatisticsProgressVO> selectListTherapistProgress(CrmStatisticsParamVO vo) throws Exception;
}
