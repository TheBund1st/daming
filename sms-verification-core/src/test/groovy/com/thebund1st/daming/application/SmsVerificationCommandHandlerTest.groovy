package com.thebund1st.daming.application

import com.thebund1st.daming.core.SmsVerificationCodeGenerator
import com.thebund1st.daming.core.SmsVerificationStore
import com.thebund1st.daming.core.exceptions.MobileIsNotUnderVerificationException
import com.thebund1st.daming.core.exceptions.MobileIsStillUnderVerificationException
import com.thebund1st.daming.core.exceptions.SmsVerificationCodeMismatchException
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static com.thebund1st.daming.commands.SendSmsVerificationCodeCommandFixture.aSendSmsVerificationCodeCommand
import static com.thebund1st.daming.commands.VerifySmsVerificationCodeCommandFixture.aVerifySmsVerificationCodeCommand
import static com.thebund1st.daming.core.SmsVerificationFixture.aSmsVerification

@SpringBootTest
class SmsVerificationCommandHandlerTest extends Specification {

    @Autowired
    private SmsVerificationCommandHandler subject

    @SpringBean
    private SmsVerificationCodeGenerator smsVerificationCodeGenerator = Mock()

    @SpringBean
    private SmsVerificationStore smsVerificationStore = Mock()

    @SpringBean
    private SmsVerificationSender smsVerificationSender = Mock()


    def "it should store and send verification code"() {
        given:
        def verification = aSmsVerification().build()
        def command = aSendSmsVerificationCodeCommand().sendTo(verification.mobile).build()

        and:
        smsVerificationCodeGenerator.generate() >> verification.getCode()

        when: "it handles send sms verification code"
        subject.handle(command)

        then: "it should store the code"

        with(smsVerificationStore) {
            1 * store(verification)
        }

        and:
        1 * smsVerificationSender.send(verification)
    }

    def "it should skip given the mobile is under verification"() {
        given:
        def command = aSendSmsVerificationCodeCommand().sendTo('13411116789').build()

        and:
        smsVerificationStore.exists(command.mobile) >> true

        when: "it handles send sms verification code"
        subject.handle(command)

        then: "it should store the code"

        def thrown = thrown(MobileIsStillUnderVerificationException.class)
        assert thrown.getMessage() == "The mobile [134****6789] is under verification."
    }

    def "it should verify the verification code"() {
        given:
        def verification = aSmsVerification().build()
        def command = aVerifySmsVerificationCodeCommand()
                .sendTo(verification.mobile).codeIs(verification.code).build()

        and:
        smsVerificationStore.shouldFindBy(verification.mobile) >> verification

        when: "it verified send sms verification code"
        subject.handle(command)

        then: "it should remove the code"

        with(smsVerificationStore) {
            1 * remove(verification)
        }
    }

    def "it should throw given the verification code does not match"() {
        given:
        def verification = aSmsVerification().sendTo("13411116789").codeIs('123456').build()
        def command = aVerifySmsVerificationCodeCommand()
                .sendTo(verification.mobile).codeIs("654321").build()

        and:
        smsVerificationStore.shouldFindBy(verification.mobile) >> verification

        when: "it verifies sms verification code"
        subject.handle(command)

        then: "it should remove the code"

        with(smsVerificationStore) {
            0 * remove(verification)
        }

        and: "throw"
        def thrown = thrown(SmsVerificationCodeMismatchException)
        assert thrown.getMessage() == "The actual code [654321] does not match the code [123456] sent to [134****6789]."
    }

    def "it should throw given the verification does not exist"() {
        given:
        def verification = aSmsVerification().sendTo("13411116789").build()
        def command = aVerifySmsVerificationCodeCommand()
                .sendTo(verification.mobile).codeIs(verification.code).build()

        and:
        smsVerificationStore.shouldFindBy(verification.mobile) >> {
            throw new MobileIsNotUnderVerificationException(verification.mobile)
        }

        when: "it verifies sms verification code"
        subject.handle(command)

        then: "it should not remove the code"

        with(smsVerificationStore) {
            0 * remove(verification)
        }

        and: "throw"
        def thrown = thrown(MobileIsNotUnderVerificationException)
        assert thrown.getMessage() == "The mobile [134****6789] is not under verification."
    }
}
