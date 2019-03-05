package com.thebund1st.daming.sms;

import com.thebund1st.daming.application.SmsVerificationSender;
import com.thebund1st.daming.core.SmsVerification;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingSmsVerificationSender implements SmsVerificationSender {
    @Override
    public void send(SmsVerification verification) {
        log.info("Sending verification code {} to mobile {}, the code is expiring in {}",
                verification.getCode(), verification.getMobile(), verification.getExpires());
    }
}
