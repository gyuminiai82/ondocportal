package kr.ondoc.controller.legacy.ondocplus;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import kr.ondoc.controller.legacy.penchart.OldEmrController;
import kr.ondoc.domain.sybase.legacy.ondocplus.ByungdongDataVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ByungdongVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.IpwonPatientCheobangDataVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.IpwonPatientCheobangVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.IpwonPatientParamVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.IpwonPatientSangByungDataVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.IpwonPatientSangByungVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.IpwonPatientSymptomDataVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.IpwonPatientSymptomVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.MedroomDataVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.MedroomVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.PatientObgyDataVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.PatientSearchDataVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.PatientSearchParamVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.PatientSearchVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.UsrmngrDataVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.UsrmngrParamVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.UsrmngrVO;
import kr.ondoc.domain.sybase.legacy.penchart.InPatientSangbyungDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.InPatientSangbyungVO;
import kr.ondoc.domain.sybase.legacy.penchart.PatientDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.PatientRoomSymptomVO;
import kr.ondoc.domain.sybase.legacy.penchart.PatientVO;
import kr.ondoc.mapper.sybase.legacy.ondocplus.OldOndocplusMapper;
import kr.ondoc.mapper.sybase.legacy.penchart.OldPatientMapper;
import kr.ondoc.mapper.sybase.penchart.PatientMapper;

@CrossOrigin(origins = "*")
@RestController
public class OldOndocplusController {

    private final OldEmrController oldEmrController;
	@Autowired
	private OldOndocplusMapper mapper;
	
	@Autowired
	private OldPatientMapper patientMapper;

    OldOndocplusController(OldEmrController oldEmrController) {
        this.oldEmrController = oldEmrController;
    }
	
	@RequestMapping(value="/usrmngr", method=RequestMethod.GET)
    public UsrmngrVO selectUsrmngr(UsrmngrParamVO vo) throws Exception {
		UsrmngrVO resultVO = new UsrmngrVO();
		
		List<UsrmngrDataVO> dataList = mapper.selectUsrmngr(vo);
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/chartmedroom", method=RequestMethod.GET)
    public MedroomVO selectMedroom() throws Exception {
		MedroomVO resultVO = new MedroomVO();
		
		List<MedroomDataVO> dataList = mapper.selectMedroom();
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/chartbyungdong", method=RequestMethod.GET)
    public ByungdongVO selectByungdong() throws Exception {
		ByungdongVO resultVO = new ByungdongVO();
		
		List<ByungdongDataVO> dataList = mapper.selectByungdong();
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/patientsearch", method=RequestMethod.GET)
    public PatientSearchVO selectPatientsearch(PatientSearchParamVO vo) throws Exception {
		PatientSearchVO resultVO = new PatientSearchVO();
		
		List<PatientSearchDataVO> dataList = null;
		if(vo.getSearch_type().equals("")) {
			dataList = mapper.selectPatientSearch(vo);
		} else {
			dataList = mapper.selectPatientSearchType(vo);
		}
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value= {"/patient"}, method=RequestMethod.GET)
    public kr.ondoc.domain.sybase.legacy.ondocplus.PatientVO selectOutpatientstandby(String ptno) throws Exception {
		kr.ondoc.domain.sybase.legacy.ondocplus.PatientVO resultVO = new kr.ondoc.domain.sybase.legacy.ondocplus.PatientVO();
		
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
		
		List<kr.ondoc.domain.sybase.legacy.ondocplus.PatientDataVO> dataList = patientMapper.selectOnDocPlusPatient(ptno);
		
		List<kr.ondoc.domain.sybase.legacy.ondocplus.PatientObgyDataVO> obgyList = patientMapper.selectOnDocPlusPatientObgy(ptno);
		
		String symptom = "";
		String room_symptom = "";
		
		if(dataList.get(0).getOpt().equals("1")) {
			symptom = dataList.get(0).getSymptom_rtf();
			room_symptom = temp_room_symptom_rtf;
		} else {
			symptom = temp_symptom;
            room_symptom = temp_room_symptom;
		}
		String temp_obgy = "";
		
		AtomicInteger cnt = new AtomicInteger(0);
		for (PatientObgyDataVO vo : obgyList) {
			cnt.set(0);
			
			
			temp_obgy += "LMP : "+ changeDate(vo.getRpo_oblmp(), cnt);
			temp_obgy += ",&nbsp;&nbsp;EDC : "+ changeDate(vo.getRpo_obedc(), cnt);
			temp_obgy += ",&nbsp;&nbsp;PMP : "+ changeDate(vo.getRpo_obpmp(), cnt);
			temp_obgy += "\nInt : "+ nullCheck(vo.getRpo_interval(), cnt) + ",&nbsp;&nbsp;Dur : "+nullCheck(vo.getRpo_duration(), cnt) + ",&nbsp;&nbsp;Amt : "+changePM(vo.getRpo_amount(), cnt) + ",&nbsp;&nbsp;Pa : "+changePM(vo.getRpo_pain(), cnt);
			temp_obgy += "\nParity : "+ obparityCheck(vo.getRpo_obparity1(), cnt)+" &nbsp;-&nbsp; "+ obparityCheck(vo.getRpo_obparity2(), cnt)+" &nbsp;-&nbsp; "+ obparityCheck(vo.getRpo_obparity3(), cnt)+" &nbsp;-&nbsp; "+ obparityCheck(vo.getRpo_obparity4(), cnt);
			temp_obgy += "\n♂ : "+vo.getRpo_obm()+",&nbsp;&nbsp;♀ : "+vo.getRpo_obw() +",&nbsp;&nbsp;A.A : "+vo.getRpo_obw()+",&nbsp;&nbsp;S.A : "+vo.getRpo_obw();
			temp_obgy += "\n결혼구분 : ";
			if(vo.getRpo_marry().equals("1")) {temp_obgy += "Virgin";}
			if(vo.getRpo_marry().equals("2")) {temp_obgy += "Single";}
			if(vo.getRpo_marry().equals("3")) {temp_obgy += "Married";}
			if(vo.getRpo_marry().equals("4")) {temp_obgy += "Divorced";}
			if(vo.getRpo_marry().equals("5")) {temp_obgy += "Widow";}
			temp_obgy += ",&nbsp;&nbsp;분만구분 : ";
			if(vo.getRpo_bunman().equals("1")) {temp_obgy += "NSVD";}
			if(vo.getRpo_bunman().equals("2")) {temp_obgy += "C/S";}
		}
		
		dataList.get(0).setSymptom(symptom);
		dataList.get(0).setRoom_symptom(room_symptom);
		
//		System.out.println("\n\n길이 측정 다시");
//		System.out.println("temp_obgy.length : "+temp_obgy.length());
//		System.out.println("\n\n ------------ cnt : "+cnt.get());
		if(cnt.get() > 0) {
			dataList.get(0).setObgy(temp_obgy);
		}
		
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	//v2 신병동 dr은 잘못 붙인 이름임
	@RequestMapping(value="/dripwonpatientsangbyung", method=RequestMethod.GET)
    public IpwonPatientSangByungVO selectIpwonPatientSangByung(IpwonPatientParamVO vo) throws Exception {
		IpwonPatientSangByungVO resultVO = new IpwonPatientSangByungVO();
		
		List<IpwonPatientSangByungDataVO> dataList = mapper.selectIpwonPatientSangByung(vo);
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/dripwonpatientsymptom", method=RequestMethod.GET)
    public IpwonPatientSymptomVO selectIpwonPatientSymptom(IpwonPatientParamVO vo) throws Exception {
		IpwonPatientSymptomVO resultVO = new IpwonPatientSymptomVO();
		
		List<IpwonPatientSymptomDataVO> dataList = mapper.selectIpwonPatientSymptom(vo);
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/dripwonpatientcheobang", method=RequestMethod.GET)
    public IpwonPatientCheobangVO selectIpwonPatientCheobang(IpwonPatientParamVO vo) throws Exception {
		IpwonPatientCheobangVO resultVO = new IpwonPatientCheobangVO();
		
		List<IpwonPatientCheobangDataVO> dataList = mapper.selectIpwonPatientCheobang(vo);
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	public String changeDate(String oldDate, AtomicInteger cnt) {
		String date ="";
		if(oldDate == null) { date = "";}
		else if(oldDate.length() == 8) {	//8자리일경우 YYYYMMDD
			date = oldDate.substring(0, 4)+"-"+oldDate.substring(4, 6)+"-"+oldDate.substring(6, 8);
			cnt.incrementAndGet();
		}
		return date;
	}
	
	public String changePM(String oldPm, AtomicInteger cnt) {
		String pm ="";
		//1. -, 2: ±, 3.+, 4.++
		if(oldPm == null) {pm ="";}
		else if(oldPm.equals("1")) {cnt.incrementAndGet(); pm = "-";}
		else if(oldPm.equals("2")) {cnt.incrementAndGet(); pm = "±";}
		else if(oldPm.equals("3")) {cnt.incrementAndGet(); pm = "+";}
		else if(oldPm.equals("4")) {cnt.incrementAndGet(); pm = "++";}

		return pm;
	}
	
	public String nullCheck(String oldStr, AtomicInteger cnt) {
		if(oldStr == null) {return "";}
		else {cnt.incrementAndGet(); return oldStr;	}
	}
	
	public String obparityCheck(String oldStr, AtomicInteger cnt) {
		if(oldStr == null) {return "0";}
		else if(oldStr.equals("")) {return "0";}
		else {cnt.incrementAndGet(); return oldStr;	}
	}
}
