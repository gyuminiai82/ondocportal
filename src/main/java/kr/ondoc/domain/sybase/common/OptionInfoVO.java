package kr.ondoc.domain.sybase.common;

public class OptionInfoVO {
	String xso_base08 = "";	//xso_base08: 1(dr사용), 0(dr사용안함)
	String xpt_jiwn12 = "";	//0:미사용, 1:사용, 2:퇴원심사(병동기능), 3:병동 Ver2
	
	public String getXso_base08() {
		return xso_base08;
	}
	public void setXso_base08(String xso_base08) {
		this.xso_base08 = xso_base08;
	}
	public String getXpt_jiwn12() {
		return xpt_jiwn12;
	}
	public void setXpt_jiwn12(String xpt_jiwn12) {
		this.xpt_jiwn12 = xpt_jiwn12;
	}
}