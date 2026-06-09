package kr.ondoc.controller.legacy.penchart;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ondoc.domain.sybase.legacy.penchart.PatientSearchVO;
import kr.ondoc.domain.sybase.legacy.penchart.PatientSearchDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.PatientSearchInJaewonDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.PatientSearchInJaewonVO;
import kr.ondoc.domain.sybase.legacy.penchart.PatientSearchInToewonDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.PatientSearchInToewonVO;
import kr.ondoc.domain.sybase.legacy.penchart.PatientSearchOutCompleteDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.PatientSearchOutCompleteVO;
import kr.ondoc.domain.sybase.legacy.penchart.PatientSearchOutDaegiDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.PatientSearchOutDaegiVO;
import kr.ondoc.domain.sybase.legacy.penchart.PatientSearchParamVO;
import kr.ondoc.error.NoResultException;
import kr.ondoc.mapper.sybase.common.OptionInfoMapper;
import kr.ondoc.mapper.sybase.legacy.penchart.OldPatientSearchMapper;
import kr.ondoc.mapper.sybaseemr.legacy.penchart.DbOldPatientSearchMapper;

@CrossOrigin(origins = "*")
@RestController
public class OldPatientSearchController {
	@Autowired
	private OldPatientSearchMapper patientSearchMapper;
	
	@Autowired
	private DbOldPatientSearchMapper dbPatientSearchMapper;
	
	@Autowired
	private OptionInfoMapper optionInfoMapper;
	
	@RequestMapping(value="/outpatientstandby", method=RequestMethod.GET)
    public PatientSearchOutDaegiVO selectOutpatientstandby(PatientSearchParamVO vo) throws Exception {
		String base72 = optionInfoMapper.selectBase72();	//0: 암호화 사용함, 1: 암호화 사용안함, 2: db사용
		
		PatientSearchOutDaegiVO resultVO = new PatientSearchOutDaegiVO();
		
		if(vo.getType().equals("count")) {
			List<PatientSearchOutDaegiDataVO> dataList = patientSearchMapper.selectPatientSearchOutDaegiCount(vo);
			
			resultVO.setTotalCount(Integer.toString(dataList.size()));
			resultVO.setData(dataList);
		} else if(vo.getType().equals("treate")) {
			List<PatientSearchOutDaegiDataVO> dataList = patientSearchMapper.selectPatientSearchOutDaegiTreate(vo);
			
			resultVO.setTotalCount(Integer.toString(dataList.size()));
			resultVO.setData(dataList);
		} else {
			List<PatientSearchOutDaegiDataVO> dataList = null;
			
			if(base72.equals("2")) {
				dataList = patientSearchMapper.selectPatientSearchOutDaegi2(vo);
				
				if(!vo.getDate2().equals("")) {
					for(PatientSearchOutDaegiDataVO ele: dataList) {
						int count = dbPatientSearchMapper.countConsentCount(ele.getRwj_ptno(), vo.getDate2());
						
						ele.setConsent_count(Integer.toString(count));
					}
				}
			} else {
				dataList = patientSearchMapper.selectPatientSearchOutDaegi(vo);
			}
			
			resultVO.setTotalCount(Integer.toString(dataList.size()));
			resultVO.setData(dataList);
		}
		
		return resultVO;    }
	
	@RequestMapping(value="/outpatientcomplete", method=RequestMethod.GET)
    public PatientSearchOutCompleteVO selectOutpatientcomplete(PatientSearchParamVO vo) throws Exception {
		String base72 = optionInfoMapper.selectBase72();	//0: 암호화 사용함, 1: 암호화 사용안함, 2: db사용
		
		PatientSearchOutCompleteVO resultVO = new PatientSearchOutCompleteVO();
		
		List<PatientSearchOutCompleteDataVO> dataList = patientSearchMapper.selectPatientSearchOutComplete(vo);
		
		if(base72.equals("2")) {
			dataList = patientSearchMapper.selectPatientSearchOutComplete2(vo);
			
			if(!vo.getDate2().equals("")) {
				for(PatientSearchOutCompleteDataVO ele: dataList) {
					int count = dbPatientSearchMapper.countConsentCountComplete(ele.getOnw_ptno(), vo.getDate2());
					
					ele.setConsent_count(Integer.toString(count));
				}
			}
		} else {
			dataList = patientSearchMapper.selectPatientSearchOutComplete(vo);
		}
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/ipwonpatientjaewon", method=RequestMethod.GET)
    public PatientSearchInJaewonVO selectIpwonpatientjaewon(PatientSearchParamVO vo) throws Exception {
		PatientSearchInJaewonVO resultVO = new PatientSearchInJaewonVO();
		
		List<PatientSearchInJaewonDataVO> dataList = patientSearchMapper.selectPatientSearchInJaewon(vo);
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/ipwonpatienttoewon", method=RequestMethod.GET)
    public PatientSearchInToewonVO selectIpwonpatienttoewon(PatientSearchParamVO vo) throws Exception {
		if(vo.getStart_date().equals("") || vo.getEnd_date().equals("")) {
			throw new NoResultException("NO_RESULT");
		}
		
		PatientSearchInToewonVO resultVO = new PatientSearchInToewonVO();
		
		List<PatientSearchInToewonDataVO> dataList = patientSearchMapper.selectPatientSearchInToewon(vo);
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/mobileChartPatientSearch", method=RequestMethod.GET)
    public PatientSearchVO selectPatientSearch(PatientSearchParamVO vo) throws Exception {
		PatientSearchVO resultVO = new PatientSearchVO();
		
		List<PatientSearchDataVO> dataList = patientSearchMapper.selectPatientSearch(vo);
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
}
