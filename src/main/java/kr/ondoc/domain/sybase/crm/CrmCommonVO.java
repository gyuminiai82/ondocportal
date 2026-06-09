package kr.ondoc.domain.sybase.crm;

import kr.ondoc.domain.sybase.common.CommonVO;

public class CrmCommonVO extends CommonVO {
	String ptno = "";
	String age = "";
	String idx = "";
	String sms_reserved_id = "";
	String kakao_group_id = "";
	
	public String getPtno() {
		return ptno;
	}
	public void setPtno(String ptno) {
		this.ptno = ptno;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getIdx() {
		return idx;
	}
	public void setIdx(String idx) {
		this.idx = idx;
	}
	public String getSms_reserved_id() {
		return sms_reserved_id;
	}
	public void setSms_reserved_id(String sms_reserved_id) {
		this.sms_reserved_id = sms_reserved_id;
	}
	public String getKakao_group_id() {
		return kakao_group_id;
	}
	public void setKakao_group_id(String kakao_group_id) {
		this.kakao_group_id = kakao_group_id;
	}
}
