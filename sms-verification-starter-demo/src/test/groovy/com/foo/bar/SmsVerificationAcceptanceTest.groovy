package com.foo.bar

import com.jayway.jsonpath.JsonPath
import com.thebund1st.daming.commands.SendSmsVerificationCodeCommand
import com.thebund1st.daming.core.SmsVerificationCode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import redis.embedded.RedisServer
import spock.lang.Specification

import static com.thebund1st.daming.commands.SendSmsVerificationCodeCommandFixture.aSendSmsVerificationCodeCommand
import static com.thebund1st.daming.commands.VerifySmsVerificationCodeCommandFixture.aVerifySmsVerificationCodeCommand
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SmsVerificationAcceptanceTest extends Specification {

    @Autowired
    private MockMvc mockMvc

    @Autowired
    private SmsVerificationCodeSenderStub senderStub

    private RedisServer redisServer

    def setup() {
        this.redisServer = new RedisServer(16380)
        redisServer.start()
    }

    def cleanup() {
        this.redisServer.stop()
    }

    def "I ask for sms verification code"() {
        given: "I want to do sth but it requires sms verification"

        def command = aSendSmsVerificationCodeCommand().build()

        when: "I ask for sms verification code with my phone number"

        ResultActions resultActions = askFor(command)

        then: "I should receive a code on my phone"

        resultActions
                .andExpect(status().isAccepted())
        assert senderStub.sendCount(command.getMobile(), command.scope) == 1
    }

    def "I verify sms verification code"() {
        given: "I receive a sms verification code on my phone"

        def send = aSendSmsVerificationCodeCommand().build()
        def code = receiveSmsVerificationCode(send)

        when: "I verify my phone with the code"

        def command = aVerifySmsVerificationCodeCommand().sendTo(send.mobile).with(send.scope).codeIs(code).build()
        ResultActions resultActions = verify(command)

        then: "I should pass the verification"
        resultActions
                .andExpect(status().isOk())
                .andExpect({ result ->
            assert JsonPath.read(result.response.contentAsString, "token") != null
        })
    }

    def "I should get too many requests error given asking sms verification code in x seconds"() {
        given: "I receive a sms verification code on my phone"

        def command = aSendSmsVerificationCodeCommand().build()
        receiveSmsVerificationCode(command)

        when: "I want to send the code again"

        def resultActions = askFor(command)

        then: "I should get an error"
        resultActions
                .andExpect(status().isTooManyRequests())
    }

    def "It invalidates sms verification code given too many failure attempts"() {
        given: "I ask for a code"
        def send = aSendSmsVerificationCodeCommand().build()
        def code = receiveSmsVerificationCode(send)

        and: "Too many failure attempts"
        def wrongCode = aVerifySmsVerificationCodeCommand().sendTo(send.mobile).with(send.scope).build()

        5.times {
            verify(wrongCode)
        }

        when: "I verify my phone with the code again"
        def rightCode = aVerifySmsVerificationCodeCommand().sendTo(send.mobile).with(send.scope).codeIs(code).build()
        def resultActions = verify(rightCode)

        then: "I should pass the verification"
        resultActions
                .andExpect(status().isPreconditionFailed())
    }

    private ResultActions askFor(SendSmsVerificationCodeCommand command) {
        mockMvc.perform(
                post("/api/sms/verification/code")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content("""
                            {
                                "mobile": "${command.getMobile().getValue()}",
                                "scope": "${command.getScope().getValue()}"
                            }
                        """)
        )
    }

    private ResultActions verify(command) {
        def resultActions = mockMvc.perform(
                delete("/api/sms/verification/code")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content("""
                            {
                                "mobile": "${command.getMobile().getValue()}",
                                "scope": "${command.getScope().getValue()}",
                                "code": "${command.getCode().getValue()}"
                            }
                        """)
        )
        resultActions
    }

    private SmsVerificationCode receiveSmsVerificationCode(SendSmsVerificationCodeCommand command) {
        askFor(command).andExpect(status().isAccepted())
        assert senderStub.sendCount(command.getMobile(), command.scope) == 1
        senderStub.getTheOnly(command.getMobile(), command.scope).get().getCode()
    }

}
