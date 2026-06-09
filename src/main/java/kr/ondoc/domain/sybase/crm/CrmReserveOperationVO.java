package kr.ondoc.domain.sybase.crm;

public class CrmReserveOperationVO {
	//시술스케쥴 예약정보
	String rev_seq = "";
	String rev_date = "";
	String rev_no = "";			//환자관리번호-crm_patient사용시 필요
	String rev_ptno = "";
	String rev_time_start = "";
	String rev_time_end = "";
	
	//환자정보
	String rev_name = "";
	String rev_birth = "";
	String rev_age = "";
	String rev_sex = "";
	String rev_hpno = "";
	String rev_telno = "";
	String rev_email = "";
	
	String rev_revchk = "";
	
	String rev_opcode = "";	//시술구분 코드
	
	String rev_in_uid = "";
	String rev_in_date = "";
	String rev_up_uid = "";
	String rev_up_date = "";
	String rev_cancel_uid = "";		//취소아이디
	String rev_cancel_date = "";	//취소일
	String rev_cancel_memo = "";	//취소사유
	String rev_cancel_code = "";	//취소사유코드
	
	String rev_sms = "";
	String rev_reserved_id = "";
	
	String con_seq = "";	//상담 일련번호
	
	String con_fieldkind = "";
	String con_part = "";
	String con_opcode = "";
	
	String con_doctor = "";
	String con_uid = "";
	
	public String getRev_seq() {
		return rev_seq;
	}
	public void setRev_seq(String rev_seq) {
		this.rev_seq = rev_seq;
	}
	public String getRev_date() {
		return rev_date;
	}
	public void setRev_date(String rev_date) {
		this.rev_date = rev_date;
	}
	public String getRev_no() {
		return rev_no;
	}
	public void setRev_no(String rev_no) {
		this.rev_no = rev_no;
	}
	public String getRev_ptno() {
		return rev_ptno;
	}
	public void setRev_ptno(String rev_ptno) {
		this.rev_ptno = rev_ptno;
	}
	public String getRev_time_start() {
		return rev_time_start;
	}
	public void setRev_time_start(String rev_time_start) {
		this.rev_time_start = rev_time_start;
	}
	public String getRev_time_end() {
		return rev_time_end;
	}
	public void setRev_time_end(String rev_time_end) {
		this.rev_time_end = rev_time_end;
	}
	public String getRev_name() {
		return rev_name;
	}
	public void setRev_name(String rev_name) {
		this.rev_name = rev_name;
	}
	public String getRev_birth() {
		return rev_birth;
	}
	public void setRev_birth(String rev_birth) {
		this.rev_birth = rev_birth;
	}
	public String getRev_age() {
		return rev_age;
	}
	public void setRev_age(String rev_age) {
		this.rev_age = rev_age;
	}
	public String getRev_sex() {
		return rev_sex;
	}
	public void setRev_sex(String rev_sex) {
		this.rev_sex = rev_sex;
	}
	public String getRev_hpno() {
		return rev_hpno;
	}
	public void setRev_hpno(String rev_hpno) {
		this.rev_hpno = rev_hpno;
	}
	public String getRev_telno() {
		return rev_telno;
	}
	public void setRev_telno(String rev_telno) {
		this.rev_telno = rev_telno;
	}
	public String getRev_email() {
		return rev_email;
	}
	public void setRev_email(String rev_email) {
		this.rev_email = rev_email;
	}
	public String getRev_revchk() {
		return rev_revchk;
	}
	public void setRev_revchk(String rev_revchk) {
		this.rev_revchk = rev_revchk;
	}
	public String getRev_opcode() {
		return rev_opcode;
	}
	public void setRev_opcode(String rev_opcode) {
		this.rev_opcode = rev_opcode;
	}
	public String getRev_in_uid() {
		return rev_in_uid;
	}
	public void setRev_in_uid(String rev_in_uid) {
		this.rev_in_uid = rev_in_uid;
	}
	public String getRev_in_date() {
		return rev_in_date;
	}
	public void setRev_in_date(String rev_in_date) {
		this.rev_in_date = rev_in_date;
	}
	public String getRev_up_uid() {
		return rev_up_uid;
	}
	public void setRev_up_uid(String rev_up_uid) {
		this.rev_up_uid = rev_up_uid;
	}
	public String getRev_up_date() {
		return rev_up_date;
	}
	public void setRev_up_date(String rev_up_date) {
		this.rev_up_date = rev_up_date;
	}
	public String getRev_cancel_uid() {
		return rev_cancel_uid;
	}
	public void setRev_cancel_uid(String rev_cancel_uid) {
		this.rev_cancel_uid = rev_cancel_uid;
	}
	public String getRev_cancel_date() {
		return rev_cancel_date;
	}
	public void setRev_cancel_date(String rev_cancel_date) {
		this.rev_cancel_date = rev_cancel_date;
	}
	public String getRev_cancel_memo() {
		return rev_cancel_memo;
	}
	public void setRev_cancel_memo(String rev_cancel_memo) {
		this.rev_cancel_memo = rev_cancel_memo;
	}
	public String getRev_cancel_code() {
		return rev_cancel_code;
	}
	public void setRev_cancel_code(String rev_cancel_code) {
		this.rev_cancel_code = rev_cancel_code;
	}
	public String getRev_sms() {
		return rev_sms;
	}
	public void setRev_sms(String rev_sms) {
		this.rev_sms = rev_sms;
	}
	public String getRev_reserved_id() {
		return rev_reserved_id;
	}
	public void setRev_reserved_id(String rev_reserved_id) {
		this.rev_reserved_id = rev_reserved_id;
	}
	public String getCon_seq() {
		return con_seq;
	}
	public void setCon_seq(String con_seq) {
		this.con_seq = con_seq;
	}
	public String getCon_fieldkind() {
		return con_fieldkind;
	}
	public void setCon_fieldkind(String con_fieldkind) {
		this.con_fieldkind = con_fieldkind;
	}
	public String getCon_part() {
		return con_part;
	}
	public void setCon_part(String con_part) {
		this.con_part = con_part;
	}
	public String getCon_opcode() {
		return con_opcode;
	}
	public void setCon_opcode(String con_opcode) {
		this.con_opcode = con_opcode;
	}
	public String getCon_doctor() {
		return con_doctor;
	}
	public void setCon_doctor(String con_doctor) {
		this.con_doctor = con_doctor;
	}
	public String getCon_uid() {
		return con_uid;
	}
	public void setCon_uid(String con_uid) {
		this.con_uid = con_uid;
	}
}
