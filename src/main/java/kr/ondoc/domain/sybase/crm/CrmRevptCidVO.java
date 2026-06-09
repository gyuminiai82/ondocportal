package kr.ondoc.domain.sybase.crm;

public class CrmRevptCidVO {
	String cid_no = ""; 		/* 자동증가값 */
	String cid_susin_date = ""; /* 수신일시 */
	String cid_call_no = "";	/* 수신전화번호 */
	String cid_ptno = "";		/* 차트번호 */
	String cid_name = "";		/* 이름 */
	String cid_susin_gu = "";	/* 수신구분 1:수신, 2:발신 */
	String cid_state = "";		/* 통화결과: 0:링, 9:통화  */
	String cid_call_date = "";	/* 응대시간 - 2025.08.06 추가함 */
	String cid_uid = "";		/* 상담자 ID */
	String cid_unm = "";		/* 상담자명  */
	String cid_memo = "";		/* 통화메모 */
	String cid_action = "";		/* 동작 '':시작, 1:통화, 9:완료 */
	String cid_recall = "";		/* 리콜여부 Y/N */
	String cid_recall_date = "";/* 리콜시간 YYYY-MM-DD HH:MM */
	String cid_channel = "";	/* LG U+ 채녈  - 2025.08.06 추가함   */
	String minute = "";
	
	public String getCid_no() {
		return cid_no;
	}
	public void setCid_no(String cid_no) {
		this.cid_no = cid_no;
	}
	public String getCid_susin_date() {
		return cid_susin_date;
	}
	public void setCid_susin_date(String cid_susin_date) {
		this.cid_susin_date = cid_susin_date;
	}
	public String getCid_call_no() {
		return cid_call_no;
	}
	public void setCid_call_no(String cid_call_no) {
		this.cid_call_no = cid_call_no;
	}
	public String getCid_ptno() {
		return cid_ptno;
	}
	public void setCid_ptno(String cid_ptno) {
		this.cid_ptno = cid_ptno;
	}
	public String getCid_name() {
		return cid_name;
	}
	public void setCid_name(String cid_name) {
		this.cid_name = cid_name;
	}
	public String getCid_susin_gu() {
		return cid_susin_gu;
	}
	public void setCid_susin_gu(String cid_susin_gu) {
		this.cid_susin_gu = cid_susin_gu;
	}
	public String getCid_state() {
		return cid_state;
	}
	public void setCid_state(String cid_state) {
		this.cid_state = cid_state;
	}
	public String getCid_call_date() {
		return cid_call_date;
	}
	public void setCid_call_date(String cid_call_date) {
		this.cid_call_date = cid_call_date;
	}
	public String getCid_uid() {
		return cid_uid;
	}
	public void setCid_uid(String cid_uid) {
		this.cid_uid = cid_uid;
	}
	public String getCid_unm() {
		return cid_unm;
	}
	public void setCid_unm(String cid_unm) {
		this.cid_unm = cid_unm;
	}
	public String getCid_memo() {
		return cid_memo;
	}
	public void setCid_memo(String cid_memo) {
		this.cid_memo = cid_memo;
	}
	public String getCid_action() {
		return cid_action;
	}
	public void setCid_action(String cid_action) {
		this.cid_action = cid_action;
	}
	public String getCid_recall() {
		return cid_recall;
	}
	public void setCid_recall(String cid_recall) {
		this.cid_recall = cid_recall;
	}
	public String getCid_recall_date() {
		return cid_recall_date;
	}
	public void setCid_recall_date(String cid_recall_date) {
		this.cid_recall_date = cid_recall_date;
	}
	public String getCid_channel() {
		return cid_channel;
	}
	public void setCid_channel(String cid_channel) {
		this.cid_channel = cid_channel;
	}
	public String getMinute() {
		return minute;
	}
	public void setMinute(String minute) {
		this.minute = minute;
	}
}
