package com.thebund1st.daming.application.domain

import cn.binarywang.tools.generator.ChineseMobileNumberGenerator
import com.thebund1st.daming.application.domain.MobilePhoneNumber

import static com.thebund1st.daming.application.domain.MobilePhoneNumber.mobilePhoneNumberOf

class TestingMobile {

    static MobilePhoneNumber aMobilePhoneNumber() {
        mobilePhoneNumberOf(ChineseMobileNumberGenerator.getInstance().generateFake())
    }

}
