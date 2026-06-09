package kr.ondoc.controller.crm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ondoc.domain.sybase.common.CommonVO;
import kr.ondoc.domain.sybase.crm.CrmOnlineReserveMedroomVO;
import kr.ondoc.domain.sybase.crm.CrmRevptNaverVO;
import kr.ondoc.mapper.sybase.crm.CrmOnlineReserveMapper;
import kr.ondoc.util.PagingUtil;

@CrossOrigin(origins = "*")
@RestController
public class OnlineReserveController {
	@Autowired
	private CrmOnlineReserveMapper onlineReserveMapper;
	
	//환경설정 > 부서관리 > 온라인예약설정 클릭시 호출되어 차트매니저 진료실과 crm 부서정보를 가져옴
	@RequestMapping(value="/crmOnlineReserveMedroom", method=RequestMethod.GET)
    public List<CrmOnlineReserveMedroomVO> selectListOnlineReserveMedroom() throws Exception {
		return onlineReserveMapper.selectListOnlineReserveMedroom();
    }

	@RequestMapping(value="/crmOnlineReserve", method=RequestMethod.GET)
    public List<CrmRevptNaverVO> selectListOnlineReserve(CrmRevptNaverVO vo) throws Exception {
		return onlineReserveMapper.selectRevptNaver(vo);
    }
	

	@RequestMapping(value="/crmOnlineReserve", method=RequestMethod.PUT)
    public CommonVO updateOnlineReserve(CrmRevptNaverVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		
		try {
			onlineReserveMapper.updateOnlineReserve(vo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("Internal Sever Error!");
			return commonVO;
		}
		
		return commonVO;
    }
}
