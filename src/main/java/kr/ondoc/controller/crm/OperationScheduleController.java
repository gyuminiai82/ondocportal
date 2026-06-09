package kr.ondoc.controller.crm;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ondoc.domain.sybase.common.CommonVO;
import kr.ondoc.domain.sybase.crm.CrmCommonVO;
import kr.ondoc.domain.sybase.crm.CrmOperationScheduleItemRelationVO;
import kr.ondoc.domain.sybase.crm.CrmOperationScheduleLimitVO;
import kr.ondoc.domain.sybase.crm.CrmOperationSchedulePatientListVO;
import kr.ondoc.domain.sybase.crm.CrmOperationScheduleVO;
import kr.ondoc.domain.sybase.crm.CrmPatientVO;
import kr.ondoc.domain.sybase.crm.CrmRevptNaverVO;
import kr.ondoc.domain.sybase.crm.CrmTotalVO;
import kr.ondoc.domain.sybase.manless.AgeTempVO;
import kr.ondoc.mapper.sybase.crm.CrmManlessQuestionMapper;
import kr.ondoc.mapper.sybase.crm.CrmManlessQuestionReplyMapper;
import kr.ondoc.mapper.sybase.crm.CrmOnlineReserveMapper;
import kr.ondoc.mapper.sybase.crm.CrmOperationScheduleItemRelationMapper;
import kr.ondoc.mapper.sybase.crm.CrmOperationScheduleMapper;
import kr.ondoc.mapper.sybase.crm.CrmPatientMapper;
import kr.ondoc.mapper.sybase.crm.CrmRevptNaverMapper;
import kr.ondoc.mapper.sybase.manless.ManlessMapper;
import kr.ondoc.sendsms.SendsmsRestClient;
import kr.ondoc.service.KakaoTemplateService;

@CrossOrigin(origins = "*")
@RestController
public class OperationScheduleController {
	@Autowired
    private KakaoTemplateService kakaoTemplateService;
	
	@Autowired
	SendsmsRestClient sendsmsRestClient;
	
	@Autowired
	private CrmOperationScheduleMapper operationScheduleMapper;
	
	@Autowired
	private CrmOperationScheduleItemRelationMapper operationScheduleItemRelationMapper;
	
	@Autowired
	private CrmPatientMapper patientMapper;
	
	@Autowired
	private ManlessMapper manlessMapper;
	
	@Autowired
	private CrmOnlineReserveMapper onlineReserveMapper;
	
	@Autowired
	private CrmManlessQuestionMapper manlessQuestionMapper;
	
	@Autowired
	private CrmManlessQuestionReplyMapper manlessQuestionReplyMapper;
	
	@Autowired
	private CrmRevptNaverMapper revptNaverMapper;
	
	//예약폼 하단 예약/진료/상담 내역 제공-예약은 삭제 내역도 보여줌
	@RequestMapping(value="/crmTotal", method=RequestMethod.GET)
    public List<CrmTotalVO> selectListTotal(CrmTotalVO vo) throws Exception {
		return operationScheduleMapper.selectListTotal(vo);
    }
	
	@RequestMapping(value="/crmOperationSchedulePatient", method=RequestMethod.GET)
    public List<CrmOperationSchedulePatientListVO> selectListOperationSchedulePatient(CrmOperationSchedulePatientListVO vo) throws Exception {
		List<CrmOperationSchedulePatientListVO> listVO = new ArrayList<>();
		
		operationScheduleMapper.selectListOperationSchedulePatient(vo).forEach(schedule -> {
			
			//ONDOC 모바일웹에서 접수 bookingkey값이 있는 상태에서 등록한 문진이 있는경우 문진내용 가져오기
			if(schedule.getReserve_type().equals("ONDOC") && !schedule.getRev_bookingkey().equals("")) {
				try {
					String revSeq = revptNaverMapper.selectManlessQuestionSeq(schedule.getRev_bookingkey());
					
					if(revSeq != null) {
						String reply = manlessQuestionReplyMapper.selectManlessQuestionReply(revSeq);
						
						schedule.setQuestion_reply(reply);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			listVO.add(schedule);
		});
		
		return listVO;
    }
	
	@RequestMapping(value="/crmOperationScheduleIdx", method=RequestMethod.GET)
    public CrmOperationSchedulePatientListVO selectOperationScheduleIdx(String idx) throws Exception {
		CrmOperationSchedulePatientListVO returnVO = operationScheduleMapper.selectListOperationScheduleIdx(idx);
		
		//ONDOC 모바일웹에서 접수 bookingkey값이 있는 상태에서 등록한 문진이 있는경우 문진내용 가져오기
		if(returnVO.getReserve_type().equals("ONDOC") && !returnVO.getRev_bookingkey().equals("")) {
			try {
				String revSeq = revptNaverMapper.selectManlessQuestionSeq(returnVO.getRev_bookingkey());
				
				if(revSeq != null) {
					String reply = manlessQuestionReplyMapper.selectManlessQuestionReply(revSeq);
					
					returnVO.setQuestion_reply(reply);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return returnVO;
    }
	
	@RequestMapping(value="/crmOperationScheduleLimit", method=RequestMethod.GET)
    public List<CrmOperationScheduleLimitVO> selectListOperationScheduleLimit(CrmOperationScheduleLimitVO vo) throws Exception {
		return operationScheduleMapper.selectListOperationScheduleLimit(vo);
    }
	
	@RequestMapping(value="/crmOperationSchedule", method=RequestMethod.GET)
    public List<CrmOperationScheduleVO> selectListOperationSchedule(CrmOperationScheduleVO vo) throws Exception {
		return operationScheduleMapper.selectListOperationSchedule(vo);
    }
	
	@RequestMapping(value="/crmOperationSchedule", method=RequestMethod.POST)
	public CommonVO insertOperationSchedule(CrmOperationScheduleVO vo) throws Exception {
		CrmCommonVO commonVO = new CrmCommonVO();
		try {
			
			String smsRandomNumber = "";
			String kakaoGroupId = "";
			//메시지 보내기 체크시만 문자 발송함
			if(vo.getSms_send_check().equals("Y")) {
				try {
					smsRandomNumber = sendsmsRestClient.sendMunJa(vo, "register");
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					kakaoGroupId = kakaoTemplateService.sendKakaoAlimtalk(vo, "register");
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			
			//문자발송한 고유번호를 스케줄 저장시 삽입(취소시 필요)
			vo.setSms_reserved_id(smsRandomNumber);
			vo.setKakao_group_id(kakaoGroupId);
			
			operationScheduleMapper.insertOperationSchedule(vo);
			
			String idx = operationScheduleMapper.selectMaxIdx();
			
			commonVO.setIdx(idx);
			commonVO.setSms_reserved_id(smsRandomNumber);
			commonVO.setKakao_group_id(kakaoGroupId);
			
			JSONArray arr = new JSONArray(vo.getItem_arr());
			
			for (int i = 0; i < arr.length(); i++) {
				String str = arr.get(i).toString() ;
				
				JSONObject obj = new JSONObject(str);
				
				CrmOperationScheduleItemRelationVO opVO = new CrmOperationScheduleItemRelationVO();
				opVO.setOperation_schedule_idx(idx);
				opVO.setItem_idx(obj.getString("item_idx"));
				opVO.setItem_detail_idx(obj.getString("item_detail_idx"));
				
				operationScheduleItemRelationMapper.insertOperationScheduleItemRelation(opVO);
			}
			
			//회원정보 수정
			CrmPatientVO pVo = new CrmPatientVO();
			
			if(!vo.getBirthday().equals("")) {
				AgeTempVO age = manlessMapper.selectAgeTemp(vo.getBirthday());
				
				pVo.setBpt_yage(age.getyAge());
				pVo.setBpt_mage(age.getmAge());
			}
			
			pVo.setBpt_ptno(vo.getPtno());
			pVo.setBpt_name(vo.getName());
			pVo.setBpt_cen(vo.getCen());
			pVo.setBpt_resno(vo.getResno());
			pVo.setBpt_birth(vo.getBirthday());
			pVo.setBpt_hpno(vo.getHpno());
			pVo.setBpt_sex(vo.getSex());
			pVo.setBpt_zip(vo.getZip());
			pVo.setBpt_addr(vo.getAddr());
			pVo.setBpt_addr2(vo.getAddr2());
			pVo.setBpt_jubsumemo(vo.getCustomer_memo());
			
			patientMapper.updatePatient(pVo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmOperationScheduleCount", method=RequestMethod.GET)
	public int countOperationSchedule(CrmOperationScheduleVO vo) throws Exception {
		CrmCommonVO commonVO = new CrmCommonVO();
		
		try {
			return operationScheduleMapper.countOperationSchedule(vo);
		} catch (Exception e) {
		}
		
		return 1;
	}
	
	@RequestMapping(value="/crmOperationScheduleRestore", method=RequestMethod.PUT)
	public CommonVO restoreOperationSchedule(CrmOperationScheduleVO vo) throws Exception {
		CrmCommonVO commonVO = new CrmCommonVO();
		
		try {
			operationScheduleMapper.restoreOperationSchedule(vo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			
			return commonVO;
		}
		
		return commonVO;
	}
	
	@RequestMapping(value="/crmOperationSchedule", method=RequestMethod.PUT)
	public CommonVO updateOperationSchedule(CrmOperationScheduleVO vo) throws Exception {
		CrmCommonVO commonVO = new CrmCommonVO();
		try {
			
			if(vo.getStatus_idx().equals("1")) {
				//예약 취소시 기존 문자 취소 후에 취소시 문자 발송해야 함
				CrmOperationScheduleVO operationScheduleVO = operationScheduleMapper.selectOperationSchedule(vo);
				
				if(!operationScheduleVO.getSms_reserved_id().equals("")) {
					try {
						sendsmsRestClient.deleteMunja(operationScheduleVO.getSms_reserved_id());
					} catch (Exception e) {
						// TODO: handle exception
					}
					
					try {
						String smsRandomNumber = sendsmsRestClient.sendMunJa(operationScheduleVO, "cancel");
						//문자발송한 고유번호를 스케줄 저장시 삽입(취소시 필요)
						commonVO.setSms_reserved_id(smsRandomNumber);
					} catch (Exception e) {
						// TODO: handle exception
					}
					
				}
				
				//TODO 카카오메시지는 즉시발송 기능만 있어 예약취소 추후구현
				if(!operationScheduleVO.getKakao_group_id().equals("")) {
					try {
						//TODO 카카오 메시지 삭제
					} catch (Exception e) {
						// TODO: handle exception
					}		
					
					try {
						String kakaoGroupId = kakaoTemplateService.sendKakaoAlimtalk(operationScheduleVO, "cancel");
						//문자발송한 고유번호를 스케줄 저장시 삽입(취소시 필요)
						commonVO.setKakao_group_id(kakaoGroupId);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
			
			//예약문자가 있는 상태에서 예약스케줄 폼에서 부서/부서번호/날짜/시간 중 하나를 변경하면 
			//기존문자 내역 취소하고 문자예약 재 실행
			if(!vo.getSms_reserved_id().equals("") && vo.getAction_type().equals("move")) {
				sendsmsRestClient.deleteMunja(vo.getSms_reserved_id());
				
				String smsRandomNumber = "";
				//메시지 보내기 체크시만 문자 발송함
				if(vo.getSms_send_check().equals("Y")) {
					try {
						smsRandomNumber = sendsmsRestClient.sendMunJa(vo, "move");
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				
				//문자발송한 고유번호를 스케줄 저장시 삽입(취소시 필요)
				commonVO.setSms_reserved_id(smsRandomNumber);
			}
			
			//카카오 메시지 이동시 보내기(예약시 발송한 기록이 있으면)
			if(!vo.getKakao_group_id().equals("") && vo.getAction_type().equals("move")) {
				//sendsmsRestClient.deleteMunja(vo.getSms_reserved_id());
				
				String kakaoGroupId = "";
				//메시지 보내기 체크시만 문자 발송함
				if(vo.getSms_send_check().equals("Y")) {
					try {
						kakaoGroupId = kakaoTemplateService.sendKakaoAlimtalk(vo, "move");
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				
				//문자발송한 고유번호를 스케줄 저장시 삽입(취소시 필요)
				commonVO.setKakao_group_id(kakaoGroupId);
			}
			
			//스케줄 업데이트
			operationScheduleMapper.updateOperationSchedule(vo);
			
			if(vo.getAction_type().equals("move")) {
				//예약을 사용자가 변경할 경우 네이버 예약 테이블에 cell정보 갱신하여 사용자가 취소시 cell을 정상적으로 찾아가도록 함
				//ONDOC은 온닥CRM 모바일 접수 웹프로그램
				if(!(vo.getReserve_type().equals("normal") || vo.getReserve_type().equals("ONDOC"))) {
					CrmRevptNaverVO rnVO = new CrmRevptNaverVO();
					rnVO.setRev_bookingkey(vo.getRev_bookingkey());
					rnVO.setRev_date(vo.getSchedule_date());
					rnVO.setRev_time(vo.getSchedule_time());
					rnVO.setRev_cell(vo.getDepartment_idx()+"_"+vo.getDepartment_number()+"_"+vo.getSchedule_time());
					onlineReserveMapper.updateOnlineReserve(rnVO);
				}
			}
			
			//스케줄에 딸린 진료항목 업데이트
			if(!vo.getItem_arr().equals("")) {
				JSONArray arr = new JSONArray(vo.getItem_arr());
				
				operationScheduleItemRelationMapper.deleteOperationScheduleItemRelation(vo.getOperation_schedule_idx());
				for (int i = 0; i < arr.length(); i++) {
					String str = arr.get(i).toString() ;
					
					JSONObject obj = new JSONObject(str);
					
					CrmOperationScheduleItemRelationVO opVO = new CrmOperationScheduleItemRelationVO();
					opVO.setOperation_schedule_idx(vo.getOperation_schedule_idx());
					opVO.setItem_idx(obj.getString("item_idx"));
					opVO.setItem_detail_idx(obj.getString("item_detail_idx"));
					
					operationScheduleItemRelationMapper.insertOperationScheduleItemRelation(opVO);
				}
			}
			
			//회원정보 수정
			CrmPatientVO pVo = new CrmPatientVO();
			
			if(!vo.getBirthday().equals("")) {
				AgeTempVO age = manlessMapper.selectAgeTemp(vo.getBirthday());
				
				pVo.setBpt_yage(age.getyAge());
				pVo.setBpt_mage(age.getmAge());
				
				commonVO.setAge(age.getyAge());
			}
			
			pVo.setBpt_ptno(vo.getPtno());
			pVo.setBpt_name(vo.getName());
			pVo.setBpt_cen(vo.getCen());
			pVo.setBpt_resno(vo.getResno());
			pVo.setBpt_birth(vo.getBirthday());
			pVo.setBpt_hpno(vo.getHpno());
			pVo.setBpt_sex(vo.getSex());
			pVo.setBpt_zip(vo.getZip());
			pVo.setBpt_addr(vo.getAddr());
			pVo.setBpt_addr2(vo.getAddr2());
			pVo.setBpt_jubsumemo(vo.getCustomer_memo());
			
			if(!vo.getPtno().equals("")) patientMapper.updatePatient(pVo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmOperationScheduleMove", method=RequestMethod.PUT)
	public CommonVO updateOperationScheduleMove(CrmOperationScheduleVO vo) throws Exception {
		CrmCommonVO commonVO = new CrmCommonVO();
		
		try {
			if(!vo.getSms_reserved_id().equals("")) {
				sendsmsRestClient.deleteMunja(vo.getSms_reserved_id());
				
				String smsRandomNumber = "";
				//메시지 보내기 체크시만 문자 발송함
				if(vo.getSms_send_check().equals("Y")) {
					try {
						smsRandomNumber = sendsmsRestClient.sendMunJa(vo, "move");
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				
				//문자발송한 고유번호를 스케줄 저장시 삽입(취소시 필요)
				commonVO.setSms_reserved_id(smsRandomNumber);
			}
			
			if(!vo.getKakao_group_id().equals("")) {
				//TODO 카카오메시지는 예약발송 없어서 추후 구현필요
				
				String kakaoGroupId = "";
				//메시지 보내기 체크시만 문자 발송함
				if(vo.getSms_send_check().equals("Y")) {
					try {
						kakaoGroupId = kakaoTemplateService.sendKakaoAlimtalk(vo, "move");
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				
				//문자발송한 고유번호를 스케줄 저장시 삽입(취소시 필요)
				commonVO.setKakao_group_id(kakaoGroupId);
			}
			
			operationScheduleMapper.updateOperationSchedule(vo);
			
			//예약을 사용자가 변경할 경우 네이버 예약 테이블에 cell정보 갱신하여 사용자가 취소시 cell을 정상적으로 찾아가도록 함
			if(!(vo.getReserve_type().equals("normal") || vo.getReserve_type().equals("ONDOC"))) {
				CrmRevptNaverVO rnVO = new CrmRevptNaverVO();
				rnVO.setRev_bookingkey(vo.getRev_bookingkey());
				rnVO.setRev_date(vo.getSchedule_date());
				rnVO.setRev_time(vo.getSchedule_time());
				rnVO.setRev_cell(vo.getDepartment_idx()+"_"+vo.getDepartment_number()+"_"+vo.getSchedule_time());
				onlineReserveMapper.updateOnlineReserve(rnVO);
			}
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmOperationSchedule", method=RequestMethod.DELETE)
    public CommonVO deleteOperationSchedule(CrmOperationScheduleVO vo) throws Exception {
		CommonVO commonVO = new CommonVO();
		try {
			if(!vo.getSms_reserved_id().equals("")) {
				try {
					sendsmsRestClient.deleteMunja(vo.getSms_reserved_id());
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				try {
					String smsRandomNumber = sendsmsRestClient.sendMunJa(vo, "remove");
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			
			if(!vo.getKakao_group_id().equals("")) {
				//예약 취소시 기존 문자 취소 후에 취소시 문자 발송해야 함
				CrmOperationScheduleVO operationScheduleVO = operationScheduleMapper.selectOperationSchedule(vo);
				
				try {
					//TODO 카카오메시지는 예약메시지가 없어 문자취수 추후구현
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				try {
					String kakaoGroupId = kakaoTemplateService.sendKakaoAlimtalk(operationScheduleVO, "remove");
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
			
			operationScheduleMapper.deleteOperationSchedule(vo);
		} catch (Exception e) {
			commonVO.setStatus("500");
			commonVO.setMessage("failure");
			return commonVO;
		}
		
		return commonVO;
    }
	
	@RequestMapping(value="/crmPatientCalculateAge", method=RequestMethod.GET)
    public String selectPatientCalculateAge(CrmPatientVO vo) throws Exception {
		AgeTempVO age = manlessMapper.selectAgeTemp(vo.getBpt_birth());
		
		vo.setBpt_yage(age.getyAge());
		vo.setBpt_mage(age.getmAge());
		
		try {
			patientMapper.updatePatientAge(vo);  
		} catch (Exception e) {
		}
		
		return age.getyAge();
    }
	
	@RequestMapping(value="/cntReserveRelation", method=RequestMethod.GET)
    public int cntReserveRelation(String idx) throws Exception {
		return operationScheduleMapper.cntReserveRelation(idx);
    }
}
