package com.thebund1st.daming.sdk.boot.web;

import com.thebund1st.daming.sdk.web.handler.BadSmsVerificationJwtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(WebMvcConfiguration.class)
@Configuration
public class WebConfiguration {

    @Bean
    public BadSmsVerificationJwtExceptionHandler badSmsVerificationJwtExceptionHandler() {
        return new BadSmsVerificationJwtExceptionHandler();
    }
}
