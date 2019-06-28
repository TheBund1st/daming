package com.thebund1st.daming.boot.core;

import com.thebund1st.daming.core.FixedSmsVerificationCode;
import com.thebund1st.daming.sms.DefaultSmsVerificationCodeSender;
import com.thebund1st.daming.sms.SmsVerificationCodeSenderBlocker;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "daming.sms.verification.bypass")
@ConditionalOnProperty(prefix = "daming.sms.verification.bypass",
        value = "enabled", havingValue = "true", matchIfMissing = false)
public class BypassConfiguration {

    @Setter
    private String value;

    @Autowired
    private DefaultSmsVerificationCodeSender defaultSmsVerificationCodeSender;

    @Bean
    public FixedSmsVerificationCode fixedSmsVerificationCode() {
        FixedSmsVerificationCode bean = new FixedSmsVerificationCode();
        bean.setValue(value);
        return bean;
    }

    @PostConstruct
    public void blockSmsSending() {
        defaultSmsVerificationCodeSender.addInterceptor(new SmsVerificationCodeSenderBlocker());
    }
}
