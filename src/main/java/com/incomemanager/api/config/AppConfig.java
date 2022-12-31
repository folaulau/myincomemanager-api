package com.incomemanager.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.incomemanager.api.utils.ObjectUtils;

@Profile({"local", "github", "dev", "prod"})
@Configuration
public class AppConfig {

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = ObjectUtils.getObjectMapper();
        return objectMapper;
    }
}
