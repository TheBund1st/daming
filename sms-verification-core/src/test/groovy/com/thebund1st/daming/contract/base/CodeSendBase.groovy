package com.thebund1st.daming.contract.base


import com.thebund1st.daming.security.ratelimiting.Errors
import com.thebund1st.daming.security.ratelimiting.TooManyRequestsException
import com.thebund1st.daming.web.AbstractWebMvcTest

import static com.thebund1st.daming.commands.SendSmsVerificationCodeCommandFixture.aSendSmsVerificationCodeCommand
import static com.thebund1st.daming.core.MobilePhoneNumber.mobilePhoneNumberOf
import static com.thebund1st.daming.core.SmsVerificationScope.smsVerificationScopeOf

class CodeSendBase extends AbstractWebMvcTest {

    def setup() {
        handleTooManyRequests()
    }

    private void handleTooManyRequests() {
        def command = aSendSmsVerificationCodeCommand()
                .with(smsVerificationScopeOf("DEMO"))
                .sendTo(mobilePhoneNumberOf("13912222274"))
                .build()
        sendSmsVerificationCodeCommandHandler.handle(command) >> {
            throw new TooManyRequestsException(Errors.empty()
                    .append("Only 1 request is allowed by [DEMO][13912222274] in every 15 seconds"))
        }
    }

}
