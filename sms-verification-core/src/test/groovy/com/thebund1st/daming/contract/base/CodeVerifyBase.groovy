package com.thebund1st.daming.contract.base

import com.thebund1st.daming.application.domain.exceptions.MobileIsNotUnderVerificationException
import com.thebund1st.daming.application.domain.exceptions.SmsVerificationCodeMismatchException
import com.thebund1st.daming.web.AbstractWebMvcTest

import static com.thebund1st.daming.application.command.VerifySmsVerificationCodeCommandFixture.aVerifySmsVerificationCodeCommand
import static com.thebund1st.daming.application.domain.SmsVerificationFixture.aSmsVerification

class CodeVerifyBase extends AbstractWebMvcTest {

    def setup() {
        handleHappy()
        handleCodeMismatch()
        handleNotUnderVerification()
    }

    private void handleNotUnderVerification() {
        def command = aVerifySmsVerificationCodeCommand()
                .withScope("DEMO")
                .sendTo("13912222274")
                .codeIs("123456")
                .build()

        verifySmsVerificationCodeCommandHandler.handle(command) >> {
            throw new MobileIsNotUnderVerificationException(command.mobile, command.scope)
        }
    }

    private void handleCodeMismatch() {
        def verification = aSmsVerification()
                .withScope("DEMO")
                .sendTo("13912222273")
                .codeIs("123456")
                .build()
        def command = aVerifySmsVerificationCodeCommand()
                .with(verification.scope)
                .sendTo(verification.mobile)
                .codeIs("434434")
                .build()

        verifySmsVerificationCodeCommandHandler.handle(command) >> {
            throw new SmsVerificationCodeMismatchException(verification, command.code)
        }
    }

    private void handleHappy() {
        def command = aVerifySmsVerificationCodeCommand()
                .withScope("DEMO")
                .sendTo("13912222273")
                .codeIs("123456")
                .build()
        def jwt = "This is a JWT token contains scope and mobile"

        smsVerifiedJwtIssuer.issue(command.getMobile(), command.scope) >> jwt
    }

}
