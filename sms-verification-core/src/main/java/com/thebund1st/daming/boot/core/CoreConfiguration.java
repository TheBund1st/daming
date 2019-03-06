package com.thebund1st.daming.boot.core;

import com.thebund1st.daming.core.SmsVerificationCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class CoreConfiguration {

    @Bean
    public SmsVerificationCodeGenerator smsVerificationCodeGenerator() {
        return new SmsVerificationCodeGenerator();
    }

}
