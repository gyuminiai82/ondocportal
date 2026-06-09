package kr.ondoc.mapper.sybase.crm;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.ondoc.domain.sybase.crm.CrmPatientVO;
import kr.ondoc.domain.sybase.crm.CrmRevptNaverVO;

public interface CrmRevptNaverMapper {
	/*OndocCrm에서 사용*/
	@Select("<script>"
			+ "SELECT * from patient "
			+ "WHERE 0=0 "
			+ "<if test=\"phoneFront != '' and phoneEnd != ''\">"
			+ "AND bpt_hpno like '${num}%' AND (bpt_hpno like '%${phoneFront}____' OR bpt_hpno like '%${phoneFront}_____') AND bpt_hpno like '%${phoneEnd}' "
			+ "</if>"
			+ "</script>")
	public List<CrmPatientVO> selectPatient(String num, String phoneFront, String phoneEnd) throws Exception;
	
	/*OnlineScheduleRestClient의 스케줄러에 의해 사용중*/
	//닥톡에 주기적으로 요청하여 예약정보 있으면 가져옴
	@Select("<script>"
			+ "SELECT Top 1 bpt_ptno from patient "
			+ "WHERE 0=0 "
			+ "AND bpt_name = #{name} "
			+ "<if test=\"phoneFront != '' and phoneEnd != ''\">"
			+ "AND bpt_hpno like '010%' AND (bpt_hpno like '%${phoneFront}____' OR bpt_hpno like '%${phoneFront}_____') AND bpt_hpno like '%${phoneEnd}' "
			+ "</if>"
			+ "</script>")
	public String selectPtno(String name, String phoneFront, String phoneEnd) throws Exception;
	
	@Select("<script>"
			+ "SELECT * from revpt_naver "
			+ "WHERE 0=0 "
			+ "AND rev_state = 'BS_WAITING' "
			+ "AND rev_confirm = 'Y' "
			+ "</script>")
	public List<CrmRevptNaverVO> selectCheckRevptNaver() throws Exception;

	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT rev_seq from revpt_naver WHERE 0=0 "
			+ "AND rev_bookingkey = #{rev_bookingkey} "
			+ "]]>"
			+ "</script>")
	public String selectManlessQuestionSeq(String rev_bookingkey) throws Exception;
	
	@Select("<script>"
			+ "SELECT * from revpt_naver "
			+ "WHERE 0=0 "
			+ "AND rev_bookingkey = #{rev_bookingkey}"
			+ "</script>")
	public CrmRevptNaverVO selectRevptNaver(CrmRevptNaverVO vo) throws Exception;
	
	@Insert("<script>"
			+ "INSERT INTO revpt_naver ("
			+ "rev_reservationkey, rev_bookingkey, rev_datetime, rev_name, rev_phone, "
			+ "rev_state, rev_naveruserid, rev_bookingtype, rev_platformtype, rev_cancelleddesc, "
			+ "rev_email, rev_userrequest, rev_prebookingkey, rev_visitorname, rev_visitorphone, "
			+ "rev_resno, rev_customformdata, rev_itemoptions, rev_ptno, rev_date, "
			+ "rev_time, rev_confirm, rev_reservationname, rev_period, rev_cell"
			+ ") "
			+ "VALUES ( "
			+ "#{rev_reservationkey}, #{rev_bookingkey}, #{rev_datetime}, #{rev_name}, #{rev_phone}, "
			+ "#{rev_state}, #{rev_naveruserid}, #{rev_bookingtype}, #{rev_platformtype}, #{rev_cancelleddesc},"
			+ "#{rev_email}, #{rev_userrequest}, #{rev_prebookingkey}, #{rev_visitorname}, #{rev_visitorphone},"
			+ "#{rev_resno}, #{rev_customformdata}, #{rev_itemoptions}, #{rev_ptno}, #{rev_date},"
			+ "#{rev_time}, #{rev_confirm}, #{rev_reservationname}, #{rev_period}, #{rev_cell})"
			+ "</script>")
	public void insertRevptNaver(CrmRevptNaverVO vo) throws Exception;
	
	@Select("<script>"
			+ "SELECT MAX(rev_seq) from revpt_naver "
			+ "</script>")
	public String getMaxSeq() throws Exception;
	
	@Update("<script>"
			+ "UPDATE revpt_naver SET "
			+ "rev_seq = rev_seq "
			+ "<if test=\"rev_reservationkey != null and rev_reservationkey != ''\">"
			+ ",rev_reservationkey = #{rev_reservationkey} "
			+ "</if>"
			+ "<if test=\"rev_bookingkey != null and rev_bookingkey != ''\">"
			+ ",rev_bookingkey = #{rev_bookingkey} "
			+ "</if>"
			+ "<if test=\"rev_datetime != null and rev_datetime != ''\">"
			+ ",rev_datetime = #{rev_datetime} "
			+ "</if>"
			+ "<if test=\"rev_name != null and rev_name != ''\">"
			+ ",rev_name = #{rev_name} "
			+ "</if>"
			+ "<if test=\"rev_phone != null and rev_phone != ''\">"
			+ ",rev_phone = #{rev_phone} "
			+ "</if>"
			+ "<if test=\"rev_state != null and rev_state != ''\">"
			+ ",rev_state = #{rev_state} "
			+ "</if>"
			+ "<if test=\"rev_naveruserid != null and rev_naveruserid != ''\">"
			+ ",rev_naveruserid = #{rev_naveruserid} "
			+ "</if>"
			+ "<if test=\"rev_bookingtype != null and rev_bookingtype != ''\">"
			+ ",rev_bookingtype = #{rev_bookingtype} "
			+ "</if>"
			+ "<if test=\"rev_platformtype != null and rev_platformtype != ''\">"
			+ ",rev_platformtype = #{rev_platformtype} "
			+ "</if>"
			+ "<if test=\"rev_cancelleddesc != null and rev_cancelleddesc != ''\">"
			+ ",rev_cancelleddesc = #{rev_cancelleddesc} "
			+ "</if>"
			+ "<if test=\"rev_email != null and rev_email != ''\">"
			+ ",rev_email = #{rev_email} "
			+ "</if>"
			+ "<if test=\"rev_userrequest != null and rev_userrequest != ''\">"
			+ ",rev_userrequest = #{rev_userrequest} "
			+ "</if>"
			+ "<if test=\"rev_prebookingkey != null and rev_prebookingkey != ''\">"
			+ ",rev_prebookingkey = #{rev_prebookingkey} "
			+ "</if>"
			+ "<if test=\"rev_visitorname != null and rev_visitorname != ''\">"
			+ ",rev_visitorname = #{rev_visitorname} "
			+ "</if>"
			+ "<if test=\"rev_visitorphone != null and rev_visitorphone != ''\">"
			+ ",rev_visitorphone = #{rev_visitorphone} "
			+ "</if>"
			+ "<if test=\"rev_resno != null and rev_resno != ''\">"
			+ ",rev_resno = #{rev_resno} "
			+ "</if>"
			+ "<if test=\"rev_customformdata != null and rev_customformdata != ''\">"
			+ ",rev_customformdata = #{rev_customformdata} "
			+ "</if>"
			+ "<if test=\"rev_itemoptions != null and rev_itemoptions != ''\">"
			+ ",rev_itemoptions = #{rev_itemoptions} "
			+ "</if>"
			+ "<if test=\"rev_ptno != null and rev_ptno != ''\">"
			+ ",rev_ptno = #{rev_ptno} "
			+ "</if>"
			+ "<if test=\"rev_date != null and rev_date != ''\">"
			+ ",rev_date = #{rev_date} "
			+ "</if>"
			+ "<if test=\"rev_time != null and rev_time != ''\">"
			+ ",rev_time = #{rev_time} "
			+ "</if>"
			+ "<if test=\"rev_confirm != null and rev_confirm != ''\">"
			+ ",rev_confirm = #{rev_confirm} "
			+ "</if>"
			+ "<if test=\"rev_reservationname != null and rev_reservationname != ''\">"
			+ ",rev_reservationname = #{rev_reservationname} "
			+ "</if>"
			+ "<if test=\"rev_period != null and rev_period != ''\">"
			+ ",rev_period = #{rev_period} "
			+ "</if>"
			+ "<if test=\"rev_cell != null and rev_cell != ''\">"
			+ ",rev_cell = #{rev_cell} "
			+ "</if>"
			+ "WHERE 0=0 "
			+ "AND rev_bookingkey = #{rev_bookingkey}"
			+ "</script>")
	public void updateRevptNaver(CrmRevptNaverVO vo) throws Exception;
}
