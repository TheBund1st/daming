package com.thebund1st.daming.commands

import com.thebund1st.daming.core.MobilePhoneNumber
import com.thebund1st.daming.core.SmsVerificationScope

import static com.thebund1st.daming.core.MobilePhoneNumber.mobilePhoneNumberOf
import static com.thebund1st.daming.core.SmsVerificationScope.smsVerificationScopeOf
import static com.thebund1st.daming.core.TestingMobile.aMobilePhoneNumber
import static com.thebund1st.daming.core.TestingSmsVerificationScope.smsLogin

class SendSmsVerificationCodeCommandFixture {
    private SendSmsVerificationCodeCommand target = new SendSmsVerificationCodeCommand()

    def sendTo(String mobile) {
        target.setMobile(mobilePhoneNumberOf(mobile))
        this
    }

    def sendTo(MobilePhoneNumber mobile) {
        this.sendTo(mobile.value)
    }

    def with(SmsVerificationScope scope) {
        target.setScope(scope)
        this
    }

    def withScope(String value) {
        this.with(smsVerificationScopeOf(value))
    }

    def build() {
        target
    }

    static def aSendSmsVerificationCodeCommand() {
        new SendSmsVerificationCodeCommandFixture()
                .sendTo(aMobilePhoneNumber())
                .with(smsLogin())
    }
}
