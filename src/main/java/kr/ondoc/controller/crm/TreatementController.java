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
import kr.ondoc.domain.sybase.crm.CrmTreatementCounselItemRelationVO;
import kr.ondoc.domain.sybase.crm.CrmTreatementItemRelationVO;
import kr.ondoc.domain.sybase.crm.CrmTreatementVO;
import kr.ondoc.mapper.sybase.crm.CrmTreatementCounselItemRelationMapper;
import kr.ondoc.mapper.sybase.crm.CrmTreatementItemRelationMapper;
import kr.ondoc.mapper.sybase.crm.CrmTreatementMapper;
import kr.ondoc.mapper.sybase.crm.CrmTreatementPaymentDetailMapper;

@CrossOrigin(origins = "*")
@RestController
public class TreatementController {
	@Autowired
	private CrmTreatementMapper treatementMapper;
	
	@Autowired
	private CrmTreatementItemRelationMapper treatementItemRelationMapper;
	
	@Autowired
	private CrmTreatementPaymentDetailMapper paymentDetailMapper;
	
	@RequestMapping(value="/crmTreatementList", method=RequestMethod.GET)
    public List<CrmTreatementVO> selectListTreatement(CrmTreatementVO vo) throws Exception {
		return treatementMapper.selectListTreatement(vo);
    }
	
	@RequestMapping(value="/crmTreatementPaymentItemList", method=RequestMethod.GET)
    public List<CrmTreatementItemRelationVO> selectListTreatementPaymentItem(String ptno) throws Exception {
		return treatementMapper.selectListTreatementPaymentItem(ptno);
    }
	
	@RequestMapping(value="/crmTreatement", method=RequestMethod.POST)
	public CommonVO insertTreatement(CrmTreatementVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			treatementMapper.insertTreatement(vo);
			
			String idx = treatementMapper.selectMaxIdx();
			
			JSONArray arr = new JSONArray(vo.getItem_arr());
			
			for (int i = 0; i < arr.length(); i++) {
				String str = arr.get(i).toString() ;
				
				JSONObject obj = new JSONObject(str);
				
				CrmTreatementItemRelationVO opVO = new CrmTreatementItemRelationVO();
				opVO.setTreatement_idx(idx);
				opVO.setItem_idx(obj.getString("item_idx"));
				opVO.setItem_detail_idx(obj.getString("item_detail_idx"));
				opVO.setPayment_detail_idx(obj.getString("payment_detail_idx"));
				
				if(!(obj.getString("payment_detail_idx").equals("") || obj.getString("payment_detail_idx").equals("0"))) {
					paymentDetailMapper.updatePaymentDetailCountUp(obj.getString("payment_detail_idx"));
				}
				
				treatementItemRelationMapper.insertTreatementItemRelation(opVO);
			}
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmTreatement", method=RequestMethod.PUT)
	public CommonVO updateTreatement(CrmTreatementVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			treatementMapper.updateTreatement(vo);
			
			JSONArray arr = new JSONArray(vo.getItem_arr());
			
			//수정전 해당 진료 항목건 수납내역 있으면 삭제
			CrmTreatementItemRelationVO ctirVO = new CrmTreatementItemRelationVO();
			ctirVO.setTreatement_idx(vo.getTreatement_idx());
			List<CrmTreatementItemRelationVO> list = treatementItemRelationMapper.selectListTreatementItemRelation(ctirVO);
			
			//진료내역 삭제시 선결제 카운드 다시 올림
			list.forEach(item -> {
				try {
					paymentDetailMapper.updatePaymentDetailCountDown(item.getPayment_detail_idx());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			//진료항목 삭제
			treatementItemRelationMapper.deleteTreatementItemRelation(vo.getTreatement_idx());
			
			
			for (int i = 0; i < arr.length(); i++) {
				String str = arr.get(i).toString() ;
				
				JSONObject obj = new JSONObject(str);
				
				CrmTreatementItemRelationVO opVO = new CrmTreatementItemRelationVO();
				opVO.setTreatement_idx(vo.getTreatement_idx());
				opVO.setItem_idx(obj.getString("item_idx"));
				opVO.setItem_detail_idx(obj.getString("item_detail_idx"));
				opVO.setPayment_detail_idx(obj.getString("payment_detail_idx"));
				
				if(!(obj.getString("payment_detail_idx").equals("") || obj.getString("payment_detail_idx").equals("0"))) {
					paymentDetailMapper.updatePaymentDetailCountUp(obj.getString("payment_detail_idx"));
				}
				
				treatementItemRelationMapper.insertTreatementItemRelation(opVO);
			}
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmTreatement", method=RequestMethod.DELETE)
    public CommonVO deleteDepartment(CrmTreatementVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			//삭제전 해당 진료 항목건 수납내역 있으면 삭제
			CrmTreatementItemRelationVO ctirVO = new CrmTreatementItemRelationVO();
			ctirVO.setTreatement_idx(vo.getTreatement_idx());
			List<CrmTreatementItemRelationVO> list = treatementItemRelationMapper.selectListTreatementItemRelation(ctirVO);
			
			//진료내역 삭제시 선결제 카운드 다시 올림
			list.forEach(item -> {
				try {
					paymentDetailMapper.updatePaymentDetailCountDown(item.getPayment_detail_idx());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			
			treatementMapper.deleteTreatement(vo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			return commonVO;
		}
		
		return commonVO;
    }
}
