package com.thebund1st.daming.application.command


import com.thebund1st.daming.application.domain.MobilePhoneNumber
import com.thebund1st.daming.application.domain.SmsVerificationScope

import static com.thebund1st.daming.application.domain.MobilePhoneNumber.mobilePhoneNumberOf
import static com.thebund1st.daming.application.domain.SmsVerificationScope.smsVerificationScopeOf
import static com.thebund1st.daming.application.domain.TestingMobile.aMobilePhoneNumber
import static com.thebund1st.daming.application.domain.TestingSmsVerificationScope.smsLogin

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
