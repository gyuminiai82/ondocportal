package kr.ondoc.controller.healthcheck;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ondoc.domain.sybase.healthcheck.HealthCheckConsentVO;
import kr.ondoc.domain.sybase.healthcheck.HealthCheckDataVO;
import kr.ondoc.domain.sybase.healthcheck.HealthCheckParamVO;
import kr.ondoc.domain.sybase.healthcheck.HealthCheckVO;
import kr.ondoc.domain.sybase.legacy.CommonVO;
import kr.ondoc.domain.sybase.legacy.ondoc.JinryoReserveParamVO;
import kr.ondoc.domain.sybase.penchart.EmrAddVO;
import kr.ondoc.domain.sybase.penchart.TemplateBoilerplateDetailVO;
import kr.ondoc.mapper.sybase.healthcheck.HealthCheckMapper;
import kr.ondoc.util.AES256;
import kr.ondoc.util.Base64Utils;
import kr.ondoc.util.FileSystemStorage;

@CrossOrigin(origins = "*")
@RestController
public class HealthCheckController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private HealthCheckMapper mapper;
	
	@RequestMapping(value="/healthCheck", method=RequestMethod.GET)
    public HealthCheckVO selectHealthCheckList(HealthCheckParamVO vo) throws Exception {
		HealthCheckVO resultVO = new HealthCheckVO();
		
		List<HealthCheckDataVO> dataList = mapper.selectHealthCheckList(vo);
		
		for(int i=0; i<dataList.size(); i++) {
			HealthCheckDataVO healthCheckDataVO = dataList.get(i);
			
			healthCheckDataVO.setBpt_resno_decrypt((new AES256()).decrypt(healthCheckDataVO.getBpt_resno3()));	
		}
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/healthCheckConsent", method=RequestMethod.GET)
    public List<HealthCheckConsentVO> selectHealthCheckConsent(HealthCheckConsentVO vo) throws Exception {
		return mapper.selectHealthCheckConsent(vo);
    }
	
	@RequestMapping(value="/healthCheckConsent", method=RequestMethod.POST)
    public CommonVO insertHealthCheckConsent(HealthCheckConsentVO vo) throws Exception {
		CommonVO resultVO = new CommonVO();
		
		vo.setGrc_sign_image(fnDataUrlToJpgByte(vo.getGrc_sign()));
		
		mapper.insertHealthCheckConsent(vo);
		
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
}
