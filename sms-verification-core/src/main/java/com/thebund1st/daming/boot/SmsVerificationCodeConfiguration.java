package com.thebund1st.daming.boot;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmsVerificationCodeConfiguration {

    @ConfigurationProperties(prefix = "daming.sms.verification.code")
    @Bean
    public SmsVerificationCodeProperties smsVerificationCodeProperties() {
        return new SmsVerificationCodeProperties();
    }

}