package kr.ondoc.mapper.sybaseemr.legacy.penchart;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import kr.ondoc.domain.sybase.legacy.penchart.TemplateBoilerplateDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.TemplateBoilerplateDetailDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.TemplateParamVO;
import kr.ondoc.domain.sybase.legacy.penchart.TemplateTreatementDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.TemplateTreatementDetailDataVO;

public interface DbOldTemplateMapper {
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT bek_code, bek_mngrno, bek_name FROM sangyongkind WHERE 0=0 "
			+ "]]>"
			+ "</script>")
	public List<TemplateTreatementDataVO> selectTemplateTreatement(TemplateParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT bes_code, bes_mngrno, bes_name, bes_kcode, bes_groupcd, bes_medroom, bes_naeyong "
			+ "FROM sangyonggu "
			+ "WHERE 0=0 "
			+ "]]>"
			+ "<if test=\"kcode != null and kcode != ''\">"
			+ "AND bes_kcode=#{kcode} "
			+ "</if>"
			+ "</script>")
	public List<TemplateTreatementDetailDataVO> selectTemplateTreatementDetail(TemplateParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT bek2_code, bek2_mngrno, bek2_name FROM sangyongkind2 WHERE 0=0 "
			+ "]]>"
			+ "</script>")
	public List<TemplateBoilerplateDataVO> selectTemplateBoilerplate(TemplateParamVO vo) throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT bes2_code, bes2_mngrno, bes2_medroom, bes2_kcode, bes2_naeyong "
			+ "FROM sangyonggu2 "
			+ "WHERE 0=0 AND bes2_kcode=#{kcode} "
			+ "]]>"
			+ "</script>")
	public List<TemplateBoilerplateDetailDataVO> selectTemplateBoilerplateDetail(TemplateParamVO vo) throws Exception;
}
