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
import kr.ondoc.domain.sybase.crm.CrmCodeParamVO;
import kr.ondoc.domain.sybase.crm.CrmDashOutDaegiVO;
import kr.ondoc.domain.sybase.crm.CrmDashReserveVO;
import kr.ondoc.domain.sybase.crm.CrmDashSunapVO;
import kr.ondoc.domain.sybase.crm.CrmFieldkindVO;
import kr.ondoc.domain.sybase.crm.CrmNoticeVO;
import kr.ondoc.mapper.sybase.crm.CrmDashboardMapper;
import kr.ondoc.mapper.sybase.crm.CrmNoticeMapper;

@CrossOrigin(origins = "*")
@RestController
public class DashboardController {
private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CrmDashboardMapper dashboardMapper;
	
	@Autowired
	private CrmNoticeMapper noticeMapper;
	
	@RequestMapping(value="/crmDashOutDaegi", method=RequestMethod.GET)
    public List<CrmDashOutDaegiVO> selectListOutDaegi(String date) throws Exception {
		return dashboardMapper.selectListOutDaegi(date);
    }
	
	@RequestMapping(value="/crmDashReserve", method=RequestMethod.GET)
    public List<CrmDashReserveVO> selectListReserve(String date) throws Exception {
		return dashboardMapper.selectListReserve(date);
    }
	
	@RequestMapping(value="/crmDashSunap", method=RequestMethod.GET)
    public List<CrmDashSunapVO> selectListSunap(String date) throws Exception {
		return dashboardMapper.selectListSunap(date);
    }
	
	@RequestMapping(value="/crmNotice", method=RequestMethod.GET)
    public List<CrmNoticeVO> selectNotice(CrmNoticeVO vo) throws Exception {
		return noticeMapper.selectNotice(vo);
    }
	
	@RequestMapping(value="/crmNotice", method=RequestMethod.POST)
    public CommonVO insertNotice(CrmNoticeVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			noticeMapper.insertNotice(vo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmNotice", method=RequestMethod.DELETE)
    public CommonVO deleteNotice(CrmNoticeVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			noticeMapper.deleteNotice(vo);  
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			return commonVO;
		}
		
		return commonVO;
    }
}
