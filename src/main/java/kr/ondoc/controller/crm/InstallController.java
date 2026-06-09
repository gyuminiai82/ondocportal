package kr.ondoc.controller.crm;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ondoc.domain.sybase.common.CommonVO;
import kr.ondoc.domain.sybase.crm.CrmDepartmentVO;
import kr.ondoc.domain.sybase.crm.CrmOndocGroupMenuRelationVO;
import kr.ondoc.domain.sybase.crm.CrmOndocGroupVO;
import kr.ondoc.domain.sybase.crm.CrmOperationScheduleStatusVO;
import kr.ondoc.domain.sybase.crm.CrmOperationScheduleVO;
import kr.ondoc.domain.sybase.crm.CrmSendSmsBoilerplateGroupVO;
import kr.ondoc.domain.sybase.crm.CrmSendSmsBoilerplateVO;
import kr.ondoc.domain.sybase.crm.CrmSendSmsVO;
import kr.ondoc.domain.sybase.crm.CrmTreatementCounselVO;
import kr.ondoc.domain.sybase.crm.CrmTreatementItemDetailVO;
import kr.ondoc.domain.sybase.crm.CrmTreatementItemVO;
import kr.ondoc.domain.sybase.crm.CrmUsrmngrGroupRelationVO;
import kr.ondoc.domain.sybase.crm.CrmUsrmngrVO;
import kr.ondoc.domain.sybase.manless.ReceiptPatientVO;
import kr.ondoc.mapper.sybase.crm.CrmDepartmentMapper;
import kr.ondoc.mapper.sybase.crm.CrmInstallMapper;
import kr.ondoc.mapper.sybase.crm.CrmOndocGroupMapper;
import kr.ondoc.mapper.sybase.crm.CrmOndocGroupMenuRelationMapper;
import kr.ondoc.mapper.sybase.crm.CrmOperationScheduleMapper;
import kr.ondoc.mapper.sybase.crm.CrmOperationScheduleStatusMapper;
import kr.ondoc.mapper.sybase.crm.CrmSendSmsBoilerplateGroupMapper;
import kr.ondoc.mapper.sybase.crm.CrmSendSmsBoilerplateMapper;
import kr.ondoc.mapper.sybase.crm.CrmSendSmsMapper;
import kr.ondoc.mapper.sybase.crm.CrmTreatementCounselMapper;
import kr.ondoc.mapper.sybase.crm.CrmTreatementItemDetailMapper;
import kr.ondoc.mapper.sybase.crm.CrmTreatementItemMapper;
import kr.ondoc.mapper.sybase.crm.CrmUsrmngrGroupRelationMapper;
import kr.ondoc.mapper.sybase.crm.CrmUsrmngrMapper;
import kr.ondoc.mapper.sybase.manless.ManlessMapper;
import kr.ondoc.util.Sha256;

@CrossOrigin(origins = "*")
@RestController
public class InstallController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CrmInstallMapper installMapper;
	
	@Autowired
	private CrmOperationScheduleStatusMapper statusMapper;
	
	@Autowired
	private CrmDepartmentMapper departmentMapper;
	
	@Autowired
	private CrmTreatementItemMapper treatementItemMapper;
	
	@Autowired
	private CrmTreatementItemDetailMapper treatementItemDetailMapper;
	
	@Autowired
	private CrmUsrmngrMapper usrmngrMapper;
	
	@Autowired
	private CrmOndocGroupMapper groupMapper;
	
	@Autowired
	private CrmOndocGroupMenuRelationMapper groupMenuRelationMapper;
	
	@Autowired
	private CrmUsrmngrGroupRelationMapper usrmngrGroupRelationMapper;
	
	@Autowired
	private CrmSendSmsMapper sendSmsMapper;
	
	@Autowired
	private CrmSendSmsBoilerplateGroupMapper sendSmsBoilerplateGroupMapper;
	
	@Autowired
	private CrmSendSmsBoilerplateMapper sendSmsBoilerplateMapper;
	
	@RequestMapping(value="/crmInstall", method=RequestMethod.GET)
	public CommonVO crmInstall() throws Exception {
		/*****############################################################### crm_setting table *****/
		//기본설정
		if(installMapper.checkTable("crm_setting").equals("0")) {
			installMapper.createCrmSetting();
			installMapper.insertSetting();
		} else {
			try {
				//네이버 예약 때문에 필드 추가
				installMapper.addFieldOnlineReserveYn();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			try {
				//문자발송 때문에 필드 추가
				installMapper.addFieldSmsMemberId();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			try {
				//문자발송 때문에 필드 추가
				installMapper.addFieldSmsSendNumber();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			try {
				installMapper.addFieldStatusSetcolorUseYn();
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				installMapper.addFieldStatusSetcolorChangeUseYn();
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				installMapper.addFieldStatusFirstBackgroundColor();
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				installMapper.addFieldStatusFirstColor();
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				installMapper.addFieldStatusReBackgroundColor();
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				installMapper.addFieldStatusReColor();
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				installMapper.addFieldSettingSmsUseYn();
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				installMapper.addFieldSettingSmsKakaoHospitalCode();
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				installMapper.addFieldSettingSmsKakaoSendNumber();
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				installMapper.addFieldSettingSmsKakaoUseYn();
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				installMapper.addFieldStatusReColor();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		/***** crm_setting table *****/
		
		/*****############################################################### crm_department table *****/
		//부서관리
		if(installMapper.checkTable("crm_department").equals("0")) {
			installMapper.createCrmDepartment();
			
			CrmDepartmentVO vo = new CrmDepartmentVO();
			
			vo.setDepartment_name("OP");
			vo.setDepartment_number("3");
			vo.setUse_yn("Y");
			vo.setOnline_reserve_yn("N");
			vo.setReceipt_default_yn("N");
			vo.setManless_reserve_yn("N");
			vo.setWrite_id("install");
			
			departmentMapper.insertDepartment(vo);
			
			vo.setDepartment_name("상담");
			vo.setDepartment_number("3");
			vo.setUse_yn("Y");
			vo.setOnline_reserve_yn("N");
			vo.setReceipt_default_yn("N");
			vo.setManless_reserve_yn("N");
			vo.setWrite_id("install");
			
			departmentMapper.insertDepartment(vo);
			
			vo.setDepartment_name("쁘띠");
			vo.setDepartment_number("5");
			vo.setUse_yn("Y");
			vo.setOnline_reserve_yn("N");
			vo.setReceipt_default_yn("N");
			vo.setManless_reserve_yn("N");
			vo.setWrite_id("install");
			
			departmentMapper.insertDepartment(vo);
			
			vo.setDepartment_name("에스테틱");
			vo.setDepartment_number("5");
			vo.setUse_yn("Y");
			vo.setOnline_reserve_yn("N");
			vo.setReceipt_default_yn("N");
			vo.setManless_reserve_yn("N");
			vo.setWrite_id("install");
			
			departmentMapper.insertDepartment(vo);
			
			vo.setDepartment_name("비만");
			vo.setDepartment_number("1");
			vo.setUse_yn("Y");
			vo.setOnline_reserve_yn("N");
			vo.setReceipt_default_yn("N");
			vo.setManless_reserve_yn("N");
			vo.setWrite_id("install");
			
			departmentMapper.insertDepartment(vo);
		} else {
			try {
				//모바일접수시 기본부서 설정-병원에서 QR코드 찍으면 접수 하는데 접수시 부서를 지정
				installMapper.addFieldReceiptDefaultYn();
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				//모바일예약시 선택부서 설정
				installMapper.addFieldManlessReserveYn();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		/***** crm_department table *****/
		
		/*****############################################################### crm_treatement_item table *****/
		//진료항목(1차 카테고리)
		if(installMapper.checkTable("crm_treatement_item").equals("0")) {
			installMapper.createCrmTreatementItem();
			
			CrmTreatementItemVO vo = new CrmTreatementItemVO();
			
			vo.setInitInsert("BTX", "Y", "N", "install");
			treatementItemMapper.insertTreatementItem(vo);
			
			vo.setInitInsert("Filer", "Y", "N", "install");
			treatementItemMapper.insertTreatementItem(vo);
			
			vo.setInitInsert("레이저", "Y", "N", "install");
			treatementItemMapper.insertTreatementItem(vo);
			
			vo.setInitInsert("남성제모1", "Y", "N", "install");
			treatementItemMapper.insertTreatementItem(vo);
			
			vo.setInitInsert("리프팅(Lifting)", "Y", "N", "install");
			treatementItemMapper.insertTreatementItem(vo);
		}
		/***** crm_treatement_item table *****/
		
		/*****############################################################### crm_treatement_item_detail table *****/
		//진료항목(2차 카테고리)
		if(installMapper.checkTable("crm_treatement_item_detail").equals("0")) {
			installMapper.createCrmTreatementItemDetail();
			
			CrmTreatementItemDetailVO vo = new CrmTreatementItemDetailVO();
			
			vo.setInitInsert("1", "angle", "10000", "Y", "N", "install");
			treatementItemDetailMapper.insertTreatementItemDetail(vo);
			
			vo.setInitInsert("1", "눈가", "20000", "Y", "N", "install");
			treatementItemDetailMapper.insertTreatementItemDetail(vo);
			
			vo.setInitInsert("1", "angle용량업", "5000", "Y", "N", "install");
			treatementItemDetailMapper.insertTreatementItemDetail(vo);
			
			vo.setInitInsert("1", "눈밑", "20000", "Y", "N", "install");
			treatementItemDetailMapper.insertTreatementItemDetail(vo);
			
			vo.setInitInsert("1", "이마", "35000", "Y", "N", "install");
			treatementItemDetailMapper.insertTreatementItemDetail(vo);
			
			vo.setInitInsert("1", "미간", "25000", "Y", "N", "install");
			treatementItemDetailMapper.insertTreatementItemDetail(vo);
			
			vo.setInitInsert("1", "콧잔등(세로주름)", "35000", "Y", "N", "install");
			treatementItemDetailMapper.insertTreatementItemDetail(vo);

			vo.setInitInsert("1", "콧잔등(가로주름)", "35000", "Y", "N", "install");
			treatementItemDetailMapper.insertTreatementItemDetail(vo);
			
			
			vo.setInitInsert("2", "콧잔등주름", "50000", "Y", "N", "install");
			treatementItemDetailMapper.insertTreatementItemDetail(vo);
			
			vo.setInitInsert("2", "콧잔등주름", "50000", "Y", "N", "install");
			treatementItemDetailMapper.insertTreatementItemDetail(vo);
			
			vo.setInitInsert("2", "팔자주름", "50000", "Y", "N", "install");
			treatementItemDetailMapper.insertTreatementItemDetail(vo);
			
			vo.setInitInsert("2", "입가주름", "50000", "Y", "N", "install");
			treatementItemDetailMapper.insertTreatementItemDetail(vo);
			
			vo.setInitInsert("2", "Full Face", "1200000", "Y", "N", "install");
			treatementItemDetailMapper.insertTreatementItemDetail(vo);
			
			
			vo.setInitInsert("3", "co2", "0", "Y", "N", "install");
			treatementItemDetailMapper.insertTreatementItemDetail(vo);
			
			vo.setInitInsert("3", "피코토닝", "0", "Y", "N", "install");
			treatementItemDetailMapper.insertTreatementItemDetail(vo);
			
			vo.setInitInsert("3", "프리미엄패키지", "1800000", "Y", "N", "install");
			treatementItemDetailMapper.insertTreatementItemDetail(vo);
			
			vo.setInitInsert("3", "레이져토닝", "15000", "Y", "N", "install");
			treatementItemDetailMapper.insertTreatementItemDetail(vo);
			
			vo.setInitInsert("3", "겨드랑이토닝", "15000", "Y", "N", "install");
			treatementItemDetailMapper.insertTreatementItemDetail(vo);
			
			
			vo.setInitInsert("4", "이마전체", "70000", "Y", "N", "install");
			treatementItemDetailMapper.insertTreatementItemDetail(vo);
			
			vo.setInitInsert("4", "헤어라인", "50000", "Y", "N", "install");
			treatementItemDetailMapper.insertTreatementItemDetail(vo);
			
			vo.setInitInsert("4", "구렛나루", "50000", "Y", "N", "install");
			treatementItemDetailMapper.insertTreatementItemDetail(vo);
			
			vo.setInitInsert("4", "눈썹", "30000", "Y", "N", "install");
			treatementItemDetailMapper.insertTreatementItemDetail(vo);
			
			vo.setInitInsert("4", "겨드랑이", "50000", "Y", "N", "install");
			treatementItemDetailMapper.insertTreatementItemDetail(vo);
			
			
			vo.setInitInsert("4", "겨드랑이", "50000", "Y", "N", "install");
			treatementItemDetailMapper.insertTreatementItemDetail(vo);
			
			
			vo.setInitInsert("5", "슈링크(얼굴)", "55000", "Y", "N", "install");
			treatementItemDetailMapper.insertTreatementItemDetail(vo);
			
			vo.setInitInsert("5", "슈링크(바디)", "110000", "Y", "N", "install");
			treatementItemDetailMapper.insertTreatementItemDetail(vo);
			
			vo.setInitInsert("5", "슈링크(아이/브이)", "75000", "Y", "N", "install");
			treatementItemDetailMapper.insertTreatementItemDetail(vo);
			
			vo.setInitInsert("5", "인모드(FX)", "80000", "Y", "N", "install");
			treatementItemDetailMapper.insertTreatementItemDetail(vo);
			
			vo.setInitInsert("5", "울쎄라", "150000", "Y", "N", "install");
			treatementItemDetailMapper.insertTreatementItemDetail(vo);
		} else {
			try {
				installMapper.addFieldItemDetailSalePrice();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			try {
				installMapper.addFieldItemDetailItemCount();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		/***** crm_treatement_item_detail table *****/
		
		/*****############################################################### crm_operation_schedule table *****/
		//스케줄 예약 테이블
		if(installMapper.checkTable("crm_operation_schedule").equals("0")) {
			installMapper.createCrmOperationSchedule();
		} else {
			try {
				//온라인 예약 때문에 필드 추가
				installMapper.addFieldCancelMemo();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			try {
				//온라인 예약시 구분값 저장
				installMapper.addFieldReserveType();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			try {
				//온라인 예약시 수정 변경시 필요
				installMapper.addFieldRevBookingkey();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			try {
				//온라인 예약시 수정 변경시 필요
				installMapper.addFieldRevReservationkey();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			try {
				//온라인 예약시 수정 변경시 필요
				installMapper.addFieldSmsReservedId();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			try {
				//온라인 예약시 고객지정색 지정
				installMapper.addFieldSetcolor();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			try {
				//카카오메시지 그룹 아이디
				installMapper.addFieldKakaoGroupId();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			try {
				//치료사 추가
				installMapper.addFieldCosTherapistId();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			try {
				//초까지 저장하기 위해 14로 늘림
				installMapper.modifyFieldWriteDate();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			try {
				//초까지 저장하기 위해 14로 늘림
				installMapper.modifyFieldModifyDate();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			try {
				//초까지 저장하기 위해 14로 늘림
				installMapper.modifyFieldDelDate();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			try {
				//초 01로 세팅
				installMapper.updateDate();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		/***** crm_operation_schedule table *****/
		
		/*****############################################################### crm_operation_schedule_closed table *****/
		//스케줄 휴진설정
		if(installMapper.checkTable("crm_operation_schedule_closed").equals("0")) {
			installMapper.createCrmOperationScheduleClosed();
		}
		/***** crm_operation_schedule_closed table *****/
		
		/*****############################################################### crm_operation_schedule_item_relation table *****/
		if(installMapper.checkTable("crm_operation_schedule_item_relation").equals("0")) {
			installMapper.createCrmOperationScheduleItemRelation();
		}
		/***** crm_operation_schedule_item_relation table *****/
		
		/*****############################################################### crm_operation_schedule_status table *****/
		if(installMapper.checkTable("crm_operation_schedule_status").equals("0")) {
			installMapper.createCrmOperationScheduleStatus();
			
			CrmOperationScheduleStatusVO vo = new CrmOperationScheduleStatusVO();
			
			//예약취소는 무조건 status_idx 값이 1이여야함(예약 취소시 문자 발생하기 위한 고정값 필요)
			//문자발송(예약)에서 예약취소시 발송문자가 등록되어 있으면 발송됨
			vo.setInitInsert("예약취소", "#f3fb83", "#292929", "1", "Y", "install");
			statusMapper.insertOperationScheduleStatusSort(vo);
			
			//추후 차트매니제니저 진료 완료 처리시 고정값이 필요하므로 2값은 완료로 하고 순서는 마지막에 오도록 6 넣음
			vo.setInitInsert("완료", "#b5b9c0", "#292929", "6", "Y", "install");
			statusMapper.insertOperationScheduleStatusSort(vo);
			
			vo.setInitInsert("진료대기", "#91eede", "#292929", "2", "Y", "install");
			statusMapper.insertOperationScheduleStatusSort(vo);
			
			vo.setInitInsert("시/수술대기", "#f08acf", "#292929", "3", "Y", "install");
			statusMapper.insertOperationScheduleStatusSort(vo);
			
			vo.setInitInsert("진료중", "#f08acf", "#292929", "4", "Y", "install");
			statusMapper.insertOperationScheduleStatusSort(vo);
			
			vo.setInitInsert("시/수술중", "#f7bc69", "#292929", "5", "Y", "install");
			statusMapper.insertOperationScheduleStatusSort(vo);
		}
		/***** crm_operation_schedule_status table *****/
		
		/*****############################################################### crm_treatement_counsel table *****/
		if(installMapper.checkTable("crm_treatement_counsel").equals("0")) {
			installMapper.createCrmCounsel();
		} else {
			try {
				//예약 일련번호 추가
				installMapper.addFieldTreatementCounselOperationScheduleIdx();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			try {
				//치료사 추가
				installMapper.addFieldCtcTherapistId();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		/***** crm_treatement_counsel table *****/
		
		/*****############################################################### crm_treatement_counsel_item_relation table *****/
		if(installMapper.checkTable("crm_treatement_counsel_item_relation").equals("0")) {
			installMapper.createCrmCounselItemRelation();
		}
		/***** crm_treatement_counsel_item_relation table *****/
		
		/*****############################################################### crm_treatement table *****/
		if(installMapper.checkTable("crm_treatement").equals("0")) {
			installMapper.createCrmTreatement();
		} else {
			try {
				//예약 일련번호 추가
				installMapper.addFieldTreatementOperationScheduleIdx();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			try {
				//치료사 추가
				installMapper.addFieldCtTherapistId();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		/***** crm_treatement table *****/
		
		/*****############################################################### crm_treatement_item_relation table *****/
		if(installMapper.checkTable("crm_treatement_item_relation").equals("0")) {
			installMapper.createCrmTreatementItemRelation();
		}
		/***** crm_treatement_item_relation table *****/
		
		/*****############################################################### crm_treatement_payment table *****/
		if(installMapper.checkTable("crm_treatement_payment").equals("0")) {
			installMapper.createCrmPayment();
		} else {
			try {
				//예약시간 필드 추가
				installMapper.addFieldCashTransferPrice();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			try {
				//예약 일련번호 추가
				installMapper.addFieldTreatementPaymentOperationScheduleIdx();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			try {
				//예약 미수금수납액 추가
				installMapper.addFieldUnpaidPaymentPrice();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			try {
				//status 값 안쓰는거 없애기
				installMapper.updateFieldStatus();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		/***** crm_treatement_payment table *****/
		
		/*****############################################################### crm_treatement_payment_unpaid_log table *****/
		if(installMapper.checkTable("crm_treatement_payment_unpaid_log").equals("0")) {
			installMapper.createCrmPaymentUnpaidLog();
		} else {
			
		}
		/***** crm_treatement_payment_unpaid_log table *****/
		
		/*****############################################################### crm_treatement_payment_detail table *****/
		if(installMapper.checkTable("crm_treatement_payment_detail").equals("0")) {
			installMapper.createCrmPaymentDetail();
		} else {
			try {
				//예약 일련번호 추가
				installMapper.addFieldTreatementPaymentDetailOperationScheduleIdx();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			try {
				//치료사 추가
				installMapper.addFieldCtpdTherapistId();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		/***** crm_treatement_payment_detail table *****/
		
		/*****############################################################### revpt_naver table *****/
		//온라인 예약 테이블
		if(installMapper.checkTable("revpt_naver").equals("0")) {
			installMapper.createCrmRecptNaver();
		} else {
			try {
				//예약시간 필드 추가
				installMapper.addFieldPeriod();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			try {
				//예약셀 정보 필드 추가
				installMapper.addFieldCell();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			try {
				//필드 확장
				installMapper.addFieldCancelledescExpand();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			try {
				//필드 확장
				installMapper.addFieldUserrequestExpand();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		/***** revpt_naver table *****/
		
		/*****############################################################### crm_send_sms table *****/
		//문자 발송
		if(installMapper.checkTable("crm_send_sms").equals("0")) {
			installMapper.createCrmSendSms();
			
			CrmSendSmsVO vo = new CrmSendSmsVO();
			
			//예약 등록시
			vo.setInitInsert("register", "0000", "", "{병원명}\r\n"
					+ "{환자명}님\r\n"
					+ "{MM월DD 오전HH시MM분}으로\r\n"
					+ "예약이 완료되었습니다", "N", "N", "install");
			sendSmsMapper.insertSendSms(vo);
			
			vo.setInitInsert("register", "1100", "1", "{병원명}\r\n"
					+ "{환자명}님\r\n"
					+ "{MM월DD 오전HH시MM분}\r\n"
					+ "예약 방문일입니다\r\n"
					+ "무료수신거부 0801394009", "N", "N", "install");
			sendSmsMapper.insertSendSms(vo);
			
			//예약 이동시
			vo.setInitInsert("move", "0000", "", "{병원명}\r\n"
					+ "{환자명}님\r\n"
					+ "{MM월DD 오전HH시MM분}으로\r\n"
					+ "예약이 변경되었습니다", "N", "N", "install");
			sendSmsMapper.insertSendSms(vo);
			
			vo.setInitInsert("move", "1400", "1", "{병원명}\r\n"
					+ "{환자명}님\r\n"
					+ "{MM월DD 오전HH시MM분}\r\n"
					+ "예약 방문일입니다\r\n"
					+ "무료수신거부 0801394009", "N", "N", "install");
			sendSmsMapper.insertSendSms(vo);
			
			vo.setInitInsert("cancel", "0000", "", "{병원명}\r\n"
					+ "{환자명}님\r\n"
					+ "{MM월DD 오전HH시MM분}\r\n"
					+ "예약이 취소되었습니다.\r\n"
					+ "무료수신거부 0801394009", "N", "N", "install");
			sendSmsMapper.insertSendSms(vo);
			
			vo.setInitInsert("remove", "0000", "", "{병원명}\r\n"
					+ "{환자명}님\r\n"
					+ "{MM월DD 오전HH시MM분}\r\n"
					+ "예약이 삭제되었습니다.\r\n"
					+ "무료수신거부 0801394009", "N", "N", "install");
			sendSmsMapper.insertSendSms(vo);
		}
		/***** crm_send_sms table *****/
		
		/*****############################################################### crm_send_sms_boilerplate_group table *****/
		//문자 발송(상용구) 그룹
		if(installMapper.checkTable("crm_send_sms_boilerplate_group").equals("0")) {
			installMapper.createCrmSendSmsBoilerplateGroup();
			
			CrmSendSmsBoilerplateGroupVO vo = new CrmSendSmsBoilerplateGroupVO();
			
			vo.setInitInsert("인포", "Y", "N", "install");
			sendSmsBoilerplateGroupMapper.insertSendSmsBoilerplateGroup(vo);
			
			vo.setInitInsert("월별인사", "Y", "N", "install");
			sendSmsBoilerplateGroupMapper.insertSendSmsBoilerplateGroup(vo);
			
			vo.setInitInsert("케어", "Y", "N", "install");
			sendSmsBoilerplateGroupMapper.insertSendSmsBoilerplateGroup(vo);
			
			vo.setInitInsert("시술 후 주의사항", "Y", "N", "install");
			sendSmsBoilerplateGroupMapper.insertSendSmsBoilerplateGroup(vo);
			
			vo.setInitInsert("기타", "Y", "N", "install");
			sendSmsBoilerplateGroupMapper.insertSendSmsBoilerplateGroup(vo);
		} else {
			try {
				installMapper.addFieldSendSmsBoilerplateGroupSort();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		/***** crm_send_sms_boilerplate_group table *****/
		
		/*****############################################################### crm_send_sms_boilerplate table *****/
		//문자 발송(상용구)
		if(installMapper.checkTable("crm_send_sms_boilerplate").equals("0")) {
			installMapper.createCrmSendSmsBoilerplate();
			
			CrmSendSmsBoilerplateVO vo = new CrmSendSmsBoilerplateVO();
			
			//인포
			vo.setInitInsert("1", "추석연휴진료안내", "(광고)\r\n"
					+ "추석연휴 기간 중 \r\n"
					+ "정상진료합니다.\r\n"
					+ "{병원명}\r\n"
					+ "무료수신거부 0801394009", "Y", "N", "install");
			sendSmsBoilerplateMapper.insertSendSmsBoilerplate(vo);
			
			vo.setInitInsert("1", "새해인사", "(광고)\r\n"
					+ "{환자명}님\r\n"
					+ "새해에도 건강한 한 해 보내시기 바랍니다. {병원명}\r\n"
					+ "무료수신거부 0801394009", "Y", "N", "install");
			sendSmsBoilerplateMapper.insertSendSmsBoilerplate(vo);
			
			vo.setInitInsert("1", "생일축하", "(광고)\r\n"
					+ "세상 그 무엇보다 소중한 {환자명}님의 생일을 사랑 가득 담아 축하드립니다. {병원명}\r\n"
					+ "무료수신거부 0801394009", "Y", "N", "install");
			sendSmsBoilerplateMapper.insertSendSmsBoilerplate(vo);
			
			vo.setInitInsert("1", "추석연휴 안내", "(광고)\r\n"
					+ "{병원명}\r\n"
					+ "{환자명}님 추석 연휴 전에 점, 주근깨, 검버섯, 칙칙한 흔적들, 울둥불퉁한 여드름흉터 치료 예약 중입니다. 9월 9일~9월 12일 휴진입니다.\r\n"
					+ "무료수신거부 0801394009", "Y", "N", "install");
			sendSmsBoilerplateMapper.insertSendSmsBoilerplate(vo);
			
			vo.setInitInsert("1", "설날 문자", "(광고) {환자명}님 즐거운 설날을 맞이하여 건강과 함께 밝은 한해, 평온한 가정 되시길 기원하며 복 듬뿍 받으세요. {병원명} 무료수신거부 0801394009", "Y", "N", "install");
			sendSmsBoilerplateMapper.insertSendSmsBoilerplate(vo);
			
			//월별인사
			vo.setInitInsert("2", "8월인사", "(광고)\r\n"
					+ "{병원명}\r\n"
					+ "{환자명}님 태양의계절 8월입니다~ 폭염에건강조심하시고즐거운휴가보내세요~♪\r\n"
					+ "무료수신거부 0801394009", "Y", "N", "install");
			sendSmsBoilerplateMapper.insertSendSmsBoilerplate(vo);
			
			vo.setInitInsert("2", "9월인사", "(광고)\r\n"
					+ "{병원명}\r\n"
					+ "{환자명}님 ♡ 감기조심 ♡아침저녁으로 쌀쌀한 요즘, 감기 조심하세요.. 늘 행복하세요.\r\n"
					+ "무료수신거부 0801394009", "Y", "N", "install");
			sendSmsBoilerplateMapper.insertSendSmsBoilerplate(vo);
			
			vo.setInitInsert("2", "10월인사", "(광고)\r\n"
					+ "{병원명}\r\n"
					+ "{환자명}님 10월입니다 조금씩 추워지고 있습니다 감기조심하시고 10달에도 행복하세요\r\n"
					+ "무료수신거부 0801394009", "Y", "N", "install");
			sendSmsBoilerplateMapper.insertSendSmsBoilerplate(vo);
			
			vo.setInitInsert("2", "11월인사", "(광고)\r\n"
					+ "{병원명}\r\n"
					+ "{환자명}님 찬바람이 살속으로 파고들기 시작하는 입동입니다. 몸관리 잘하시고 행복만가득하세요\r\n"
					+ "무료수신거부 0801394009", "Y", "N", "install");
			sendSmsBoilerplateMapper.insertSendSmsBoilerplate(vo);
			
			vo.setInitInsert("2", "12월인사", "(광고)\r\n"
					+ "{병원명}\r\n"
					+ "{환자명}님 한해동안관심과사랑에감사드립니다새해에는건강과행운이늘함께하시길소망합니다\r\n"
					+ "무료수신거부 0801394009", "Y", "N", "install");
			sendSmsBoilerplateMapper.insertSendSmsBoilerplate(vo);
			
			//케어
			vo.setInitInsert("3", "웰컴 문자1", "(광고)\r\n"
					+ "{환자명}님 치료받은 곳은 괜찮아지셨나요? 항상 {환자명}님의 건강을 생각하는 {병원명}입니다.\r\n"
					+ "무료수신거부 0801394009ㄴ", "Y", "N", "install");
			sendSmsBoilerplateMapper.insertSendSmsBoilerplate(vo);
			
			vo.setInitInsert("3", "웰컴 문자2", "(광고)\r\n"
					+ "\r\n"
					+ "진료는 잘 받으셨나요? {병원명}에서 {환자명}님의 건강과행복을 기원하겠습니다\r\n"
					+ "무료수신거부 0801394009", "Y", "N", "install");
			sendSmsBoilerplateMapper.insertSendSmsBoilerplate(vo);
			
			//시술 후 주의사항
			vo.setInitInsert("4", "성형 주의사항-1", "(광고)\r\n"
					+ "수술2~3일간얼굴의부기가심할수있으나10일째가되면가라앉습니다\r\n"
					+ "{병원명}\r\n"
					+ "무료수신거부 0801394009", "Y", "N", "install");
			sendSmsBoilerplateMapper.insertSendSmsBoilerplate(vo);
			
			vo.setInitInsert("4", "성형 주의사항-2", "(광고)\r\n"
					+ "수술5일간은 항상머리를가슴보다높게베게로받쳐줘야부기가덜합니다. {병원명}\r\n"
					+ "무료수신거부 0801394009", "Y", "N", "install");
			sendSmsBoilerplateMapper.insertSendSmsBoilerplate(vo);
			
			vo.setInitInsert("4", "성형 주의사항-3", "(광고)\r\n"
					+ "수술후식사는미음으로하고지시에따라고형식으로바꾸세요.\r\n"
					+ "{병원명}\r\n"
					+ "무료수신거부 0801394009", "Y", "N", "install");
			sendSmsBoilerplateMapper.insertSendSmsBoilerplate(vo);
			
			vo.setInitInsert("4", "코 주의사항-당일", "(광고)\r\n"
					+ "3일까지는 냉찜질을 눈 주위에 해주십시오. {병원명}\r\n"
					+ "무료수신거부 0801394009\r\n"
					+ "", "Y", "N", "install");
			sendSmsBoilerplateMapper.insertSendSmsBoilerplate(vo);
			
			vo.setInitInsert("4", "코 주의사항-3일차", "(광고)\r\n"
					+ "목욕과 사우나는 2주 후부터 하십시오. 격렬한 운동은 4주 후부터 하세요  {병원명}\r\n"
					+ "무료수신거부 0801394009", "Y", "N", "install");
			sendSmsBoilerplateMapper.insertSendSmsBoilerplate(vo);

			vo.setInitInsert("4", "안면운곽술 주의사항-당일", "(광고)\r\n"
					+ "{병원명}\r\n"
					+ "식사는 2-3일 까지는 미음이 가능하고 3일후부터는 죽과부드러운 음식은드셔도됩니다.\r\n"
					+ "무료수신거부 0801394009", "Y", "N", "install");
			sendSmsBoilerplateMapper.insertSendSmsBoilerplate(vo);
			
			vo.setInitInsert("4", "안면운곽술 주의사항-3일차", "(광고)\r\n"
					+ "{병원명}\r\n"
					+ "수술 후 8일째 부터 양치질을 하실 수 있습니다.\r\n"
					+ "무료수신거부 0801394009", "Y", "N", "install");
			sendSmsBoilerplateMapper.insertSendSmsBoilerplate(vo);
			

			vo.setInitInsert("5", "입금계좌안내", "(광고)\r\n"
					+ "입금 계좌 안내\r\n"
					+ "신한 110-000-00000 \r\n"
					+ "{병원명}\r\n"
					+ "무료수신거부 0801394009", "Y", "N", "install");
			sendSmsBoilerplateMapper.insertSendSmsBoilerplate(vo);
		} else {
			try {
				installMapper.addFieldSendSmsBoilerplateGroupIdx();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			try {
				installMapper.addFieldSendSmsBoilerplateSort();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		/***** crm_send_sms_boilerplate table *****/
		
		/*****############################################################### crm_send_sms_log table *****/
		if(installMapper.checkTable("crm_send_sms_log").equals("0")) {
			installMapper.createCrmSendSmsLog();
		}
		/***** crm_send_sms_log table *****/
		
		if(installMapper.checkTable("crm_send_sms_boilerplate_log").equals("0")) {
			installMapper.createCrmSendSmsBoilerplateLog();
		}
		
		if(installMapper.checkTable("crm_setcolor").equals("0")) {
			installMapper.createCrmSetcolor();
		}
		
		/*****############################################################### crm_manless_question table *****/
		if(installMapper.checkTable("crm_manless_question").equals("0")) {
			installMapper.createCrmManlessQuestion();
		}
		/***** crm_manless_question table *****/
		
		/*****############################################################### crm_manless_question_reply table *****/
		if(installMapper.checkTable("crm_manless_question_reply").equals("0")) {
			installMapper.createCrmManlessQuestionReply();
		} else {
			try {
				installMapper.addFieldManlessQuestionReplyRevSeq();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		/***** crm_manless_question_reply table *****/
		
		/*****############################################################### crm_memo table *****/
		if(installMapper.checkTable("crm_memo").equals("0")) {
			installMapper.createCrmMemo();
		} else {
			try {
				installMapper.addFieldMemoImage();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		/***** crm_memo table *****/
		
		
		/*****############################################################### crm_ondoc_group table *****/
		if(installMapper.checkTable("crm_ondoc_group").equals("0")) {
			installMapper.createCrmOndocGroup();
			
			//이름에 Ondoc을 붙인 이유는 이미 crm_group 테이블이 다른 곳에서 쓰고 있어서 붙임 다른 의미는 없음
			//회원 그룹 생성
			CrmOndocGroupVO groupVO = new CrmOndocGroupVO();
			try {
				groupVO.setName("병원관리자");
				groupVO.setWrite_id("install");
				groupMapper.insertInitGroup(groupVO);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		/*****############################################################### crm_ondoc_group_menu_relation table *****/
		if(installMapper.checkTable("crm_ondoc_group_menu_relation").equals("0")) {
			installMapper.createCrmOndocGroupMenuRelation();
			
			CrmOndocGroupMenuRelationVO vo = new CrmOndocGroupMenuRelationVO();
			
			//회원그룹 접근메뉴 설정
			CrmOndocGroupMenuRelationVO groupMenuRelationVO = new CrmOndocGroupMenuRelationVO();
			groupMenuRelationVO.setGroup_idx("1");
			try {
				groupMenuRelationVO.setGroup_menu_idx("1");
				groupMenuRelationVO.setMenu_code("operationSchedule");
				groupMenuRelationVO.setDel_yn("N");
				groupMenuRelationVO.setWrite_id("inatall");
				groupMenuRelationMapper.initInsert(groupMenuRelationVO);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			try {
				groupMenuRelationVO.setGroup_menu_idx("2");
				groupMenuRelationVO.setMenu_code("sms");
				groupMenuRelationVO.setDel_yn("N");
				groupMenuRelationVO.setWrite_id("inatall");
				groupMenuRelationMapper.initInsert(groupMenuRelationVO);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			try {
				groupMenuRelationVO.setGroup_menu_idx("3");
				groupMenuRelationVO.setMenu_code("patient");
				groupMenuRelationVO.setDel_yn("N");
				groupMenuRelationVO.setWrite_id("inatall");
				groupMenuRelationMapper.initInsert(groupMenuRelationVO);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			try {
				groupMenuRelationVO.setGroup_menu_idx("4");
				groupMenuRelationVO.setMenu_code("memo");
				groupMenuRelationVO.setDel_yn("N");
				groupMenuRelationVO.setWrite_id("inatall");
				groupMenuRelationMapper.initInsert(groupMenuRelationVO);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			try {
				groupMenuRelationVO.setGroup_menu_idx("5");
				groupMenuRelationVO.setMenu_code("statistics");
				groupMenuRelationVO.setDel_yn("N");
				groupMenuRelationVO.setWrite_id("inatall");
				groupMenuRelationMapper.initInsert(groupMenuRelationVO);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			try {
				groupMenuRelationVO.setGroup_menu_idx("6");
				groupMenuRelationVO.setMenu_code("setting");
				groupMenuRelationVO.setDel_yn("N");
				groupMenuRelationVO.setWrite_id("inatall");
				groupMenuRelationMapper.initInsert(groupMenuRelationVO);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		/*****############################################################### crm_usrmngr_group_relation table *****/
		if(installMapper.checkTable("crm_usrmngr_group_relation").equals("0")) {
			installMapper.createCrmUsrmngrGroupRelation();
		}
		
		/*****############################################################### crm_usrmngr_select_relation table *****/
		if(installMapper.checkTable("crm_usrmngr_select_relation").equals("0")) {
			installMapper.createCrmUsrmngrSelectRelation();
		}
		
		/*****############################################################### crm_send_kakao table *****/
		if(installMapper.checkTable("crm_send_kakao").equals("0")) {
			installMapper.createCrmSendKakao();
		}
		/***** crm_send_kakao table *****/
		
		/*****############################################################### crm_send_kakao_log table *****/
		if(installMapper.checkTable("crm_send_kakao_log").equals("0")) {
			installMapper.createCrmSendKakaoLog();
		}
		/***** crm_send_kakao_log table *****/
		
		/*****############################################################### 온닥CRM 최초 로그인 계정 생성 *****/
		//최초에는 병원사용자(usmrgr) 테이블에 온닥CRM환경설정 권한 있는 계정이 없으므로 임으로 생성함
		//초기 설정권한가진 사용자 생성
		CrmUsrmngrVO vo = new CrmUsrmngrVO();
		vo.setXur_id("ONDOCCRM");
		
		int cnt  = usrmngrMapper.checkUsrmngr(vo);
		
		if(cnt == 0) {
			vo.setXur_pass("medicare1234");
			vo.setXur_pass(Sha256.hash(vo.getXur_pass()));
			vo.setXur_name("다솜메디케어");
			vo.setXur_stop("N");
			
			usrmngrMapper.insertUsrmngr(vo);
		}
		
		//사용자 그룹 설정
		CrmUsrmngrGroupRelationVO  usrmngrGroupRelationVO = new CrmUsrmngrGroupRelationVO();
		try {
			usrmngrGroupRelationVO.setUser_group_idx("1");
			usrmngrGroupRelationVO.setXur_id("ONDOCCRM");
			usrmngrGroupRelationVO.setGroup_idx("1");
			usrmngrGroupRelationVO.setDel_yn("N");
			usrmngrGroupRelationVO.setWrite_id("inatall");
			usrmngrGroupRelationMapper.initInsert(usrmngrGroupRelationVO);
		} catch (Exception e) {
			// TODO: handle exception
		}
		/***** 온닥CRM 최초 로그인 계정 생성 *****/
		
		return new CommonVO();
	}
	
	@RequestMapping(value="/crmInstallTest", method=RequestMethod.GET)
	public CommonVO crmInstallTest() throws Exception {
		
		
		return new CommonVO();
	}
	
	@Autowired
	private CrmOperationScheduleMapper operationScheduleMapper;
	
	@Autowired
	private CrmTreatementCounselMapper counselMapper;
	
	@RequestMapping(value="/crmMigrationTest", method=RequestMethod.GET)
	public void crmTest() throws Exception {
		String text = "봍톡스1년패 결제ok/ 두피보톡스4-1 진행.";
		
		//safeWriteToFile("reserveInsertFail.txt", removeSuspiciousKorean(text));
	}
	
//data01.ics : 외래,  data02.ics : 관리
	//https://127.0.0.1:8080/crmGoogleCalendarMigration?fileName=data01.ics&department=1
	//연세모벨르(이근주님, 2025-11-25)에서 구글캘린더 마이그레이션 요청
	@RequestMapping(value="/crmGoogleCalendarMigration", method=RequestMethod.GET)
	public CommonVO crmGoogleCalendarMigration(String fileName, String department) throws Exception {
		CommonVO rVO = new CommonVO();
		
		if(department.equals("")) {
			rVO.setStatus("500");
			rVO.setMessage("department 값이 없습니다.");
			return rVO;
		}
		
		
		// 읽을 파일의 경로를 지정합니다. (예: "data.txt")
        //String fileName = "data01.ics";

     // try-with-resources 구문을 사용하여 자원을 자동적으로 닫습니다.
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8))) {
            String line;
            int lineNumber = 1;
            int lineNumber2 = 0;
            int errorNum = 0;

        	MigrationVO migrationVO = null;
        	String description = "";
            // readLine()은 파일의 끝에 도달하면 null을 반환합니다.
            while ((line = reader.readLine()) != null) {
            	if(lineNumber == 1) migrationVO = new MigrationVO();
            	if(line.equals("BEGIN:VEVENT")) {
            		System.out.println("///////////////////////////////////////////////////////////////////////////////////////"+lineNumber2++);
            	}
            	
            	//시작일자
            	if(line.startsWith("DTSTART")) {
            		String tempDate = line.substring("DTSTART".length()+1);
            		if(tempDate.length() == 16) {
	            		String date = convertUtcToKst(line.substring("DTSTART".length()+1));
	            		
	            		migrationVO.setScheduleDate(date.split("T")[0]);
	            		migrationVO.setScheduleTime(date.split("T")[1].substring(0, 4));
            		} else if(tempDate.length() == 19) {
            			String date = tempDate.substring("VALUE=DATE:".length());
            			
            			migrationVO.setScheduleDate(date);
	            		migrationVO.setScheduleTime("0830");
            		}
            	}

            	if(line.startsWith("DESCRIPTION:")) {
            		description = ".";
            	}
            	
            	if(!description.equals("")) {
            		if(!line.startsWith("LAST-MODIFIED:")) description += line;
            	}
            	
            	if(line.startsWith("LAST-MODIFIED:")) {
            		migrationVO.setHpno(extractFirstPhoneNumber(description));
            		
            		//전화번호는 제거
            		migrationVO.setCounselMemo(cleanDescription(description));
            		
            		
            		if(migrationVO.getHpno() != null && migrationVO.getHpno().length() == 11) {
            			ReceiptPatientVO patientVO = installMapper.selectReceiptPatient(migrationVO.getHpno().substring(0, 3), 
            												migrationVO.getHpno().substring(3, 7), migrationVO.getHpno().substring(7, 11));
            			
            			if(patientVO != null) migrationVO.setPtno(patientVO.getBpt_ptno());
            		}
            		
            		description = "";
            	}
            	
            	if(line.equals("END:VALARM")) {
                	description = "";
            	}
            	
            	if(line.startsWith("SUMMARY:")) {
            		String summary = line.substring("SUMMARY:".length());
            		migrationVO.setReserveMemo(summary);
            	}
                
            	//System.out.println("Line " + lineNumber + ": " + line);
                lineNumber++;
                
                if(line.equals("END:VEVENT")) {
                	description = "";
                	
                	//여기서 insert 처리 해야함
                	System.out.println(migrationVO.toString());
                	
                	if(migrationVO.getPtno() == null) {
                		System.out.println("차트번호가 없음" + migrationVO.getHpno());
                		
                		StringBuilder str = new StringBuilder();
                		str.append("차트번호검색안됨-");
                		if(department.equals("1")) str.append(" (외래)");
                		if(department.equals("2")) str.append(" (관리)");
                		str.append("날짜: "+migrationVO.getScheduleDate());
                		str.append(", 시간: "+migrationVO.getScheduleTime());
                		str.append(", 연락처: "+addHyphen(migrationVO.getHpno()));
                		str.append(", 내용: "+migrationVO.getCounselMemo());
                		
                		safeWriteToFile("reserveInsertFail.txt", str.toString());
                		
                	} else {
	                	CrmOperationScheduleVO vo = new CrmOperationScheduleVO();
	                	//if(fileName.equals("data01.ics")) vo.setDepartment_idx("1");	//상담(외래)
	                	//if(fileName.equals("data02.ics")) vo.setDepartment_idx("2");	//관리(관리)
	                	vo.setDepartment_idx(department);
	                	
	                	vo.setSchedule_date(migrationVO.getScheduleDate());
	                	vo.setSchedule_time(migrationVO.getScheduleTime());
	                	
	                	int count = operationScheduleMapper.countOperationSchedule(vo) + 1;
	                	
	                	vo.setDepartment_number(String.valueOf(count));
	                	vo.setTreatement_time("30");
	                	vo.setPtno(migrationVO.getPtno());
	                	vo.setStatus_idx("0");
	                	vo.setItem_arr("[]");
	                	vo.setWrite_id("migration");
	                	vo.setReserve_type("normal");
	                	vo.setVisit_type("re");
	                	vo.setSms_send_check("N");
	                	vo.setReserve_memo(migrationVO.getReserveMemo());
	                	//스케줄 등록===============================================================
	                	operationScheduleMapper.insertOperationSchedule(vo);
	                	
	                	String operationScheduleIdx = operationScheduleMapper.selectMaxIdx();
	                	
	                	CrmTreatementCounselVO counselVO = new CrmTreatementCounselVO();
	                	counselVO.setOperation_schedule_idx(operationScheduleIdx);
	                	counselVO.setPtno(migrationVO.getPtno());
	                	counselVO.setWrite_id("migration");
	                	counselVO.setCounsel_date(migrationVO.getScheduleDate());
	                	//counselVO.setCounsel_memo(normalizeAllSpaces(migrationVO.getCounselMemo()));
	                	counselVO.setCounsel_memo(removeSuspiciousKorean(normalizeAllSpaces(migrationVO.getCounselMemo())).replace(".DESCRIPTION:", ""));
	                	counselVO.setItem_arr("[]");
	                	counselMapper.insertCounsel(counselVO);
	                	//스케줄 등록===============================================================
                	}
                	
                	migrationVO = new MigrationVO();
                }
                
                //if(lineNumber2 == 2) throw new IOException("에러 발생");
            }
            
            System.out.println(errorNum);
        } catch (IOException e) {
            // 파일이 없거나 읽기 오류 발생 시 처리
            System.err.println("파일을 읽는 도중 오류가 발생했습니다: " + e.getMessage());
        }
		
		return new CommonVO();
	}
	
	public class MigrationVO {
		private String scheduleDate;
		private String scheduleTime;
		private String ptno;
		private String hpno;
		private String reserveMemo;
		private String counselMemo;
		
		public String getScheduleDate() {
			return scheduleDate;
		}
		public void setScheduleDate(String scheduleDate) {
			this.scheduleDate = scheduleDate;
		}
		public String getScheduleTime() {
			return scheduleTime;
		}
		public void setScheduleTime(String scheduleTime) {
			this.scheduleTime = scheduleTime;
		}
		public String getPtno() {
			return ptno;
		}
		public void setPtno(String ptno) {
			this.ptno = ptno;
		}
		public String getHpno() {
			return hpno;
		}
		public void setHpno(String hpno) {
			this.hpno = hpno;
		}
		public String getReserveMemo() {
			return reserveMemo;
		}
		public void setReserveMemo(String reserveMemo) {
			this.reserveMemo = reserveMemo;
		}
		public String getCounselMemo() {
			return counselMemo;
		}
		public void setCounselMemo(String counselMemo) {
			this.counselMemo = counselMemo;
		}
		
		@Override
		public String toString() {
		    return "YourClassName{" +
		            "scheduleDate='" + scheduleDate + '\'' +
		            ", scheduleTime='" + scheduleTime + '\'' +
		            ", ptno='" + ptno + '\'' +
		            ", hpno='" + hpno + '\'' +
		            ", reserveMemo='" + reserveMemo + '\'' +
		            ", counselMemo='" + counselMemo + '\'' +
		            '}';
		}
	}
	
	/**
     * UTC 시간 문자열을 한국 시간(KST) 문자열로 변환합니다.
     * * @param utcString "YYYYMMDDTHHMMSSZ" 형식의 UTC 시간 문자열 (예: "20251125T140000Z")
     * @return "YYYYMMDDTHHMMSS" 형식의 KST 시간 문자열 (예: "20251125T230000")
     */
    public static String convertUtcToKst(String utcString) {
        
        // 1. 입력 및 출력 형식 정의
        // 입력 문자열 포맷: "20251125T140000Z"
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");
        
        // 출력 문자열 포맷: "20251125T230000"
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss");
        
        // 2. 입력 문자열을 UTC ZonedDateTime 객체로 파싱
        // withZone(ZoneId.of("UTC"))를 사용하여 입력 문자열이 UTC임을 명시
        ZonedDateTime utcDateTime = ZonedDateTime.parse(
            utcString, 
            inputFormatter.withZone(ZoneId.of("UTC"))
        );
        
        // 3. 한국 시간대 (KST)로 변환
        ZoneId kstZone = ZoneId.of("Asia/Seoul");
        ZonedDateTime kstDateTime = utcDateTime.withZoneSameInstant(kstZone);
        
        // 4. KST 객체를 원하는 문자열 형식으로 포맷팅하여 반환
        return kstDateTime.format(outputFormatter);
    }
    
    /**
     * 문자열에서 한국 전화번호 패턴과 일치하는 첫 번째 문자열을 추출하고, 
     * 하이픈과 공백을 제거한 순수 숫자 문자열을 반환합니다.
     * * @param text 전화번호가 포함된 입력 문자열
     * @return 하이픈과 공백이 제거된 첫 번째 전화번호 (일치하는 번호가 없으면 null 반환)
     */
    public static String extractFirstPhoneNumber(String text) {
        
        // 1. 한국 전화번호 정규 표현식 정의
        // 정규식 설명:
        // [ -]? : 공백(' ') 또는 하이픈('-')이 있을 수도 있고 없을 수도 있음
        // {3,4} : 3~4자리 숫자
        // {4} : 4자리 숫자
        String regex = 
            "(01[016789][ -]?\\d{3,4}[ -]?\\d{4})|" +  // 휴대폰 번호 (공백/하이픈 허용)
            "(0[2-6]\\d[ -]?\\d{3,4}[ -]?\\d{4})";    // 유선/지역 번호 (공백/하이픈 허용)
        
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        
        // 2. 패턴과 일치하는 첫 번째 항목을 찾습니다.
        if (matcher.find()) {
            String foundNumber = matcher.group(); 
            
            // 3. 추출된 문자열에서 하이픈(-)과 공백(' ')을 모두 제거합니다.
            String cleanNumber = foundNumber.replaceAll("[ -]", ""); 
            
            return cleanNumber;
        }
        
        // 4. 일치하는 항목이 없으면 null 반환
        return null;
    }
    
    /**
     * ICS DESCRIPTION 필드의 텍스트를 정제합니다.
     * HTML 태그 제거, 특수문자 디코딩, 줄바꿈 처리를 수행합니다.
     */
    public static String cleanDescription(String description) {
        if (description == null || description.isEmpty()) {
            return "";
        }
        
        String cleaned = description;
        
        // 1. HTML 태그 제거
        cleaned = removeHtmlTags(cleaned);
        
        // 2. ICS 특수문자 디코딩
        cleaned = decodeIcsSpecialChars(cleaned);
        
        // 3. 연속된 공백/줄바꿈 정리
        cleaned = normalizeWhitespace(cleaned);
        
        // 4. 앞뒤 공백 제거
        cleaned = cleaned.trim();
        
        return cleaned;
    }
    
    /**
     * 문자열에서 <ul>...</ul> 블록과 <li>...</li> 블록 및 그 내용을 모두 제거합니다.
     * * @param htmlString HTML 내용이 포함된 입력 문자열
     * @return <ul>, <li> 블록이 제거된 문자열
     */
    public static String removeUlLiBlocks(String htmlString) {
        
        // 정규 표현식 (두 가지 패턴을 OR(|)로 연결):
        
        // 1. UL 블록 제거: <ul[^>]*>.*?</ul>
        //    <ul[^>]*> : <ul ...> (속성 포함)
        //    .*?       : UL 태그 사이의 모든 문자 (non-greedy)
        //    </ul>     : </ul> 닫는 태그
        
        // 2. LI 블록 제거: <li[^>]*>.*?</li>
        //    <li[^>]*> : <li ...> (속성 포함)
        
        // S (DOTALL) 플래그를 사용하여 .이 줄 바꿈 문자(\n)까지 매치하도록 설정합니다.
        // i (CASE_INSENSITIVE) 플래그를 사용하여 대소문자를 무시하도록 설정합니다.
        String regex = "<ul[^>]*>.*?</ul>|<li[^>]*>.*?</li>";
        
        // Pattern.CASE_INSENSITIVE (대소문자 무시) | Pattern.DOTALL (줄바꿈 매치)
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        
        Matcher matcher = pattern.matcher(htmlString);
        
        // 일치하는 모든 블록을 빈 문자열로 대체
        return matcher.replaceAll("");
    }
    
    // 5. 안전한 파일 쓰기 (예외 처리 포함)
    public static boolean safeWriteToFile(String filePath, String content) {
    	try {
            Path path = Paths.get(filePath).toAbsolutePath().normalize();
            
            // 부모 디렉토리 생성
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }

            // 파일이 존재하는지 확인
            if (Files.exists(path)) {
                // 기존 파일에 새 줄 추가 (append 모드)
                Files.writeString(path, "\n" + content, StandardCharsets.UTF_8,
                    StandardOpenOption.APPEND);
            } else {
                // 새 파일 생성하고 쓰기
                Files.writeString(path, content, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE);
            }
            
            return true;
        } catch (InvalidPathException e) {
            System.err.println("잘못된 파일 경로: " + e.getMessage());
            return false;
        } catch (IOException e) {
            System.err.println("파일 쓰기 실패: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * HTML 태그를 모두 제거합니다.
     */
    private static String removeHtmlTags(String text) {
        // <tag>...</tag> 형태의 모든 HTML 태그 제거
        String result = text.replaceAll("<[^>]+>", " ");
        
        // &nbsp; 등의 HTML 엔티티를 공백으로 변환
        result = result.replaceAll("&nbsp;", " ");
        result = result.replaceAll("&lt;", "<");
        result = result.replaceAll("&gt;", ">");
        result = result.replaceAll("&amp;", "&");
        result = result.replaceAll("&quot;", "\"");
        
        return result;
    }
    
    /**
     * ICS 파일 형식의 특수문자를 디코딩합니다.
     */
    private static String decodeIcsSpecialChars(String text) {
        String result = text;
        
        // ICS 형식의 이스케이프 문자 처리
        result = result.replaceAll("\\\\n", "\n");      // 줄바꿈
        result = result.replaceAll("\\\\,", ",");        // 쉼표
        result = result.replaceAll("\\\\;", ";");        // 세미콜론
        result = result.replaceAll("\\\\\\\\", "\\\\");  // 백슬래시
        
        // URL 인코딩된 문자 디코딩 (예: %20 -> 공백)
        try {
            // -&gt\; 같은 불완전한 HTML 엔티티 처리
            result = result.replaceAll("-&gt\\\\;", "→");
            result = result.replaceAll("&gt\\\\;", ">");
            result = result.replaceAll("&lt\\\\;", "<");
            
            // 일반적인 URL 디코딩 시도
            if (result.contains("%")) {
                result = URLDecoder.decode(result, "UTF-8");
            }
        } catch (UnsupportedEncodingException | IllegalArgumentException e) {
            // 디코딩 실패 시 원본 유지
        }
        
        return result;
    }
    
    /**
     * 연속된 공백과 줄바꿈을 정리합니다.
     */
    private static String normalizeWhitespace(String text) {
        // 연속된 공백을 하나로
        String result = text.replaceAll(" +", " ");
        
        // 연속된 줄바꿈을 최대 2개로 제한
        result = result.replaceAll("(\r?\n){3,}", "\n\n");
        
        return result;
    }
    
    /**
     * 모든 종류의 특수 공백을 일반 공백으로 정규화
     */
    public static String normalizeAllSpaces(String text) {
        if (text == null) return null;
        
        return text
            .replace('\u0020', ' ')  // SPACE (일반 공백)
            .replace('\u00A0', ' ')  // NO-BREAK SPACE
            .replace('\u2007', ' ')  // FIGURE SPACE
            .replace('\u202F', ' ')  // NARROW NO-BREAK SPACE ⭐
            .replace('\u2060', ' ')  // WORD JOINER
            .replace('\u200B', ' ')  // ZERO WIDTH SPACE
            .replace('\u180E', ' ')  // MONGOLIAN VOWEL SEPARATOR
            .replace('\u3000', ' ')  // IDEOGRAPHIC SPACE (전각 공백)
            .replaceAll("\\s+", " ") // 연속된 공백 정리
            .trim();
    }
    
    public static void findProblematicChars(String input) {
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            int codePoint = (int) c;
            
            System.out.printf("위치 %d: 문자코드 U+%04X (%s)%n", 
                    i, codePoint, Character.getName(codePoint));
            
            // 의심스러운 문자 출력
//            if (Character.isISOControl(c) || 
//                codePoint == 0x200B || codePoint == 0x200C || 
//                codePoint == 0x200D || codePoint == 0xFEFF) {
//                
//            }
        }
    }
    
    
    
    
    // 한글 자음 (초성)
    private static final String[] CHOSEONG = {
        "ㄱ", "ㄲ", "ㄴ", "ㄷ", "ㄸ", "ㄹ", "ㅁ", "ㅂ", "ㅃ", "ㅅ",
        "ㅆ", "ㅇ", "ㅈ", "ㅉ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ"
    };
    
    // 한글 모음 (중성)
    private static final String[] JUNGSEONG = {
        "ㅏ", "ㅐ", "ㅑ", "ㅒ", "ㅓ", "ㅔ", "ㅕ", "ㅖ", "ㅗ", "ㅘ",
        "ㅙ", "ㅚ", "ㅛ", "ㅜ", "ㅝ", "ㅞ", "ㅟ", "ㅠ", "ㅡ", "ㅢ", "ㅣ"
    };
    
    // 한글 받침 (종성) - 없음 포함
    private static final String[] JONGSEONG = {
        "", "ㄱ", "ㄲ", "ㄳ", "ㄴ", "ㄵ", "ㄶ", "ㄷ", "ㄹ", "ㄺ",
        "ㄻ", "ㄼ", "ㄽ", "ㄾ", "ㄿ", "ㅀ", "ㅁ", "ㅂ", "ㅄ", "ㅅ",
        "ㅆ", "ㅇ", "ㅈ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ"
    };
    
    /**
     * 한글 글자 분해 클래스
     */
    public static class JamoDecomposition {
        public String choseong;   // 초성
        public String jungseong;  // 중성
        public String jongseong;  // 종성
        
        public JamoDecomposition(String cho, String jung, String jong) {
            this.choseong = cho;
            this.jungseong = jung;
            this.jongseong = jong;
        }
        
        @Override
        public String toString() {
            return String.format("초성:%s, 중성:%s, 종성:%s", choseong, jungseong, jongseong);
        }
    }
    
    /**
     * 한글 글자 분해
     */
    public static JamoDecomposition decomposeHangul(char ch) {
        if (ch < '가' || ch > '힣') {
            return null;
        }
        
        int code = ch - 0xAC00;
        
        int choIndex = code / (21 * 28);
        int jungIndex = (code % (21 * 28)) / 28;
        int jongIndex = code % 28;
        
        return new JamoDecomposition(
            CHOSEONG[choIndex],
            JUNGSEONG[jungIndex],
            JONGSEONG[jongIndex]
        );
    }
    
    /**
     * 한글 사전에 거의 없는 이상한 조합인지 확인
     */
    public static boolean isSuspiciousKorean(char ch) {
        if (ch < '가' || ch > '힣') {
            return false;
        }
        
        JamoDecomposition jamo = decomposeHangul(ch);
        if (jamo == null) {
            return false;
        }
        
        String cho = jamo.choseong;
        String jong = jamo.jongseong;
        
        // 받침이 없으면 대부분 정상
        if (jong.isEmpty()) {
            return false;
        }
        
        // === 의심스러운 조합 규칙 ===
        
        // 1. ㅂ 계열 + ㅍ,ㅌ,ㅋ,ㅊ 받침 (거의 없음)
        if (cho.equals("ㅂ") || cho.equals("ㅃ") || cho.equals("ㅁ")) {
            if (jong.equals("ㅍ") || jong.equals("ㅌ") || jong.equals("ㅋ") || jong.equals("ㅊ")) {
                return true;  // 봍, 뿍, 믾 등
            }
        }
        
        // 2. ㄱ 계열 + ㅂ,ㅍ,ㅌ 받침 (드묾)
        if (cho.equals("ㄱ") || cho.equals("ㄲ") || cho.equals("ㅋ")) {
            if (jong.equals("ㅂ") || jong.equals("ㅍ") || jong.equals("ㅌ")) {
                return true;  // 깁, 꿥 등
            }
        }
        
        // 3. ㄷ 계열 + ㅂ,ㅍ,ㅋ 받침 (드묾)
        if (cho.equals("ㄷ") || cho.equals("ㄸ") || cho.equals("ㅌ")) {
            if (jong.equals("ㅂ") || jong.equals("ㅍ") || jong.equals("ㅋ")) {
                return true;  // 딮, 똟 등
            }
        }
        
        // 4. ㅈ 계열 + ㅂ,ㅍ,ㅋ,ㅌ 받침 (드묾)
        if (cho.equals("ㅈ") || cho.equals("ㅉ") || cho.equals("ㅊ")) {
            if (jong.equals("ㅂ") || jong.equals("ㅍ") || jong.equals("ㅋ") || jong.equals("ㅌ")) {
                return true;  // 짚, 쮑 등
            }
        }
        
        // 5. 쌍자음 + 거센소리 받침 (거의 없음)
        if (cho.equals("ㄲ") || cho.equals("ㄸ") || cho.equals("ㅃ") || 
            cho.equals("ㅆ") || cho.equals("ㅉ")) {
            if (jong.equals("ㅋ") || jong.equals("ㅌ") || jong.equals("ㅊ") || jong.equals("ㅍ")) {
                return true;  // 꿱, 뜅, 쓩 등
            }
        }
        
        // 6. ㅅ,ㅆ 초성 + ㅂ,ㅍ,ㅋ,ㅌ,ㅊ,ㅈ 받침 (드묾)
        if (cho.equals("ㅅ") || cho.equals("ㅆ")) {
            if (jong.equals("ㅂ") || jong.equals("ㅍ") || jong.equals("ㅋ") || 
                jong.equals("ㅌ") || jong.equals("ㅊ") || jong.equals("ㅈ")) {
                return true;  // 솝, 쓩 등
            }
        }
        
        // 7. ㅎ 초성 + ㅂ,ㅍ,ㅋ,ㅌ,ㅊ,ㅈ,ㄷ 받침 (드묾)
        if (cho.equals("ㅎ")) {
            if (jong.equals("ㅂ") || jong.equals("ㅍ") || jong.equals("ㅋ") || 
                jong.equals("ㅌ") || jong.equals("ㅊ") || jong.equals("ㅈ") || jong.equals("ㄷ")) {
                return true;  // 힢, 흪 등
            }
        }
        
        return false;
    }
    
    /**
     * 의심스러운 한글 글자 제거
     */
    public static String removeSuspiciousKorean(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        
        StringBuilder result = new StringBuilder();
        
        for (char ch : text.toCharArray()) {
            // 의심스러운 글자가 아니면 유지
            if (!isSuspiciousKorean(ch)) {
                result.append(ch);
            }
            // 의심스러운 글자는 제거
        }
        
        return result.toString();
    }
    
    /**
     * 한글 글자 분석
     */
    public static void analyzeKoreanChar(char ch) {
        System.out.println("=== 한글 글자 분석: '" + ch + "' ===");
        System.out.printf("유니코드: U+%04X%n", (int)ch);
        
        if (ch < '가' || ch > '힣') {
            System.out.println("완성형 한글이 아닙니다.");
            return;
        }
        
        JamoDecomposition jamo = decomposeHangul(ch);
        System.out.println(jamo);
        
        boolean suspicious = isSuspiciousKorean(ch);
        System.out.println("의심스러움: " + (suspicious ? "⚠️  예 (제거됨)" : "✓ 아니오 (정상)"));
        System.out.println();
    }
    
    /**
     * 텍스트 전체 분석
     */
    public static String analyzeText(String text) {
        boolean foundIssue = false;
        
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            
            if (ch >= '가' && ch <= '힣') {
                if (isSuspiciousKorean(ch)) {
                    System.out.printf("위치 %d: '%c' ⚠️  의심스러운 글자 (제거 대상)%n", i, ch);
                    JamoDecomposition jamo = decomposeHangul(ch);
                    System.out.println("  -> " + jamo);
                }
            }
        }
        
        return removeSuspiciousKorean(text);
    }
    
    /**
     * 의심스러운 단어 찾기
     */
    public static List<String> findSuspiciousWords(String text) {
        List<String> suspicious = new ArrayList<>();
        
        String[] words = text.split("\\s+");
        
        for (String word : words) {
            for (char ch : word.toCharArray()) {
                if (isSuspiciousKorean(ch)) {
                    suspicious.add(word);
                    break;
                }
            }
        }
        
        return suspicious;
    }
    
    /**
     * 연락처에 하이픈을 추가하는 함수
     * @param phoneNumber 하이픈 없는 전화번호 (예: "01090752630")
     * @return 하이픈이 추가된 전화번호 (예: "010-9075-2630")
     */
    public static String addHyphen(String phoneNumber) {
        // null 또는 빈 문자열 체크
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return phoneNumber;
        }
        
        // 숫자만 추출 (기존에 하이픈이나 다른 문자가 있을 경우 제거)
        String numbers = phoneNumber.replaceAll("[^0-9]", "");
        
        // 전화번호 길이에 따라 포맷 적용
        if (numbers.length() == 11) {
            // 010-XXXX-XXXX 형식 (휴대폰)
            return numbers.substring(0, 3) + "-" + 
                   numbers.substring(3, 7) + "-" + 
                   numbers.substring(7);
        } else if (numbers.length() == 10) {
            // 02-XXXX-XXXX 또는 0XX-XXX-XXXX 형식
            if (numbers.startsWith("02")) {
                // 서울 지역번호 (02-XXXX-XXXX)
                return numbers.substring(0, 2) + "-" + 
                       numbers.substring(2, 6) + "-" + 
                       numbers.substring(6);
            } else {
                // 기타 지역번호 (0XX-XXX-XXXX)
                return numbers.substring(0, 3) + "-" + 
                       numbers.substring(3, 6) + "-" + 
                       numbers.substring(6);
            }
        } else if (numbers.length() == 9) {
            // 02-XXX-XXXX 형식
            return numbers.substring(0, 2) + "-" + 
                   numbers.substring(2, 5) + "-" + 
                   numbers.substring(5);
        } else if (numbers.length() == 8) {
            // 지역번호 없는 전화번호 (XXXX-XXXX)
            return numbers.substring(0, 4) + "-" + 
                   numbers.substring(4);
        }
        
        // 형식에 맞지 않는 경우 원본 반환
        return phoneNumber;
    }
}
