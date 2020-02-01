package com.thebund1st.daming.adapter.web.spring.rest


import com.thebund1st.daming.application.domain.exceptions.SmsVerificationCodeMismatchException
import com.thebund1st.daming.web.AbstractWebMvcTest

import static com.thebund1st.daming.application.command.SendSmsVerificationCodeCommandFixture.aSendSmsVerificationCodeCommand
import static com.thebund1st.daming.application.command.VerifySmsVerificationCodeCommandFixture.aVerifySmsVerificationCodeCommand
import static com.thebund1st.daming.application.domain.SmsVerificationFixture.aSmsVerification
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class SmsVerificationRestControllerTest extends AbstractWebMvcTest {

    def "it should accept sms verification request"() {
        given:
        def command = aSendSmsVerificationCodeCommand().build()

        when:
        def resultActions = mockMvc.perform(
                post("/api/sms/verification/code")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content("""
                            {
                                "mobile": "${command.getMobile().getValue()}",
                                "scope": "${command.getScope().getValue()}"
                            }
                        """)
        )

        then:
        resultActions
                .andExpect(status().isAccepted())

        and:
        1 * sendSmsVerificationCodeCommandHandler.handle(command)

    }

    def "it should return a JWT for subsequent request when verifying sms verification code"() {
        given:
        def command = aVerifySmsVerificationCodeCommand().build()
        def jwt = "This is a JWT token"

        and:
        smsVerifiedJwtIssuer.issue(command.getMobile(), command.scope) >> jwt

        when:
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

        then:
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "token": "${jwt}"
                        }
                """))

        and:
        1 * verifySmsVerificationCodeCommandHandler.handle(command)
    }

    def "it should return 412 when verifying sms verification code given code mismatches"() {
        given:
        def verification = aSmsVerification().build()
        def command = aVerifySmsVerificationCodeCommand()
                .sendTo(verification.mobile)
                .with(verification.scope)
                .build()

        and:
        verifySmsVerificationCodeCommandHandler.handle(command) >> {
            throw new SmsVerificationCodeMismatchException(verification, command.code)
        }

        when:
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

        then:
        resultActions
                .andExpect(status().isPreconditionFailed())
    }
}
