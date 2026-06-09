package kr.ondoc.controller.crm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ondoc.domain.sybase.common.CommonVO;
import kr.ondoc.domain.sybase.crm.CrmMemoPagingVO;
import kr.ondoc.domain.sybase.crm.CrmMemoVO;
import kr.ondoc.mapper.sybase.crm.CrmMemoMapper;
import kr.ondoc.util.PagingUtil;

@CrossOrigin(origins = "*")
@RestController
public class MemoController {
	@Autowired
	private CrmMemoMapper memoMapper;
	
	@RequestMapping(value="/crmMemoList", method=RequestMethod.GET)
    public CrmMemoPagingVO selectListMemo(CrmMemoVO vo) throws Exception {
		CrmMemoPagingVO resultVO = new CrmMemoPagingVO();
		
		int cnt = memoMapper.countMemo(vo);
		PagingUtil paging = new PagingUtil(vo.getFnName(), Integer.toString(cnt), vo.getPage(), vo.getListNum(), vo.getBlockNum());
		
		vo.setFirst(Integer.toString(paging.getFirst()));
		
		List<CrmMemoVO> dataList = memoMapper.selectMemoList(vo);
		
		resultVO.setPaging(paging.getPaging());
		resultVO.setTotalPage(paging.getTotalPage());
		resultVO.setTotalRecord(Integer.toString(cnt));
		resultVO.setFirst(Integer.toString(paging.getFirst()));
		resultVO.setLast(Integer.toString(paging.getLast()));
		resultVO.setNum(paging.getNum());
		
		resultVO.setData(dataList);
		return resultVO;
    }
	
	@RequestMapping(value="/crmMemoMonthList", method=RequestMethod.GET)
    public List<CrmMemoVO> selectMemMonthList(CrmMemoVO vo) throws Exception {
		return memoMapper.selectMemoMonthList(vo);
    }
	
	@RequestMapping(value="/crmMemoDayList", method=RequestMethod.GET)
    public List<CrmMemoVO> selectMemDayList(CrmMemoVO vo) throws Exception {
		return memoMapper.selectMemoDayList(vo);
    }
	
	@RequestMapping(value="/crmMemo", method=RequestMethod.POST)
	public CommonVO insertMemo(CrmMemoVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			memoMapper.insertMemo(vo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmMemo", method=RequestMethod.DELETE)
    public CommonVO deleteMemo(CrmMemoVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			memoMapper.deleteMemo(vo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			return commonVO;
		}
		
		return commonVO;
    }

}
