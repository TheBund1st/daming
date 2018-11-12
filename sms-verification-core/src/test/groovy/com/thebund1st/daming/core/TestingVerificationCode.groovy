package com.thebund1st.daming.core

import cn.binarywang.tools.generator.ChineseMobileNumberGenerator

import java.security.SecureRandom
import java.text.DecimalFormat

class TestingVerificationCode {

    static String aVerificationCodeOf(int size) {
        new DecimalFormat("######").format(new SecureRandom().nextInt(100000))
    }

}
