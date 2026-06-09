package kr.ondoc.controller.crm;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ondoc.domain.sybase.crm.CrmIpwonCategoryParamVO;
import kr.ondoc.domain.sybase.crm.CrmIpwonCategoryVO;
import kr.ondoc.domain.sybase.crm.CrmIpwonPagingVO;
import kr.ondoc.domain.sybase.crm.CrmIpwonParamVO;
import kr.ondoc.domain.sybase.crm.CrmIpwonVO;
import kr.ondoc.domain.sybase.crm.CrmOutPagingVO;
import kr.ondoc.domain.sybase.crm.CrmOutParamVO;
import kr.ondoc.domain.sybase.crm.CrmOutVO;
import kr.ondoc.domain.sybase.crm.CrmPatientParamVO;
import kr.ondoc.domain.sybase.crm.SystemOptionVO;
import kr.ondoc.domain.sybase.crm.detail.CrmReservePagingVO;
import kr.ondoc.domain.sybase.crm.detail.CrmReserveVO;
import kr.ondoc.mapper.sybase.crm.CrmOptionMapper;
import kr.ondoc.mapper.sybase.crm.CrmPatientDetailMapper;
import kr.ondoc.util.PagingUtil;

@CrossOrigin(origins = "*")
@RestController
public class PatientDetailController {
private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CrmPatientDetailMapper patientDetailMapper;
	
	@Autowired
	private CrmOptionMapper optionMapper;
	
	@RequestMapping(value="/crmPatientDetailReserveList", method=RequestMethod.GET)
    public CrmReservePagingVO selectPatientDetailReserveList(CrmPatientParamVO vo) throws Exception {
		CrmReservePagingVO resultVO = new CrmReservePagingVO();
		
		int cnt = patientDetailMapper.countReserve(vo);
		PagingUtil paging = new PagingUtil(vo.getFnName(), Integer.toString(cnt), vo.getPage(), vo.getListNum(), vo.getBlockNum());
		
		vo.setFirst(Integer.toString(paging.getFirst()));
		
		List<CrmReserveVO> dataList = patientDetailMapper.selectReserveList(vo);
		
		resultVO.setPaging(paging.getPaging());
		resultVO.setTotalPage(paging.getTotalPage());
		resultVO.setTotalRecord(Integer.toString(cnt));
		resultVO.setFirst(Integer.toString(paging.getFirst()));
		resultVO.setLast(Integer.toString(paging.getLast()));
		resultVO.setNum(paging.getNum());
		
		resultVO.setData(dataList);
		return resultVO;
    }
	
	@RequestMapping(value="/crmPatientDetailOutList", method=RequestMethod.GET)
    public CrmOutPagingVO selectPatientDetailOutList(CrmOutParamVO vo) throws Exception {
		CrmOutPagingVO resultVO = new CrmOutPagingVO();
		
		int cnt = patientDetailMapper.countOut(vo);
		PagingUtil paging = new PagingUtil(vo.getFnName(), Integer.toString(cnt), vo.getPage(), vo.getListNum(), vo.getBlockNum());
		
		vo.setFirst(Integer.toString(paging.getFirst()));
		
		List<CrmOutVO> dataList = patientDetailMapper.selectOutList(vo);
		
		resultVO.setPaging(paging.getPaging());
		resultVO.setTotalPage(paging.getTotalPage());
		resultVO.setTotalRecord(Integer.toString(cnt));
		resultVO.setFirst(Integer.toString(paging.getFirst()));
		resultVO.setLast(Integer.toString(paging.getLast()));
		resultVO.setNum(paging.getNum());
		
		resultVO.setData(dataList);
		return resultVO;
    }
	
	@RequestMapping(value="/crmPatientDetailIpwonCategoryList", method=RequestMethod.GET)
    public List<CrmIpwonCategoryVO> selectPatientDetailIpwonCategoryList(CrmIpwonCategoryParamVO vo) throws Exception {
		SystemOptionVO systemOptionVO = optionMapper.selectOption();
		
		//xpt_jiwn12 - 0:미사용, 1:사용, 2:퇴원심사(병동기능), 3:병동 Ver2
		String xpt_jiwn12 = systemOptionVO.getXpt_jiwn12();
		
		List<CrmIpwonCategoryVO> resultVO = null;
		
		if(xpt_jiwn12.equals("3")) {
			resultVO = patientDetailMapper.selectIpwonCategoryV2List(vo);
		} else {
			resultVO = patientDetailMapper.selectIpwonCategoryList(vo);
		}
		
		return resultVO;
    }
	
	@RequestMapping(value="/crmPatientDetailIpwonList", method=RequestMethod.GET)
    public CrmIpwonPagingVO selectPatientDetailIpwonList(CrmIpwonParamVO vo) throws Exception {
		SystemOptionVO systemOptionVO = optionMapper.selectOption();
		
		//xpt_jiwn12 - 0:미사용, 1:사용, 2:퇴원심사(병동기능), 3:병동 Ver2
		String xpt_jiwn12 = systemOptionVO.getXpt_jiwn12();
	
		CrmIpwonPagingVO resultVO = new CrmIpwonPagingVO();
		
		if(xpt_jiwn12.equals("3")) {
			int cnt = patientDetailMapper.countIpwonV2(vo);
			PagingUtil paging = new PagingUtil(vo.getFnName(), Integer.toString(cnt), vo.getPage(), vo.getListNum(), vo.getBlockNum());
			
			vo.setFirst(Integer.toString(paging.getFirst()));
			
			List<CrmIpwonVO> dataList = patientDetailMapper.selectIpwonV2List(vo);
			
			resultVO.setPaging(paging.getPaging());
			resultVO.setTotalPage(paging.getTotalPage());
			resultVO.setTotalRecord(Integer.toString(cnt));
			resultVO.setFirst(Integer.toString(paging.getFirst()));
			resultVO.setLast(Integer.toString(paging.getLast()));
			resultVO.setNum(paging.getNum());
			
			resultVO.setData(dataList);
		} else {
			int cnt = patientDetailMapper.countIpwon(vo);
			PagingUtil paging = new PagingUtil(vo.getFnName(), Integer.toString(cnt), vo.getPage(), vo.getListNum(), vo.getBlockNum());
			
			vo.setFirst(Integer.toString(paging.getFirst()));
			
			List<CrmIpwonVO> dataList = patientDetailMapper.selectIpwonList(vo);
			
			resultVO.setPaging(paging.getPaging());
			resultVO.setTotalPage(paging.getTotalPage());
			resultVO.setTotalRecord(Integer.toString(cnt));
			resultVO.setFirst(Integer.toString(paging.getFirst()));
			resultVO.setLast(Integer.toString(paging.getLast()));
			resultVO.setNum(paging.getNum());
			
			resultVO.setData(dataList);
		}
		
		return resultVO;
    }
}
