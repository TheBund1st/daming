package com.thebund1st.daming.boot.sms;

import com.thebund1st.daming.adapter.sms.DefaultSmsVerificationCodeSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultSmsVerificationSenderConfiguration {

    @Bean(name = "smsVerificationSender")
    public DefaultSmsVerificationCodeSender smsVerificationSender() {
        return new DefaultSmsVerificationCodeSender();
    }


}
