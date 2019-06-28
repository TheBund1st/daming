package com.thebund1st.daming.sms;

import com.thebund1st.daming.core.SmsVerification;
import lombok.extern.slf4j.Slf4j;

/**
 * @since 0.9.6
 */
@Slf4j
public class SmsVerificationCodeSenderBlocker implements SmsVerificationCodeSenderInterceptor {

    @Override
    public boolean preHandle(SmsVerification verification) {
        log.debug("Block sending [{}] code to [{}] due to blocker is enabled",
                verification.getScope(), verification.getMobile());
        return false;
    }
}
