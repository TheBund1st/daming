package com.thebund1st.daming.sms

import com.thebund1st.daming.core.SmsVerificationCodeSender
import com.thebund1st.daming.core.SmsVerification
import spock.lang.Specification

import static com.thebund1st.daming.core.MobilePhoneNumber.mobilePhoneNumberOf
import static com.thebund1st.daming.core.SmsVerificationFixture.aSmsVerification

class WhitelistSmsVerificationCodeSenderTest extends Specification {

    private WhitelistSmsVerificationCodeSender subject
    private SmsVerificationCodeSender target = Mock()

    def setup() {
        subject = new WhitelistSmsVerificationCodeSender(target)
    }

    def "it should block sms verification code sending given whitelist is enabled and the mobile is not in the list"() {

        given:
        def verification = aSmsVerification().sendTo('13917777711').build()

        and:
        subject.setWhitelist(['13917777788'].collect { mobilePhoneNumberOf(it) })

        when:
        subject.send(verification)

        then:
        0 * target.send(_ as SmsVerification)
    }

    def "it should continue sms verification code sending given whitelist is enabled and the mobile is in the list"() {

        given:
        def verification = aSmsVerification().sendTo('13917777711').build()

        and:
        subject.setWhitelist(['13917777711'].collect { mobilePhoneNumberOf(it) })

        when:
        subject.send(verification)

        then:
        1 * target.send(verification)
    }

    def "it should continue sms verification code sending given whitelist is disable"() {

        given:
        def verification = aSmsVerification().sendTo('13917777711').build()

        when:
        subject.send(verification)

        then:
        1 * target.send(verification)
    }
}
