package kr.ondoc.controller.legacy.ondoc;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Stack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ondoc.domain.sybase.legacy.CommonVO;
import kr.ondoc.domain.sybase.legacy.ondoc.InfoValidCheckParamVO;
import kr.ondoc.domain.sybase.legacy.ondoc.InfoValidCheckVO;
import kr.ondoc.domain.sybase.legacy.ondoc.JinryoDataVO;
import kr.ondoc.domain.sybase.legacy.ondoc.JinryoParamVO;
import kr.ondoc.domain.sybase.legacy.ondoc.JinryoPatientDataVO;
import kr.ondoc.domain.sybase.legacy.ondoc.JinryoReceiveParamVO;
import kr.ondoc.domain.sybase.legacy.ondoc.JinryoReserveDataVO;
import kr.ondoc.domain.sybase.legacy.ondoc.JinryoReserveParamVO;
import kr.ondoc.domain.sybase.legacy.ondoc.JinryoReserveVO;
import kr.ondoc.domain.sybase.legacy.ondoc.JinryoVO;
import kr.ondoc.domain.sybase.legacy.ondoc.MedroomDataVO;
import kr.ondoc.domain.sybase.legacy.ondoc.MedroomReceiptDataVO;
import kr.ondoc.domain.sybase.legacy.ondoc.MedroomReceiptVO;
import kr.ondoc.domain.sybase.legacy.ondoc.MedroomVO;
import kr.ondoc.domain.sybase.legacy.ondoc.ReservePhysicalDataVO;
import kr.ondoc.domain.sybase.legacy.ondoc.ReservePhysicalParamVO;
import kr.ondoc.domain.sybase.legacy.ondoc.ReservePhysicalVO;
import kr.ondoc.domain.sybase.legacy.ondoc.WaitJinryoDataVO;
import kr.ondoc.domain.sybase.legacy.ondoc.WaitJinryoVO;
import kr.ondoc.domain.sybase.legacy.ondoc.WorkDateDataVO;
import kr.ondoc.domain.sybase.legacy.ondoc.WorkDateExceptDataVO;
import kr.ondoc.domain.sybase.legacy.ondoc.WorkDateVO;
import kr.ondoc.domain.sybase.legacy.ondoc.WorkPlanDataVO;
import kr.ondoc.domain.sybase.legacy.ondoc.WorkPlanParamVO;
import kr.ondoc.domain.sybase.legacy.ondoc.WorkPlanVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ManlessReceiptAgeVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ManlessReceiptLastVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ManlessReceiptMedroomVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ManlessReceiptOptioninfoVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ManlessReceiptPatientVO;
import kr.ondoc.mapper.sybase.legacy.ondoc.OldOndocMapper;
import kr.ondoc.mapper.sybase.legacy.ondocplus.OldManlessReceiptMapper;

@CrossOrigin(origins = "*")
@RestController
public class OldOndocController {
	@Autowired
	private OldOndocMapper mapper;
	
	@Autowired
	private OldManlessReceiptMapper manlessMapper;
	
	@RequestMapping(value="/Waitjinryo", method=RequestMethod.GET)
    public WaitJinryoVO selectWaitjinryo() throws Exception {
		WaitJinryoVO resultVO = new WaitJinryoVO();
		
		List<WaitJinryoDataVO> dataList = mapper.selectWaitJinryo();
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
		
	@RequestMapping(value="/ReservePhysicalCnt", method=RequestMethod.GET)
    public ReservePhysicalVO selectReservePhysicalCnt(ReservePhysicalParamVO vo) throws Exception {
		ReservePhysicalVO resultVO = new ReservePhysicalVO();
		
		String ptno = mapper.selectPtnos(vo);
		
		vo.setPtno(ptno);
		
		List<ReservePhysicalDataVO> dataList = mapper.selectPhysicalCnt(vo);
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/MedroomReceipt", method=RequestMethod.GET)
    public MedroomReceiptVO selectMedroomReceipt() throws Exception {
		MedroomReceiptVO resultVO = new MedroomReceiptVO();
		
		List<MedroomReceiptDataVO> dataList = mapper.selectMedroomReceipt();
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/Medroom", method=RequestMethod.GET)
    public MedroomVO selectMedroom() throws Exception {
		MedroomVO resultVO = new MedroomVO();
		
		List<MedroomDataVO> dataList = mapper.selectMedroom();
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/Workdate", method=RequestMethod.GET)
    public WorkDateVO selectWorkdate(String code) throws Exception {
		WorkDateVO resultVO = new WorkDateVO();
		
		List<WorkDateExceptDataVO> exceptList = mapper.selectWorkDateExcept(code);
		
		StringBuilder strExceptDate = new StringBuilder();
		String except = "''";
		
		if(exceptList.size() > 0) {
			Stack<Integer> exceptDate = new Stack<Integer>();
			
			for(WorkDateExceptDataVO vo : exceptList) {
				for(int i=Integer.parseInt(vo.getBds_fdate()); i<=Integer.parseInt(vo.getBds_tdate()); i++) {
					exceptDate.push(i);
				}
			}
			
			for(int i=0; i<exceptDate.size(); i++) {
				strExceptDate.append(exceptDate.get(i)+",");
			}
			
			except = strExceptDate.substring(0, strExceptDate.length()-1);
		}

		List<WorkDateDataVO> dataList = mapper.selectWorkDate(code, except);
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/Workplan", method=RequestMethod.GET)
    public WorkPlanVO selectWorkplan(WorkPlanParamVO vo) throws Exception {
		WorkPlanVO resultVO = new WorkPlanVO();
		
//		int cnt = mapper.selectWorkPlanCnt(vo);	//	0 매주, 1 당일
//		
//		vo.setFlagCheck(Integer.toString(cnt));

		List<WorkPlanDataVO> dataList = mapper.selectWorkPlan(vo);
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);

		return resultVO;
    }
	
	//진료예약
	@RequestMapping(value="/JinryoReserve", method=RequestMethod.GET)
    public JinryoReserveVO selectJinryoReserve(JinryoReserveParamVO vo) throws Exception {
		JinryoReserveVO resultVO = new JinryoReserveVO();

		List<JinryoReserveDataVO> dataList = mapper.selectJinryoReserve(vo);
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);

		return resultVO;
    }
	//진료예약
	@RequestMapping(value="/JinryoReserve", method=RequestMethod.POST)
    public CommonVO insertJinryoReserve(JinryoReserveParamVO vo) throws Exception {
		CommonVO resultVO = new CommonVO();
		mapper.insertJinryoReserve(vo);
		
		return resultVO;
    }
	//진료예약
	@RequestMapping(value="/JinryoReserve", method=RequestMethod.PUT)
    public CommonVO updateJinryoReserve(JinryoReserveParamVO vo) throws Exception {
		CommonVO resultVO = new CommonVO();
		mapper.updateJinryoReserve(vo);
		
		return resultVO;
    }
	//진료예약
	@RequestMapping(value="/JinryoReserve", method=RequestMethod.DELETE)
    public CommonVO deleteJinryoReserve(JinryoReserveParamVO vo) throws Exception {
		CommonVO resultVO = new CommonVO();
		mapper.deleteJinryoReserve(vo);
		
		return resultVO;
    }
	//진료접수
	@RequestMapping(value="/JinryoReceive", method=RequestMethod.POST)
    public CommonVO insertJinryoReceive(JinryoReceiveParamVO vo) throws Exception {
		CommonVO resultVO = new CommonVO();
		
		ManlessReceiptOptioninfoVO optioninfo = manlessMapper.selectOption();	
		
		String sHwanja = "";
		String sPtno = "";
		String sHpno = "";
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
		String sJumin = "";
		
		ManlessReceiptPatientVO patientVO = mapper.selectPatient(vo);
		if(patientVO != null) {
			sHpno = patientVO.getBpt_hpno();
			
			if(sHpno.equals(vo.getHpno())) {
				sPtno = patientVO.getBpt_ptno();
			} else {
				sHpno = sHpno.replaceAll("-",  "");
				if(sHpno.equals(vo.getHpno())) {
					sPtno = patientVO.getBpt_ptno();
				} else {
					sHpno = sHpno.replaceAll("-",  "");
					//sHpno = sHpno.replaceAll(")",  ""); 에러남
					if(sHpno.equals(vo.getHpno())) {
						sPtno = patientVO.getBpt_ptno();
					}
				}
			}
			
			if(!sPtno.equals("")) {
				sJogubun = patientVO.getBpt_jogubun();
			}
		}
		
		if(sJogubun.equals("")) sJogubun = "21";
		
		ManlessReceiptMedroomVO medroomVO = mapper.selectMedrooms(vo);
		if(medroomVO != null) {
			sKwa = medroomVO.getBmr_kwa();
		}
				
		if(medroomVO != null) {
			sKwa = medroomVO.getBmr_kwa();
		}
		
		if(sPtno.equals("")) {
			//신환인 경우
			ManlessReceiptLastVO lastVO = mapper.selectLast();
			
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
		} else {
			sHwanja = "J";//구환
		}
		
		ManlessReceiptAgeVO age = mapper.selectAge(vo.getBirth());
		sCen = vo.getBirth().substring(0, 2);
		
		sYAge = age.getyAge();
		sMAge = age.getmAge();
		
		vo.setsHwanja(sHwanja);
		vo.setPtno(sPtno);
		vo.setsYAge(sYAge);
		vo.setsMAge(sMAge);
		vo.setsCen(sCen);
		
		if(vo.getSex().equals("M") && sCen.equals("19")) {sJumin = vo.getBirth().substring(2, 8) + "1******";}
		if(vo.getSex().equals("F") && sCen.equals("19")) {sJumin = vo.getBirth().substring(2, 8) + "2******";}
		if(vo.getSex().equals("M") && sCen.equals("20")) {sJumin = vo.getBirth().substring(2, 8) + "3******";}
		if(vo.getSex().equals("F") && sCen.equals("20")) {sJumin = vo.getBirth().substring(2, 8) + "4******";}
		if(vo.getSex().equals("M") && sCen.equals("18")) {sJumin = vo.getBirth().substring(2, 8) + "9******";}
		if(vo.getSex().equals("F") && sCen.equals("18")) {sJumin = vo.getBirth().substring(2, 8) + "0******";}
		
		vo.setJumin1(sJumin);
		
		if(sHwanja.equals("C")) {
			int cnt = mapper.selectPatientCnt(sPtno);
			
			if(cnt == 1) {
				resultVO.setStatus("1003");
				resultVO.setMessage("receipt fail");
				
				return resultVO;
			}
			
			mapper.insertPatient(vo);
			mapper.insertMemo(vo);
			
			if((sNewPtno.equals("2") || sNewPtno.equals("3")) 
					&& (sPtno.length()==1 || !sPtno.substring(0, 2).equals("M-"))) {
				mapper.updateLastNptno(sPtno);
			} else {
				mapper.updateLastMptno(sPtno);
			}
		} else {
			if(!vo.getIdno().equals("")) {
				mapper.updatePatientIdno(vo);
			}
		}
		
		vo.setsKwa(sKwa);
		mapper.insertReg(vo);
		
		sRegCnt = mapper.selectRegno();
		if(sRegCnt.equals("0")) sRegCnt = mapper.selectRegno2(vo);
		
		vo.setsRegCnt(sRegCnt);
		if(vo.getsHwanja().equals("C")) vo.setJin_kind("0");
		
		vo.setsJogubun(sJogubun);
		vo.setTmp3("ON"+sHwanja);

		mapper.insertWaitjin(vo);
		
		return resultVO;
    }
	
	@RequestMapping(value="/JinryoReceive", method=RequestMethod.PUT)
    public CommonVO updateJinryoReceive(JinryoReceiveParamVO vo) throws Exception {
		CommonVO resultVO = new CommonVO();
		
		mapper.updateWaitjin(vo);
		
		return resultVO;
    }
	
	//진료내역
	@RequestMapping(value="/Jinryo", method=RequestMethod.GET)
    public JinryoVO selectJinryo(JinryoParamVO vo) throws Exception {
		JinryoVO resultVO = new JinryoVO();
		
		if(!vo.getName().equals("") && !vo.getHpno().equals("") && !vo.getBirth().equals("")) {
			String ptno = fnGetPtno(vo.getName(), vo.getHpno(), vo.getBirth());
			vo.setPtno(ptno);
			
			Date nowDate = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			String sCurrentDate = format.format(nowDate);
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(nowDate);
			cal.add(Calendar.DATE, Integer.parseInt(vo.getPeriod())*-1);
			
			vo.setStartDate(format.format(cal.getTime()));
			vo.setEndDate(sCurrentDate);
			
			List<JinryoDataVO> dataList = mapper.selectJinryo(vo);
			
			resultVO.setTotalCount(Integer.toString(dataList.size()));
			resultVO.setData(dataList);
		} else if(!vo.getIndate().equals("") && !vo.getPubSeq().equals("")) {
			vo.setIndateLeft(vo.getIndate().substring(0, 4));
			List<JinryoDataVO> dataList = mapper.selectJinryoDetail(vo);
			
			resultVO.setTotalCount(Integer.toString(dataList.size()));
			resultVO.setData(dataList);
		}
		
		return resultVO;
    }
	
	//개인정보 동의 체크
	@RequestMapping(value="/infoValidCheck", method=RequestMethod.GET)
    public InfoValidCheckVO infoValidCheck(InfoValidCheckParamVO vo) throws Exception {
		InfoValidCheckVO resultVO = new InfoValidCheckVO();
		
		String ptno = fnGetPtno(vo.getName(), vo.getHpno(), vo.getBirthday());
		
		String checkDate = mapper.selectInfoValidCheck(ptno);
		String endDate = "";
		
		if(checkDate != null) {
			String dateFormatType = "yyyyMMdd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormatType);
			Date date = simpleDateFormat.parse(checkDate);
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.MONTH, 6);
			
			endDate = simpleDateFormat.format(cal.getTime());
		}
		
		resultVO.setCheckDate(checkDate);
		resultVO.setCheckEndDate(endDate);
		
		if(!endDate.equals("")) {
			if(Integer.parseInt(endDate) > Integer.parseInt(vo.getBaseDay())) {
				resultVO.setValid("true");
			} else {
				resultVO.setValid("false");
			}
		} else {
			resultVO.setValid("false");
		}
			
		return resultVO;
    }
	
	public String fnGetPtno(String name, String hpno, String birth) throws Exception {
		List<JinryoPatientDataVO> ptno = mapper.selectPtno(name, birth);
				
		if(ptno.size() == 0) return "no matching data";
		
		StringBuilder strPtno = new StringBuilder();
		for (JinryoPatientDataVO patient : ptno) {
			if(patient.getBpt_hpno().replaceAll("[^0-9]", "").equals(hpno)) {
				strPtno.append(patient.getBpt_ptno()+",");
			}
		}
		
		if(strPtno.toString().equals("")) return "no matching phonenumber";
		
		return strPtno.toString().substring(0, strPtno.toString().length()-1);
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
