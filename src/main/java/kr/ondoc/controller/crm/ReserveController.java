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
import kr.ondoc.domain.sybase.crm.CrmMedroomVO;
import kr.ondoc.domain.sybase.crm.CrmReceiptPatientParamVO;
import kr.ondoc.domain.sybase.crm.CrmReceiptPatientVO;
import kr.ondoc.domain.sybase.crm.CrmReserveCancelVO;
import kr.ondoc.domain.sybase.crm.CrmReserveGubunVO;
import kr.ondoc.domain.sybase.crm.CrmReserveParamVO;
import kr.ondoc.domain.sybase.crm.CrmReserveVO;
import kr.ondoc.domain.sybase.crm.CrmWorkplanParamVO;
import kr.ondoc.domain.sybase.crm.CrmWorkplanVO;
import kr.ondoc.mapper.sybase.crm.CrmMedroomMapper;
import kr.ondoc.mapper.sybase.crm.CrmReceiptMapper;
import kr.ondoc.mapper.sybase.crm.CrmReserveMapper;

@CrossOrigin(origins = "*")
@RestController
public class ReserveController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CrmReserveMapper reserveMapper;
	
	@Autowired
	private CrmMedroomMapper medroomMapper;
	
	
	@RequestMapping(value="/crmReserve", method=RequestMethod.GET)
    public List<CrmReserveVO> selectReserve(CrmReserveParamVO vo) throws Exception {
		return reserveMapper.selectReserve(vo);
    }
	
	@RequestMapping(value="/crmReserve", method=RequestMethod.PUT)
    public CommonVO updateReserve(CrmReserveParamVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			reserveMapper.updateReserve(vo);  
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("Internal Sever Error!");
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmReserve", method=RequestMethod.POST)
    public CommonVO insertReserve(CrmReserveParamVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			reserveMapper.insertReserve(vo);  
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("Internal Sever Error!");
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmReserveGubun", method=RequestMethod.GET)
    public List<CrmReserveGubunVO> selectGubun() throws Exception {
		return reserveMapper.selectGubun();
    }
	
	@RequestMapping(value="/crmReserveCancel", method=RequestMethod.GET)
    public List<CrmReserveCancelVO> selectCancel() throws Exception {
		return reserveMapper.selectCancel();
    }
	
	//온닥에서는 모바일 예약도 카운트에 포함
	//온닥CRM에서는 실제 예약만 카운트에 포함
	@RequestMapping(value="/crmWorkplan", method=RequestMethod.GET)
    public List<CrmWorkplanVO> selectReceipt(CrmWorkplanParamVO vo) throws Exception {
		return reserveMapper.selectWorkPlan(vo);
    }
	
	@RequestMapping(value="/crmMedroom", method=RequestMethod.GET)
    public List<CrmMedroomVO> selectMedroom(CrmReserveParamVO vo) throws Exception {
		return medroomMapper.selectMedroom();
    }
}
