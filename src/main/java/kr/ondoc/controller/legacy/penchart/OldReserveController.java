package kr.ondoc.controller.legacy.penchart;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ondoc.domain.sybase.legacy.CommonVO;
import kr.ondoc.domain.sybase.legacy.penchart.ReserveDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.ReserveParamVO;
import kr.ondoc.domain.sybase.legacy.penchart.ReserveVO;
import kr.ondoc.mapper.sybase.legacy.penchart.OldReserveMapper;
import kr.ondoc.mapper.sybase.legacy.penchart.OldTreeMapper;

@CrossOrigin(origins = "*")
@RestController
public class OldReserveController {
	@Autowired
	private OldReserveMapper reserveMapper;
	
	@RequestMapping(value="/patientreserve", method=RequestMethod.GET)
    public ReserveVO selectReserve(ReserveParamVO vo) throws Exception {
		ReserveVO resultVO = new ReserveVO();
		
		List<ReserveDataVO> dataList = reserveMapper.selectReserve(vo);
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value = "/patientreserve", method = RequestMethod.PUT)
    public CommonVO updateReserve(ReserveParamVO vo) throws Exception {
		CommonVO resultVO = new CommonVO();
		
		reserveMapper.updateReserve(vo);
		
		return resultVO;
    }
}
