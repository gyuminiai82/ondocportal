package kr.ondoc.controller.crm;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ondoc.domain.sybase.crm.CrmRevptCidVO;
import kr.ondoc.mapper.sybase.crm.CrmRevptCidMapper;

@CrossOrigin(origins = "*")
@RestController
public class RevptCidController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CrmRevptCidMapper revptCidMapper;
	
	@RequestMapping(value="/crmRevptCid", method=RequestMethod.GET)
    public List<CrmRevptCidVO> selectRevptCid(CrmRevptCidVO vo) throws Exception {
		return revptCidMapper.selectList(vo);
    }
}
