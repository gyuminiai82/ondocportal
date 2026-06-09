package kr.ondoc.controller.legacy.penchart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ondoc.domain.sybase.legacy.penchart.NaewonParamVO;
import kr.ondoc.domain.sybase.legacy.penchart.OutPatientCheobangDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.OutPatientCheobangVO;
import kr.ondoc.domain.sybase.legacy.penchart.OutPatientSangbyungDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.OutPatientSangbyungVO;
import kr.ondoc.domain.sybase.legacy.penchart.OutPatientSymptomDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.OutPatientSymptomVO;
import kr.ondoc.domain.sybase.legacy.penchart.InPatientCheobangDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.InPatientCheobangVO;
import kr.ondoc.domain.sybase.legacy.penchart.InPatientNaewonDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.InPatientNaewonV2DataVO;
import kr.ondoc.domain.sybase.legacy.penchart.InPatientNaewonV2VO;
import kr.ondoc.domain.sybase.legacy.penchart.InPatientNaewonVO;
import kr.ondoc.domain.sybase.legacy.penchart.InPatientSangbyungDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.InPatientSangbyungVO;
import kr.ondoc.domain.sybase.legacy.penchart.InPatientSymptomDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.InPatientSymptomVO;
import kr.ondoc.mapper.sybase.legacy.penchart.OldOcsInMapper;

@CrossOrigin(origins = "*")
@RestController
public class OldOcsInController {
	@Autowired
	private OldOcsInMapper mapper;
	
	@RequestMapping(value="/ipwonpatientnaewon", method=RequestMethod.GET)
    public InPatientNaewonVO selectNaewon(NaewonParamVO vo) throws Exception {
		InPatientNaewonVO resultVO = new InPatientNaewonVO();
		
		List<InPatientNaewonDataVO> dataList = null;
		
		if(vo.getRegno().equals("")) dataList = mapper.selectInPatientNaewonPtno(vo);
		else  dataList = mapper.selectInPatientNaewonRegno(vo);
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/ipwonpatientsangbyung", method=RequestMethod.GET)
    public InPatientSangbyungVO selectSangbyung(NaewonParamVO vo) throws Exception {
		InPatientSangbyungVO resultVO = new InPatientSangbyungVO();
		
		List<InPatientSangbyungDataVO> dataList = mapper.selectInPatientSangbyung(vo);
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/ipwonpatientsymptom", method=RequestMethod.GET)
    public InPatientSymptomVO selectSymptom(NaewonParamVO vo) throws Exception {
		InPatientSymptomVO resultVO = new InPatientSymptomVO();
		
		List<InPatientSymptomDataVO> dataList = mapper.selectInPatientSymptom(vo);
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/ipwonpatientcheobang", method=RequestMethod.GET)
    public InPatientCheobangVO selectCheobang(NaewonParamVO vo) throws Exception {
		InPatientCheobangVO resultVO = new InPatientCheobangVO();
		
		List<InPatientCheobangDataVO> dataList = mapper.selectInPatientCheobang(vo);
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	
	
	@RequestMapping(value="/dripwonpatientnaewon", method=RequestMethod.GET)
    public InPatientNaewonV2VO selectNaewonV2(NaewonParamVO vo) throws Exception {
		InPatientNaewonV2VO resultVO = new InPatientNaewonV2VO();
		
		List<InPatientNaewonV2DataVO> dataList = null;
		
		if(vo.getRegno().equals("")) dataList = mapper.selectInPatientNaewonV2Ptno(vo);
		else  dataList = mapper.selectInPatientNaewonV2Regno(vo);
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
}
