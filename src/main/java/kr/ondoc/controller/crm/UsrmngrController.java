package kr.ondoc.controller.crm;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ondoc.domain.sybase.common.CommonVO;
import kr.ondoc.domain.sybase.crm.CrmLoginParamVO;
import kr.ondoc.domain.sybase.crm.CrmLoginVO;
import kr.ondoc.domain.sybase.crm.CrmSettingVO;
import kr.ondoc.domain.sybase.crm.CrmUsrmngrVO;
import kr.ondoc.mapper.sybase.crm.CrmUsrmngrMapper;
import kr.ondoc.util.Sha256;

@CrossOrigin(origins = "*")
@RestController
public class UsrmngrController {
private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CrmUsrmngrMapper usrmngrMapper;
	
	@RequestMapping(value="/crmLogin", method=RequestMethod.GET)
	//public UsrmngrVO usrmngrSelect(UsrmngrVO vo) throws Exception {
    public CrmLoginVO select(CrmLoginParamVO vo) throws Exception {
		CrmLoginVO result = null;
		try {
			result = usrmngrMapper.selectLogin(vo);
			
			result.setXur_pass_hash(createPassHash(vo.getXur_pass()));
			
//			if(vo.getClient_ip().equals("210.220.255.17") || vo.getClient_ip().equals("210.220.255.18") || vo.getClient_ip().equals("210.220.255.10")) {
//				result.setXur_pass(vo.getXur_pass_hash());
//			}
		} catch (Exception e) {
			//throw new NoResultException("NO_RESULT");
			return new CrmLoginVO();
		}
		
        return result;
    }
	
	public static String createPassHash(String pass) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(pass.toUpperCase().getBytes(StandardCharsets.UTF_8));
            
            StringBuilder hexHash = new StringBuilder();
            for (byte b : hash) {
                hexHash.append(String.format("%02x", b));
            }
            
            return hexHash.toString().toUpperCase();
            
        } catch (Exception e) {
            throw new RuntimeException("SHA-256 해싱 실패", e);
        }
    }
	
	@RequestMapping(value="/crmUsrmngr", method=RequestMethod.GET)
    public List<CrmUsrmngrVO> selectUsrmngr(CrmUsrmngrVO vo) throws Exception {
		return usrmngrMapper.selectUsrmngr(vo);
    }
	
	@RequestMapping(value="/crmUsrmngrList", method=RequestMethod.GET)
    public List<CrmUsrmngrVO> selectUsrmngrList(CrmUsrmngrVO vo) throws Exception {
		return usrmngrMapper.selectListUsrmngr(vo);
    }
	
	@RequestMapping(value="/crmUsrmngr", method=RequestMethod.POST)
	public CommonVO insertUsrmngr(CrmUsrmngrVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		
		vo.setXur_id(vo.getXur_id().toUpperCase());
		vo.setXur_pass(Sha256.hash(vo.getXur_pass()));
		try {
			usrmngrMapper.insertUsrmngr(vo);  
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			return commonVO;
		}
		
		return commonVO;
	  }
	
	@RequestMapping(value="/crmUsrmngr", method=RequestMethod.PUT)
	public CommonVO updateUsrmngr(CrmUsrmngrVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		
		if(!vo.getXur_pass().equals("")) vo.setXur_pass(Sha256.hash(vo.getXur_pass()));
		try {
			usrmngrMapper.updateUsrmngr(vo);  
		} catch (Exception e) {
			commonVO.setStatus("500");
		commonVO.setMessage("failure");
			return commonVO;
		}
		
		return commonVO;
	}
}
