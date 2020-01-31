package com.thebund1st.daming.application.commandhandling.impl

import com.thebund1st.daming.adapter.redis.RedisSendSmsVerificationCodeNextWindowRateLimiter
import com.thebund1st.daming.core.DomainEventPublisher
import com.thebund1st.daming.core.SmsVerification
import com.thebund1st.daming.core.SmsVerificationRepository
import com.thebund1st.daming.core.exceptions.MobileIsNotUnderVerificationException
import com.thebund1st.daming.core.exceptions.SmsVerificationCodeMismatchException
import com.thebund1st.daming.events.SmsVerificationCodeMismatchEvent
import com.thebund1st.daming.events.SmsVerificationCodeVerifiedEvent
import com.thebund1st.daming.events.SmsVerificationRequestedEvent
import com.thebund1st.daming.security.ratelimiting.Errors
import com.thebund1st.daming.security.ratelimiting.ErrorsFactory
import com.thebund1st.daming.time.Clock
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import javax.validation.ConstraintViolationException
import java.time.ZonedDateTime

import static com.thebund1st.daming.commands.SendSmsVerificationCodeCommandFixture.aSendSmsVerificationCodeCommand
import static com.thebund1st.daming.commands.VerifySmsVerificationCodeCommandFixture.aVerifySmsVerificationCodeCommand
import static com.thebund1st.daming.core.SmsVerificationFixture.aSmsVerification

//FIXME why can't I set this value in applicaiton-commit.properties?
@SpringBootTest(properties = "daming.sms.verification.scope.valid=SMS_LOGIN")
@ActiveProfiles("commit")
class SmsVerificationCommandHandlerTest extends Specification {

    @Autowired
    private SmsVerificationCommandHandler subject

    @SpringBean
    private SmsVerificationRepository smsVerificationStore = Mock()

    @SuppressWarnings("GroovyAssignabilityCheck")
    @SpringBean
    private RedisSendSmsVerificationCodeNextWindowRateLimiter rateLimitingHandler =
            Mock(name: "oneSendSmsVerificationCodeCommandInEveryXSeconds")

    @SpringBean
    private Clock clock = Mock()

    @SpringBean
    private ErrorsFactory errorsFactory = Mock()

    @SpringBean
    private DomainEventPublisher eventPublisher = Mock()

    def "it should store and send verification code"() {
        given:
        def now = ZonedDateTime.now()
        def verification = aSmsVerification().createdAt(now.toLocalDateTime()).build()
        def command = aSendSmsVerificationCodeCommand().sendTo(verification.mobile).with(verification.scope).build()

        and:
        errorsFactory.empty() >> Errors.empty()
        clock.now() >> now

        when: "it handles send sms verification code"
        def actual = subject.handle(command)

        then: "it should store the code"

        with(smsVerificationStore) {
            1 * store(_ as SmsVerification)
        }

        assert actual.mobile == verification.mobile
        assert actual.scope == verification.scope
        assert actual.code != null
        assert actual.createdAt == now
        assert actual.expires == subject.expires

        and:
        1 * eventPublisher.publish(_ as SmsVerificationRequestedEvent)
        1 * rateLimitingHandler.postHandle(command, verification)
    }

    def "it should skip given invalid mobile"() {
        given:
        def command = aSendSmsVerificationCodeCommand()
                .sendTo('12345').build()

        and:
        errorsFactory.empty() >> Errors.empty()

        when: "it handles send sms verification code"
        subject.handle(command)

        then: "it throw"

        def thrown = thrown(ConstraintViolationException.class)
        assert thrown.getMessage().contains("Invalid mobile phone number")
    }

    def "it should skip given invalid scope"() {
        given:
        def command = aSendSmsVerificationCodeCommand()
                .withScope("A Fake Scope").build()

        and:
        errorsFactory.empty() >> Errors.empty()

        when: "it handles send sms verification code"
        subject.handle(command)

        then: "it throw"

        def thrown = thrown(ConstraintViolationException.class)
        assert thrown.getMessage().contains("Invalid sms verification scope [A Fake Scope]")
    }

    def "it should verify the verification code"() {
        given:
        def verification = aSmsVerification().build()
        def command = aVerifySmsVerificationCodeCommand()
                .sendTo(verification.mobile)
                .with(verification.scope)
                .codeIs(verification.code)
                .build()

        and:
        smsVerificationStore.shouldFindBy(command.mobile, command.scope) >> verification

        when: "it verified send sms verification code"
        subject.handle(command)

        then: "it should remove the code"

        with(smsVerificationStore) {
            1 * remove(verification)
        }

        1 * eventPublisher.publish(_ as SmsVerificationCodeVerifiedEvent)
    }

    def "it should skip verifying given invalid mobile"() {
        given:
        def command = aVerifySmsVerificationCodeCommand()
                .sendTo('12345').build()

        when: "it handles send sms verification code"
        subject.handle(command)

        then: "it throw"

        def thrown = thrown(ConstraintViolationException.class)
        assert thrown.getMessage().contains("Invalid mobile phone number")
    }

    def "it should skip verifying given invalid code"() {
        given:
        def command = aVerifySmsVerificationCodeCommand()
                .codeIs("Not a code").build()

        when: "it handles send sms verification code"
        subject.handle(command)

        then: "it throw"

        def thrown = thrown(ConstraintViolationException.class)
        assert thrown.getMessage().contains("Invalid sms verification code")
    }

    def "it should skip verifying given invalid scope"() {
        given:
        def command = aVerifySmsVerificationCodeCommand()
                .withScope("A Fake Scope").build()

        when: "it handles send sms verification code"
        subject.handle(command)

        then: "it throw"

        def thrown = thrown(ConstraintViolationException.class)
        assert thrown.getMessage().contains("Invalid sms verification scope [A Fake Scope]")
    }

    def "it should throw given the verification code does not match"() {
        given:
        def verification = aSmsVerification()
                .sendTo("13411116789").withScope("Login")
                .codeIs('123456').build()
        def command = aVerifySmsVerificationCodeCommand()
                .sendTo(verification.mobile).codeIs("654321").build()

        and:
        smsVerificationStore.shouldFindBy(command.mobile, command.scope) >> verification

        when: "it verifies sms verification code"
        subject.handle(command)

        then: "it should remove the code"

        with(smsVerificationStore) {
            0 * remove(verification)
        }

        and: "It raises a SmsVerificationCodeMismatchEvent"
        1 * eventPublisher.publish(_ as SmsVerificationCodeMismatchEvent)

        and: "throw"
        def thrown = thrown(SmsVerificationCodeMismatchException)
        assert thrown.getMessage() ==
                "The actual code [654321] does not match the code sent to [134****6789][Login]."
    }

    def "it should throw given the verification does not exist"() {
        given:
        def verification = aSmsVerification()
                .sendTo("13411116789").withScope("SMS_LOGIN").build()
        def command = aVerifySmsVerificationCodeCommand()
                .sendTo(verification.mobile).with(verification.scope).codeIs(verification.code).build()

        and:
        smsVerificationStore.shouldFindBy(command.mobile, command.scope) >> {
            throw new MobileIsNotUnderVerificationException(command.mobile, command.scope)
        }

        when: "it verifies sms verification code"
        subject.handle(command)

        then: "it should not remove the code"

        with(smsVerificationStore) {
            0 * remove(verification)
        }

        and: "throw"
        def thrown = thrown(MobileIsNotUnderVerificationException)
        assert thrown.getMessage() == "The mobile [134****6789] is not under [SMS_LOGIN] verification."
    }
}
