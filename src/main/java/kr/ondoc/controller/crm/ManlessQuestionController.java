package kr.ondoc.controller.crm;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ondoc.domain.sybase.common.CommonVO;
import kr.ondoc.domain.sybase.crm.CrmArrDepartmentVO;
import kr.ondoc.domain.sybase.crm.CrmArrManlessQuestionVO;
import kr.ondoc.domain.sybase.crm.CrmDepartmentVO;
import kr.ondoc.domain.sybase.crm.CrmManlessQuestionReplyVO;
import kr.ondoc.domain.sybase.crm.CrmManlessQuestionVO;
import kr.ondoc.mapper.sybase.crm.CrmManlessQuestionMapper;
import kr.ondoc.mapper.sybase.crm.CrmManlessQuestionReplyMapper;

@CrossOrigin(origins = "*")
@RestController
public class ManlessQuestionController {
	@Autowired
	private CrmManlessQuestionMapper manlessQuestionMapper;
	
	@Autowired
	private CrmManlessQuestionReplyMapper manlessQuestionReplyMapper;
	
	@RequestMapping(value="/crmManlessQuestionList", method=RequestMethod.GET)
    public List<CrmManlessQuestionVO> selectListManlessQuestion(CrmManlessQuestionVO vo) 
    		throws Exception {
		return manlessQuestionMapper.selectListManlessQuestion(vo);
    }
	
	@RequestMapping(value="/crmManlessQuestion", method=RequestMethod.POST)
	public CommonVO insertManlessQuestion(CrmManlessQuestionVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			manlessQuestionMapper.insertManlessQuestion(vo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmManlessQuestion", method=RequestMethod.PUT)
	public CommonVO updateManlessQuestion(CrmManlessQuestionVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			manlessQuestionMapper.updateManlessQuestion(vo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			
			return commonVO;
		}
		
		return commonVO;
    }
	
	
	@RequestMapping(value="/crmManlessQuestionSort", method=RequestMethod.PUT)
	public CommonVO updateDepartmentSort(CrmArrManlessQuestionVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		
		JSONArray arr = new JSONArray(vo.getArrManlessQuestion());
		
		for (int i = 0; i < arr.length(); i++) {
			String str = arr.get(i).toString() ;
			
			JSONObject obj = new JSONObject(str);
			
			CrmManlessQuestionVO  manlessQuestion = new CrmManlessQuestionVO();
			manlessQuestion.setQuestion_idx(obj.getString("question_idx"));
			manlessQuestion.setSort(Integer.toString(obj.getInt("sort")));
			
			try {
				manlessQuestionMapper.updateManlessQuestion(manlessQuestion);  
			} catch (Exception e) {
				commonVO.setStatus("500");
				commonVO.setMessage("failure");
				return commonVO;
			}
		}
		
		try {
			
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmManlessQuestion", method=RequestMethod.DELETE)
    public CommonVO deleteManlessQuestion(CrmManlessQuestionVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			manlessQuestionMapper.deleteManlessQuestion(vo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmManlessQuestionReplyList", method=RequestMethod.GET)
    public List<CrmManlessQuestionReplyVO> selectListManlessQuestionReply(CrmManlessQuestionReplyVO vo) 
    		throws Exception {
		return manlessQuestionReplyMapper.selectListManlessQuestionReply(vo);
    }
}
