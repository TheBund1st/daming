package com.thebund1st.daming.core

import cn.binarywang.tools.generator.ChineseMobileNumberGenerator

import java.security.SecureRandom
import java.text.DecimalFormat

class TestingVerificationCode {

    static String aVerificationCodeOf(int size) {
        SecureRandom rNo = new SecureRandom()
        final int code = rNo.nextInt((999999 - 100000) + 1) + 100000
        String.valueOf(code)
    }

}
