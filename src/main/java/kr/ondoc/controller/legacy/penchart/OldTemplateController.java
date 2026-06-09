package kr.ondoc.controller.legacy.penchart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ondoc.domain.sybase.legacy.penchart.TemplateBoilerplateDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.TemplateBoilerplateDetailDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.TemplateBoilerplateDetailVO;
import kr.ondoc.domain.sybase.legacy.penchart.TemplateBoilerplateVO;
import kr.ondoc.domain.sybase.legacy.penchart.TemplateParamVO;
import kr.ondoc.domain.sybase.legacy.penchart.TemplateTreatementDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.TemplateTreatementDetailDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.TemplateTreatementDetailVO;
import kr.ondoc.domain.sybase.legacy.penchart.TemplateTreatementVO;
import kr.ondoc.mapper.sybase.legacy.OldOptionInfoMapper;
import kr.ondoc.mapper.sybase.legacy.penchart.OldTemplateMapper;
import kr.ondoc.mapper.sybaseemr.legacy.penchart.DbOldEmrMapper;
import kr.ondoc.mapper.sybaseemr.legacy.penchart.DbOldTemplateMapper;

@CrossOrigin(origins = "*")
@RestController
public class OldTemplateController {
	@Autowired
	private OldOptionInfoMapper systemOption;
	
	@Autowired
	private OldTemplateMapper templateMapper;
	
	@Autowired
	private DbOldTemplateMapper dbTemplateMapper;
	
	@RequestMapping(value="/mobileChartTemplateTreatement", method=RequestMethod.GET)
    public TemplateTreatementVO selectTemplateTreatement(TemplateParamVO vo) throws Exception {
		String base72 = systemOption.selectBase72();	//0: 암호화 사용함, 1: 암호화 사용안함, 2: db사용
		
		TemplateTreatementVO resultVO = new TemplateTreatementVO();
		
		List<TemplateTreatementDataVO> dataList = null;
		
		if(base72.equals("2")) {
			dataList = dbTemplateMapper.selectTemplateTreatement(vo);
		} else {
			dataList = templateMapper.selectTemplateTreatement(vo);
		}
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/mobileChartTemplateTreatementDetail", method=RequestMethod.GET)
    public TemplateTreatementDetailVO selectTemplateTreatementDetail(TemplateParamVO vo) throws Exception {
		String base72 = systemOption.selectBase72();	//0: 암호화 사용함, 1: 암호화 사용안함, 2: db사용
		
		TemplateTreatementDetailVO resultVO = new TemplateTreatementDetailVO();
		
		List<TemplateTreatementDetailDataVO> dataList = null;
		
		if(base72.equals("2")) {
			dataList = dbTemplateMapper.selectTemplateTreatementDetail(vo);
		} else {
			dataList = templateMapper.selectTemplateTreatementDetail(vo);
		}
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/mobileChartTemplateBoilerPlate", method=RequestMethod.GET)
    public TemplateBoilerplateVO selectTemplateBoilerplate(TemplateParamVO vo) throws Exception {
		String base72 = systemOption.selectBase72();	//0: 암호화 사용함, 1: 암호화 사용안함, 2: db사용
		
		TemplateBoilerplateVO resultVO = new TemplateBoilerplateVO();
		
		List<TemplateBoilerplateDataVO> dataList = null;
		
		if(base72.equals("2")) {
			dataList = dbTemplateMapper.selectTemplateBoilerplate(vo);
		} else {
			dataList = templateMapper.selectTemplateBoilerplate(vo);
		}
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/mobileChartTemplateBoilerPlateDetail", method=RequestMethod.GET)
    public TemplateBoilerplateDetailVO selectTemplateBoilerplateDetail(TemplateParamVO vo) throws Exception {
		String base72 = systemOption.selectBase72();	//0: 암호화 사용함, 1: 암호화 사용안함, 2: db사용
		
		TemplateBoilerplateDetailVO resultVO = new TemplateBoilerplateDetailVO();
		
		List<TemplateBoilerplateDetailDataVO> dataList = null;
		
		if(base72.equals("2")) {
			dataList = dbTemplateMapper.selectTemplateBoilerplateDetail(vo);
		} else {
			dataList = templateMapper.selectTemplateBoilerplateDetail(vo);
		}
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
}
