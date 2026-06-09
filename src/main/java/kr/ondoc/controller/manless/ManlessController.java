package kr.ondoc.controller.manless;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ondoc.domain.sybase.common.CommonVO;
import kr.ondoc.domain.sybase.manless.AgeTempVO;
import kr.ondoc.domain.sybase.manless.IdentificationVO;
import kr.ondoc.domain.sybase.manless.MedroomTempVO;
import kr.ondoc.domain.sybase.manless.MunjinVO;
import kr.ondoc.domain.sybase.manless.OptioninfoVO;
import kr.ondoc.domain.sybase.manless.ReceiptMedroomVO;
import kr.ondoc.domain.sybase.manless.ReceiptPatientCheckVO;
import kr.ondoc.domain.sybase.manless.ReceiptPatientVO;
import kr.ondoc.domain.sybase.manless.ReceiptPtnoLastVO;
import kr.ondoc.domain.sybase.manless.ReceiptPatientParamVO;
import kr.ondoc.domain.sybase.manless.ReceiptPatientSignVO;
import kr.ondoc.domain.sybase.manless.ReceiptPatientTempVO;
import kr.ondoc.mapper.sybase.manless.ManlessMapper;
import kr.ondoc.util.AES256;
import kr.ondoc.util.Base64Utils;
import kr.ondoc.util.FileSystemStorage;

@CrossOrigin(origins = "*")
@RestController
public class ManlessController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ManlessMapper manlessMapper;
	
	//문진 가져오기
	@RequestMapping(value="/selectMunjin", method=RequestMethod.GET)
    public List<MunjinVO> selectMunjin(String seq) throws Exception {
		return manlessMapper.selectMunjin(seq);
    }
	
	
	@RequestMapping(value="/selectReceiptMedroom", method=RequestMethod.GET)
    public List<ReceiptMedroomVO> selectReceiptMedroom() throws Exception {
		return manlessMapper.selectReceiptMedroom();
    }
	
	//대기중인 환자인지 체크
	@RequestMapping(value="/checkStandByJumin", method=RequestMethod.GET)
    public ReceiptPatientCheckVO checkStandByJumin(String jumin) throws Exception {
		ReceiptPatientCheckVO resultVO = new ReceiptPatientCheckVO();
		
		String jumin1 = jumin.substring(0, 7);
		String jumin2Enc = (new AES256()).encrypt(jumin.substring(7));
		
		//waitjin 테이블 검색
		int check = manlessMapper.checkStandBy(jumin1, jumin2Enc, "", "");
		
		if(check > 0) resultVO.setCheckStandBy(true);
		
		//같은 사람이 차트번호가 2개이상인 경우는 다른 종별로 접수한 경우인데 옵션에 따라 가장최근 접수한 종별로 접수하거나 접수실로 보냄
		resultVO.setCntPtno(manlessMapper.cntReceiptPatient(jumin1, jumin2Enc, "", ""));
		
		return resultVO;
	}
	
	@RequestMapping(value="/checkStandByPhone", method=RequestMethod.GET)
    public ReceiptPatientCheckVO checkStandByPhone(String jumin, String phone) throws Exception {
		ReceiptPatientCheckVO resultVO = new ReceiptPatientCheckVO();
		
		String jumin1 = "";
		String jumin2Enc = "";
		
		if(jumin != null && jumin.length() >= 13) {
			jumin1 = jumin.substring(0, 7);
			jumin2Enc = (new AES256()).encrypt(jumin.substring(7));
		}
		
		String phoneFront = "";
		String phoneEnd = "";
		
		if(phone != null && phone.length() >= 11) {
			phoneFront = phone.substring(3, 7);
			phoneEnd = phone.substring(7);
		}
		
		//waitjin 테이블 검색
		int check = manlessMapper.checkStandBy(jumin1, jumin2Enc, phoneFront, phoneEnd);
		
		if(check > 0) resultVO.setCheckStandBy(true);
		
		//같은 사람이 차트번호가 2개이상인 경우는 다른 종별로 접수한 경우인데 옵션에 따라 가장최근 접수한 종별로 접수하거나 접수실로 보냄
		resultVO.setCntPtno(manlessMapper.cntReceiptPatient(jumin1, jumin2Enc, phoneFront, phoneEnd));
		
		if(jumin != null && jumin.equals("")) {
			int cntPhoneMan = manlessMapper.cntPhoneMan(phoneFront, phoneEnd);
			
			//연락처로 검색시 차트번호가 2개이상인 경우 한사람인지 그 이상인지 확인 2명이상이면 true
			if(cntPhoneMan > 1) resultVO.setCheckPhoneManOneGreaterThan(true);
		}
		
		return resultVO;
	}
	
	@RequestMapping(value="/selectReceiptPatient", method=RequestMethod.GET)
    public ReceiptPatientVO selectReceiptPatient(String jumin, String phone) throws Exception {
		ReceiptPatientVO resultVO = new ReceiptPatientVO();
		
		String jumin1 = "";
		String jumin2Enc = "";
		
		if(jumin != null && jumin.length() >= 13) {
			jumin1 = jumin.substring(0, 7);
			jumin2Enc = (new AES256()).encrypt(jumin.substring(7));
		}
		
		String phoneFront = "";
		String phoneEnd = "";
		
		if(phone != null && phone.length() >= 11) {
			phoneFront = phone.substring(3, 7);
			phoneEnd = phone.substring(7);
		}
		
		//종별 21, 39 사이인 경우 검색
		List<ReceiptPatientVO> receiptPatientVO = manlessMapper.selectReceiptPatient("ok", jumin1, jumin2Enc, phoneFront, phoneEnd);
		
		//종별 21, 39 사이인 경우가 없을경우 그외 검색
		if(receiptPatientVO.size() == 0) receiptPatientVO = manlessMapper.selectReceiptPatient("", jumin1, jumin2Enc, phoneFront, phoneEnd);
		
		if(receiptPatientVO.size() == 1) {
			resultVO = receiptPatientVO.get(0);
			resultVO.setOnw_medroom(manlessMapper.selectReceiptPatientMedroom(resultVO.getBpt_ptno()));
		}
		
		return resultVO;
	}
	
	//접수완료 처리
	@RequestMapping(value="/insertReceiptPatient", method=RequestMethod.POST)
    public CommonVO insertReceiptPatient(ReceiptPatientParamVO vo) throws Exception {
		CommonVO resultVO = new CommonVO();
		
		OptioninfoVO optioninfo = manlessMapper.selectOption();	
		//String sEtc49 = optioninfo.getXpt_etc49();//1이면 임시테이블에 넣는방식, 2이면 직접입력 현재는 2만 쓰므로 옵션값 무시
		
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
		
		if(vo.getJumin2().length() == 6) vo.setJumin2((new AES256()).encrypt(vo.getJumin2()));
		
		//가장최근의 환자 차트번호와 종별값 가져옴
		ReceiptPatientTempVO patientTempVO = manlessMapper.selectPatientTemp(vo);
		if(patientTempVO != null) {
			sPtno = patientTempVO.getBpt_ptno();
			sJogubun = patientTempVO.getBpt_jogubun();
		}
		
		MedroomTempVO medroomTempVO = manlessMapper.selectMedroomTemp(vo);
		if(medroomTempVO != null) {
			sKwa = medroomTempVO.getBmr_kwa();
		}
		
		if(sPtno.equals("")) {//신환
		////////////////////////////////////////////////////////////////////////
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
			
		////////////////////////////////////////////////////////////////////////	
		} else {
			sHwanja = "J";//구환
		}
		
		AgeTempVO age = manlessMapper.selectAgeTemp(vo.getBirth());
		
		sCen = vo.getBirth().substring(0, 2);
		
		sYAge = age.getyAge();
		sMAge = age.getmAge();
		
		vo.setsHwanja(sHwanja);
		vo.setPtno(sPtno);
		vo.setsYAge(sYAge);
		vo.setsMAge(sMAge);
		vo.setsCen(sCen);
		
		if(sHwanja.equals("C")) {//신환
			////////////////////////////////////////////////////////////////////////
			int cnt = manlessMapper.selectPatientCnt(sPtno);
			
			if(cnt >= 1) {
				resultVO.setStatus("1003");
				resultVO.setMessage("receipt fail");
				
				return resultVO;
			}
			
			sJogubun = "21";
			//vo.setJumin2((new AES256()).encrypt(vo.getJumin2()));
			
			manlessMapper.insertPatient(vo);
			
			ReceiptPatientSignVO patientSignVO = new ReceiptPatientSignVO();
			patientSignVO.setPtno(vo.getPtno());
			patientSignVO.setName(vo.getName());
			//sign dataurl to byte[]
			if(!vo.getSign().equals("")) {
				patientSignVO.setPsign(fnDataUrlToJpgByte(vo.getSign()));
				manlessMapper.insertPatientSign(patientSignVO);
			}
			
			//동의일자 업데이트
			manlessMapper.updatePatient(sPtno);
					
			if((sNewPtno.equals("2") || sNewPtno.equals("3")) 
					&& (sPtno.length()==1 || !sPtno.substring(0, 2).equals("M-"))) {
				manlessMapper.updateLastNptno(sPtno);
			} else {
				manlessMapper.updateLastMptno(sPtno);
			}
			////////////////////////////////////////////////////////////////////////
		}
		
		vo.setsKwa(sKwa);
		manlessMapper.insertReg(vo);
		
		sRegCnt = manlessMapper.selectRegno();
		if(sRegCnt.equals("0")) sRegCnt = manlessMapper.selectRegno2(vo);
		
		vo.setsRegCnt(sRegCnt);
		if(vo.getsClass().equals("J")) vo.setsClass("W");
		
		vo.setsJogubun(sJogubun);
		vo.setTmp3(vo.getsClass()+"N"+sHwanja);
		
		if(vo.getsClass().equals("J")) vo.setJin_gubun("30");
		if(vo.getsHwanja().equals("C")) vo.setJin_kind("0");
		if(vo.getsClass().equals("J")) vo.setWait_sclass("50");
		
		manlessMapper.insertWaitjin(vo);
		
		if(!vo.getReply().equals(""))
			manlessMapper.insertMunjinReply(vo);
		
		return resultVO;
	}
	
	//dataUrl 받아서 BLOB타입에 저장할수 있게 값 생성
		public byte[] fnDataUrlToJpgByte(String dataUrl) throws IOException {
			String filePath = "C:\\StNeo\\Bin\\ondocportalserver\\Sign";
			
			byte[] image = Base64Utils.decodeBase64ToBytes(dataUrl);
			
			Path path = Paths.get("C:\\StNeo\\Bin\\ondocportalserver");
			FileSystemStorage storage = new FileSystemStorage(path);
			storage.store(image, "Sign.png");
			
			
			File beforeFile = new File(filePath+".png");
			File afterFile  = new File(filePath+".jpg");

			File jpgFile = new File(filePath+".jpg");
			
			BufferedImage pngImage = ImageIO.read(beforeFile);
			
			BufferedImage jpgImage = new BufferedImage(pngImage.getWidth(), pngImage.getHeight(), BufferedImage.TYPE_INT_BGR);
			jpgImage.createGraphics().drawImage(pngImage, 0, 0, Color.white, null);
			ImageIO.write(jpgImage, "jpg", jpgFile);
			
			
			BufferedImage sourceimage = ImageIO.read(new File(filePath+".jpg"));
			ByteArrayOutputStream b = new ByteArrayOutputStream();
			
			ImageIO.write(sourceimage, "jpg", b);
		    byte[] jpgByteArray = b.toByteArray();
		    
		    Path png = Paths.get(filePath+".png");
		    Path jpg = Paths.get(filePath+".jpg");
		    try {
		    	Files.deleteIfExists(png);
		    	Files.deleteIfExists(jpg);
	    	} catch (IOException e) {
	    		e.printStackTrace();
	    	}
		    
		    return jpgByteArray;
		}
	
	public Boolean isNumaric(String val) {
		try {
	      Integer.parseInt(val);
	      return true;
	    } catch (NumberFormatException ex) {
	      return false;
	    }
	}
	
	@RequestMapping(value="/identification", method=RequestMethod.GET)
    public IdentificationVO selectIdentification(String ptno) throws Exception {
		return manlessMapper.selectIdentification(ptno);
    }
}
