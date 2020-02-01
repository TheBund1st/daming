package com.thebund1st.daming.adapter.sms

import com.thebund1st.daming.adapter.sms.SmsVerificationCodeSenderBlocker
import spock.lang.Specification

import static com.thebund1st.daming.application.domain.SmsVerificationFixture.aSmsVerification

class SmsVerificationCodeSenderBlockerTest extends Specification {

    private SmsVerificationCodeSenderBlocker subject = new SmsVerificationCodeSenderBlocker()


    def "it should block sms verification code sending given whitelist is enabled and the mobile is not in the list"() {

        given:
        def verification = aSmsVerification().build()

        when:
        def result = subject.preHandle(verification)

        then:
        assert !result
    }
}
