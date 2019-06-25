package com.thebund1st.daming.boot.core;

import com.thebund1st.daming.core.FixedSmsVerificationCode;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "daming.sms.verification.bypass")
@ConditionalOnProperty(prefix = "daming.sms.verification.bypass",
        value = "enabled", havingValue = "true", matchIfMissing = false)
public class ByPassConfiguration {

    @Setter
    private String value;

    @Bean
    public FixedSmsVerificationCode fixedSmsVerificationCode() {
        FixedSmsVerificationCode bean = new FixedSmsVerificationCode();
        bean.setValue(value);
        return bean;
    }
}
