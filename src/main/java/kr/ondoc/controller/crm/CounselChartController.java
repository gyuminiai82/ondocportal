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
import kr.ondoc.domain.sybase.crm.CrmCounselChartParamVO;
import kr.ondoc.domain.sybase.crm.CrmCounselSubReserveVO;
import kr.ondoc.domain.sybase.crm.CrmCounselChartVO;
import kr.ondoc.domain.sybase.crm.CrmReserveOperationVO;
import kr.ondoc.domain.sybase.crm.CrmReserveVO;
import kr.ondoc.mapper.sybase.crm.CrmCounselChartMapper;
import kr.ondoc.mapper.sybase.crm.CrmReserveMapper;
import kr.ondoc.mapper.sybase.crm.CrmReserveOperationMapper;

@CrossOrigin(origins = "*")
@RestController
public class CounselChartController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CrmCounselChartMapper counselMapper;
	
	@Autowired
	private CrmReserveMapper reserveMapper;
	
	@Autowired
	private CrmReserveOperationMapper reserveOperationMapper;
	
	@RequestMapping(value="/crmCounselChart", method=RequestMethod.GET)
    public List<CrmCounselChartVO> selectCounsel(CrmCounselChartParamVO vo) throws Exception {
		return counselMapper.selectCounsel(vo);
    }
	
	@RequestMapping(value="/crmCounselChart", method=RequestMethod.PUT)
    public CommonVO updateReserve(CrmCounselChartParamVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			counselMapper.updateCounsel(vo);  
		} catch (Exception e) {
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmCounselChart", method=RequestMethod.POST)
    public CommonVO insertReserve(CrmCounselChartParamVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			counselMapper.insertCounsel(vo);  
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmCounselChartSubRevpt", method=RequestMethod.GET)
    public CrmCounselSubReserveVO selectCounselSubRevpt(String seqs) throws Exception {
		CrmCounselSubReserveVO result = new CrmCounselSubReserveVO();
		
		List<CrmReserveVO> reserveList = reserveMapper.selectCounselRelationReserve(seqs);
		
		List<CrmReserveOperationVO> reserveOperationList = reserveOperationMapper.selectCounselRelationReserveOperation(seqs);
		
		result.setReserveList(reserveList);
		result.setReserveOperationList(reserveOperationList);
		
		return result;
    }
}
