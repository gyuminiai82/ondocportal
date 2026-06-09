package kr.ondoc.controller.legacy.penchart;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ondoc.domain.sybase.legacy.penchart.PatientSearchFormDataByungdongVO;
import kr.ondoc.domain.sybase.legacy.penchart.PatientSearchFormDataMedroomVO;
import kr.ondoc.domain.sybase.legacy.penchart.PatientSearchFormDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.PatientSearchFormVO;
import kr.ondoc.mapper.sybase.legacy.penchart.OldPatientSearchFormMapper;

@CrossOrigin(origins = "*")
@RestController
public class OldPatientSearchFormController {
	@Autowired
	private OldPatientSearchFormMapper patientSearchFormMapper;
	
	@RequestMapping(value="/mobileChartPatientSearchForm", method=RequestMethod.GET)
    public PatientSearchFormVO selectPatientSearchForm() throws Exception {
		PatientSearchFormVO resultVO = new PatientSearchFormVO();
		
		PatientSearchFormDataVO data = new PatientSearchFormDataVO();
		
		List<PatientSearchFormDataByungdongVO> byungdongList = patientSearchFormMapper.selectByungdong();
		List<PatientSearchFormDataMedroomVO> medroomList = patientSearchFormMapper.selectMedroom();
		
		data.setByungdong(byungdongList);
		data.setMedroom(medroomList);

		resultVO.setData(data);
		
		return resultVO;
    }
}
