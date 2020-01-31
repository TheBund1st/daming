package com.thebund1st.daming.sms;

import com.thebund1st.daming.application.domain.SmsVerification;
import com.thebund1st.daming.application.domain.SmsVerificationCodeSender;
import lombok.extern.slf4j.Slf4j;

/**
 * This implementation is for testing only.
 */
@Slf4j
public class SmsVerificationCodeSenderStub implements SmsVerificationCodeSender {
    @Override
    public void send(SmsVerification verification) {
        log.info("Sending [{}] code {} to [{}], the code is expiring in {}",
                verification.getScope(), verification.getCode(), verification.getMobile(), verification.getExpires());
    }
}
