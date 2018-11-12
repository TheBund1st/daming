package com.thebund1st.daming.commands

import com.thebund1st.daming.core.MobilePhoneNumber
import com.thebund1st.daming.core.SmsVerificationCode
import com.thebund1st.daming.core.SmsVerificationFixture

import static com.thebund1st.daming.core.MobilePhoneNumber.mobilePhoneNumberOf
import static com.thebund1st.daming.core.SmsVerificationCode.smsVerificationCodeOf

class VerifySmsVerificationCodeCommandFixture {
    private VerifySmsVerificationCodeCommand target = new VerifySmsVerificationCodeCommand()

    def sendTo(String mobile) {
        target.setMobile(mobilePhoneNumberOf(mobile))
        this
    }

    def sendTo(MobilePhoneNumber mobile) {
        this.sendTo(mobile.value)
    }

    def codeIs(String code) {
        target.setCode(smsVerificationCodeOf(code))
        this
    }

    def codeIs(SmsVerificationCode code) {
        this.codeIs(code.value)
    }

    def build() {
        target
    }

    static def aVerifySmsVerificationCodeCommand() {
        def verification = SmsVerificationFixture.aSmsVerification().build()
        new VerifySmsVerificationCodeCommandFixture()
                .sendTo(verification.getMobile())
                .codeIs(verification.getCode())
    }

}
