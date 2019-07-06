package com.thebund1st.daming.boot.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SlidingWindowConfiguration {

    @ConfigurationProperties(prefix = "daming.sms.verification.code.sliding.window")
    @Bean
    public SlidingWindowProperties slidingWindowProperties() {
        return new SlidingWindowProperties();
    }


}
