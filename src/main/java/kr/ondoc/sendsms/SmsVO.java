package kr.ondoc.sendsms;

import java.util.List;

public class SmsVO {
	String medicareNum = "";
	List<SmsMessageVO> messages;
	String text = "";
	String from = "";
	String reserved_time = "";
	String reserved_id = "";
	
	public String getMedicareNum() {
		return medicareNum;
	}
	public void setMedicareNum(String medicareNum) {
		this.medicareNum = medicareNum;
	}
	public List<SmsMessageVO> getMessages() {
		return messages;
	}
	public void setMessages(List<SmsMessageVO> messages) {
		this.messages = messages;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getReserved_time() {
		return reserved_time;
	}
	public void setReserved_time(String reserved_time) {
		this.reserved_time = reserved_time;
	}
	public String getReserved_id() {
		return reserved_id;
	}
	public void setReserved_id(String reserved_id) {
		this.reserved_id = reserved_id;
	}
}

