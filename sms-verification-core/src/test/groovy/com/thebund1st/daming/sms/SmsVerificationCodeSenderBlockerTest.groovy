package com.thebund1st.daming.sms

import spock.lang.Specification

import static com.thebund1st.daming.core.SmsVerificationFixture.aSmsVerification

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
