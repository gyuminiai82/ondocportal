package kr.ondoc.mapper.sybase.healthcheck;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import kr.ondoc.domain.sybase.healthcheck.HealthCheckConsentVO;
import kr.ondoc.domain.sybase.healthcheck.HealthCheckDataVO;
import kr.ondoc.domain.sybase.healthcheck.HealthCheckParamVO;

public interface HealthCheckMapper {
	@Select("<script>"
			+ "SELECT * FROM gmjn_naewon AS gn "
			+ "LEFT OUTER JOIN patient AS pi "
			+ "ON gn.gng_ptno=pi.bpt_ptno "
			+ "LEFT OUTER JOIN gmjn_result_consent AS gc "
			+ "ON gn.gng_regno=gc.grc_regno "
			+ "WHERE 0=0 "
			+ "AND gn.gng_ch_date BETWEEN #{start_date} AND #{end_date} "
			+ "</script>")
	public List<HealthCheckDataVO> selectHealthCheckList(HealthCheckParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "SELECT * FROM gmjn_result_consent "
			+ "WHERE 0=0 "
			+ "AND grc_regno=#{grc_regno} "
			+ "<if test=\"grc_yyyy == null or grc_yyyy == ''\">"
			+ "AND grc_yyyy=#{grc_yyyy} "
			+ "</if>"
			+ "</script>")
	public List<HealthCheckConsentVO> selectHealthCheckConsent(HealthCheckConsentVO vo) throws Exception;
	
	@Insert("<script>"
			+ "INSERT INTO gmjn_result_consent ("
			+ "grc_regno, grc_ch_date, grc_yyyy, grc_ptno, grc_general, "
			+ "grc_cancer, grc_psychopathy, grc_agree, grc_name, grc_jumin, grc_jumin_enc,"
			+ "grc_sign, grc_sign_image, grc_hosnum, grc_hosname) "
			+ "VALUES ( "
			+ "#{grc_regno}, #{grc_ch_date}, #{grc_yyyy}, #{grc_ptno}, #{grc_general}, "
			+ "#{grc_cancer}, #{grc_psychopathy}, #{grc_agree}, #{grc_name}, #{grc_jumin}, #{grc_jumin_enc},"
			+ "#{grc_sign}, #{grc_sign_image}, #{grc_hosnum}, #{grc_hosname})"
			+ "</script>")
	public void insertHealthCheckConsent(HealthCheckConsentVO vo) throws Exception;
}
