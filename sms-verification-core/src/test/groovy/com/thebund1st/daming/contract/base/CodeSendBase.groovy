package com.thebund1st.daming.contract.base


import com.thebund1st.daming.security.ratelimiting.Errors
import com.thebund1st.daming.security.ratelimiting.TooManyRequestsException
import com.thebund1st.daming.web.AbstractWebMvcTest

import static com.thebund1st.daming.commands.SendSmsVerificationCodeCommandFixture.aSendSmsVerificationCodeCommand
import static com.thebund1st.daming.core.MobilePhoneNumber.mobilePhoneNumberOf
import static com.thebund1st.daming.core.SmsVerificationScope.smsVerificationScopeOf

class CodeSendBase extends AbstractWebMvcTest {

    def setup() {
        def command = aSendSmsVerificationCodeCommand()
                .with(smsVerificationScopeOf("DEMO"))
                .sendTo(mobilePhoneNumberOf("13912222274"))
                .build()
        smsVerificationHandler.handle(command) >> {
            throw new TooManyRequestsException(Errors.empty().append("Too many requests"))
        }
    }

}
