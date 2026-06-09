package kr.ondoc.controller.legacy.penchart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ondoc.domain.sybase.legacy.penchart.LoginDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.LoginVO;
import kr.ondoc.mapper.sybase.legacy.penchart.OldLoginMapper;

@CrossOrigin(origins = "*")
@RestController
public class OldLoginController {
	@Autowired
	private OldLoginMapper loginMapper;
	
	@RequestMapping(value="/mobileChartLogin", method=RequestMethod.GET)
    public LoginVO selectLogin(String id, String pwd_hash) throws Exception {
		LoginVO resultVO = new LoginVO();
		
		List<LoginDataVO> dataList = loginMapper.selectLogin(id);
		
		
		if(!pwd_hash.equals("")) {
			if(pwd_hash.equals(dataList.get(0).getPass())) {
				dataList.get(0).setCheck("true");
			} else {
				dataList.get(0).setId("");
				dataList.get(0).setMedroom("");
				dataList.get(0).setName("");
				dataList.get(0).setResno("");
			}
		}
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
}
