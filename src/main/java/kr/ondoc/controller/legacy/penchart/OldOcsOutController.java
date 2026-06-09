package kr.ondoc.controller.legacy.penchart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ondoc.domain.sybase.legacy.penchart.NaewonParamVO;
import kr.ondoc.domain.sybase.legacy.penchart.NaewonYearDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.NaewonYearVO;
import kr.ondoc.domain.sybase.legacy.penchart.OutPatientCheobangDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.OutPatientCheobangVO;
import kr.ondoc.domain.sybase.legacy.penchart.OutPatientNaewonDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.OutPatientNaewonVO;
import kr.ondoc.domain.sybase.legacy.penchart.OutPatientSangbyungDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.OutPatientSangbyungVO;
import kr.ondoc.domain.sybase.legacy.penchart.OutPatientSymptomDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.OutPatientSymptomVO;
import kr.ondoc.mapper.sybase.legacy.penchart.OldOcsOutMapper;

@CrossOrigin(origins = "*")
@RestController
public class OldOcsOutController {
	@Autowired
	private OldOcsOutMapper mapper;
	
	@RequestMapping(value="/outpatientnaewonyear", method=RequestMethod.GET)
    public NaewonYearVO selectNaewonYear(String ptno) throws Exception {
		NaewonYearVO resultVO = new NaewonYearVO();
		
		List<NaewonYearDataVO> dataList = mapper.selectNaewonYear(ptno);
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/outpatientnaewon", method=RequestMethod.GET)
    public OutPatientNaewonVO selectNaewonYear(NaewonParamVO vo) throws Exception {
		OutPatientNaewonVO resultVO = new OutPatientNaewonVO();
		
		List<OutPatientNaewonDataVO> dataList = mapper.selectOutPatientNaewon(vo);
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/outpatientsangbyung", method=RequestMethod.GET)
    public OutPatientSangbyungVO selectSangbyung(NaewonParamVO vo) throws Exception {
		OutPatientSangbyungVO resultVO = new OutPatientSangbyungVO();
		
		List<OutPatientSangbyungDataVO> dataList = mapper.selectOutPatientSangbyung(vo);
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/outpatientsymptom", method=RequestMethod.GET)
    public OutPatientSymptomVO selectSymptom(NaewonParamVO vo) throws Exception {
		OutPatientSymptomVO resultVO = new OutPatientSymptomVO();
		
		List<OutPatientSymptomDataVO> dataList = mapper.selectOutPatientSymptom(vo);
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/outpatientcheobang", method=RequestMethod.GET)
    public OutPatientCheobangVO selectCheobang(NaewonParamVO vo) throws Exception {
		OutPatientCheobangVO resultVO = new OutPatientCheobangVO();
		
		List<OutPatientCheobangDataVO> dataList = mapper.selectOutPatientCheobang(vo);
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
}
