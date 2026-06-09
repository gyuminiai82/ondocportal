package kr.ondoc.controller.legacy.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ondoc.domain.sybase.legacy.HosinfoDataVO;
import kr.ondoc.domain.sybase.legacy.HosinfoVO;
import kr.ondoc.domain.sybase.legacy.IdentificationDataVO;
import kr.ondoc.domain.sybase.legacy.IdentificationVO;
import kr.ondoc.domain.sybase.legacy.Optioninfo2DavaVO;
import kr.ondoc.domain.sybase.legacy.Optioninfo2VO;
import kr.ondoc.domain.sybase.legacy.SystemOptionDataVO;
import kr.ondoc.domain.sybase.legacy.SystemOptionVO;
import kr.ondoc.mapper.sybase.legacy.OldOptionInfoMapper;

@CrossOrigin(origins = "*")
@RestController
public class OldServerController {
	@Autowired
	private OldOptionInfoMapper systemOption;

	@RequestMapping(value="/systemoption", method=RequestMethod.GET)
    public SystemOptionVO selectsystemoption() throws Exception {
		SystemOptionVO resultVO = new SystemOptionVO();
		
		List<SystemOptionDataVO> dataList = systemOption.select();
		
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/hosinfo", method=RequestMethod.GET)
    public HosinfoVO selectHosinfo() throws Exception {
		HosinfoVO resultVO = new HosinfoVO();
		
		List<HosinfoDataVO> dataList = systemOption.selectHosinfo();
		
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/lagecy_optioninfo2", method=RequestMethod.GET)
    public Optioninfo2VO selectOptioninfo2() throws Exception {
		Optioninfo2VO resultVO = new Optioninfo2VO();
		
		List<Optioninfo2DavaVO> dataList = systemOption.selectOptioninfo2();
		
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/lagecy_identification", method=RequestMethod.GET)
    public IdentificationVO selectIdentification(String ptno) throws Exception {
		IdentificationVO resultVO = new IdentificationVO();
		
		List<IdentificationDataVO> dataList = systemOption.selectIdentification(ptno);
		
		resultVO.setData(dataList);
		
		return resultVO;
    }
}
