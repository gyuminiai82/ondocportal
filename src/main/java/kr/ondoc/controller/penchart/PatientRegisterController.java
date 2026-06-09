package kr.ondoc.controller.penchart;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ondoc.domain.sybase.common.CommonVO;
import kr.ondoc.domain.sybase.manless.AgeTempVO;
import kr.ondoc.domain.sybase.manless.OptioninfoVO;
import kr.ondoc.domain.sybase.manless.ReceiptPatientTempVO;
import kr.ondoc.domain.sybase.manless.ReceiptPtnoLastVO;
import kr.ondoc.domain.sybase.penchart.PatientNewParamVO;
import kr.ondoc.mapper.sybase.manless.ManlessMapper;
import kr.ondoc.mapper.sybase.penchart.PatientMapper;
import kr.ondoc.util.AES256;

@CrossOrigin(origins = "*")
@RestController
public class PatientRegisterController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PatientMapper patientMapper;
	
	@Autowired
	private ManlessMapper manlessMapper;
	
	
	//접수완료 처리
	@RequestMapping(value="/insertPatientNew", method=RequestMethod.POST)
    public CommonVO insertReceiptPatient(PatientNewParamVO vo) throws Exception {
		CommonVO resultVO = new CommonVO();
		
		String sHwanja = "";
		String sPtno = "";
		String sJogubun = "";
		String sKwa = "";
		String sLPtno = "";
		String sMPtno = "";
		String tmpPtno = "";
		String sNewPtno = "";
		String sCen = "";
		String sAge = "";
		String sYAge = "";
		String sMAge = "";
		String sRegCnt = "";
		String sClass = "";
		
		String jumin = vo.getJumin1() + vo.getJumin2();
		String jumin1 = jumin.substring(0, 7);
		String jumin2 = jumin.substring(7);
		
		vo.setJumin1(jumin1);
		if(jumin2.length() == 6) vo.setJumin2((new AES256()).encrypt(jumin2));
		
		//가장최근의 환자 차트번호와 종별값 가져옴
		ReceiptPatientTempVO patientTempVO = patientMapper.selectPatientTemp(vo);
		if(patientTempVO != null) {
			resultVO.setMessage("exist");
			resultVO.setPtno(patientTempVO.getBpt_ptno());
			resultVO.setName(patientTempVO.getBpt_name());
			return resultVO;
		}
		
		OptioninfoVO optioninfo = manlessMapper.selectOption();	
		//String sEtc49 = optioninfo.getXpt_etc49();//1이면 임시테이블에 넣는방식, 2이면 직접입력 현재는 2만 쓰므로 옵션값 무시
		
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
		
		vo.setBirth(getBirthDateFromSeven(jumin1));
		
		AgeTempVO age = manlessMapper.selectAgeTemp(vo.getBirth());
		sCen = vo.getBirth().substring(0, 2);
		

		sYAge = age.getyAge();
		sMAge = age.getmAge();
		
		vo.setPtno(sPtno);
		vo.setsYAge(sYAge);
		vo.setsMAge(sMAge);
		vo.setsCen(sCen);
		
		patientMapper.insertPatient(vo);
		
		if((sNewPtno.equals("2") || sNewPtno.equals("3")) 
				&& (sPtno.length()==1 || !sPtno.substring(0, 2).equals("M-"))) {
			manlessMapper.updateLastNptno(sPtno);
		} else {
			manlessMapper.updateLastMptno(sPtno);
		}
		
		resultVO.setPtno(sPtno);
		////////////////////////////////////////////////////////////////////////
		
		return resultVO;
	}
	
	public Boolean isNumaric(String val) {
		try {
	      Integer.parseInt(val);
	      return true;
	    } catch (NumberFormatException ex) {
	      return false;
	    }
	}
	
	/**
     * 주민번호 7자리(YYMMDDG)를 입력받아 생년월일을 반환합니다.
     */
    public static String getBirthDateFromSeven(String jumin7) {
        // 1. 길이 및 기본 유효성 검사
        if (jumin7 == null || jumin7.length() != 7) {
            return "잘못된 입력입니다.";
        }

        String front = jumin7.substring(0, 6);   // "820228"
        char genderCode = jumin7.charAt(6);      // '1'
        String yearPrefix = "";

        // 2. 뒷자리 첫 숫자로 세대(세기) 판별
        switch (genderCode) {
            case '1': case '2': case '5': case '6':
                yearPrefix = "19";
                break;
            case '3': case '4': case '7': case '8':
                yearPrefix = "20";
                break;
            case '9': case '0':
                yearPrefix = "18";
                break;
            default:
                return "유효하지 않은 성별 코드입니다.";
        }

        // 3. 날짜 조합 및 유효성 체크
        try {
            String fullYear = yearPrefix + front.substring(0, 2);
            String month = front.substring(2, 4);
            String day = front.substring(4, 6);
            String birthStr = fullYear + month + day;

            return birthStr;
        } catch (Exception e) {
            return "유효하지 않은 날짜 데이터입니다.";
        }
    }
}
