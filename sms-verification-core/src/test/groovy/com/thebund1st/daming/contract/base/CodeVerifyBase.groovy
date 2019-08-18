package com.thebund1st.daming.contract.base


import com.thebund1st.daming.web.AbstractWebMvcTest

import static com.thebund1st.daming.commands.VerifySmsVerificationCodeCommandFixture.aVerifySmsVerificationCodeCommand

class CodeVerifyBase extends AbstractWebMvcTest {

    def setup() {
        def command = aVerifySmsVerificationCodeCommand()
                .withScope("DEMO")
                .sendTo("13912222273")
                .codeIs("123456")
                .build()
        def jwt = "This is a JWT token contains scope and mobile"

        and:
        smsVerifiedJwtIssuer.issue(command.getMobile(), command.scope) >> jwt
    }

}
