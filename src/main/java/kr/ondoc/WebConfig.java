package kr.ondoc;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/ondoccrm/**")
                .addResourceLocations("classpath:/static/ondoccrm/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        // 빈 경로 또는 "/" 일 때 index.html로
                        if (resourcePath.isEmpty() || resourcePath.equals("/")) {
                            return new ClassPathResource("/static/ondoccrm/index.html");
                        }
                        
                        Resource requestedResource = location.createRelative(resourcePath);
                        
                        // 실제 파일이 존재하면 반환
                        if (requestedResource.exists() && requestedResource.isReadable()) {
                            return requestedResource;
                        }
                        
                        // 파일 확장자가 있으면 null (404)
                        if (resourcePath.contains(".")) {
                            return null;
                        }
                        
                        // Vue Router 경로는 index.html
                        return new ClassPathResource("/static/ondoccrm/index.html");
                    }
                });
    }
}