package kr.ondoc.controller.crm;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ondoc.domain.sybase.common.CommonVO;
import kr.ondoc.domain.sybase.crm.CrmCommonVO;
import kr.ondoc.domain.sybase.crm.CrmManlessQuestionReplyVO;
import kr.ondoc.domain.sybase.crm.CrmPatientVO;
import kr.ondoc.domain.sybase.crm.CrmRevptNaverVO;
import kr.ondoc.domain.sybase.manless.AgeTempVO;
import kr.ondoc.domain.sybase.manless.OptioninfoVO;
import kr.ondoc.domain.sybase.manless.ReceiptPatientSignVO;
import kr.ondoc.domain.sybase.manless.ReceiptPtnoLastVO;
import kr.ondoc.mapper.sybase.crm.CrmManlessQuestionReplyMapper;
import kr.ondoc.mapper.sybase.crm.CrmPatientMapper;
import kr.ondoc.mapper.sybase.crm.CrmRevptNaverMapper;
import kr.ondoc.mapper.sybase.manless.ManlessMapper;

import kr.ondoc.util.AES256;
import kr.ondoc.util.Base64Utils;
import kr.ondoc.util.FileSystemStorage;

@CrossOrigin(origins = "*")
@RestController
public class RevptNaverController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CrmRevptNaverMapper revptNaverMapper;
	
	@Autowired
	private CrmManlessQuestionReplyMapper manlessQuestionReplyMapper;
	
	@Autowired
	private ManlessMapper manlessMapper;
	
	@Autowired
	private CrmPatientMapper patientMapper;
	
	@RequestMapping(value="/crmManlessListPatientSearch", method=RequestMethod.GET)
    public List<CrmPatientVO> selectManlessListPatientSearch(String phone) throws Exception {
		return revptNaverMapper.selectPatient(phone.substring(0, 3), phone.substring(3, 7), phone.substring(7, 11));
    }
	
	@RequestMapping(value="/crmManlessRevptNaver", method=RequestMethod.POST)
	public CommonVO insertManlessRevptNaver(CrmRevptNaverVO vo) throws Exception {
		CrmCommonVO commonVO = new CrmCommonVO();

		try {
			vo.setRev_bookingkey("ONDOCCRM-"+generateAlphanumeric(20));
			
			if(vo.getRev_ptno().equals("")) {
				CrmPatientVO patientVO = new CrmPatientVO();
				
				String sPtno = "";
				String tmpPtno = "";
				String sNewPtno = "";
				String sLPtno = "";
				String sMPtno = "";
				
				OptioninfoVO optioninfo = manlessMapper.selectOption();
				
				//차트번호 생성 Start
				ReceiptPtnoLastVO lastVO = manlessMapper.selectLast();
				
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
				
				if(!vo.getRev_birth().equals("")) {
					AgeTempVO age = manlessMapper.selectAgeTemp(vo.getRev_birth());
					
					String cen  = vo.getRev_birth().substring(0, 2);
					
					patientVO.setBpt_cen(cen);
					patientVO.setBpt_yage(age.getyAge());
					patientVO.setBpt_mage(age.getmAge());
					
				}
				
				patientVO.setBpt_name(vo.getRev_name());
				patientVO.setBpt_hpno(vo.getRev_phone());
				patientVO.setBpt_birth(vo.getRev_birth());
				patientVO.setBpt_ptno(sPtno);
				int cnt = manlessMapper.selectPatientCnt(sPtno);
				
				if(cnt >= 1) {
					commonVO.setStatus("1003");
					commonVO.setMessage("receipt fail");
					
					return commonVO;
				}
				
				patientMapper.insertPatient(patientVO);  
				
				ReceiptPatientSignVO patientSignVO = new ReceiptPatientSignVO();
				patientSignVO.setPtno(patientVO.getBpt_ptno());
				patientSignVO.setName(patientVO.getBpt_name());
				//sign dataurl to byte[]
				try {
					
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				if((sNewPtno.equals("2") || sNewPtno.equals("3")) 
						&& (sPtno.length()==1 || !sPtno.substring(0, 2).equals("M-"))) {
					manlessMapper.updateLastNptno(sPtno);
				} else {
					manlessMapper.updateLastMptno(sPtno);
				}
				
				//여기서 부터 접수 처리 위는 차트번호 생성해서 patient에 넣는 중
				vo.setRev_ptno(sPtno);
				revptNaverMapper.insertRevptNaver(vo);
				
				String seq = revptNaverMapper.getMaxSeq();
				
				//문진 저장
				CrmManlessQuestionReplyVO replyVO = new CrmManlessQuestionReplyVO();
				
				if(!vo.getReply().equals("")) {
					replyVO.setRev_seq(seq);
					replyVO.setName(vo.getRev_name());
					replyVO.setHpno(vo.getRev_phone());
					replyVO.setPtno(vo.getRev_ptno());
					replyVO.setReply(vo.getReply());
					
					manlessQuestionReplyMapper.insertManlessQuestionReply(replyVO);
				}
			} else {
				revptNaverMapper.insertRevptNaver(vo);
				
				String seq = revptNaverMapper.getMaxSeq();
				
				//문진 저장
				CrmManlessQuestionReplyVO replyVO = new CrmManlessQuestionReplyVO();
				
				if(!vo.getReply().equals("")) {
					replyVO.setRev_seq(seq);
					replyVO.setName(vo.getRev_name());
					replyVO.setHpno(vo.getRev_phone());
					replyVO.setPtno(vo.getRev_ptno());
					replyVO.setReply(vo.getReply());
					
					manlessQuestionReplyMapper.insertManlessQuestionReply(replyVO);
				}
			}
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmManlessTest", method=RequestMethod.GET)
	public CommonVO crmManlessTest(CrmRevptNaverVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();

		return commonVO;
    }
	
	public static String generateAlphanumeric(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        
        return sb.toString();
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
