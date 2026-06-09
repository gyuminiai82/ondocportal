package kr.ondoc.controller.common;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ondoc.domain.sybase.common.UsrmngrVO;
import kr.ondoc.error.NoResultException;
import kr.ondoc.mapper.sybase.common.UsrmngrMapper;

@CrossOrigin(origins = "*")
@RestController
public class UsermngrController {
	
	@Autowired
	private UsrmngrMapper usrmngMapper;
	
	@RequestMapping(value="/usrmngrportal", method=RequestMethod.GET)
	//public UsrmngrVO usrmngrSelect(UsrmngrVO vo) throws Exception {
    public UsrmngrVO select(UsrmngrVO vo) throws Exception {
		UsrmngrVO result = null;
		try {
			result = usrmngMapper.select(vo);
			
			result.setXur_pass_hash(vo.getXur_pass_hash());
		} catch (Exception e) {
			//throw new NoResultException("NO_RESULT");
			return new UsrmngrVO();
		}
		
        return result;
    }
}
