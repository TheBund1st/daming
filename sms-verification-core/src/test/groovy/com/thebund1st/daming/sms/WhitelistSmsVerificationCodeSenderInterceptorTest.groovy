package com.thebund1st.daming.sms


import spock.lang.Specification

import static com.thebund1st.daming.application.domain.MobilePhoneNumber.mobilePhoneNumberOf
import static com.thebund1st.daming.application.domain.SmsVerificationFixture.aSmsVerification

class WhitelistSmsVerificationCodeSenderInterceptorTest extends Specification {

    private WhitelistSmsVerificationCodeSenderInterceptor subject = new WhitelistSmsVerificationCodeSenderInterceptor()


    def "it should block sms verification code sending given whitelist is enabled and the mobile is not in the list"() {

        given:
        def verification = aSmsVerification().sendTo('13917777711').build()

        and:
        subject.setWhitelist(['13917777788'].collect { mobilePhoneNumberOf(it) })

        when:
        def result = subject.preHandle(verification)

        then:
        assert !result
    }

    def "it should continue sms verification code sending given whitelist is enabled and the mobile is in the list"() {

        given:
        def verification = aSmsVerification().sendTo('13917777711').build()

        and:
        subject.setWhitelist(['13917777711'].collect { mobilePhoneNumberOf(it) })

        when:
        def result = subject.preHandle(verification)

        then:
        assert result
    }

    def "it should continue sms verification code sending given whitelist is disable"() {

        given:
        def verification = aSmsVerification().sendTo('13917777711').build()

        when:
        def result = subject.preHandle(verification)

        then:
        assert result
    }
}
