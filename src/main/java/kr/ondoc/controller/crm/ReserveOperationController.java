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
import kr.ondoc.domain.sybase.crm.CrmReserveOperationVO;
import kr.ondoc.mapper.sybase.crm.CrmReserveOperationMapper;

@CrossOrigin(origins = "*")
@RestController
public class ReserveOperationController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CrmReserveOperationMapper reserveOperationMapper;
	
	@RequestMapping(value="/crmReserveOperation", method=RequestMethod.GET)
    public List<CrmReserveOperationVO> selectReserve(CrmReserveOperationVO vo) throws Exception {
		return reserveOperationMapper.selectReserveOperation(vo);
    }
	
	@RequestMapping(value="/crmReserveOperation", method=RequestMethod.PUT)
    public CommonVO updateReserve(CrmReserveOperationVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			reserveOperationMapper.updateReserveOperation(vo);  
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("Internal Sever Error!");
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmReserveOperation", method=RequestMethod.POST)
    public CommonVO insertReserve(CrmReserveOperationVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			reserveOperationMapper.insertReserveOperation(vo);  
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("Internal Sever Error!");
			return commonVO;
		}
		
		return commonVO;
    }

}
