package kr.ondoc.controller.legacy.ondocplus;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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

import kr.ondoc.domain.sybase.legacy.CommonVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ManlessQuestionDataVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ManlessQuestionParamVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ManlessQuestionResponseDataVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ManlessQuestionResponseParamVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ManlessQuestionResponseVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ManlessQuestionVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ManlessReceiptAgeVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ManlessReceiptLastVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ManlessReceiptMedroomVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ManlessReceiptOptioninfoVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ManlessReceiptParamVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ManlessReceiptPatientSignVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ManlessReceiptPatientVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ManlessSunapDataVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ManlessSunapVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ReceiptPatientDataVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ReceiptPatientParamVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.ReceiptPatientVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.SupportStateDataVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.SupportStateParamVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.SupportStateVO;
import kr.ondoc.domain.sybase.penchart.TempGroupAuthVO;
import kr.ondoc.error.NoResultException;
import kr.ondoc.mapper.sybase.legacy.ondocplus.OldManlessReceiptMapper;
import kr.ondoc.util.AES256;
import kr.ondoc.util.Base64Utils;
import kr.ondoc.util.FileSystemStorage;
import kr.ondoc.util.ProcessStream;

@CrossOrigin(origins = "*")
@RestController
public class OldManlessReceiptController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private OldManlessReceiptMapper mapper;
	
	private static final String STD_IN = "stdin";
    private static final String STD_ERR = "stderr";
	
	@RequestMapping(value="/manlessQuestion", method=RequestMethod.GET)
    public ManlessQuestionVO selectManlessQuestion(ManlessQuestionParamVO vo) throws Exception {
		ManlessQuestionVO resultVO = new ManlessQuestionVO();
		
		List<ManlessQuestionDataVO> dataList = mapper.selectManlessQuestion(vo);
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/manlessQuestion", method=RequestMethod.POST)
    public CommonVO insertManlessQuestion(ManlessQuestionParamVO vo) throws Exception {
		CommonVO resultVO = new CommonVO();
		mapper.insertManlessQuestion(vo);
		
		return resultVO;
    }
	
	@RequestMapping(value="/manlessQuestion", method=RequestMethod.PUT)
    public CommonVO updateManlessQuestion(ManlessQuestionParamVO vo) throws Exception {
		CommonVO resultVO = new CommonVO();
		mapper.updateManlessQuestion(vo);
		
		return resultVO;
    }
	
	@RequestMapping(value="/manlessQuestion", method=RequestMethod.DELETE)
    public CommonVO deleteManlessQuestion(ManlessQuestionParamVO vo) throws Exception {
		CommonVO resultVO = new CommonVO();
		mapper.deleteManlessQuestion(vo);
		
		return resultVO;
    }
	
	@RequestMapping(value="/ReceiptPatient", method=RequestMethod.GET)
    public ReceiptPatientVO selectReceiptPatient(ReceiptPatientParamVO vo) throws Exception {
		ReceiptPatientVO resultVO = new ReceiptPatientVO();
		
		String jumin2 = vo.getJumin2();
		
		if(!jumin2.equals("")) {
			vo.setJumin2((new AES256()).encrypt(vo.getJumin2()));
			
			int intChkJumin = mapper.chkPatient(vo);
			
			if(intChkJumin == 0) {
				Runtime rt = Runtime.getRuntime();
				
				String exec = "C:\\StNeo\\Bin\\Resno2To3.exe "+jumin2;
				Process pro;
				ProcessStream processInStream = null;
		        ProcessStream processErrStream = null;
				
				try {
					pro = rt.exec(exec);
					
					processInStream = new ProcessStream(STD_IN, pro.getInputStream());
		            processErrStream = new ProcessStream(STD_ERR, pro.getErrorStream());
		            
		            processInStream.start();
		            processErrStream.start();
		            pro.getOutputStream().close();
		            
					pro.waitFor();
				} catch (Exception e) {
					// TODO: handle exception
					logger.warn(e.getMessage());
				}
				
				String path = "C:\\StNeo\\Bin\\ondocportalserver\\" + jumin2 + ".txt";
				
				FileInputStream input=new FileInputStream(path);
		        InputStreamReader reader=new InputStreamReader(input,"EUC-KR");
		        BufferedReader in=new BufferedReader(reader);
				 
		        int ch;
		        
		        StringBuilder stringBuilder = new StringBuilder();
		        while ((ch = reader.read()) != -1) {
		        	stringBuilder.append((char) ch);
		        }
		        
		        input.close();
		        
		        vo.setJumin2_old(stringBuilder.toString().trim());
		        
		        int intChkJuminEnc = mapper.chkPatientEnc(vo);
		        
		        if(intChkJuminEnc > 0) {
		        	mapper.updatePatientResno3(vo);
		        }
		        
		        File file = new File(path);
		        
		    	if( file.exists() ){
		    		if(file.delete()){
		    			//System.out.println("delete file success");
		    		}else{
		    			//System.out.println("delete file failure");
		    		}
		    	}else{
		    		//System.out.println("not exist file");
		    	}
			}
		}
		
		//대기중인지 확인
		int manlessCnt = mapper.selectReceiptPatient(vo);
		
		//대기중이면 접수 못하도록 함
		if(manlessCnt > 0) {
			resultVO.setMessage("exist");
			return resultVO;
		}
		
		//같은 사람이 차트번호가 두개일 경우 확인을 위해 추가
        //주민번호로는 확인이 되나 연락처만으로는 정확하지 않음 가족끼리 같은 연락처를 쓸 수 있기 때문에
		int cntPtno = mapper.selectReceiptPatient2(vo);
		
		//연락처로 차트가 두개인 이유가 한사람인건지 두사람인지 확인해야함
		int cntPhoneMan = 1;
		if(vo.getJumin().equals("")) 
		{
			List<ReceiptPatientDataVO> dataList = mapper.selectReceiptPatient3(vo);
			cntPhoneMan = dataList.size();
		} else {
			cntPhoneMan = 1;
		}
		
		//System.out.println("manlessCnt:"+manlessCnt+", cntPtno:"+cntPtno+", cntPhoneMan:"+cntPhoneMan);
		
		List<ReceiptPatientDataVO> dataList = mapper.selectReceiptPatient4(vo);
		
		resultVO.setCntPtno(Integer.toString(cntPtno));
		resultVO.setCntPhoneMan(Integer.toString(cntPhoneMan));
		
		if(dataList.size() == 0) {
			dataList = mapper.selectReceiptPatient5(vo);
		}
		
		if(dataList.size() == 1) {
			dataList.get(0).setOnw_medroom(mapper.selectReceiptPatientMedroom(dataList.get(0).getBpt_ptno()));
			dataList.get(0).setBpt_resno2((new AES256()).decrypt(dataList.get(0).getBpt_resno3()));
		}
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/ReceiptPatientNoConvert", method=RequestMethod.GET)
    public ReceiptPatientVO selectReceiptPatientNoConvert(ReceiptPatientParamVO vo) throws Exception {
		ReceiptPatientVO resultVO = new ReceiptPatientVO();
		
		vo.setJumin2((new AES256()).encrypt(vo.getJumin2()));
		
		/*
		String jumin2 = vo.getJumin2();
		
		if(!jumin2.equals("")) {
			vo.setJumin2((new AES256()).encrypt(vo.getJumin2()));
			
			int intChkJumin = mapper.chkPatient(vo);
			
			if(intChkJumin == 0) {
				Runtime rt = Runtime.getRuntime();
				
				String exec = "C:\\StNeo\\Bin\\Resno2To3.exe "+jumin2;
				Process pro;
				ProcessStream processInStream = null;
		        ProcessStream processErrStream = null;
				
				try {
					pro = rt.exec(exec);
					
					processInStream = new ProcessStream(STD_IN, pro.getInputStream());
		            processErrStream = new ProcessStream(STD_ERR, pro.getErrorStream());
		            
		            processInStream.start();
		            processErrStream.start();
		            pro.getOutputStream().close();
		            
					pro.waitFor();
				} catch (Exception e) {
					// TODO: handle exception
					logger.warn(e.getMessage());
				}
				
				String path = "C:\\StNeo\\Bin\\ondocportalserver\\" + jumin2 + ".txt";
				
				FileInputStream input=new FileInputStream(path);
		        InputStreamReader reader=new InputStreamReader(input,"EUC-KR");
		        BufferedReader in=new BufferedReader(reader);
				 
		        int ch;
		        
		        StringBuilder stringBuilder = new StringBuilder();
		        while ((ch = reader.read()) != -1) {
		        	stringBuilder.append((char) ch);
		        }
		        
		        input.close();
		        
		        vo.setJumin2_old(stringBuilder.toString().trim());
		        
		        int intChkJuminEnc = mapper.chkPatientEnc(vo);
		        
		        if(intChkJuminEnc > 0) {
		        	mapper.updatePatientResno3(vo);
		        }
		        
		        File file = new File(path);
		        
		    	if( file.exists() ){
		    		if(file.delete()){
		    			//System.out.println("delete file success");
		    		}else{
		    			//System.out.println("delete file failure");
		    		}
		    	}else{
		    		//System.out.println("not exist file");
		    	}
			}
		}
		*/
		
		//대기중인지 확인
		int manlessCnt = mapper.selectReceiptPatient(vo);
		
		//대기중이면 접수 못하도록 함
		if(manlessCnt > 0) {
			resultVO.setMessage("exist");
			return resultVO;
		}
		
		//같은 사람이 차트번호가 두개일 경우 확인을 위해 추가
        //주민번호로는 확인이 되나 연락처만으로는 정확하지 않음 가족끼리 같은 연락처를 쓸 수 있기 때문에
		int cntPtno = mapper.selectReceiptPatient2(vo);
		
		//연락처로 차트가 두개인 이유가 한사람인건지 두사람인지 확인해야함
		int cntPhoneMan = 1;
		if(vo.getJumin().equals("")) 
		{
			List<ReceiptPatientDataVO> dataList = mapper.selectReceiptPatient3(vo);
			cntPhoneMan = dataList.size();
		} else {
			cntPhoneMan = 1;
		}
		
		//System.out.println("manlessCnt:"+manlessCnt+", cntPtno:"+cntPtno+", cntPhoneMan:"+cntPhoneMan);
		
		List<ReceiptPatientDataVO> dataList = mapper.selectReceiptPatient4(vo);
		
		resultVO.setCntPtno(Integer.toString(cntPtno));
		resultVO.setCntPhoneMan(Integer.toString(cntPhoneMan));
		
		if(dataList.size() == 0) {
			dataList = mapper.selectReceiptPatient5(vo);
		}
		
		if(dataList.size() == 1) {
			dataList.get(0).setOnw_medroom(mapper.selectReceiptPatientMedroom(dataList.get(0).getBpt_ptno()));
			dataList.get(0).setBpt_resno2((new AES256()).decrypt(dataList.get(0).getBpt_resno3()));
		}
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	//TO POST
	@RequestMapping(value="/manlessReceipt", method=RequestMethod.POST)
    public CommonVO insertManlessReceipt(ManlessReceiptParamVO vo) throws Exception {
		CommonVO resultVO = new CommonVO();
		
		ManlessReceiptOptioninfoVO optioninfo = mapper.selectOption();	
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

		vo.setJumin2((new AES256()).encrypt(vo.getJumin2()));
		
		//vo.setJumin1Front(vo.getJumin1().substring(0, 7));
		ManlessReceiptPatientVO patientVO = mapper.selectPatient(vo);
		if(patientVO != null) {
			sPtno = patientVO.getBpt_ptno();
			sJogubun = patientVO.getBpt_jogubun();
		}
		
		ManlessReceiptMedroomVO medroomVO = mapper.selectMedroom(vo);
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
		
		if(sHwanja.equals("C")) {
			int cnt = mapper.selectPatientCnt(sPtno);
			
			if(cnt == 1) {
				resultVO.setStatus("1003");
				resultVO.setMessage("receipt fail");
				
				return resultVO;
			}
			
			sJogubun = "21";
			//vo.setJumin2((new AES256()).encrypt(vo.getJumin2()));
			
			//TODO 제거
			mapper.insertPatient(vo);
			
			ManlessReceiptPatientSignVO patientSignVO = new ManlessReceiptPatientSignVO();
			patientSignVO.setPtno(vo.getPtno());
			patientSignVO.setName(vo.getName());
			//sign dataurl to byte[]
			if(!vo.getSign().equals("")) {
				patientSignVO.setPsign(fnDataUrlToJpgByte(vo.getSign()));
				mapper.insertPatientSign(patientSignVO);
			}
			
			//동의일자 업데이트
			mapper.updatePatient(sPtno);
					
			if((sNewPtno.equals("2") || sNewPtno.equals("3")) 
					&& (sPtno.length()==1 || !sPtno.substring(0, 2).equals("M-"))) {
				mapper.updateLastNptno(sPtno);
			} else {
				mapper.updateLastMptno(sPtno);
			}
		}
		
		vo.setsKwa(sKwa);
		mapper.insertReg(vo);
		
		sRegCnt = mapper.selectRegno();
		if(sRegCnt.equals("0")) sRegCnt = mapper.selectRegno2(vo);
		
		vo.setsRegCnt(sRegCnt);
		if(vo.getsClass().equals("J")) vo.setsClass("W");
		
		vo.setsJogubun(sJogubun);
		vo.setTmp3(vo.getsClass()+"N"+sHwanja);
		
		if(vo.getsClass().equals("J")) vo.setJin_gubun("30");
		if(vo.getsHwanja().equals("C")) vo.setJin_kind("0");
		if(vo.getsClass().equals("J")) vo.setWait_sclass("50");
		
		mapper.insertWaitjin(vo);

		return resultVO;
    }
	
	@RequestMapping(value="/manlessQuestionResponse", method=RequestMethod.GET)
    public ManlessQuestionResponseVO selectManlessQuestionResponse(ManlessQuestionResponseParamVO vo) throws Exception {
		ManlessQuestionResponseVO resultVO = new ManlessQuestionResponseVO();
		
		List<ManlessQuestionResponseDataVO> dataList = mapper.selectManlessQuestionReply(vo);
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/manlessQuestionResponse", method=RequestMethod.POST)
    public CommonVO insertmanlessQuestionResponse(ManlessQuestionResponseParamVO vo) throws Exception {
		CommonVO resultVO = new CommonVO();
		
		mapper.insertManlessQuestionReply(vo);

		return resultVO;
    }
	
	@RequestMapping(value="/ManlessSunap", method=RequestMethod.GET)
    public ManlessSunapVO selectManlessSunap() throws Exception {
		ManlessSunapVO resultVO = new ManlessSunapVO();
		
		List<ManlessSunapDataVO> dataList = mapper.selectManlessSunap();
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
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
}
