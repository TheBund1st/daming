package com.foo.bar

import com.foo.bar.steps.SmsVerificationStep
import io.restassured.RestAssured
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import redis.embedded.RedisServer
import spock.lang.Specification

import static com.thebund1st.daming.commands.SendSmsVerificationCodeCommandFixture.aSendSmsVerificationCodeCommand
import static com.thebund1st.daming.commands.VerifySmsVerificationCodeCommandFixture.aVerifySmsVerificationCodeCommand
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import static org.springframework.http.HttpStatus.PRECONDITION_FAILED
import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
class SmsVerificationAcceptanceTest extends Specification {

    @LocalServerPort
    int randomServerPort

    @Autowired
    private SmsVerificationCodeSenderStub senderStub

    private RedisServer redisServer

    private SmsVerificationStep i

    def setup() {
        this.redisServer = new RedisServer(16380)
        redisServer.start()
        RestAssured.port = randomServerPort
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
        i = new SmsVerificationStep(senderStub)
    }

    def cleanup() {
        this.redisServer.stop()
    }

    def "I ask for sms verification code"() {
        given: "I want to do sth but it requires sms verification"

        when: "I ask for sms verification code with my phone number"

        i.askFor(aSendSmsVerificationCodeCommand().build())

        then: "I should receive a code on my phone"
        i.shouldReceiveVerificationCodeOnMyPhone()
    }

    def "I verify sms verification code"() {
        given: "I receive a sms verification code on my phone"

        def send = aSendSmsVerificationCodeCommand().build()
        def code = i.askFor(send).then("I").shouldReceiveVerificationCodeOnMyPhone()

        when: "I verify my phone with the code"

        def command = aVerifySmsVerificationCodeCommand().sendTo(send.mobile).with(send.scope).codeIs(code).build()
        i.verify(command)

        then: "I should pass the verification"
        i.shouldPassTheVerification()
    }

    def "I should get too many requests error given asking sms verification code in x seconds"() {
        given: "I receive a sms verification code on my phone"

        def command = aSendSmsVerificationCodeCommand().build()
        i.askFor(command)
                .then("I").shouldReceiveVerificationCodeOnMyPhone()

        when: "I want to send the code again"
        i.askFor(command)

        then: "I should get an error"
        i.shouldSeeFailure(TOO_MANY_REQUESTS)

    }

    def "It invalidates sms verification code given too many failure attempts"() {
        given: "I ask for a code"
        def send = aSendSmsVerificationCodeCommand().build()
        def code = i.askFor(send).then("I").shouldReceiveVerificationCodeOnMyPhone()

        and: "Too many failure attempts"
        def wrongCode = aVerifySmsVerificationCodeCommand().sendTo(send.mobile).with(send.scope).build()

        5.times {
            i.verify(wrongCode)
        }

        when: "I verify my phone with the code again"
        def rightCode = aVerifySmsVerificationCodeCommand().sendTo(send.mobile).with(send.scope).codeIs(code).build()
        i.verify(rightCode)

        then: "I should pass the verification"
        i.shouldSeeFailure(PRECONDITION_FAILED)
    }

}
