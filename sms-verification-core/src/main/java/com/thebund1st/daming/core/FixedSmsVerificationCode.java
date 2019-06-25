package com.thebund1st.daming.core;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import static com.thebund1st.daming.core.SmsVerificationCode.smsVerificationCodeOf;

@Slf4j
public class FixedSmsVerificationCode implements SmsVerificationCodePattern, SmsVerificationCodeGenerator {

    @Setter
    private String value = "123456";

    @Override
    public SmsVerificationCode generate() {
        return smsVerificationCodeOf(value);
    }

    @Override
    public boolean matches(SmsVerificationCode value) {
        return true;
    }
}
