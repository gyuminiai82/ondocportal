package kr.ondoc;


import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class AuthInterceptor implements ClientHttpRequestInterceptor {
    private final TokenManager tokenManager;
    public AuthInterceptor(TokenManager tokenManager) { this.tokenManager = tokenManager; }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        request.getHeaders().add("Authorization", "Bearer " + tokenManager.getAccessToken());
        return execution.execute(request, body);
    }
}