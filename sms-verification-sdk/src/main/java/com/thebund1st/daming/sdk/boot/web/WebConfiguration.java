package com.thebund1st.daming.sdk.boot.web;

import com.thebund1st.daming.sdk.web.handler.BadSmsVerificationJwtExceptionHandler;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(WebMvcConfiguration.class)
@Configuration
public class WebConfiguration {

    @ConfigurationProperties(prefix = "daming.sdk.web")
    @Bean(name = "daming.sdk.WebProperties")
    public WebProperties webProperties() {
        return new WebProperties();
    }

    @Bean
    public BadSmsVerificationJwtExceptionHandler badSmsVerificationJwtExceptionHandler() {
        return new BadSmsVerificationJwtExceptionHandler();
    }
}
