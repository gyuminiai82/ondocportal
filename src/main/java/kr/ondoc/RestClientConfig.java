package kr.ondoc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;

@Configuration
public class RestClientConfig {
    @Bean
    public RestTemplate externalApiTemplate(AuthInterceptor authInterceptor) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(authInterceptor));
        return restTemplate;
    }
}