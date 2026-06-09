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
import kr.ondoc.domain.sybase.crm.CrmMemoPagingVO;
import kr.ondoc.domain.sybase.crm.CrmMemoVO;
import kr.ondoc.domain.sybase.crm.CrmTreatementPaymentDetailVO;
import kr.ondoc.domain.sybase.crm.CrmTreatementPaymentUnpaidLogPagingVO;
import kr.ondoc.domain.sybase.crm.CrmTreatementPaymentUnpaidLogVO;
import kr.ondoc.domain.sybase.crm.CrmTreatementPaymentUnpaidPagingVO;
import kr.ondoc.domain.sybase.crm.CrmTreatementPaymentVO;
import kr.ondoc.mapper.sybase.crm.CrmTreatementItemRelationMapper;
import kr.ondoc.mapper.sybase.crm.CrmTreatementPaymentDetailMapper;
import kr.ondoc.mapper.sybase.crm.CrmTreatementPaymentMapper;
import kr.ondoc.mapper.sybase.crm.CrmTreatementPaymentUnpaidLogMapper;
import kr.ondoc.util.PagingUtil;

@CrossOrigin(origins = "*")
@RestController
public class TreatementPaymentController {
	@Autowired
	private CrmTreatementPaymentMapper paymentMapper;
	
	@Autowired
	private CrmTreatementPaymentDetailMapper paymentDetailMapper;
	
	@Autowired
	private CrmTreatementItemRelationMapper treatementItemRelationMapper;
	
	@Autowired
	private CrmTreatementPaymentUnpaidLogMapper treatementPaymentUnpaidLogMapper;
	
	@RequestMapping(value="/crmTreatementPaymentList", method=RequestMethod.GET)
    public List<CrmTreatementPaymentVO> selectListPayment(CrmTreatementPaymentVO vo) throws Exception {
		return paymentMapper.selectListPayment(vo);
    }
	
	@RequestMapping(value="/crmTreatementPayment", method=RequestMethod.POST)
	public CommonVO insertPayment(CrmTreatementPaymentVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			paymentMapper.insertPayment(vo);
			
			String idx = paymentMapper.selectMaxIdx();
			
			JSONArray arr = new JSONArray(vo.getDetail_arr());
			
			for (int i = 0; i < arr.length(); i++) {
				String str = arr.get(i).toString();
				
				JSONObject obj = new JSONObject(str);
				
				CrmTreatementPaymentDetailVO pdVO = new CrmTreatementPaymentDetailVO();
				pdVO.setOperation_schedule_idx(vo.getOperation_schedule_idx());
				pdVO.setPayment_idx(idx);
				pdVO.setPayment_date(obj.getString("payment_date"));
				pdVO.setPtno(obj.getString("ptno"));
				pdVO.setReceptionist_id(obj.getString("receptionist_id"));
				pdVO.setCounselor_id(obj.getString("counselor_id"));
				pdVO.setTherapist_id(obj.getString("therapist_id"));
				pdVO.setDoctor_id(obj.getString("doctor_id"));
				pdVO.setItem_idx(obj.getString("item_idx"));
				pdVO.setItem_name(obj.getString("item_name"));
				pdVO.setItem_detail_idx(obj.getString("item_detail_idx"));
				pdVO.setItem_name_detail(obj.getString("item_name_detail"));
				pdVO.setItem_count(obj.getString("item_count"));
				pdVO.setItem_complete_count(obj.getString("item_complete_count"));
				pdVO.setTax_yn(obj.getString("tax_yn"));
				pdVO.setCharge_price(obj.getString("charge_price"));
				pdVO.setSale_price(obj.getString("sale_price"));
				pdVO.setSale_after_price(obj.getString("sale_after_price"));
				
				paymentDetailMapper.insertPaymentDetail(pdVO);
				
				if(!obj.getString("treatement_item_idx").equals("0")) {
					idx = paymentDetailMapper.selectMaxIdx();
					
					treatementItemRelationMapper.updateTreatementItemPaymentIdx(obj.getString("treatement_item_idx"), idx);
				}
				
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmTreatementPayment", method=RequestMethod.PUT)
	public CommonVO updatePayment(CrmTreatementPaymentVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			paymentMapper.updatePayment(vo);
			
			JSONArray arr = new JSONArray(vo.getDetail_arr());
			
			//삭제할시 del_yn을 변경하여 실제로는 안 지울 예정
			for (int i = 0; i < arr.length(); i++) {
				String str = arr.get(i).toString() ;
				
				JSONObject obj = new JSONObject(str);
				
				CrmTreatementPaymentDetailVO pdVO = new CrmTreatementPaymentDetailVO();
				try {
					pdVO.setPayment_idx(obj.getString("payment_idx"));
					pdVO.setPayment_detail_idx(obj.getString("payment_detail_idx"));
					pdVO.setPayment_date(obj.getString("payment_date"));
					pdVO.setPtno(obj.getString("ptno"));
					pdVO.setReceptionist_id(obj.getString("receptionist_id"));
					pdVO.setCounselor_id(obj.getString("counselor_id"));
					pdVO.setTherapist_id(obj.getString("therapist_id"));
					pdVO.setDoctor_id(obj.getString("doctor_id"));
					pdVO.setItem_idx(obj.getString("item_idx"));
					pdVO.setItem_name(obj.getString("item_name"));
					pdVO.setItem_detail_idx(obj.getString("item_detail_idx"));
					pdVO.setItem_name_detail(obj.getString("item_name_detail"));
					pdVO.setItem_count(obj.getString("item_count"));
					pdVO.setItem_complete_count(obj.getString("item_complete_count"));
					pdVO.setTax_yn(obj.getString("tax_yn"));
					pdVO.setCharge_price(obj.getString("charge_price"));
					pdVO.setSale_price(obj.getString("sale_price"));
					pdVO.setSale_after_price(obj.getString("sale_after_price"));
					
					if(obj.getString("action").equals("insert")) {
						
						paymentDetailMapper.insertPaymentDetail(pdVO);
					} else if(obj.getString("action").equals("update")) {
						
						paymentDetailMapper.updatePaymentDetail(pdVO);
					} else if(obj.getString("action").equals("delete")) {
						
						paymentDetailMapper.deletePaymentDetail(pdVO);
					}
				} catch(Exception e) {
					System.out.println(e.getMessage());
				}
			}
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmTreatementPayment", method=RequestMethod.DELETE)
    public CommonVO deletePayment(CrmTreatementPaymentVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		
		CrmTreatementPaymentDetailVO detailVO = new CrmTreatementPaymentDetailVO();
		try {
			paymentMapper.deletePayment(vo);
			
			detailVO.setPayment_idx(vo.getPayment_idx());
			
			paymentDetailMapper.deletePaymentDetail(detailVO);
;		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmTreatementPaymentDetailList", method=RequestMethod.GET)
    public List<CrmTreatementPaymentDetailVO> selectListPaymentDetail(CrmTreatementPaymentDetailVO vo) throws Exception {
		return paymentDetailMapper.selectListPaymentDetail(vo);
    }
	
	@RequestMapping(value="/crmTreatementPaymentDetailItemList", method=RequestMethod.GET)
    public List<CrmTreatementPaymentDetailVO> selectListPaymentDetailItem(CrmTreatementPaymentDetailVO vo) throws Exception {
		return paymentDetailMapper.selectListPaymentDetailItem(vo);
    }
	
	
	//미수금이 남아있는 수납내역 목록
	@RequestMapping(value="/crmTreatementPaymentUnpaidList", method=RequestMethod.GET)
    public CrmTreatementPaymentUnpaidPagingVO selectListTreatementPaymentUnpaid(CrmTreatementPaymentVO vo) throws Exception {
		
		CrmTreatementPaymentUnpaidPagingVO resultVO = new CrmTreatementPaymentUnpaidPagingVO();
		
		int cnt = paymentMapper.countPaymentUnpaid(vo);
		PagingUtil paging = new PagingUtil(vo.getFnName(), Integer.toString(cnt), vo.getPage(), vo.getListNum(), vo.getBlockNum());
		
		vo.setFirst(Integer.toString(paging.getFirst()));
		
		List<CrmTreatementPaymentVO> dataList = paymentMapper.selectListPaymentUnpaid(vo);
		
		resultVO.setPaging(paging.getPaging());
		resultVO.setTotalPage(paging.getTotalPage());
		resultVO.setTotalRecord(Integer.toString(cnt));
		resultVO.setFirst(Integer.toString(paging.getFirst()));
		resultVO.setLast(Integer.toString(paging.getLast()));
		resultVO.setNum(paging.getNum());
		
		resultVO.setData(dataList);
		return resultVO;
    }
	
	@RequestMapping(value="/crmTreatementPaymentUnpaidCheckList", method=RequestMethod.GET)
    public List<CrmTreatementPaymentVO> selectListTreatementPaymentUnpaidCheck(CrmTreatementPaymentVO vo) throws Exception {
		return paymentMapper.selectListPaymentUnpaidCheck(vo);
	}
	
	//미수금 결제처리 완료 목록
	@RequestMapping(value="/crmTreatementPaymentUnpaidLogList", method=RequestMethod.GET)
    public CrmTreatementPaymentUnpaidLogPagingVO selectListTreatementPaymentUnpaidLog(CrmTreatementPaymentUnpaidLogVO vo) throws Exception {
		CrmTreatementPaymentUnpaidLogPagingVO resultVO = new CrmTreatementPaymentUnpaidLogPagingVO();
		
		int cnt = treatementPaymentUnpaidLogMapper.countPaymentUnpaidLog(vo);
		PagingUtil paging = new PagingUtil(vo.getFnName(), Integer.toString(cnt), vo.getPage(), vo.getListNum(), vo.getBlockNum());
		
		vo.setFirst(Integer.toString(paging.getFirst()));
		
		List<CrmTreatementPaymentUnpaidLogVO> dataList = treatementPaymentUnpaidLogMapper.selectListTreatementPaymentUnpaidLog(vo);
		
		resultVO.setPaging(paging.getPaging());
		resultVO.setTotalPage(paging.getTotalPage());
		resultVO.setTotalRecord(Integer.toString(cnt));
		resultVO.setFirst(Integer.toString(paging.getFirst()));
		resultVO.setLast(Integer.toString(paging.getLast()));
		resultVO.setNum(paging.getNum());
		
		resultVO.setData(dataList);
		return resultVO;
    }
	
	//미수금 결제처리
	@RequestMapping(value="/crmTreatementPaymentUnpaidLog", method=RequestMethod.POST)
	public CommonVO insertPayment(CrmTreatementPaymentUnpaidLogVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			treatementPaymentUnpaidLogMapper.insertTreatementPaymentUnpaidLog(vo);
			
			// 2. 기존 결제 정보 조회
	        CrmTreatementPaymentVO payment = paymentMapper.selectPayment(vo.getPayment_idx());
	        if (payment == null) {
	            throw new Exception("결제 정보를 찾을 수 없습니다.");
	        }

	        // 3. 금액 계산 
	        int currentUnpaid = toInt(payment.getUnpaid_price());
	        int currentPaid = toInt(payment.getUnpaid_payment_price());
	        int payAmount = toInt(vo.getPayment_price());

	        payment.setUnpaid_price(Integer.toString(currentUnpaid - payAmount));
	        payment.setUnpaid_payment_price(Integer.toString(currentPaid + payAmount));

	        // 4. 원본 결제 정보 업데이트
	        paymentMapper.updatePayment(payment);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			
			return commonVO;
		}
		
		return commonVO;
    }
	
	public static int toInt(String str) {
	    if (str == null || str.trim().isEmpty()) {
	        return 0;
	    }
	    try {
	        return Integer.parseInt(str.trim());
	    } catch (NumberFormatException e) {
	        return 0;
	    }
	}
	
	//미수금 결제처리 삭제
	@RequestMapping(value="/crmTreatementPaymentUnpaidLog", method=RequestMethod.DELETE)
    public CommonVO deletePayment(CrmTreatementPaymentUnpaidLogVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		
		try {
			treatementPaymentUnpaidLogMapper.deleteTreatementPaymentUnpaidLog(vo);
;		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			return commonVO;
		}
		
		return commonVO;
    }
}
