package com.thebund1st.daming.application.command

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import javax.validation.ConstraintViolation
import javax.validation.ConstraintViolationException
import javax.validation.Validator

import static com.thebund1st.daming.application.command.SendSmsVerificationCodeCommandFixture.aSendSmsVerificationCodeCommand
import static com.thebund1st.daming.application.command.VerifySmsVerificationCodeCommandFixture.aVerifySmsVerificationCodeCommand
import static com.thebund1st.daming.application.command.VerifySmsVerificationCodeCommandFixture.aVerifySmsVerificationCodeCommand
import static com.thebund1st.daming.application.command.VerifySmsVerificationCodeCommandFixture.aVerifySmsVerificationCodeCommand

//FIXME why can't I set this value in applicaiton-commit.properties?
@SpringBootTest(properties = "daming.sms.verification.scope.valid=SMS_LOGIN")
@ActiveProfiles("commit")
class SendSmsVerificationCodeCommandValidatorTest extends Specification {

    @Autowired
    private Validator validator

    def "it should skip given invalid mobile"() {
        given:
        def command = aSendSmsVerificationCodeCommand()
                .sendTo('12345').build()

        when:
        Set<ConstraintViolation<SendSmsVerificationCodeCommand>> violations = validator.validate(command)

        then:
        assert violations.size() == 1
        violations.forEach {
            assert it.getMessage().contains("Invalid mobile phone number")
        }

    }

    def "it should skip given invalid scope"() {
        given:
        def command = aSendSmsVerificationCodeCommand()
                .withScope("A Fake Scope").build()

        when:
        Set<ConstraintViolation<SendSmsVerificationCodeCommand>> violations = validator.validate(command)

        then:

        assert violations.size() == 1
        violations.forEach {
            assert it.getMessage().contains("Invalid sms verification scope [A Fake Scope]")
        }
    }

    def "it should skip verifying given invalid mobile"() {
        given:
        def command = aVerifySmsVerificationCodeCommand()
                .sendTo('12345').build()

        when:
        Set<ConstraintViolation<SendSmsVerificationCodeCommand>> violations = validator.validate(command)

        then:

        assert violations.size() == 1
        violations.forEach {
            assert it.getMessage().contains("Invalid mobile phone number")
        }
    }

    def "it should skip verifying given invalid code"() {
        given:
        def command = aVerifySmsVerificationCodeCommand()
                .codeIs("Not a code").build()

        when:
        Set<ConstraintViolation<SendSmsVerificationCodeCommand>> violations = validator.validate(command)

        then:

        assert violations.size() == 1
        violations.forEach {
            assert it.getMessage().contains("Invalid sms verification code")
        }
    }

    def "it should skip verifying given invalid scope"() {
        given:
        def command = aVerifySmsVerificationCodeCommand()
                .withScope("A Fake Scope").build()

        when:
        Set<ConstraintViolation<SendSmsVerificationCodeCommand>> violations = validator.validate(command)

        then:

        assert violations.size() == 1
        violations.forEach {
            assert it.getMessage().contains("Invalid sms verification scope [A Fake Scope]")
        }
    }

}
