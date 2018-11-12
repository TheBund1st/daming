package com.thebund1st.daming.core

import cn.binarywang.tools.generator.ChineseMobileNumberGenerator

import static com.thebund1st.daming.core.MobilePhoneNumber.mobilePhoneNumberOf

class TestingMobile {

    static MobilePhoneNumber aMobilePhoneNumber() {
        mobilePhoneNumberOf(ChineseMobileNumberGenerator.getInstance().generateFake())
    }

}
