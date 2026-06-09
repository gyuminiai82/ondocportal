package kr.ondoc.mapper.sybase.legacy.penchart;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.ondoc.domain.sybase.legacy.ondoc.JinryoReserveParamVO;
import kr.ondoc.domain.sybase.legacy.penchart.PatientSearchDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.PatientSearchParamVO;
import kr.ondoc.domain.sybase.legacy.penchart.ReserveDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.ReserveParamVO;

public interface OldReserveMapper {
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT bmr_name, bmr_kwanm, bmr_hosname, bmr_docname, rev_seq, rev_ptno, rev_date, rev_time, rev_name, rev_memo, rev_examgu, rev_yage, rev_sex, rev_color "
			+ "FROM revpt AS RP "
			+ "INNER JOIN "
			+ "		(SELECT * FROM medroom"
			+ "			WHERE 0=0 AND bmr_hosnum = (SELECT bhi_hosnum FROM hosinfo WHERE bhi_usechk='Y') AND bmr_use='Y') AS MD "
			+ "ON RP.rev_medroom=MD.bmr_code  "
			+ "]]>"
			+ "<if test=\"month != null and month != ''\">"
			+ "<![CDATA["
			+ "AND RP.rev_date like '${month}__' "
			+ "]]>"
			+ "</if>"
			+ "<if test=\"dates != null and dates != ''\">"
			+ "AND RP.rev_date in (${dates}) "
			+ "</if>"
			+ "<if test=\"date != null and date != ''\">"
			+ "AND RP.rev_date = #{date} "
			+ "</if>"
			+ "<if test=\"code != null and code != ''\">"
			+ "AND MD.bmr_code = #{code} "
			+ "</if>"
			+ "<if test=\"room_name != null and room_name != ''\">"
			+ "AND MD.bmr_name = #{room_name} "
			+ "</if>"
			
			+ "<if test=\"time != null and time != '' and time == '09'\">"
			+ " AND LEFT(RP.rev_time, 2) in ('00', '01', '02', '03', '04', '05', '06', '07', '08', '09') "
			+ "</if>"
			+ "<if test=\"time != null and time != '' and time == '18'\">"
			+ " AND LEFT(RP.rev_time, 2) in ('18', '19', '20', '21', '22', '23') "
			+ "</if>"
			+ "<if test=\"time != null and time != '' and time != '09' and time != '18'\">"
			+ " AND LEFT(RP.rev_time, 2) in (${time}) "
			+ "</if>"
			
			+ "AND (RP.rev_canid is null OR RP.rev_canid = '') "
			+ "ORDER BY RP.rev_time ASC "
			+ "</script>")
	public List<ReserveDataVO> selectReserve(ReserveParamVO vo) throws Exception;
	
	@Update("<script>"
			+ "UPDATE revpt SET "
			+ "rev_seq=rev_seq "
			+ "<if test=\"examgu != null and examgu != ''\">"
			+ ", rev_examgu=#{examgu} "
			+ "</if>"
			+ "WHERE 0=0  "
			+ "AND rev_seq=#{seq} "
			+ "</script>")
	public void updateReserve(ReserveParamVO vo) throws Exception;
}
