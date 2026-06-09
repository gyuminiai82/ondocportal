package kr.ondoc.controller.legacy.ondocplus;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ondoc.domain.sybase.legacy.ondocplus.IpwonToewonStateDataVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.IpwonToewonStateParamVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.IpwonToewonStateVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.SupportReceiptStateDataVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.SupportReceiptStateParamVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.SupportReceiptStateTotDataVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.SupportReceiptStateTotVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.SupportReceiptStateVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.SupportStateDataVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.SupportStateParamVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.SupportStateTotDataVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.SupportStateTotVO;
import kr.ondoc.domain.sybase.legacy.ondocplus.SupportStateVO;
import kr.ondoc.mapper.sybase.legacy.ondocplus.OldOndocplusManageMapper;

@CrossOrigin(origins = "*")
@RestController
public class OldOndocplusManagerController {
	
	@Autowired
	private OldOndocplusManageMapper mapper;
	
	@RequestMapping(value="/managesupporttreatementstate", method=RequestMethod.GET)
    public SupportStateVO selectSupportState(SupportStateParamVO vo) throws Exception {
		SupportStateVO resultVO = new SupportStateVO();
		
		List<SupportStateDataVO> dataList = mapper.selectSupportState(vo);
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/managesupporttreatementstatetot", method=RequestMethod.GET)
    public SupportStateTotVO selectSupportStateTot(SupportStateParamVO vo) throws Exception {
		SupportStateTotVO resultVO = new SupportStateTotVO();
		
		List<SupportStateTotDataVO> dataList = mapper.selectSupportStateTot(vo);
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/managesupportipwontoewonstate", method=RequestMethod.GET)
    public IpwonToewonStateVO selectIpwonToewonState(IpwonToewonStateParamVO vo) throws Exception {
		IpwonToewonStateVO resultVO = new IpwonToewonStateVO();
		
		List<IpwonToewonStateDataVO> dataList = mapper.selectIpwonToewonState(vo);
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/managesupportreceiptstate", method=RequestMethod.GET)
    public SupportReceiptStateVO selectSupportReceiptState(SupportReceiptStateParamVO vo) throws Exception {
		SupportReceiptStateVO resultVO = new SupportReceiptStateVO();
		
		List<SupportReceiptStateDataVO> dataList = mapper.selectSupportReceiptState(vo);
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/managesupportreceiptstatetot", method=RequestMethod.GET)
    public SupportReceiptStateTotVO selectSupportReceiptStateTot(SupportReceiptStateParamVO vo) throws Exception {
		SupportReceiptStateTotVO resultVO = new SupportReceiptStateTotVO();
		
		List<SupportReceiptStateTotDataVO> dataList = mapper.selectSupportReceiptStateTot(vo);
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
}
