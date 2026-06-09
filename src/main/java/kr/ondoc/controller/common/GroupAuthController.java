package kr.ondoc.controller.common;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ondoc.domain.sybase.common.GroupAuthVO;
import kr.ondoc.domain.sybase.penchart.TempGroupAuthVO;
import kr.ondoc.mapper.sybase.common.GroupAuthMapper;

@CrossOrigin(origins = "*")
@RestController
public class GroupAuthController {
	@Autowired
	private GroupAuthMapper groupAuthMapper;
	
	@RequestMapping(value="/groupAuth", method=RequestMethod.GET)
    public GroupAuthVO groupAuth(String xur_id) throws Exception {
		
		GroupAuthVO result = new GroupAuthVO();
		
		List<TempGroupAuthVO> list = groupAuthMapper.list(xur_id);
				
		for (TempGroupAuthVO item : list) {
			if(item.getXpg_file().equals("Checknote")) result.setChecknote(true);
			if(item.getXpg_file().equals("Penchart")) result.setPenchart(true);
			if(item.getXpg_file().equals("Consent")) result.setConsent(true);
			if(item.getXpg_file().equals("Manless")) result.setManless(true);
			if(item.getXpg_file().equals("Treatement")) result.setTreatement(true);
			if(item.getXpg_file().equals("Reservation")) result.setReservation(true);
			if(item.getXpg_file().equals("Healthcheck")) result.setHealthcheck(true);
		}
			
		//배열로 해야 안드로이드에서 처리가능(Object로 넘기면 처리방법 아직 모름)
        return result;
    }
}