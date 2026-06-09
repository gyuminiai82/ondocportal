package kr.ondoc.mapper.sybase.crm;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.ondoc.domain.sybase.manless.ReceiptPatientVO;

public interface CrmInstallMapper {
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT COUNT(*) from sysobjects WHERE name='${table_name}' "
			+ "]]>"
			+ "</script>")
	public String checkTable(String table_name) throws Exception;
	
	//기본설정
	@Select("<script>"
			+ "<![CDATA["
			+ "CREATE TABLE crm_setting"
			+ "("
			+ "  setting_idx       unsigned integer NOT NULL DEFAULT autoincrement,"
			+ "  dashboard_type       VARCHAR(20) DEFAULT '',"
			+ "  open_time_monday     VARCHAR(4)  DEFAULT '',"
			+ "  close_time_monday    VARCHAR(4)  DEFAULT '',"
			+ "  open_time_tuesday    VARCHAR(4)  DEFAULT '',"
			+ "  close_time_tuesday   VARCHAR(4)  DEFAULT '',"
			+ "  open_time_wednesday  VARCHAR(4)  DEFAULT '',"
			+ "  close_time_wednesday VARCHAR(4)  DEFAULT '',"
			+ "  open_time_thursday   VARCHAR(4)  DEFAULT '',"
			+ "  close_time_thursday  VARCHAR(4)  DEFAULT '',"
			+ "  open_time_friday     VARCHAR(4)  DEFAULT '',"
			+ "  close_time_friday    VARCHAR(4)  DEFAULT '',"
			+ "  open_time_saturday   VARCHAR(4)  DEFAULT '',"
			+ "  close_time_saturday  VARCHAR(4)  DEFAULT '',"
			+ "  open_time_sunday     VARCHAR(4)  DEFAULT '',"
			+ "  close_time_sunday    VARCHAR(4)  DEFAULT '',"
			+ "  lunch_start_time     VARCHAR(4)  DEFAULT '',"
			+ "  lunch_end_time       VARCHAR(4)  DEFAULT '',"
			+ "  online_reserve_yn	  VARCHAR(1)  DEFAULT 'N',"
			+ "  sms_member_id        VARCHAR(50)  DEFAULT '',"
			+ "  sms_send_number      VARCHAR(50)  DEFAULT '',"
			+ "  sms_use_yn 	      VARCHAR(1)  DEFAULT 'N',"
			+ "  sms_kakao_hospitalCode      VARCHAR(50)  DEFAULT '',"
			+ "  sms_kakao_send_number      VARCHAR(50)  DEFAULT '',"
			+ "  sms_kakao_use_yn      VARCHAR(1)  DEFAULT 'N',"
			+ "  status_setcolor_use_yn	  			VARCHAR(1)  DEFAULT 'N',"
			+ "  status_setcolor_change_use_yn	  	VARCHAR(1)  DEFAULT 'N',"
			+ "  status_first_background_color      VARCHAR(20) DEFAULT '',"
			+ "  status_first_color       			VARCHAR(20) DEFAULT '',"
			+ "  status_re_background_color       	VARCHAR(20) DEFAULT '',"
			+ "  status_re_color       				VARCHAR(20) DEFAULT '',"
			+ "  modify_date          VARCHAR(12) NULL,"
			+ "  modify_id            VARCHAR(20) NULL,"
			+ "  PRIMARY KEY (setting_idx)"
			+ "); "
			+ "]]>"
			+ "</script>")
	public void createCrmSetting() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "ALTER TABLE crm_setting "
			+ "ADD online_reserve_yn VARCHAR(1) DEFAULT 'N' NOT NULL; "
			+ "]]>"
			+ "</script>")
	public void addFieldOnlineReserveYn() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "ALTER TABLE crm_setting "
			+ "ADD sms_member_id VARCHAR(50) DEFAULT '' NOT NULL; "
			+ "]]>"
			+ "</script>")
	public void addFieldSmsMemberId() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "ALTER TABLE crm_setting "
			+ "ADD sms_send_number VARCHAR(50) DEFAULT '' NOT NULL; "
			+ "]]>"
			+ "</script>")
	public void addFieldSmsSendNumber() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "ALTER TABLE crm_setting "
			+ "ADD status_setcolor_use_yn VARCHAR(1) DEFAULT 'N' NOT NULL; "
			+ "]]>"
			+ "</script>")
	public void addFieldStatusSetcolorUseYn() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "ALTER TABLE crm_setting "
			+ "ADD status_setcolor_change_use_yn VARCHAR(1) DEFAULT 'N' NOT NULL; "
			+ "]]>"
			+ "</script>")
	public void addFieldStatusSetcolorChangeUseYn() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "ALTER TABLE crm_setting "
			+ "ADD status_first_background_color VARCHAR(20) DEFAULT '' NOT NULL; "
			+ "]]>"
			+ "</script>")
	public void addFieldStatusFirstBackgroundColor() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "ALTER TABLE crm_setting "
			+ "ADD status_first_color VARCHAR(20) DEFAULT '' NOT NULL; "
			+ "]]>"
			+ "</script>")
	public void addFieldStatusFirstColor() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "ALTER TABLE crm_setting "
			+ "ADD status_re_background_color VARCHAR(20) DEFAULT '' NOT NULL; "
			+ "]]>"
			+ "</script>")
	public void addFieldStatusReBackgroundColor() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "ALTER TABLE crm_setting "
			+ "ADD status_re_color VARCHAR(20) DEFAULT '' NOT NULL; "
			+ "]]>"
			+ "</script>")
	public void addFieldStatusReColor() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "ALTER TABLE crm_setting "
			+ "ADD sms_use_yn VARCHAR(1)  DEFAULT 'N' NOT NULL; "
			+ "]]>"
			+ "</script>")
	public void addFieldSettingSmsUseYn() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "ALTER TABLE crm_setting "
			+ "ADD sms_kakao_hospitalCode VARCHAR(50) DEFAULT '' NOT NULL; "
			+ "]]>"
			+ "</script>")
	public void addFieldSettingSmsKakaoHospitalCode() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "ALTER TABLE crm_setting "
			+ "ADD sms_kakao_send_number VARCHAR(50) DEFAULT '' NOT NULL; "
			+ "]]>"
			+ "</script>")
	public void addFieldSettingSmsKakaoSendNumber() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "ALTER TABLE crm_setting "
			+ "ADD sms_kakao_use_yn VARCHAR(1)  DEFAULT 'N' NOT NULL; "
			+ "]]>"
			+ "</script>")
	public void addFieldSettingSmsKakaoUseYn() throws Exception;
	
	@Insert("<script>"
			+ "<![CDATA["
			+ "INSERT INTO crm_setting(dashboard_type "
			+ ", open_time_monday, close_time_monday "
			+ ", open_time_tuesday, close_time_tuesday "
			+ ", open_time_wednesday, close_time_wednesday "
			+ ", open_time_thursday, close_time_thursday "
			+ ", open_time_friday, close_time_friday "
			+ ", lunch_start_time, lunch_end_time "
			+ ", status_first_background_color, status_first_color "
			+ ", status_re_background_color, status_re_color "
			+ ") VALUES ('operation_schedule'"
			+ ", '0900', '1800'"
			+ ", '0900', '1800'"
			+ ", '0900', '1800'"
			+ ", '0900', '1800'"
			+ ", '0900', '1800'"
			+ ", '1300', '1400'"
			+ ", '#f28282', '#ffffff'"
			+ ", '#8884f0', '#ffffff'"
			+ ")"
			+ "]]>"
			+ "</script>")
	public void insertSetting() throws Exception;
	
	//부서
	@Select("<script>"
			+ "<![CDATA["
			+ "CREATE TABLE crm_department"
			+ "("
			+ "  department_idx      unsigned integer NOT NULL DEFAULT autoincrement,"
			+ "  department_name     VARCHAR(50) NOT NULL,"
			+ "  department_number   INT         NOT NULL,"
			+ "  sort                INT         NULL DEFAULT 0,"
			+ "  use_yn              VARCHAR(1) NULL DEFAULT 'Y',"
			+ "  del_yn              VARCHAR(1) NULL DEFAULT 'N',"
			+ "  online_reserve_yn   VARCHAR(1) NULL DEFAULT 'N',"
			+ "  receipt_default_yn   VARCHAR(1) NULL DEFAULT 'N',"
			+ "  manless_reserve_yn   VARCHAR(1) NULL DEFAULT 'N',"
			+ "  write_date          VARCHAR(12) NULL,"
			+ "  write_id            VARCHAR(20) NULL,"
			+ "  modify_date         VARCHAR(12) NULL,"
			+ "  modify_id           VARCHAR(20) NULL,"
			+ "  del_date            VARCHAR(12) NULL,"
			+ "  del_id              VARCHAR(20) NULL,"
			+ "  PRIMARY KEY (department_idx)"
			+ "); "
			+ "]]>"
			+ "</script>")
	public void createCrmDepartment() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "ALTER TABLE crm_department "
			+ "ADD receipt_default_yn VARCHAR(1) NULL DEFAULT 'N'; "
			+ "]]>"
			+ "</script>")
	public void addFieldReceiptDefaultYn() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "ALTER TABLE crm_department "
			+ "ADD manless_reserve_yn VARCHAR(1) NULL DEFAULT 'N'; "
			+ "]]>"
			+ "</script>")
	public void addFieldManlessReserveYn() throws Exception;
	
	//휴진
	@Select("<script>"
			+ "<![CDATA["
			+ "CREATE TABLE crm_operation_schedule_closed"
			+ "("
			+ "  operation_schedule_closed_idx unsigned integer NOT NULL DEFAULT autoincrement,"
			+ "  department_idx                unsigned integer NOT NULL,"
			+ "  department_number             unsigned integer NOT NULL,"
			+ "  schedule_date                 VARCHAR(8)  NULL,"
			+ "  schedule_time                 VARCHAR(4)  NULL,"
			+ "  write_date                    VARCHAR(12) NULL,"
			+ "  write_id                      VARCHAR(20) NULL,"
			+ "  PRIMARY KEY (operation_schedule_closed_idx)"
			+ "); "
			+ "]]>"
			+ "</script>")
	public void createCrmOperationScheduleClosed() throws Exception;
	
	//진료항목(대분류)
	@Select("<script>"
			+ "<![CDATA["
			+ ""
			+ "CREATE TABLE crm_treatement_item"
			+ "("
			+ "  treatement_item_idx     unsigned integer NOT NULL DEFAULT autoincrement,"
			+ "  code                    VARCHAR(50) NULL,"
			+ "  name                    VARCHAR(50) NULL,"
			+ "  sort                    INT         NULL DEFAULT 0,"
			+ "  use_yn                  VARCHAR(1)  NULL DEFAULT 'Y',"
			+ "  del_yn                  VARCHAR(1)  NULL DEFAULT 'N',"
			+ "  write_date              VARCHAR(12) NULL,"
			+ "  write_id                VARCHAR(20) NULL,"
			+ "  modify_date             VARCHAR(12) NULL,"
			+ "  modify_id               VARCHAR(20) NULL,"
			+ "  del_date                VARCHAR(12) NULL,"
			+ "  del_id                  VARCHAR(20) NULL,"
			+ "  PRIMARY KEY (treatement_item_idx)"
			+ "); "
			+ "]]>"
			+ "</script>")
	public void createCrmTreatementItem() throws Exception;
	
	//진료항목(소분류)
	@Select("<script>"
			+ "<![CDATA["
			+ "CREATE TABLE crm_treatement_item_detail"
			+ "("
			+ "  treatement_item_detail_idx  unsigned integer NOT NULL DEFAULT autoincrement,"
			+ "  treatement_item_idx         unsigned integer NOT NULL,"
			+ "  code                    	VARCHAR(50) NULL,"
			+ "  name                        VARCHAR(50) NULL,"
			+ "  price                        INT NULL DEFAULT 0,"
			+ "  sale_price					 INT NULL DEFAULT 0,"
			+ "  item_count					 INT NULL DEFAULT 1,"
			+ "  sort                        INT         NULL DEFAULT 0,"
			+ "  use_yn                      VARCHAR(1)  NULL DEFAULT 'Y',"
			+ "  del_yn                      VARCHAR(1)  NULL DEFAULT 'N',"
			+ "  write_date                  VARCHAR(12) NULL,"
			+ "  write_id                    VARCHAR(20) NULL,"
			+ "  modify_date                 VARCHAR(12) NULL,"
			+ "  modify_id                   VARCHAR(20) NULL,"
			+ "  del_date                    VARCHAR(12) NULL,"
			+ "  del_id                      VARCHAR(20) NULL,"
			+ "  PRIMARY KEY (treatement_item_detail_idx)"
			+ "); "
			+ "]]>"
			+ "</script>")
	public void createCrmTreatementItemDetail() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "ALTER TABLE crm_treatement_item_detail "
			+ "ADD sale_price INT NULL DEFAULT 0; "
			+ "]]>"
			+ "</script>")
	public void addFieldItemDetailSalePrice() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "ALTER TABLE crm_treatement_item_detail "
			+ "ADD item_count INT NULL DEFAULT 1; "
			+ "]]>"
			+ "</script>")
	public void addFieldItemDetailItemCount() throws Exception;
	
	//시술스케줄 예약
	@Select("<script>"
			+ "<![CDATA["
			+ ""
			+ "CREATE TABLE crm_operation_schedule"
			+ "("
			+ "  operation_schedule_idx      unsigned integer NOT NULL DEFAULT autoincrement,"
			+ "  status_idx                  unsigned integer NOT NULL,"
			+ "  department_idx              unsigned integer NOT NULL,"
			+ "  department_number           unsigned integer NOT NULL,"
			+ "  setcolor_idx                unsigned integer NULL,"
			+ "  schedule_date               VARCHAR(8)  NULL,"
			+ "  schedule_time               VARCHAR(4)  NULL,"
			+ "  treatement_time             VARCHAR(5)  NULL,"
			+ "  ptno                        VARCHAR(15)  NULL,"
			+ "  receptionist_id             VARCHAR(20)  NULL,"
			+ "  counselor_id                VARCHAR(20)  NULL,"
			+ "  therapist_id                VARCHAR(20)  NULL,"
			+ "  doctor_id                   VARCHAR(20)  NULL,"
			+ "  visit_type                  VARCHAR(5)  NULL,"
			+ "  sms_send_check              VARCHAR(1)  NULL,"
			+ "  reserve_memo                VARCHAR(5000)  NULL,"
			+ "  reserve_type                VARCHAR(20)  NULL DEFAULT 'normal',"	//normal이 아니면 : 온라인 예약으로 예약
			+ "  rev_bookingkey              VARCHAR(50)  NULL,"	//online : 온라인 예약시 상품키
			+ "  rev_reservationkey          VARCHAR(50)  NULL,"	//online : 온라인 예약시 진료실키
			+ "  cancel_memo                 VARCHAR(5000)  NULL,"
			+ "  item_arr                    VARCHAR(5000)  NULL,"
			+ "	 sms_reserved_id             VARCHAR(500) NULL,"
			+ "	 kakao_group_id             VARCHAR(500) NULL,"
			+ "  del_yn                      VARCHAR(1)  NULL DEFAULT 'N',"
			+ "  write_date                  VARCHAR(14) NULL,"
			+ "  write_id                    VARCHAR(20) NULL,"
			+ "  modify_date                 VARCHAR(14) NULL,"
			+ "  modify_id                   VARCHAR(20) NULL,"
			+ "  del_date                    VARCHAR(14) NULL,"
			+ "  del_id                      VARCHAR(20) NULL,"
			+ "  PRIMARY KEY (operation_schedule_idx)"
			+ "); "
			+ "]]>"
			+ "</script>")
	public void createCrmOperationSchedule() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "ALTER TABLE crm_operation_schedule "
			+ "ADD cancel_memo VARCHAR(5000) NULL; "
			+ "]]>"
			+ "</script>")
	public void addFieldCancelMemo() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "ALTER TABLE crm_operation_schedule "
			+ "ADD reserve_type VARCHAR(20) NULL DEFAULT 'normal'; "
			+ "]]>"
			+ "</script>")
	public void addFieldReserveType() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "ALTER TABLE crm_operation_schedule "
			+ "ADD rev_bookingkey VARCHAR(50) NULL; "
			+ "]]>"
			+ "</script>")
	public void addFieldRevBookingkey() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "ALTER TABLE crm_operation_schedule "
			+ "ADD rev_reservationkey VARCHAR(50) NULL; "
			+ "]]>"
			+ "</script>")
	public void addFieldRevReservationkey() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "ALTER TABLE crm_operation_schedule "
			+ "ADD sms_reserved_id VARCHAR(500) NULL; "
			+ "]]>"
			+ "</script>")
	public void addFieldSmsReservedId() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "ALTER TABLE crm_operation_schedule "
			+ "ADD setcolor_idx unsigned integer NULL; "
			+ "]]>"
			+ "</script>")
	public void addFieldSetcolor() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "ALTER TABLE crm_operation_schedule "
			+ "ADD therapist_id VARCHAR(20) NULL; "
			+ "]]>"
			+ "</script>")
	public void addFieldCosTherapistId() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "ALTER TABLE crm_operation_schedule "
			+ "MODIFY write_date VARCHAR(14) NULL; "
			+ "]]>"
			+ "</script>")
	public void modifyFieldWriteDate() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "ALTER TABLE crm_operation_schedule "
			+ "MODIFY modify_date VARCHAR(14) NULL; "
			+ "]]>"
			+ "</script>")
	public void modifyFieldModifyDate() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "ALTER TABLE crm_operation_schedule "
			+ "ADD kakao_group_id VARCHAR(500) NULL; "
			+ "]]>"
			+ "</script>")
	public void addFieldKakaoGroupId() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "ALTER TABLE crm_operation_schedule "
			+ "MODIFY del_date VARCHAR(14) NULL; "
			+ "]]>"
			+ "</script>")
	public void modifyFieldDelDate() throws Exception;
	
	@Update("UPDATE crm_operation_schedule SET "
	        + "write_date = CASE WHEN write_date IS NOT NULL AND write_date != '' "
	        + "                  THEN CONCAT(write_date, '01') ELSE write_date END, "
	        + "modify_date = CASE WHEN modify_date IS NOT NULL AND modify_date != '' "
	        + "                   THEN CONCAT(modify_date, '01') ELSE modify_date END, "
	        + "del_date = CASE WHEN del_date IS NOT NULL AND del_date != '' "
	        + "               THEN CONCAT(del_date, '01') ELSE del_date END")
	public void updateDate() throws Exception;
	
	//시술스케줄 예약 진료항목
	@Select("<script>"
			+ "<![CDATA["
			+ "CREATE TABLE crm_operation_schedule_item_relation"
			+ "("
			+ "  operation_schedule_item_idx     unsigned integer NOT NULL DEFAULT autoincrement,"
			+ "  operation_schedule_idx          unsigned integer NOT NULL,"
			+ "  item_idx                        unsigned integer NOT NULL,"
			+ "  item_detail_idx                 unsigned integer NOT NULL,"
			+ "  PRIMARY KEY (operation_schedule_item_idx)"
			+ "); "
			+ "]]>"
			+ "</script>")
	public void createCrmOperationScheduleItemRelation() throws Exception;
	
	//시술스케줄 상태
	@Select("<script>"
			+ "<![CDATA["
			+ "CREATE TABLE crm_operation_schedule_status"
			+ "("
			+ "  status_idx      unsigned integer NOT NULL DEFAULT autoincrement,"
			+ "  name            VARCHAR(50) NULL,"
			+ "  background_color VARCHAR(20) NULL,"
			+ "  color           VARCHAR(20) NULL,"
			+ "  sort            INT         NULL DEFAULT 0,"
			+ "  use_yn          VARCHAR(1)  NULL DEFAULT 'Y',"
			+ "  del_yn          VARCHAR(1)  NULL DEFAULT 'N',"
			+ "  write_date      VARCHAR(12) NULL,"
			+ "  write_id        VARCHAR(20) NULL,"
			+ "  modify_date     VARCHAR(12) NULL,"
			+ "  modify_id       VARCHAR(20) NULL,"
			+ "  del_date        VARCHAR(12) NULL,"
			+ "  del_id          VARCHAR(20) NULL,"
			+ "  PRIMARY KEY (status_idx)"
			+ "); "
			+ "]]>"
			+ "</script>")
	public void createCrmOperationScheduleStatus() throws Exception;
	
	//상담
	@Select("<script>"
			+ "<![CDATA["
			+ "CREATE TABLE crm_treatement_counsel"
			+ "("
			+ "  counsel_idx      			 unsigned integer NOT NULL DEFAULT autoincrement,"
			+ "  operation_schedule_idx      unsigned integer NOT NULL,"
			+ "  counsel_date                VARCHAR(8)  NULL,"
			+ "  ptno                        VARCHAR(15)  NULL,"
			+ "  receptionist_id             VARCHAR(20)  NULL,"
			+ "  counselor_id                VARCHAR(20)  NULL,"
			+ "  therapist_id                VARCHAR(20)  NULL,"
			+ "  doctor_id                   VARCHAR(20)  NULL,"
			+ "  counsel_memo                VARCHAR(5000)  NULL,"
			+ "  item_arr                    VARCHAR(5000)  NULL,"
			+ "  del_yn                      VARCHAR(1)  NULL DEFAULT 'N',"
			+ "  write_date                  VARCHAR(12) NULL,"
			+ "  write_id                    VARCHAR(20) NULL,"
			+ "  modify_date                 VARCHAR(12) NULL,"
			+ "  modify_id                   VARCHAR(20) NULL,"
			+ "  del_date                    VARCHAR(12) NULL,"
			+ "  del_id                      VARCHAR(20) NULL,"
			+ "  PRIMARY KEY (counsel_idx)"
			+ "); "
			+ "]]>"
			+ "</script>")
	public void createCrmCounsel() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "ALTER TABLE crm_treatement_counsel "
			+ "ADD operation_schedule_idx unsigned integer NULL; "
			+ "]]>"
			+ "</script>")
	public void addFieldTreatementCounselOperationScheduleIdx() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "ALTER TABLE crm_treatement_counsel "
			+ "ADD therapist_id VARCHAR(20) NULL; "
			+ "]]>"
			+ "</script>")
	public void addFieldCtcTherapistId() throws Exception;
	
	//상담 진료항목
	@Select("<script>"
			+ "<![CDATA["
			+ "CREATE TABLE crm_treatement_counsel_item_relation"
			+ "("
			+ "  counsel_item_idx     		unsigned integer NOT NULL DEFAULT autoincrement,"
			+ "  counsel_idx          		unsigned integer NOT NULL,"
			+ "  item_idx                   unsigned integer NOT NULL,"
			+ "  item_detail_idx            unsigned integer NOT NULL,"
			+ "  PRIMARY KEY (counsel_item_idx)"
			+ "); "
			+ "]]>"
			+ "</script>")
	public void createCrmCounselItemRelation() throws Exception;
	
	//진료
	@Select("<script>"
			+ "<![CDATA["
			+ "CREATE TABLE crm_treatement"
			+ "("
			+ "  treatement_idx      unsigned integer NOT NULL DEFAULT autoincrement,"
			+ "  operation_schedule_idx      unsigned integer NOT NULL,"
			+ "  treatement_date             VARCHAR(8)  NULL,"
			+ "  ptno                        VARCHAR(15)  NULL,"
			+ "  receptionist_id             VARCHAR(20)  NULL,"
			+ "  counselor_id                VARCHAR(20)  NULL,"
			+ "  therapist_id                VARCHAR(20)  NULL,"
			+ "  doctor_id                   VARCHAR(20)  NULL,"
			+ "  treatement_memo             VARCHAR(5000)  NULL,"
			+ "  item_arr                    VARCHAR(5000)  NULL,"
			+ "  del_yn                      VARCHAR(1)  NULL DEFAULT 'N',"
			+ "  write_date                  VARCHAR(12) NULL,"
			+ "  write_id                    VARCHAR(20) NULL,"
			+ "  modify_date                 VARCHAR(12) NULL,"
			+ "  modify_id                   VARCHAR(20) NULL,"
			+ "  del_date                    VARCHAR(12) NULL,"
			+ "  del_id                      VARCHAR(20) NULL,"
			+ "  PRIMARY KEY (treatement_idx)"
			+ "); "
			+ "]]>"
			+ "</script>")
	public void createCrmTreatement() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "ALTER TABLE crm_treatement "
			+ "ADD operation_schedule_idx unsigned integer NULL; "
			+ "]]>"
			+ "</script>")
	public void addFieldTreatementOperationScheduleIdx() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "ALTER TABLE crm_treatement "
			+ "ADD therapist_id VARCHAR(20) NULL; "
			+ "]]>"
			+ "</script>")
	public void addFieldCtTherapistId() throws Exception;
	
	//진료 진료항목
	@Select("<script>"
			+ "<![CDATA["
			+ "CREATE TABLE crm_treatement_item_relation"
			+ "("
			+ "  treatement_item_idx     	unsigned integer NOT NULL DEFAULT autoincrement,"
			+ "  treatement_idx          	unsigned integer NOT NULL,"
			+ "  item_idx                   unsigned integer NOT NULL,"
			+ "  item_detail_idx            unsigned integer NOT NULL,"
			+ "  payment_detail_idx         unsigned integer NOT NULL,"
			+ "  PRIMARY KEY (treatement_item_idx)"
			+ "); "
			+ "]]>"
			+ "</script>")
	public void createCrmTreatementItemRelation() throws Exception;
	
	//메모
	@Select("<script>"
			+ "<![CDATA["
			+ "CREATE TABLE crm_memo"
			+ "("
			+ "  memo_idx      unsigned integer NOT NULL DEFAULT autoincrement,"
			+ "  notice_yn                   VARCHAR(1)  NULL DEFAULT 'N',"
			+ "  memo             VARCHAR(5000)  NULL,"
			+ "  memo_date                   VARCHAR(8) NULL,"
			+ "  memo_image                  TEXT NULL,"
			+ "  del_yn                      VARCHAR(1)  NULL DEFAULT 'N',"
			+ "  write_date                  VARCHAR(12) NULL,"
			+ "  write_id                    VARCHAR(20) NULL,"
			+ "  write_name                  VARCHAR(20) NULL,"
			+ "  modify_date                 VARCHAR(12) NULL,"
			+ "  modify_id                   VARCHAR(20) NULL,"
			+ "  del_date                    VARCHAR(12) NULL,"
			+ "  del_id                      VARCHAR(20) NULL,"
			+ "  PRIMARY KEY (memo_idx)"
			+ "); "
			+ "]]>"
			+ "</script>")
	public void createCrmMemo() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "ALTER TABLE crm_memo "
			+ "ADD memo_image TEXT NULL; "
			+ "]]>"
			+ "</script>")
	public void addFieldMemoImage() throws Exception;
	
	//수납
	@Select("<script>"
			+ "<![CDATA["
			+ "CREATE TABLE crm_treatement_payment"
			+ "("
			+ "  payment_idx      unsigned integer NOT NULL DEFAULT autoincrement,"
			+ "  operation_schedule_idx      unsigned integer NOT NULL,"
			+ "  payment_date                VARCHAR(8)  NULL,"
			+ "  ptno                        VARCHAR(15)  NULL,"
			+ "  charge_price                INT  NULL,"
			+ "  sale_price                  INT  NULL,"
			+ "  sale_after_price            INT  NULL,"
			+ "  payment_price               INT  NULL,"
			+ "  unpaid_price                INT  NULL,"
			+ "  unpaid_payment_price        INT  NULL,"
			+ "  card_price                  INT  NULL,"
			+ "  card_gubun                  VARCHAR(20)  NULL,"
			+ "  card_installment_period     INT  NULL,"
			+ "  cash_price                  INT  NULL,"
			+ "  cash_transfer_price         INT  NULL,"
			+ "  cash_receipt_price          INT  NULL,"
			+ "  cash_receipt_yn             VARCHAR(1)  NULL,"
			+ "  payment_memo                VARCHAR(5000)  NULL,"
			+ "  status                      VARCHAR(10)  NULL,  "
			+ "  payment_time                VARCHAR(30)  NULL,"
			+ "  del_yn                      VARCHAR(1)  NULL DEFAULT 'N',"
			+ "  write_date                  VARCHAR(12) NULL,"
			+ "  write_id                    VARCHAR(20) NULL,"
			+ "  modify_date                 VARCHAR(12) NULL,"
			+ "  modify_id                   VARCHAR(20) NULL,"
			+ "  del_date                    VARCHAR(12) NULL,"
			+ "  del_id                      VARCHAR(20) NULL,"
			+ "  PRIMARY KEY (payment_idx)"
			+ "); "
			+ "]]>"
			+ "</script>")
	public void createCrmPayment() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "ALTER TABLE crm_treatement_payment "
			+ "ADD cash_transfer_price INT NULL; "
			+ "]]>"
			+ "</script>")
	public void addFieldCashTransferPrice() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "ALTER TABLE crm_treatement_payment "
			+ "ADD operation_schedule_idx unsigned integer NULL; "
			+ "]]>"
			+ "</script>")
	public void addFieldTreatementPaymentOperationScheduleIdx() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "ALTER TABLE crm_treatement_payment "
			+ "ADD unpaid_payment_price INT NULL; "
			+ "]]>"
			+ "</script>")
	public void addFieldUnpaidPaymentPrice() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "UPDATE crm_treatement_payment set status = 'complete' WHERE status = 'ing' "
			+ "]]>"
			+ "</script>")
	public void updateFieldStatus() throws Exception;
	
	//수납 미수금처리 로그
	@Select("<script>"
			+ "<![CDATA["
			+ ""
			+ "CREATE TABLE crm_treatement_payment_unpaid_log"
			+ "("
			+ "  log_idx      unsigned integer NOT NULL DEFAULT autoincrement,"
			+ "  payment_idx             unsigned integer NOT NULL,"
			+ "  payment_price             unsigned integer NOT NULL,"
			+ "  payment_type                VARCHAR(10)  NULL,"
			+ "  card_gubun                	VARCHAR(20)  NULL,"
			+ "  del_yn                      VARCHAR(1)  NULL DEFAULT 'N',"
			+ "  write_date                  VARCHAR(12) NULL,"
			+ "  write_id                    VARCHAR(20) NULL,"
			+ "  del_date                    VARCHAR(12) NULL,"
			+ "  del_id                      VARCHAR(20) NULL,"
			+ "  PRIMARY KEY (log_idx)"
			+ "); "
			+ "]]>"
			+ "</script>")
	public void createCrmPaymentUnpaidLog() throws Exception;
	
	//수납 세부내역
	@Select("<script>"
			+ "<![CDATA["
			+ ""
			+ "CREATE TABLE crm_treatement_payment_detail"
			+ "("
			+ "  payment_detail_idx      unsigned integer NOT NULL DEFAULT autoincrement,"
			+ "  operation_schedule_idx      unsigned integer NOT NULL,"
			+ "  payment_idx             unsigned integer NOT NULL,"
			+ "  payment_date                VARCHAR(8)  NULL,"
			+ "  ptno                        VARCHAR(15)  NULL,"
			+ "  receptionist_id             VARCHAR(20)  NULL,"
			+ "  counselor_id                VARCHAR(20)  NULL,"
			+ "  therapist_id                VARCHAR(20)  NULL,"
			+ "  doctor_id                   VARCHAR(20)  NULL,"
			+ "  item_idx                    INT  NULL,"
			+ "  item_name                   VARCHAR(50)  NULL,"
			+ "  item_detail_idx             INT  NULL,"
			+ "  item_name_detail            VARCHAR(50)  NULL,"
			+ "  item_count                  INT  NULL,"
			+ "  item_complete_count         INT  NULL,"
			+ "  tax_yn                      VARCHAR(1)  NULL DEFAULT 'N',"
			+ "  charge_price                INT  NULL,"
			+ "  sale_price                  INT  NULL,"
			+ "  sale_after_price            INT  NULL,"
			+ "  del_yn                      VARCHAR(1)  NULL DEFAULT 'N',"
			+ "  write_date                  VARCHAR(12) NULL,"
			+ "  write_id                    VARCHAR(20) NULL,"
			+ "  modify_date                 VARCHAR(12) NULL,"
			+ "  modify_id                   VARCHAR(20) NULL,"
			+ "  del_date                    VARCHAR(12) NULL,"
			+ "  del_id                      VARCHAR(20) NULL,"
			+ "  PRIMARY KEY (payment_detail_idx)"
			+ "); "
			+ "]]>"
			+ "</script>")
	public void createCrmPaymentDetail() throws Exception;
	
	
	@Select("<script>"
			+ "<![CDATA["
			+ "ALTER TABLE crm_treatement_payment_detail "
			+ "ADD operation_schedule_idx unsigned integer NULL; "
			+ "]]>"
			+ "</script>")
	public void addFieldTreatementPaymentDetailOperationScheduleIdx() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "ALTER TABLE crm_treatement_payment_detail "
			+ "ADD therapist_id VARCHAR(20) NULL; "
			+ "]]>"
			+ "</script>")
	public void addFieldCtpdTherapistId() throws Exception;
	
	
	@Select("<script>"
			+ "<![CDATA["
			+ "CREATE TABLE revpt_naver ("
			+ "rev_seq int NOT NULL DEFAULT autoincrement,"
			+ "rev_reservationkey varchar(50) NOT NULL,"
			+ "rev_bookingkey varchar(50) NOT NULL,"
			+ "rev_datetime varchar(30) NOT NULL,"
			+ "rev_name varchar(40) NOT NULL,"
			+ "rev_phone varchar(20) NOT NULL,"
			+ "rev_state varchar(15) NOT NULL,"
			+ "rev_naveruserid varchar(50) NOT NULL,"
			+ "rev_bookingtype varchar(20) NOT NULL,"
			+ "rev_platformtype varchar(20) NOT NULL,"
			+ "rev_cancelleddesc varchar(1000) NULL,"
			+ "rev_email varchar(50) NULL,"
			+ "rev_userrequest varchar(1000) NULL,"
			+ "rev_prebookingkey varchar(50) NULL,"
			+ "rev_visitorname varchar(40) NULL,"
			+ "rev_visitorphone varchar(20) NULL,"
			+ "rev_resno varchar(13) NULL,"
			+ "rev_customformdata varchar(50) NULL,"
			+ "rev_itemoptions varchar(50) NULL,"
			+ "rev_date char(8) NULL,"
			+ "rev_time char(6) NULL,"
			+ "rev_confirm char(1) NULL DEFAULT 'N',"
			+ "rev_currdate datetime DEFAULT current time NULL,"
			+ "rev_ptno varchar(15) NULL,"
			+ "rev_reservationname varchar(50) NULL,"
			+ "rev_period int NULL,"
			+ "rev_cell varchar(10) NULL,"
			+ "PRIMARY KEY (rev_seq)"
			+ "); "
			+ "]]>"
			+ "</script>")
	public void createCrmRecptNaver() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "ALTER TABLE revpt_naver "
			+ "ADD rev_period int NULL; "
			+ "]]>"
			+ "</script>")
	public void addFieldPeriod() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "ALTER TABLE revpt_naver "
			+ "ADD rev_cell varchar(10) NULL; "
			+ "]]>"
			+ "</script>")
	public void addFieldCell() throws Exception;
	
	//취소사유 필드 늘리기
	@Select("<script>"
			+ "<![CDATA["
			+ "ALTER TABLE revpt_naver "
			+ "MODIFY rev_cancelleddesc varchar(1000) NULL; "
			+ "]]>"
			+ "</script>")
	public void addFieldCancelledescExpand() throws Exception;
	
	//요청사항 필드 늘리기
	@Select("<script>"
			+ "<![CDATA["
			+ "ALTER TABLE revpt_naver "
			+ "MODIFY rev_userrequest varchar(1000) NULL; "
			+ "]]>"
			+ "</script>")
	public void addFieldUserrequestExpand() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "CREATE TABLE crm_send_sms"
			+ "("
			+ "  sms_idx                 unsigned integer NOT NULL DEFAULT autoincrement,"
			+ "  action                      VARCHAR(10)  NULL,"
			+ "  send_time						VARCHAR(4)  NULL,"
			+ "  sent_ago_day             	VARCHAR(2)  NULL,"
			+ "  sms_message             			VARCHAR(5000)  NULL,"
			+ "  use_yn                      VARCHAR(1)  NULL DEFAULT 'Y',"
			+ "  del_yn                      VARCHAR(1)  NULL DEFAULT 'N',"
			+ "  write_date                  VARCHAR(12) NULL,"
			+ "  write_id                    VARCHAR(20) NULL,"
			+ "  modify_date                 VARCHAR(12) NULL,"
			+ "  modify_id                   VARCHAR(20) NULL,"
			+ "  del_date                    VARCHAR(12) NULL,"
			+ "  del_id                      VARCHAR(20) NULL,"
			+ "  PRIMARY KEY (sms_idx)"
			+ "); "
			+ "]]>"
			+ "</script>")
	public void createCrmSendSms() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "CREATE TABLE crm_send_sms_log"
			+ "("
			+ "  log_idx                 	unsigned integer NOT NULL DEFAULT autoincrement,"
			+ "  reserved_id                VARCHAR(20)  NULL,"
			+ "  status						VARCHAR(10)  NULL,"
			+ "  send_datetime             	VARCHAR(30)  NULL,"
			+ "  sms_message             	VARCHAR(5000)  NULL,"
			+ "  receive_phone             	VARCHAR(20)  NULL,"
			+ "  receive_name             	VARCHAR(50)  NULL,"
			+ "  write_date                  VARCHAR(12) NULL,"
			+ "  write_id                    VARCHAR(20) NULL,"
			+ "  cancel_date                VARCHAR(12) NULL,"
			+ "  cancel_id                  VARCHAR(20) NULL,"
			+ "  PRIMARY KEY (log_idx)"
			+ "); "
			+ "]]>"
			+ "</script>")
	public void createCrmSendSmsLog() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "CREATE TABLE crm_send_sms_boilerplate_group"
			+ "("
			+ "  sms_boilerplate_group_idx   unsigned integer NOT NULL DEFAULT autoincrement,"
			+ "  group_title                 VARCHAR(100)  NULL,"
			+ "  sort            				INT         NULL DEFAULT 0,"
			+ "  use_yn                      VARCHAR(1)  NULL DEFAULT 'Y',"
			+ "  del_yn                      VARCHAR(1)  NULL DEFAULT 'N',"
			+ "  write_date                  VARCHAR(12) NULL,"
			+ "  write_id                    VARCHAR(20) NULL,"
			+ "  modify_date                 VARCHAR(12) NULL,"
			+ "  modify_id                   VARCHAR(20) NULL,"
			+ "  del_date                    VARCHAR(12) NULL,"
			+ "  del_id                      VARCHAR(20) NULL,"
			+ "  PRIMARY KEY (sms_boilerplate_group_idx)"
			+ "); "
			+ "]]>"
			+ "</script>")
	public void createCrmSendSmsBoilerplateGroup() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "ALTER TABLE crm_send_sms_boilerplate_group "
			+ "ADD sort  INT NULL DEFAULT 0; "
			+ "]]>"
			+ "</script>")
	public void addFieldSendSmsBoilerplateGroupSort() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "CREATE TABLE crm_send_sms_boilerplate"
			+ "("
			+ "  sms_boilerplate_idx         unsigned integer NOT NULL DEFAULT autoincrement,"
			+ "  sms_boilerplate_group_idx  unsigned integer NULL, "
			+ "  sms_title                  VARCHAR(100)  NULL,"
			+ "  sms_message             	VARCHAR(5000)  NULL,"
			+ "  sort            			INT         NULL DEFAULT 0,"
			+ "  use_yn                      VARCHAR(1)  NULL DEFAULT 'Y',"
			+ "  del_yn                      VARCHAR(1)  NULL DEFAULT 'N',"
			+ "  write_date                  VARCHAR(12) NULL,"
			+ "  write_id                    VARCHAR(20) NULL,"
			+ "  modify_date                 VARCHAR(12) NULL,"
			+ "  modify_id                   VARCHAR(20) NULL,"
			+ "  del_date                    VARCHAR(12) NULL,"
			+ "  del_id                      VARCHAR(20) NULL,"
			+ "  PRIMARY KEY (sms_boilerplate_idx)"
			+ "); "
			+ "]]>"
			+ "</script>")
	public void createCrmSendSmsBoilerplate() throws Exception;
	
	//요청사항 필드 늘리기
	@Select("<script>"
			+ "<![CDATA["
			+ "ALTER TABLE crm_send_sms_boilerplate "
			+ "ADD sms_boilerplate_group_idx  unsigned integer NULL; "
			+ "]]>"
			+ "</script>")
	public void addFieldSendSmsBoilerplateGroupIdx() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "ALTER TABLE crm_send_sms_boilerplate "
			+ "ADD sort  INT NULL DEFAULT 0; "
			+ "]]>"
			+ "</script>")
	public void addFieldSendSmsBoilerplateSort() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "CREATE TABLE crm_send_sms_boilerplate_log"
			+ "("
			+ "  log_idx                 	unsigned integer NOT NULL DEFAULT autoincrement,"
			+ "  reserved_id                VARCHAR(20)  NULL,"
			+ "  status						VARCHAR(10)  NULL,"
			+ "  send_datetime             	VARCHAR(30)  NULL,"
			+ "  sms_message             	VARCHAR(5000)  NULL,"
			+ "  receive_phone             	VARCHAR(20)  NULL,"
			+ "  receive_name             	VARCHAR(50)  NULL,"
			+ "  write_date                  VARCHAR(12) NULL,"
			+ "  write_id                    VARCHAR(20) NULL,"
			+ "  cancel_date                VARCHAR(12) NULL,"
			+ "  cancel_id                  VARCHAR(20) NULL,"
			+ "  PRIMARY KEY (log_idx)"
			+ "); "
			+ "]]>"
			+ "</script>")
	public void createCrmSendSmsBoilerplateLog() throws Exception;
	
	//시술스케줄 상태
	@Select("<script>"
			+ "<![CDATA["
			+ "CREATE TABLE crm_setcolor"
			+ "("
			+ "  setcolor_idx      unsigned integer NOT NULL DEFAULT autoincrement,"
			+ "  name            VARCHAR(50) NULL,"
			+ "  background_color VARCHAR(20) NULL,"
			+ "  color           VARCHAR(20) NULL,"
			+ "  sort            INT         NULL DEFAULT 0,"
			+ "  use_yn          VARCHAR(1)  NULL DEFAULT 'Y',"
			+ "  del_yn          VARCHAR(1)  NULL DEFAULT 'N',"
			+ "  write_date      VARCHAR(12) NULL,"
			+ "  write_id        VARCHAR(20) NULL,"
			+ "  modify_date     VARCHAR(12) NULL,"
			+ "  modify_id       VARCHAR(20) NULL,"
			+ "  del_date        VARCHAR(12) NULL,"
			+ "  del_id          VARCHAR(20) NULL,"
			+ "  PRIMARY KEY (setcolor_idx)"
			+ "); "
			+ "]]>"
			+ "</script>")
	public void createCrmSetcolor() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "CREATE TABLE crm_ondoc_group"
			+ "	("
			+ "	  group_idx      unsigned integer NOT NULL DEFAULT autoincrement,"
			+ "	  name            VARCHAR(50) NULL,"
			+ "	  del_yn          VARCHAR(1)  NULL DEFAULT 'N',"
			+ "	  write_date      VARCHAR(12) NULL,"
			+ "	  write_id        VARCHAR(20) NULL,"
			+ "	  modify_date     VARCHAR(12) NULL,"
			+ "	  modify_id       VARCHAR(20) NULL,"
			+ "	  del_date        VARCHAR(12) NULL,"
			+ "	  del_id          VARCHAR(20) NULL,"
			+ "	  PRIMARY KEY (group_idx)"
			+ "	); "
			+ "]]>"
			+ "</script>")
	public void createCrmOndocGroup() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "CREATE TABLE crm_ondoc_group_menu_relation"
			+ "("
			+ "  group_menu_idx      unsigned integer NOT NULL DEFAULT autoincrement,"
			+ "  group_idx            unsigned integer NOT NULL,"
			+ "  menu_code            VARCHAR(50) NULL,"
			+ "  del_yn          VARCHAR(1)  NULL DEFAULT 'N',"
			+ "  write_date      VARCHAR(12) NULL,"
			+ "  write_id        VARCHAR(20) NULL,"
			+ "  del_date        VARCHAR(12) NULL,"
			+ "  del_id          VARCHAR(20) NULL,"
			+ "  PRIMARY KEY (group_menu_idx)"
			+ "); "
			+ "]]>"
			+ "</script>")
	public void createCrmOndocGroupMenuRelation() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "CREATE TABLE crm_usrmngr_select_relation"
			+ "	("
			+ "	  select_idx      unsigned integer NOT NULL DEFAULT autoincrement,"
			+ "	  gubun           VARCHAR(50) NULL,"
			+ "	  usrmngr_id      VARCHAR(10) NULL,"
			+ "	  del_yn          VARCHAR(1)  NULL DEFAULT 'N',"
			+ "	  write_date      VARCHAR(12) NULL,"
			+ "	  write_id        VARCHAR(20) NULL,"
			+ "	  del_date        VARCHAR(12) NULL,"
			+ "	  del_id          VARCHAR(20) NULL,"
			+ "	  PRIMARY KEY (select_idx)"
			+ "	); "
			+ "]]>"
			+ "</script>")
	public void createCrmUsrmngrSelectRelation() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "CREATE TABLE crm_usrmngr_group_relation"
			+ "	("
			+ "	  user_group_idx      unsigned integer NOT NULL DEFAULT autoincrement,"
			+ "	  xur_id            VARCHAR(10) NULL,"
			+ "	  group_idx       unsigned integer NOT NULL,"
			+ "	  del_yn          VARCHAR(1)  NULL DEFAULT 'N',"
			+ "	  write_date      VARCHAR(12) NULL,"
			+ "	  write_id        VARCHAR(20) NULL,"
			+ "	  del_date        VARCHAR(12) NULL,"
			+ "	  del_id          VARCHAR(20) NULL,"
			+ "	  PRIMARY KEY (user_group_idx)"
			+ "	); "
			+ "]]>"
			+ "</script>")
	public void createCrmUsrmngrGroupRelation() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "CREATE TABLE crm_manless_question "
			+ "	("
			+ "	  question_idx      unsigned integer NOT NULL DEFAULT autoincrement,"
			+ "	  question_title     VARCHAR(200) NULL,"
			+ "	  question_content TEXT NULL,"
			+ "	  sort          VARCHAR(1)  NULL DEFAULT 0,"
			+ "	  use_yn          VARCHAR(1) NULL DEFAULT 'Y',"
			+ "	  del_yn          VARCHAR(1)  NULL DEFAULT 'N',"
			+ "	  write_date      VARCHAR(12) NULL,"
			+ "	  write_id        VARCHAR(20) NULL,"
			+ "	  modify_date      VARCHAR(12) NULL,"
			+ "	  modify_id        VARCHAR(20) NULL,"
			+ "	  del_date        VARCHAR(12) NULL,"
			+ "	  del_id          VARCHAR(20) NULL,"
			+ "	  PRIMARY KEY (question_idx)"
			+ "	); "
			+ "]]>"
			+ "</script>")
	public void createCrmManlessQuestion() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "CREATE TABLE crm_manless_question_reply "
			+ "	("
			+ "	  question_reply_idx      unsigned integer NOT NULL DEFAULT autoincrement,"
			+ "	  rev_seq   INT NULL DEFAULT 0, "
			+ "	  name      	VARCHAR(50) NULL,"
			+ "	  birth      	VARCHAR(8) NULL,"
			+ "	  hpno      	VARCHAR(14) NULL,"
			+ "	  ptno      	VARCHAR(15) NULL,"
			+ "	  title      		VARCHAR(200) NULL,"
			+ "	  reply		 	TEXT NULL,"
			+ "	  write_date      VARCHAR(12) NULL,"
			+ "	  PRIMARY KEY (question_reply_idx)"
			+ "	); "
			+ "]]>"
			+ "</script>")
	public void createCrmManlessQuestionReply() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "ALTER TABLE crm_manless_question_reply "
			+ "ADD rev_seq INT NULL DEFAULT 0; "
			+ "]]>"
			+ "</script>")
	public void addFieldManlessQuestionReplyRevSeq() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "CREATE TABLE crm_send_kakao"
			+ "("
			+ "  kakao_idx                 unsigned integer NOT NULL DEFAULT autoincrement,"
			+ "  action                      VARCHAR(10)  NULL,"
			+ "  send_time						VARCHAR(4)  NULL,"
			+ "  sent_ago_day             	VARCHAR(2)  NULL,"
			+ "  template_code             	VARCHAR(100)  NULL,"
			+ "  template_message           VARCHAR(5000)  NULL,"
			+ "  use_yn                      VARCHAR(1)  NULL DEFAULT 'Y',"
			+ "  del_yn                      VARCHAR(1)  NULL DEFAULT 'N',"
			+ "  write_date                  VARCHAR(12) NULL,"
			+ "  write_id                    VARCHAR(20) NULL,"
			+ "  modify_date                 VARCHAR(12) NULL,"
			+ "  modify_id                   VARCHAR(20) NULL,"
			+ "  del_date                    VARCHAR(12) NULL,"
			+ "  del_id                      VARCHAR(20) NULL,"
			+ "  PRIMARY KEY (kakao_idx)"
			+ "); "
			+ "]]>"
			+ "</script>")
	public void createCrmSendKakao() throws Exception;
	
	@Select("<script>"
			+ "<![CDATA["
			+ "CREATE TABLE crm_send_kakao_log"
			+ "("
			+ "  log_idx                 	unsigned integer NOT NULL DEFAULT autoincrement,"
			+ "  group_id            	    VARCHAR(20)  NULL,"
			+ "  status						VARCHAR(10)  NULL,"
			+ "  send_datetime             	VARCHAR(30)  NULL,"
			+ "  `message`	             	VARCHAR(5000)  NULL,"
			+ "  receive_phone             	VARCHAR(20)  NULL,"
			+ "  receive_name             	VARCHAR(50)  NULL,"
			+ "  write_date                  VARCHAR(12) NULL,"
			+ "  write_id                    VARCHAR(20) NULL,"
			+ "  cancel_date                VARCHAR(12) NULL,"
			+ "  cancel_id                  VARCHAR(20) NULL,"
			+ "  PRIMARY KEY (log_idx)"
			+ "); "
			+ "]]>"
			+ "</script>")
	public void createCrmSendKakaoLog() throws Exception;
	
	
	//전화번호로 환자정보 가져오기
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT TOP 1 * "
			+ "FROM patient "
			+ "WHERE 0=0 "
			+ "]]>"
			+ "AND bpt_hpno like '${num}%' AND (bpt_hpno like '%${phoneFront}____' OR bpt_hpno like '%${phoneFront}_____') AND bpt_hpno like '%${phoneEnd}' "
			+ "ORDER BY bpt_enddt DESC "
			+ "</script>")
	public ReceiptPatientVO selectReceiptPatient(String num, String phoneFront, String phoneEnd) throws Exception;
}
