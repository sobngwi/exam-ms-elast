package org.sobngwi.exam.ms.elast.ctrl;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "exam.service")
public class RemoteExamServiceProperties {

    private String rootUrl = "http://localhost:8080/vs/";

    public String getRootUrl() {
        return this.rootUrl;
    }

    public void setRootUrl(String examServiceRootUrl) {
        this.rootUrl = examServiceRootUrl;
    }

    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder()
                .rootUri("http://example.com/question/");
    }

}
