package kr.ondoc.controller.legacy.ondocplus;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ondoc.domain.sybase.legacy.ondocplus.SignpenCategoryDataVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.SignpenCategoryParamVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.SignpenCategoryVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.SignpenChartDataVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.SignpenChartOldDataVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.SignpenChartOldDateDataVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.SignpenChartOldDateParamVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.SignpenChartOldDateVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.SignpenChartOldParamVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.SignpenChartOldVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.SignpenChartParamVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.SignpenChartVO;
import kr.ondoc.mapper.sybase.legacy.OldOptionInfoMapper;
import kr.ondoc.mapper.sybase.legacy.ondocplus.OldSignpenMapper;
import kr.ondoc.mapper.sybaseemr.legacy.ondocplus.DbOldSignpenMapper;

@CrossOrigin(origins = "*")
@RestController
public class OldSignpenController {
	@Autowired
	private OldOptionInfoMapper systemOption;
	
	@Autowired
	private OldSignpenMapper mapper;
	
	@Autowired
	private DbOldSignpenMapper dbMapper;
	
	@RequestMapping(value="/emrSignpenChartCategory", method=RequestMethod.GET)
    public SignpenCategoryVO selectSignpenCategory(SignpenCategoryParamVO vo) throws Exception {
		String base72 = systemOption.selectBase72();	//0: 암호화 사용함, 1: 암호화 사용안함, 2: db사용
		
		SignpenCategoryVO resultVO = new SignpenCategoryVO();
		
		List<SignpenCategoryDataVO> dataList = null;
		
		if(base72.equals("2")) {
			dataList = dbMapper.selectSignpenCategory(vo);
		} else {
			dataList = mapper.selectSignpenCategory(vo);
		}
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/emrSignpenChart", method=RequestMethod.GET)
    public SignpenChartVO selectSignpenChart(SignpenChartParamVO vo) throws Exception {
		String base72 = systemOption.selectBase72();	//0: 암호화 사용함, 1: 암호화 사용안함, 2: db사용
		
		SignpenChartVO resultVO = new SignpenChartVO();
		
		List<SignpenChartDataVO> dataList = null;
		
		if(base72.equals("2")) {
			dataList = dbMapper.selectSignpenChart(vo);
		} else {
			dataList = mapper.selectSignpenChart(vo);
		}
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/emrSignpenChartOldDate", method=RequestMethod.GET)
    public SignpenChartOldDateVO selectSignpenChartOldDate(SignpenChartOldDateParamVO vo) throws Exception {
		String base72 = systemOption.selectBase72();	//0: 암호화 사용함, 1: 암호화 사용안함, 2: db사용
		
		SignpenChartOldDateVO resultVO = new SignpenChartOldDateVO();
		
		List<SignpenChartOldDateDataVO> dataList = null;
		
		if(base72.equals("2")) {
			dataList = dbMapper.selectSignpenChartOldDate(vo);
		} else {
			dataList = mapper.selectSignpenChartOldDate(vo);
		}
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/emrSignpenChartOld", method=RequestMethod.GET)
    public SignpenChartOldVO selectSignpenChartOld(SignpenChartOldParamVO vo) throws Exception {
		String base72 = systemOption.selectBase72();	//0: 암호화 사용함, 1: 암호화 사용안함, 2: db사용
		
		SignpenChartOldVO resultVO = new SignpenChartOldVO();
		
		List<SignpenChartOldDataVO> dataList = null;
		
		if(base72.equals("2")) {
			dataList = dbMapper.selectSignpenChartOld(vo);
		} else {
			dataList = mapper.selectSignpenChartOld(vo);
		}
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
}
