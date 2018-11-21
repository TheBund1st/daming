package com.thebund1st.daming.sms

import com.thebund1st.daming.application.SmsVerificationSender
import com.thebund1st.daming.core.SmsVerification
import spock.lang.Specification

import static com.thebund1st.daming.core.SmsVerificationFixture.aSmsVerification

class WhitelistSmsVerificationSenderTest extends Specification {

    private WhitelistSmsVerificationSender subject
    private SmsVerificationSender target = Mock()

    def setup() {
        subject = new WhitelistSmsVerificationSender(target)
    }

    def "it should block sms verification code sending given whitelist is enabled and the mobile is not in the list"() {

        given:
        def verification = aSmsVerification().sendTo('13917777711').build()

        and:
        subject.setWhitelist(['13917777788'])

        when:
        subject.send(verification)

        then:
        0 * target.send(_ as SmsVerification)
    }

    def "it should continue sms verification code sending given whitelist is enabled and the mobile is in the list"() {

        given:
        def verification = aSmsVerification().sendTo('13917777711').build()

        and:
        subject.setWhitelist(['13917777711'])

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
