package kr.ondoc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenManager {
    @Value("${api.key}") private String apiKey;
    @Value("${api.secret}") private String clientSecret;
    @Value("${api.base-url}") private String baseUrl;

    private String accessToken;
    private String refreshToken;
    private LocalDateTime expiryTime;
    private final RestTemplate restTemplate = new RestTemplate();

    public synchronized String getAccessToken() {
        if (accessToken == null || LocalDateTime.now().isAfter(expiryTime.minusMinutes(1))) {
            refreshOrFetchToken();
        }
        return accessToken;
    }

    private void refreshOrFetchToken() {
        if (refreshToken == null) {
            fetchInitialToken();
        } else {
            try {
                fetchRefreshToken();
            } catch (Exception e) {
                fetchInitialToken();
            }
        }
    }

    private void fetchInitialToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", apiKey);
        headers.set("x-client-secret", clientSecret);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 에러 해결 포인트: 바디가 비어있으면 안 되므로 빈 JSON({}) 역할을 할 빈 Map을 넣어줌
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(new HashMap<>(), headers);
        
        // postForObject 호출 시 entity(헤더+빈바디)를 전달
        TokenResponse res = restTemplate.postForObject(baseUrl + "/auth/token", entity, TokenResponse.class);
        if (res != null) saveToken(res);
    }

    private void fetchRefreshToken() {
        Map<String, String> body = Map.of("refreshToken", refreshToken);
        TokenResponse res = restTemplate.postForObject(baseUrl + "/auth/refresh", body, TokenResponse.class);
        if (res != null) saveToken(res);
    }

    private void saveToken(TokenResponse res) {
        this.accessToken = res.getAccessToken();
        this.refreshToken = res.getRefreshToken();
        this.expiryTime = LocalDateTime.now().plusSeconds(res.getExpiresIn());
    }
}