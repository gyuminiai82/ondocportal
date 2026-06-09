package kr.ondoc.domain.sybase.crm;

public class CrmLoginParamVO {
	String xur_id;
	String xur_pass;
	String xur_pass_hash;
	String client_ip;
	
	public String getXur_id() {
		return xur_id;
	}
	public void setXur_id(String xur_id) {
		this.xur_id = xur_id;
	}
	public String getXur_pass() {
		return xur_pass;
	}
	public void setXur_pass(String xur_pass) {
		this.xur_pass = xur_pass;
	}
	public String getXur_pass_hash() {
		return xur_pass_hash;
	}
	public void setXur_pass_hash(String xur_pass_hash) {
		this.xur_pass_hash = xur_pass_hash;
	}
	public String getClient_ip() {
		return client_ip;
	}
	public void setClient_ip(String client_ip) {
		this.client_ip = client_ip;
	}
}
