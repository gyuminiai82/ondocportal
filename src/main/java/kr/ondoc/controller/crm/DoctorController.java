package kr.ondoc.controller.crm;

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
import kr.ondoc.domain.sybase.crm.CrmDoctorVO;
import kr.ondoc.domain.sybase.crm.CrmPatientVO;
import kr.ondoc.domain.sybase.crm.CrmUsrmngrVO;
import kr.ondoc.mapper.sybase.crm.CrmDoctorMapper;
import kr.ondoc.util.Sha256;

@CrossOrigin(origins = "*")
@RestController
public class DoctorController {
private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CrmDoctorMapper doctorMapper;
	
	@RequestMapping(value="/crmDoctor", method=RequestMethod.GET)
    public List<CrmDoctorVO> selectDoctor() throws Exception {
		return doctorMapper.selectDoctor();
    }
	
	@RequestMapping(value="/crmDoctor", method=RequestMethod.POST)
	public CommonVO insertUsrmngr(CrmDoctorVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		
		System.out.println("=====================doctorInsert");
		
		try {
			doctorMapper.insertDoctor(vo);  
		} catch (Exception e) {
			System.out.println("====================="+e.getMessage());
			
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			return commonVO;
		}
		
		return commonVO;
	  }
	
	@RequestMapping(value="/crmDoctor", method=RequestMethod.PUT)
	public CommonVO updateDoctor(CrmDoctorVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		
		try {
			doctorMapper.updateDoctor(vo);  
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			return commonVO;
		}
		
		return commonVO;
	}
	
	@RequestMapping(value="/crmDoctor", method=RequestMethod.DELETE)
	public CommonVO deleteDoctor(CrmDoctorVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		
		try {
			doctorMapper.deleteDoctor(vo);  
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			return commonVO;
		}
		
		return commonVO;
	}
	
}
