package com.thebund1st.daming.commands

import com.thebund1st.daming.core.MobilePhoneNumber

import static com.thebund1st.daming.core.MobilePhoneNumber.mobilePhoneNumberOf
import static com.thebund1st.daming.core.TestingMobile.aMobilePhoneNumber

class SendSmsVerificationCodeCommandFixture {
    private SendSmsVerificationCodeCommand target = new SendSmsVerificationCodeCommand()

    def sendTo(String mobile) {
        target.setMobile(mobilePhoneNumberOf(mobile))
        this
    }

    def sendTo(MobilePhoneNumber mobile) {
        this.sendTo(mobile.value)
    }

    def build() {
        target
    }

    static def aSendSmsVerificationCodeCommand() {
        new SendSmsVerificationCodeCommandFixture()
                .sendTo(aMobilePhoneNumber())
    }

}
