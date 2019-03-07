package com.thebund1st.daming.core

import spock.lang.Specification
import spock.lang.Unroll

import static com.thebund1st.daming.core.SmsVerificationCode.smsVerificationCodeOf

class SmsVerificationCodeGeneratorTest extends Specification {

    RandomNumberSmsVerificationCode subject = new RandomNumberSmsVerificationCode()

    def "it should generate a 6 length code"() {
        when:
        SmsVerificationCode actual = subject.generate()

        then:
        assert actual.getValue().length() == 6
    }

    @Unroll(value = "test if #code is valid")
    def "validate code"(String code, boolean expected) {

        expect:
        assert subject.matches(smsVerificationCodeOf(code)) == expected

        where:
        code      | expected
        '123456'  | true
        '12345a'  | false
        '1'       | false
        '12'      | false
        '123'     | false
        '1234'    | false
        '12345'   | false
        '1234567' | false
    }
}
