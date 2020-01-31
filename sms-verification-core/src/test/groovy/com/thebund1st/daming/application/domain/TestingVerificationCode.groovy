package com.thebund1st.daming.application.domain


import com.thebund1st.daming.application.domain.SmsVerificationCode

import java.security.SecureRandom

class TestingVerificationCode {

    static String aVerificationCodeOf(int size) {
        SecureRandom rNo = new SecureRandom()
        final int code = rNo.nextInt((999999 - 100000) + 1) + 100000
        String.valueOf(code)
    }

    static SmsVerificationCode aSmsVerificationCodeOf(int size) {
        SmsVerificationCode.smsVerificationCodeOf(aVerificationCodeOf(size))
    }

}
