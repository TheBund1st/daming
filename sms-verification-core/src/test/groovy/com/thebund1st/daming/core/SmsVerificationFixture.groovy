package com.thebund1st.daming.core

import static SmsVerificationCode.smsVerificationCodeOf
import static com.thebund1st.daming.core.MobilePhoneNumber.mobilePhoneNumberOf
import static com.thebund1st.daming.core.TestingMobile.aMobilePhoneNumber
import static com.thebund1st.daming.core.TestingVerificationCode.aVerificationCodeOf

class SmsVerificationFixture {
    private SmsVerification target = new SmsVerification()

    def sendTo(String mobile) {
        this.sendTo(mobilePhoneNumberOf(mobile))
    }

    def sendTo(MobilePhoneNumber mobile) {
        target.setMobile(mobile)
        this
    }

    def codeIs(String code) {
        target.setCode(smsVerificationCodeOf(code))
        this
    }

    def build() {
        target
    }

    static def aSmsVerification() {
        new SmsVerificationFixture()
                .sendTo(aMobilePhoneNumber())
                .codeIs(aVerificationCodeOf(6))
    }

}
