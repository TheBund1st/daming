package com.thebund1st.daming.boot.core;

import com.thebund1st.daming.core.MobilePhoneNumberPattern;
import com.thebund1st.daming.core.RandomNumberSmsVerificationCode;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class CoreConfiguration {

    @Bean
    public RandomNumberSmsVerificationCode randomNumberSmsVerificationCode() {
        return new RandomNumberSmsVerificationCode();
    }

    @Bean
    public MobilePhoneNumberPattern mobilePhoneNumberPattern() {
        return new MobilePhoneNumberPattern();
    }

}
