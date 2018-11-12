package com.thebund1st.daming.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

import static com.thebund1st.daming.core.SmsVerificationCode.smsVerificationCodeOf;

@Slf4j
@Component
public class SmsVerificationCodeGenerator {

    public SmsVerificationCode generate() {
        SecureRandom rNo = new SecureRandom();
        final int code = rNo.nextInt((999999 - 100000) + 1) + 100000;
        return smsVerificationCodeOf(String.valueOf(code));
    }
}
