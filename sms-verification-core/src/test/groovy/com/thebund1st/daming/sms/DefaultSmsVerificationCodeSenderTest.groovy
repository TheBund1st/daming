package com.thebund1st.daming.sms

import com.thebund1st.daming.core.SmsVerification
import com.thebund1st.daming.core.SmsVerificationCodeSender
import spock.lang.Specification

import static com.thebund1st.daming.core.SmsVerificationFixture.aSmsVerification

class DefaultSmsVerificationCodeSenderTest extends Specification {

    private DefaultSmsVerificationCodeSender subject
    private SmsVerificationCodeSenderInterceptor interceptor1 = Mock(name: "i_1")
    private SmsVerificationCodeSenderInterceptor interceptor2 = Mock(name: "i_2")
    private SmsVerificationCodeSender target = Mock()

    def setup() {
        subject = new DefaultSmsVerificationCodeSender()
        subject.setTarget(target)
        subject.addInterceptor(interceptor1)
        subject.addInterceptor(interceptor2)
    }

    def "it should allow sms verification code sending given all preHandles return true"() {

        given:
        def verification = aSmsVerification().build()
        interceptor1.preHandle(verification) >> true
        interceptor2.preHandle(verification) >> true

        when:
        subject.send(verification)

        then:
        1 * target.send(verification)
        1 * interceptor1.postHandle(verification)
        1 * interceptor2.postHandle(verification)
    }

    def "it should block sms verification code sending given any preHandles return true"() {

        given:
        def verification = aSmsVerification().build()
        interceptor1.preHandle(verification) >> true
        interceptor2.preHandle(verification) >> false

        when:
        subject.send(verification)

        then:
        0 * target.send(_ as SmsVerification)
        0 * interceptor1.postHandle(_ as SmsVerification)
        0 * interceptor2.postHandle(_ as SmsVerification)
    }

}
