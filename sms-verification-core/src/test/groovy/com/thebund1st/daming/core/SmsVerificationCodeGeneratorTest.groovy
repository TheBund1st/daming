package com.thebund1st.daming.core

import spock.lang.Specification

class SmsVerificationCodeGeneratorTest extends Specification {

    SmsVerificationCodeGenerator subject = new SmsVerificationCodeGenerator()

    def "it should generate a 6 length code"() {
        when:
        SmsVerificationCode actual = subject.generate()

        then:
        assert actual.getValue().length() == 6
    }
}
