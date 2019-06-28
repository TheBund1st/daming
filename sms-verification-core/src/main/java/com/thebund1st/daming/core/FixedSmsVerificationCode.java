package com.thebund1st.daming.core;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import static com.thebund1st.daming.core.SmsVerificationCode.smsVerificationCodeOf;

@Slf4j
@RequiredArgsConstructor
public class FixedSmsVerificationCode implements SmsVerificationCodePattern, SmsVerificationCodeGenerator {

    private final String value;

    @Override
    public SmsVerificationCode generate() {
        return smsVerificationCodeOf(value);
    }

    @Override
    public boolean matches(SmsVerificationCode value) {
        return true;
    }
}
