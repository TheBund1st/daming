package com.thebund1st.daming.boot.core;

import com.thebund1st.daming.core.FixedSmsVerificationCode;
import com.thebund1st.daming.core.MobilePhoneNumberPattern;
import com.thebund1st.daming.core.RandomNumberSmsVerificationCode;
import com.thebund1st.daming.core.SmsVerificationCodeGenerator;
import com.thebund1st.daming.core.SmsVerificationCodePattern;
import com.thebund1st.daming.core.SmsVerificationScopePattern;
import com.thebund1st.daming.core.StaticSmsVerificationScopePattern;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class CoreConfiguration {

    private final SmsVerificationScopeProperties smsVerificationScopeProperties;

    @Bean
    @ConditionalOnProperty(prefix = "daming.sms.verification",
            value = "bypass", havingValue = "true", matchIfMissing = false)
    public FixedSmsVerificationCode fixedSmsVerificationCode() {
        return new FixedSmsVerificationCode();
    }

    @Bean
    @ConditionalOnMissingBean({SmsVerificationCodePattern.class, SmsVerificationCodeGenerator.class})
    public RandomNumberSmsVerificationCode randomNumberSmsVerificationCode() {
        return new RandomNumberSmsVerificationCode();
    }

    @Bean
    public MobilePhoneNumberPattern mobilePhoneNumberPattern() {
        return new MobilePhoneNumberPattern();
    }

    @Bean
    @ConditionalOnMissingBean(SmsVerificationScopePattern.class)
    public StaticSmsVerificationScopePattern staticSmsVerificationScopePattern() {
        return new StaticSmsVerificationScopePattern(smsVerificationScopeProperties.validScopes());
    }

}
