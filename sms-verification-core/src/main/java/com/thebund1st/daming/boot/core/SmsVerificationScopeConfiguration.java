package com.thebund1st.daming.boot.core;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmsVerificationScopeConfiguration {

    @ConfigurationProperties(prefix = "daming.sms.verification.scope")
    @Bean
    public SmsVerificationScopeProperties smsVerificationScopeProperties() {
        return new SmsVerificationScopeProperties();
    }

}