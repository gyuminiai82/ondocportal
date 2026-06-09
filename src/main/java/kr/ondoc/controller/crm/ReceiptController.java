package kr.ondoc.controller.crm;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ondoc.domain.sybase.crm.CrmReceiptPatientParamVO;
import kr.ondoc.domain.sybase.crm.CrmReceiptPatientVO;
import kr.ondoc.mapper.sybase.crm.CrmReceiptMapper;

@CrossOrigin(origins = "*")
@RestController
public class ReceiptController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CrmReceiptMapper receiptMapper;
	
	@RequestMapping(value="/crmReceipt", method=RequestMethod.GET)
    public List<CrmReceiptPatientVO> selectReceipt(CrmReceiptPatientParamVO vo) throws Exception {
		return receiptMapper.selectReceipt(vo);
    }
}
