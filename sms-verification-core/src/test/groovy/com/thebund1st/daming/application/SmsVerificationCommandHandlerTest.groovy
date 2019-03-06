package com.thebund1st.daming.application

import com.thebund1st.daming.core.SmsVerification
import com.thebund1st.daming.core.SmsVerificationCodeGenerator
import com.thebund1st.daming.core.SmsVerificationCodeSender
import com.thebund1st.daming.core.SmsVerificationRepository
import com.thebund1st.daming.core.exceptions.MobileIsNotUnderVerificationException
import com.thebund1st.daming.core.exceptions.MobileIsStillUnderVerificationException
import com.thebund1st.daming.core.exceptions.SmsVerificationCodeMismatchException
import com.thebund1st.daming.time.Clock
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import java.time.ZonedDateTime

import static com.thebund1st.daming.commands.SendSmsVerificationCodeCommandFixture.aSendSmsVerificationCodeCommand
import static com.thebund1st.daming.commands.VerifySmsVerificationCodeCommandFixture.aVerifySmsVerificationCodeCommand
import static com.thebund1st.daming.core.SmsVerificationFixture.aSmsVerification

@SpringBootTest
@ActiveProfiles("commit")
class SmsVerificationCommandHandlerTest extends Specification {

    @Autowired
    private SmsVerificationCommandHandler subject

    @SpringBean
    private SmsVerificationCodeGenerator smsVerificationCodeGenerator = Mock()

    @SpringBean
    private SmsVerificationRepository smsVerificationStore = Mock()

    @SpringBean
    private SmsVerificationCodeSender smsVerificationSender = Mock(name: "smsVerificationSender")

    @SpringBean
    private Clock clock = Mock()


    def "it should store and send verification code"() {
        given:
        def now = ZonedDateTime.now()
        def verification = aSmsVerification().createdAt(now.toLocalDateTime()).build()
        def command = aSendSmsVerificationCodeCommand().sendTo(verification.mobile).with(verification.scope).build()

        and:
        smsVerificationCodeGenerator.generate() >> verification.getCode()
        clock.now() >> now

        when: "it handles send sms verification code"
        def actual = subject.handle(command)

        then: "it should store the code"

        with(smsVerificationStore) {
            1 * store(_ as SmsVerification)
        }

        assert actual.mobile == verification.mobile
        assert actual.scope == verification.scope
        assert actual.code == verification.code
        assert actual.createdAt == now.toLocalDateTime()
        assert actual.expires == subject.expires

        and:
        1 * smsVerificationSender.send(verification)
    }

    def "it should skip given the mobile is under verification"() {
        given:
        def command = aSendSmsVerificationCodeCommand()
                .sendTo('13411116789').withScope("Login")
                .build()

        and:
        smsVerificationStore.exists(command.mobile, command.scope) >> true

        when: "it handles send sms verification code"
        subject.handle(command)

        then: "it should store the code"

        def thrown = thrown(MobileIsStillUnderVerificationException.class)
        assert thrown.getMessage() == "The mobile [134****6789] is under [Login] verification."
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

        and: "throw"
        def thrown = thrown(SmsVerificationCodeMismatchException)
        assert thrown.getMessage() ==
                "The actual code [654321] does not match the code [123456] sent to [134****6789][Login]."
    }

    def "it should throw given the verification does not exist"() {
        given:
        def verification = aSmsVerification()
                .sendTo("13411116789").withScope("Login").build()
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
        assert thrown.getMessage() == "The mobile [134****6789] is not under [Login] verification."
    }
}
