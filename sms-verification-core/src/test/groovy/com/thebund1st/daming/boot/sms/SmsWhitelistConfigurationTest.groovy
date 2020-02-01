package com.thebund1st.daming.boot.sms

import com.thebund1st.daming.boot.AbstractAutoConfigurationTest
import com.thebund1st.daming.application.domain.SmsVerificationCodeSender
import com.thebund1st.daming.adapter.sms.DefaultSmsVerificationCodeSender
import com.thebund1st.daming.adapter.sms.SmsVerificationCodeSenderStub
import com.thebund1st.daming.adapter.sms.WhitelistSmsVerificationCodeSenderInterceptor
import foo.bar.WithCustomizedSmsVerificationSender

import static com.thebund1st.daming.application.domain.MobilePhoneNumber.mobilePhoneNumberOf

class SmsWhitelistConfigurationTest extends AbstractAutoConfigurationTest {

    def "it should provide WhitelistSmsVerificationSender as primary SmsVerificationSender instance"() {

        given: "no other SmsVerificationCodeSender is provided"

        when:
        def contextRunner = this.contextRunner
                .withPropertyValues(
                "daming.sms.verification.code.whitelist[0]=13917777711",
                "daming.sms.verification.code.whitelist[1]=13917777712",
        )

        then:
        contextRunner.run { it ->
            DefaultSmsVerificationCodeSender actual = it.getBean(DefaultSmsVerificationCodeSender)

            assert actual.interceptors.find {
                it instanceof WhitelistSmsVerificationCodeSenderInterceptor
            }.whitelist == ['13917777711', '13917777712'].collect { mobilePhoneNumberOf(it) }
        }
    }

    def "it should provide SmsVerificationCodeSenderStub as default SmsVerificationSender target"() {

        given: "no other SmsVerificationCodeSender is provided"

        when:
        def contextRunner = this.contextRunner

        then:
        contextRunner.run { it ->
            SmsVerificationCodeSender actual = it.getBean("smsVerificationSender", SmsVerificationCodeSender)
            assert actual instanceof DefaultSmsVerificationCodeSender
            assert actual.getTarget() instanceof SmsVerificationCodeSenderStub
        }
    }

    def "it should inject specified SmsVerificationSender to WhitelistSmsVerificationSender instance"() {

        when:
        def contextRunner = contextRunner
                .withUserConfiguration(WithCustomizedSmsVerificationSender)

        then:
        contextRunner.run { it ->
            SmsVerificationCodeSender actual = it.getBean("smsVerificationSender", SmsVerificationCodeSender)
            assert actual instanceof DefaultSmsVerificationCodeSender

            DefaultSmsVerificationCodeSender whitelist = (DefaultSmsVerificationCodeSender) actual
            assert whitelist.getTarget() instanceof WithCustomizedSmsVerificationSender.AnotherSmsVerificationCodeSender
        }
    }
}
