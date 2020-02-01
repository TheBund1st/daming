package com.thebund1st.daming.application.commandhandling.impl

import com.thebund1st.daming.application.domain.DomainEventPublisher
import com.thebund1st.daming.application.domain.SmsVerification
import com.thebund1st.daming.application.domain.SmsVerificationCodeGenerator
import com.thebund1st.daming.application.domain.SmsVerificationRepository
import com.thebund1st.daming.application.domain.exceptions.MobileIsNotUnderVerificationException
import com.thebund1st.daming.application.domain.exceptions.SmsVerificationCodeMismatchException
import com.thebund1st.daming.application.event.SmsVerificationCodeMismatchEvent
import com.thebund1st.daming.application.event.SmsVerificationCodeVerifiedEvent
import com.thebund1st.daming.application.event.SmsVerificationRequestedEvent
import com.thebund1st.daming.security.ratelimiting.Errors
import com.thebund1st.daming.time.Clock
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import javax.validation.ConstraintViolationException
import java.time.ZonedDateTime

import static com.thebund1st.daming.application.command.SendSmsVerificationCodeCommandFixture.aSendSmsVerificationCodeCommand
import static com.thebund1st.daming.application.command.VerifySmsVerificationCodeCommandFixture.aVerifySmsVerificationCodeCommand
import static com.thebund1st.daming.application.domain.SmsVerificationFixture.aSmsVerification

class DefaultSmsVerificationCommandHandlerTest extends Specification {

    private DefaultSmsVerificationCommandHandler subject

    private SmsVerificationRepository smsVerificationStore = Mock()

    private Clock clock = Mock()

    private DomainEventPublisher eventPublisher = Mock()

    private SmsVerificationCodeGenerator smsVerificationCodeGenerator = Mock()

    def setup() {
        subject = new DefaultSmsVerificationCommandHandler(smsVerificationStore,
                smsVerificationCodeGenerator, eventPublisher, clock)
    }

    def "it should store and send verification code"() {
        given:
        def now = ZonedDateTime.now()
        def verification = aSmsVerification().createdAt(now.toLocalDateTime()).build()
        def command = aSendSmsVerificationCodeCommand().sendTo(verification.mobile).with(verification.scope).build()

        and:
        smsVerificationCodeGenerator.generate() >> verification.code

        and:
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
