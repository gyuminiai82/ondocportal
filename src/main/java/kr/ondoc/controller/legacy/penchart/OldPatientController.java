package kr.ondoc.controller.legacy.penchart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ondoc.domain.sybase.legacy.penchart.PatientDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.PatientRoomSymptomVO;
import kr.ondoc.domain.sybase.legacy.penchart.PatientVO;
import kr.ondoc.mapper.sybase.legacy.penchart.OldPatientMapper;

@CrossOrigin(origins = "*")
@RestController
public class OldPatientController {
	@Autowired
	private OldPatientMapper patientMapper;
	
	@RequestMapping(value= {"/mobileChartPatient"}, method=RequestMethod.GET)
    public PatientVO selectOutpatientstandby(String ptno) throws Exception {
		PatientVO resultVO = new PatientVO();
		
		List<PatientRoomSymptomVO> symptomList = patientMapper.selectPatientRoomSymptom(ptno);
		
		String temp_room_symptom = "";
		String temp_medroom_check = "";
		
		for (PatientRoomSymptomVO symptom : symptomList) {
		    if(!temp_medroom_check.equals(symptom.getMedroom())) {
		    	temp_room_symptom += "<strong>" + symptom.getMedroom() + "</strong> ";
		    	temp_medroom_check = symptom.getMedroom();
		    }
		    
		    temp_room_symptom += symptom.getOrs_symptom() + "";
		}
		
		symptomList = patientMapper.selectPatientRoomSymptomRtf(ptno);
		
		String temp_room_symptom_rtf = "";
		
		for (PatientRoomSymptomVO symptom : symptomList) {
			temp_room_symptom_rtf += "<strong>" + symptom.getMedroom() + "</strong> ";
	    	temp_room_symptom_rtf += symptom.getOrs_symptom() + "";
		}
		
		symptomList = patientMapper.selectPatientSymptom(ptno);
		
		String temp_symptom = "";
		
		for (PatientRoomSymptomVO symptom : symptomList) {
			temp_symptom += symptom.getOsp_symptom() + "<br>";
		}
		
		List<PatientDataVO> dataList = patientMapper.selectPatient(ptno);
		
		String symptom = "";
		String room_symptom = "";
		
		if(dataList.get(0).getOpt().equals("1")) {
			symptom = dataList.get(0).getSymptom_rtf();
			room_symptom = temp_room_symptom_rtf;
		} else {
			symptom = temp_symptom;
            room_symptom = temp_room_symptom;
		}
		
		dataList.get(0).setSymptom(symptom);
		dataList.get(0).setRoom_symptom(room_symptom);
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
}
