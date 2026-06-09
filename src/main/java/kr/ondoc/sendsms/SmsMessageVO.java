package kr.ondoc.sendsms;

public class SmsMessageVO {
	String to = "";
	String senderName = "";
	
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
}
