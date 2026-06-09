package kr.ondoc.controller.penchart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ondoc.domain.sybase.penchart.SearchSelectByungdongVO;
import kr.ondoc.domain.sybase.penchart.SearchSelectMedroomVO;
import kr.ondoc.mapper.sybase.penchart.SearchSelectMapper;

@CrossOrigin(origins = "*")
@RestController
public class SearchSelectController {
	@Autowired
	private SearchSelectMapper searchSelectMapper;
	
	//[검색조건]진료실
	@RequestMapping(value="/searchSelectMedroom", method=RequestMethod.GET)
    public List<SearchSelectMedroomVO> searchSelectMedroomList() throws Exception {
		
        return searchSelectMapper.searchSelectMedroomList();
    }
	
	//[검색조건]병동
	@RequestMapping(value= "/searchSelectByungdong", method=RequestMethod.GET)
    public List<SearchSelectByungdongVO> searchSelectByungdongList() throws Exception {
		
        return searchSelectMapper.searchSelectByungdongList();
    }
}