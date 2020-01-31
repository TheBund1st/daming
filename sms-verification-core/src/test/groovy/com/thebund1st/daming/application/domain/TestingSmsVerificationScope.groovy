package com.thebund1st.daming.application.domain

import com.thebund1st.daming.application.domain.SmsVerificationScope

import java.security.SecureRandom
import java.text.DecimalFormat

import static com.thebund1st.daming.application.domain.SmsVerificationScope.smsVerificationScopeOf

class TestingSmsVerificationScope {

    static SmsVerificationScope anyScope() {
        smsVerificationScopeOf(String.valueOf(
                new DecimalFormat("######").format(new SecureRandom().nextInt(100000))))
    }

    static SmsVerificationScope smsLogin() {
        smsVerificationScopeOf("SMS_LOGIN")
    }
}
