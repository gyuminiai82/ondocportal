package kr.ondoc.controller.crm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ondoc.domain.sybase.common.CommonVO;
import kr.ondoc.domain.sybase.crm.CrmCommonVO;
import kr.ondoc.domain.sybase.crm.CrmPatientDetailVO;
import kr.ondoc.domain.sybase.crm.CrmPatientJonginfoVO;
import kr.ondoc.domain.sybase.crm.CrmPatientPagingVO;
import kr.ondoc.domain.sybase.crm.CrmPatientParamVO;
import kr.ondoc.domain.sybase.crm.CrmPatientRoomSymptomVO;
import kr.ondoc.domain.sybase.crm.CrmPatientVO;
import kr.ondoc.domain.sybase.crm.CrmReserveOperationVO;
import kr.ondoc.domain.sybase.legacy.ondoc.WaitJinryoDataVO;
import kr.ondoc.domain.sybase.legacy.ondoc.WaitJinryoVO;
import kr.ondoc.domain.sybase.legacy.penchart.PatientRoomSymptomVO;
import kr.ondoc.domain.sybase.manless.AgeTempVO;
import kr.ondoc.domain.sybase.manless.OptioninfoVO;
import kr.ondoc.domain.sybase.manless.ReceiptPtnoLastVO;
import kr.ondoc.mapper.sybase.crm.CrmPatientMapper;
import kr.ondoc.mapper.sybase.manless.ManlessMapper;
import kr.ondoc.util.PagingUtil;

@CrossOrigin(origins = "*")
@RestController
public class PatientController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CrmPatientMapper patientMapper;
	
	@Autowired
	private ManlessMapper manlessMapper;
	
	@RequestMapping(value="/crmPatient", method=RequestMethod.GET)
    public CrmPatientDetailVO selectPatient(CrmPatientParamVO vo) throws Exception {
		CrmPatientDetailVO resultVO = patientMapper.selectPatient(vo);
		
		List<CrmPatientRoomSymptomVO> symptomList = patientMapper.selectPatientRoomSymptom(vo.getPtno());
		
		String temp_room_symptom = "";
		String temp_medroom_check = "";
		
		for (CrmPatientRoomSymptomVO symptom : symptomList) {
		    if(!temp_medroom_check.equals(symptom.getMedroom())) {
		    	temp_room_symptom += "<strong>" + symptom.getMedroom() + "</strong> ";
		    	temp_medroom_check = symptom.getMedroom();
		    }
		    
		    temp_room_symptom += symptom.getOrs_symptom() + "";
		}
		
		symptomList = patientMapper.selectPatientRoomSymptomRtf(vo.getPtno());
		
		String temp_room_symptom_rtf = "";
		
		for (CrmPatientRoomSymptomVO symptom : symptomList) {
			temp_room_symptom_rtf += "<strong>" + symptom.getMedroom() + "</strong> ";
	    	temp_room_symptom_rtf += symptom.getOrs_symptom() + "";
		}
		
		symptomList = patientMapper.selectPatientSymptom(vo.getPtno());
		
		String temp_symptom = "";
		
		for (CrmPatientRoomSymptomVO symptom : symptomList) {
			temp_symptom += symptom.getOsp_symptom() + "<br>";
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
	
	@RequestMapping(value="/crmPatient", method=RequestMethod.POST)
    public CommonVO insertPatient(CrmPatientVO vo) throws Exception {
		CrmCommonVO commonVO = new CrmCommonVO();
		
		OptioninfoVO optioninfo = manlessMapper.selectOption();	
		//String sEtc49 = optioninfo.getXpt_etc49();//1이면 임시테이블에 넣는방식, 2이면 직접입력 현재는 2만 쓰므로 옵션값 무시
		
		String sHwanja = "";
		String sPtno = "";
		String tmpPtno = "";
		String sNewPtno = "";
		String sLPtno = "";
		String sMPtno = "";
		
		try {
			//신환인 경우
			//차트번호 생성 Start
			ReceiptPtnoLastVO lastVO = manlessMapper.selectLast();
			
			sHwanja = "C";//신규
			sNewPtno = optioninfo.getXpt_reg01();
			
			if(sNewPtno.equals("2") || sNewPtno.equals("3")) {
				sLPtno = lastVO.getRla_nptno();
				sMPtno = lastVO.getRla_mptno();	//M-0000001 로시작하여 순차증가
			} else {
				sLPtno = lastVO.getRla_mptno();	//M-0000001 로시작하여 순차증가
			}
			
			if(sNewPtno.equals("2")) {
				try {
					if(isNumaric(sLPtno)) {
						sPtno = Integer.toString(Integer.parseInt(sLPtno) + 1);
						
						if(sLPtno.substring(0, 1).equals("0")) {
							sPtno = "0" + sPtno;
						}
					} else {
						if(sMPtno.equals("")) sMPtno = "M-0000001";
						else sMPtno = "M-"+String.format("%0" + 7 + "d", Integer.parseInt(sMPtno.substring(2)) + 1);
						sPtno = sMPtno; 
					}
				} catch (Exception e) {
					sPtno = "1";
				}
			} else if(sNewPtno.equals("3")) {
				Date nowDate = new Date();
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
				String sCurrentDate = format.format(nowDate);
				
				tmpPtno = sLPtno.substring(7);
				
				if(isNumaric(tmpPtno)) {
					try {
						sPtno = sCurrentDate.substring(0, 6) + "-" + Integer.toString(Integer.parseInt(tmpPtno) + 1);
					} catch (Exception e) {
						// TODO: handle exception
						sPtno = sCurrentDate.substring(0, 6) + "-1";
					}
				} else {
					if(sMPtno.equals("")) sMPtno = "M-0000001";
					else sMPtno = "M-"+String.format("%0" + 7 + "d", Integer.parseInt(sMPtno.substring(2)) + 1); 
				}
			} else {
				if(sLPtno.equals("")) sLPtno = "M-0000001";
				else sLPtno = "M-"+String.format("%0" + 7 + "d", Integer.parseInt(sLPtno.substring(2)) + 1); 

				sPtno = sLPtno;
			}
			
			if(!vo.getBpt_birth().equals("")) {
				AgeTempVO age = manlessMapper.selectAgeTemp(vo.getBpt_birth());
				
				vo.setBpt_yage(age.getyAge());
				vo.setBpt_mage(age.getmAge());
				
				commonVO.setAge(age.getyAge());
			}
			
			vo.setBpt_ptno(sPtno);
			int cnt = manlessMapper.selectPatientCnt(sPtno);
			
			if(cnt >= 1) {
				commonVO.setStatus("1003");
				commonVO.setMessage("receipt fail");
				
				return commonVO;
			}
			
			patientMapper.insertPatient(vo);  
			
			commonVO.setPtno(sPtno);
			
			if((sNewPtno.equals("2") || sNewPtno.equals("3")) 
					&& (sPtno.length()==1 || !sPtno.substring(0, 2).equals("M-"))) {
				manlessMapper.updateLastNptno(sPtno);
			} else {
				manlessMapper.updateLastMptno(sPtno);
			}
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("Internal Sever Error!");
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmPatient", method=RequestMethod.PUT)
    public CommonVO updatePatient(CrmPatientVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			patientMapper.updatePatient(vo);  
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("Internal Sever Error!");
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmPatientList", method=RequestMethod.GET)
    public CrmPatientPagingVO selectPatientList(CrmPatientParamVO vo) throws Exception {
		CrmPatientPagingVO resultVO = new CrmPatientPagingVO();
		
		int cnt = patientMapper.countPatient(vo);
		PagingUtil paging = new PagingUtil(vo.getFnName(), Integer.toString(cnt), vo.getPage(), vo.getListNum(), vo.getBlockNum());
		
		vo.setFirst(Integer.toString(paging.getFirst()));
		
		List<CrmPatientVO> dataList = patientMapper.selectPatientList(vo);
		
		resultVO.setPaging(paging.getPaging());
		resultVO.setTotalPage(paging.getTotalPage());
		resultVO.setTotalRecord(Integer.toString(cnt));
		resultVO.setFirst(Integer.toString(paging.getFirst()));
		resultVO.setLast(Integer.toString(paging.getLast()));
		resultVO.setNum(paging.getNum());
		
		resultVO.setData(dataList);
		return resultVO;
    }
	
	@RequestMapping(value="/crmPatientCount", method=RequestMethod.GET)
    public int selectPatientCount(CrmPatientParamVO vo) throws Exception {
		return patientMapper.countPatient(vo);
    }
	
	@RequestMapping(value="/crmPatientJonginfo", method=RequestMethod.GET)
    public List<CrmPatientJonginfoVO> selectPatientJonginfo() throws Exception {
		return patientMapper.selectPatientJonginfo();
    }
	
	public Boolean isNumaric(String val) {
		try {
	      Integer.parseInt(val);
	      return true;
	    } catch (NumberFormatException ex) {
	      return false;
	    }
	}
}
