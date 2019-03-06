package com.thebund1st.daming.sms;

import com.thebund1st.daming.core.SmsVerification;
import com.thebund1st.daming.core.SmsVerificationCodeSender;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingSmsVerificationCodeSender implements SmsVerificationCodeSender {
    @Override
    public void send(SmsVerification verification) {
        log.info("Sending {} verification code {} to mobile {}, the code is expiring in {}",
                verification.getScope(), verification.getCode(), verification.getMobile(), verification.getExpires());
    }
}
