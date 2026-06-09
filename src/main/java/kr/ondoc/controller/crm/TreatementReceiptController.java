package kr.ondoc.controller.crm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ondoc.domain.sybase.common.CommonVO;
import kr.ondoc.domain.sybase.crm.CrmPatientDetailVO;
import kr.ondoc.domain.sybase.crm.CrmPatientParamVO;
import kr.ondoc.domain.sybase.crm.CrmRevptNaverVO;
import kr.ondoc.domain.sybase.crm.CrmTreatementReceiptVO;
import kr.ondoc.domain.sybase.manless.AgeTempVO;
import kr.ondoc.domain.sybase.manless.MedroomTempVO;
import kr.ondoc.domain.sybase.manless.ReceiptPatientParamVO;
import kr.ondoc.domain.sybase.manless.ReceiptPatientTempVO;
import kr.ondoc.mapper.sybase.crm.CrmPatientMapper;
import kr.ondoc.mapper.sybase.manless.ManlessMapper;

@CrossOrigin(origins = "*")
@RestController
public class TreatementReceiptController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CrmPatientMapper patientMapper;
	
	@Autowired
	private ManlessMapper manlessMapper;
	
	//온닥CRM에서 스케줄 마우스 오른쪽 버튼 클릭후 진료접수 버튼 클릭시 실행
	@RequestMapping(value="/crmTreatementReceipt", method=RequestMethod.POST)
    public CommonVO insertTreatementReceipt(CrmTreatementReceiptVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		
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
		
		//차트번호로 환자정보 가져오기
		CrmPatientParamVO paramVO = new CrmPatientParamVO();
		paramVO.setPtno(vo.getPtno());
		CrmPatientDetailVO patientVO = patientMapper.selectPatient(paramVO);
		
		//가장최근의 환자 차트번호와 종별값 가져옴
		sPtno = patientVO.getBpt_ptno();
		//sJogubun = patientVO.getBpt_jogubun();
		
		ReceiptPatientParamVO receiptPatientVO = new ReceiptPatientParamVO();
		
		//과를 넣으려면 선택하게 해야하는데...
		MedroomTempVO medroomTempVO = manlessMapper.selectMedroomTemp(receiptPatientVO);
		if(medroomTempVO != null) {
			sKwa = medroomTempVO.getBmr_kwa();
		}
		
		sHwanja = "J";//구환
		
		if(vo.getVisit_type().equals("first")) {
			sHwanja = "C";//신환
		}
		
		//crm은 종별구별 91로 고정
		sJogubun = "91";
		
		receiptPatientVO.setsHwanja(sHwanja);
		receiptPatientVO.setPtno(sPtno);
		receiptPatientVO.setName(patientVO.getBpt_name());
		receiptPatientVO.setsYAge(patientVO.getBpt_yage());
		receiptPatientVO.setsMAge(patientVO.getBpt_mage());
		receiptPatientVO.setSex(patientVO.getBpt_sex());
		receiptPatientVO.setJumin1(patientVO.getBpt_resno());
		receiptPatientVO.setsCen(patientVO.getBpt_cen());
		receiptPatientVO.setsClass("R");
		//R은 온닥CRM 접수일 경우로 지정
		
		receiptPatientVO.setsKwa(sKwa);
		manlessMapper.insertReg(receiptPatientVO);
		
		sRegCnt = manlessMapper.selectRegno();
		if(sRegCnt.equals("0")) sRegCnt = manlessMapper.selectRegno2(receiptPatientVO);
		
		receiptPatientVO.setsRegCnt(sRegCnt);
		if(receiptPatientVO.getsClass().equals("J")) receiptPatientVO.setsClass("W");
		
		receiptPatientVO.setsJogubun(sJogubun);
		receiptPatientVO.setTmp3(receiptPatientVO.getsClass()+"N"+sHwanja);
		 // "rwj_tmp3"          varchar(10)     NULL,  /* 모바일접수구분 X       -     X      - X */
        /*         온닥 : O      미내방:N   신환:C */
        /*     온닥무인 : N      미내방:N   신환:C */
        /*                         내방:Y   구환:J */
        /*         똑닥 : D      미내방:N   신환:C */
        /*                         내방:Y   구환:J */
        /*     예방접종 : J      미내방:N   신환:C */
        /*                         내방:Y   구환:J */
        /*     키오스크 : K      미내방:N   신환:C */
        /*                         내방:Y   구환:J */
        /*         당근 : C      미내방:N   신환:C */
		
		if(receiptPatientVO.getsClass().equals("J")) receiptPatientVO.setJin_gubun("30");
		if(receiptPatientVO.getsHwanja().equals("C")) receiptPatientVO.setJin_kind("0");
		if(receiptPatientVO.getsClass().equals("J")) receiptPatientVO.setWait_sclass("50");
		
		manlessMapper.insertWaitjin(receiptPatientVO);
		
		return commonVO;
    }
}
