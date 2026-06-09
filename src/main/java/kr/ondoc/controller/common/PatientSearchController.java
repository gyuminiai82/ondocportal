package kr.ondoc.controller.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ondoc.domain.sybase.penchart.PatientDetailVO;
import kr.ondoc.domain.sybase.penchart.PatientParamVO;
import kr.ondoc.domain.sybase.penchart.PatientRoomSymptomVO;
import kr.ondoc.domain.sybase.penchart.PatientVO;
import kr.ondoc.mapper.sybase.penchart.PatientMapper;

@CrossOrigin(origins = "*")
@RestController
public class PatientSearchController {
	
	@Autowired
	private PatientMapper patientMapper;
	
	//[외래]대기환자
	@RequestMapping(value="/patientOutStandBy", method=RequestMethod.GET)
    public List<PatientVO> patientOutStandByList(PatientParamVO vo) throws Exception {
		
        return patientMapper.patientOutStandByList(vo);
    }
	
	//[외래]완료환자
	@RequestMapping(value="/patientOutComplete", method=RequestMethod.GET)
    public List<PatientVO> patientOutCompleteList(PatientParamVO vo) throws Exception {
		
        return patientMapper.patientOutCompleteList(vo);
    }
	
	//[입원]재원환자
	@RequestMapping(value="/patientInJaewon", method=RequestMethod.GET)
    public List<PatientVO> patientInJaewonList(PatientParamVO vo) throws Exception {
		
        return patientMapper.patientInJaewonList(vo);
    }
	
	//[입원]퇴원환자
	@RequestMapping(value="/patientInToewon", method=RequestMethod.GET)
    public List<PatientVO> patientInToewonList(PatientParamVO vo) throws Exception {
		
        return patientMapper.patientInToewonList(vo);
    }
	
	//[전체]환자검색
	@RequestMapping(value="/patientSearch", method=RequestMethod.GET)
    public List<PatientVO> patientSearchList(PatientParamVO vo) throws Exception {
		
        return patientMapper.patientSearchList(vo);
    }
	
	//환자상세
	@RequestMapping(value="/patientDetail", method=RequestMethod.GET)
    public PatientDetailVO patientDetail(String ptno) throws Exception {
		PatientDetailVO resultVO = patientMapper.patientDetail(ptno);
		
		List<PatientRoomSymptomVO> symptomList = patientMapper.selectPatientRoomSymptom(ptno);
		
		String temp_room_symptom = "";
		String temp_medroom_check = "";
		
		for (PatientRoomSymptomVO symptom : symptomList) {
		    if(!temp_medroom_check.equals(symptom.getMedroom())) {
		    	temp_room_symptom += "" + symptom.getMedroom() + "";
		    	temp_medroom_check = symptom.getMedroom();
		    }
		    
		    temp_room_symptom += symptom.getOrs_symptom() + "";
		}
		
		symptomList = patientMapper.selectPatientRoomSymptomRtf(ptno);
		
		String temp_room_symptom_rtf = "";
		
		for (PatientRoomSymptomVO symptom : symptomList) {
			temp_room_symptom_rtf += "" + symptom.getMedroom() + " ";
	    	temp_room_symptom_rtf += symptom.getOrs_symptom() + "";
		}
		
		symptomList = patientMapper.selectPatientSymptom(ptno);
		
		String temp_symptom = "";
		
		for (PatientRoomSymptomVO symptom : symptomList) {
			temp_symptom += symptom.getOsp_symptom() + "";
		}
		
		String symptom = "";
		String room_symptom = "";
		
		if(resultVO.getOpt().equals("1")) {
			symptom = resultVO.getSymptom_rtf();
			room_symptom = temp_room_symptom_rtf;
		} else {
			symptom = temp_symptom;
            room_symptom = temp_room_symptom;
		}
		
		resultVO.setSymptom(symptom);
		resultVO.setRoom_symptom(room_symptom);
		
        return resultVO;
    }
}
