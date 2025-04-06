package com.jojelo.api_rest_v1.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "http-client.api-rest-v1")
@Getter
@Setter
@Component
public class ApplicationProperties {
    private String baseUrl;
    private int timeout;
}
