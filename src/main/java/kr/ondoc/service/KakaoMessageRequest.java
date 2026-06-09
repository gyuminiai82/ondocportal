package kr.ondoc.service;

import java.util.List;
import java.util.Map;

import java.util.List;
import java.util.Map;

public class KakaoMessageRequest {
	private String groupId;
    private List<Receiver> receivers;
    private String caller;
    private String templateCode;
    private boolean useFallback;
    private String fallbackMessage; // 추가: 대체 발송용 메시지 내용

    public KakaoMessageRequest() {}

    public String getGroupId() { return groupId; }
    public void setGroupId(String groupId) { this.groupId = groupId; }
    public List<Receiver> getReceivers() { return receivers; }
    public void setReceivers(List<Receiver> receivers) { this.receivers = receivers; }
    public String getCaller() { return caller; }
    public void setCaller(String caller) { this.caller = caller; }
    public String getTemplateCode() { return templateCode; }
    public void setTemplateCode(String templateCode) { this.templateCode = templateCode; }
    public boolean isUseFallback() { return useFallback; }
    public void setUseFallback(boolean useFallback) { this.useFallback = useFallback; }
    public String getFallbackMessage() { return fallbackMessage; }
    public void setFallbackMessage(String fallbackMessage) { this.fallbackMessage = fallbackMessage; }

    public static class Receiver {
        private String phone;
        private Map<String, String> variables;

        public Receiver() {}
        public Receiver(String phone, Map<String, String> variables) {
            this.phone = phone;
            this.variables = variables;
        }

        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public Map<String, String> getVariables() { return variables; }
        public void setVariables(Map<String, String> variables) { this.variables = variables; }
    }
}