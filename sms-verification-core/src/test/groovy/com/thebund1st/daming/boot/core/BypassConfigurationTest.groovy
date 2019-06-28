package com.thebund1st.daming.boot.core

import com.thebund1st.daming.boot.AbstractAutoConfigurationTest
import com.thebund1st.daming.core.FixedSmsVerificationCode
import com.thebund1st.daming.core.SmsVerificationCodeGenerator
import com.thebund1st.daming.core.SmsVerificationCodeSender
import com.thebund1st.daming.sms.DefaultSmsVerificationCodeSender
import com.thebund1st.daming.sms.SmsVerificationCodeSenderBlocker

class BypassConfigurationTest extends AbstractAutoConfigurationTest {

    def "it should skip default SmsVerificationCodeGenerator given customized configuration"() {

        when:
        def contextRunner = this.contextRunner.withPropertyValues(
                "daming.sms.verification.bypass.enabled=true",
                "daming.sms.verification.bypass.value=111111",
        )

        then:
        contextRunner.run { it ->
            SmsVerificationCodeGenerator actual = it.getBean(SmsVerificationCodeGenerator)
            assert actual instanceof FixedSmsVerificationCode
            assert actual.value == "111111"

            DefaultSmsVerificationCodeSender sender = it.getBean(SmsVerificationCodeSender)
            assert sender.interceptors.count { i -> i instanceof SmsVerificationCodeSenderBlocker } == 1
        }
    }

}
