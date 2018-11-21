package com.thebund1st.daming.boot.sms

import com.thebund1st.daming.application.SmsVerificationSender
import com.thebund1st.daming.boot.AbstractAutoConfigurationTest
import com.thebund1st.daming.boot.SmsVerificationCodeProperties
import com.thebund1st.daming.sms.SmsVerificationSenderStub
import com.thebund1st.daming.sms.WhitelistSmsVerificationSender
import foo.bar.WithCustomizedSmsVerificationSender
import foo.bar.WithTooManySmsVerificationSender
import org.springframework.beans.factory.NoUniqueBeanDefinitionException
import org.springframework.boot.test.context.assertj.AssertableApplicationContext

import java.time.Duration

import static com.thebund1st.daming.core.MobilePhoneNumber.mobilePhoneNumberOf

class SmsWhitelistConfigurationTest extends AbstractAutoConfigurationTest {

    def "it should provide WhitelistSmsVerificationSender as primary SmsVerificationSender instance"() {

        given: "no other SmsVerificationSender is provided"

        when:
        def contextRunner = this.contextRunner
                .withPropertyValues(
                "daming.sms.verification.code.whitelist[0]=13917777711",
                "daming.sms.verification.code.whitelist[1]=13917777712",
        )

        then:
        contextRunner.run { it ->
            SmsVerificationSender actual = it.getBean("smsVerificationSender", SmsVerificationSender)
            assert actual instanceof WhitelistSmsVerificationSender

            WhitelistSmsVerificationSender whitelist = (WhitelistSmsVerificationSender) actual
            assert whitelist.getWhitelist() == ['13917777711', '13917777712'].collect { mobilePhoneNumberOf(it) }
            assert whitelist.getTarget() instanceof SmsVerificationSenderStub
        }
    }

    def "it should inject specified SmsVerificationSender to WhitelistSmsVerificationSender instance"() {

        when:
        def contextRunner = contextRunner
                .withUserConfiguration(WithCustomizedSmsVerificationSender)

        then:
        contextRunner.run { it ->
            SmsVerificationSender actual = it.getBean("smsVerificationSender", SmsVerificationSender)
            assert actual instanceof WhitelistSmsVerificationSender

            WhitelistSmsVerificationSender whitelist = (WhitelistSmsVerificationSender) actual
            assert whitelist.getTarget() instanceof WithCustomizedSmsVerificationSender.AnotherSmsVerificationSender
        }
    }

    def "it should throw no unique bean given too many SmsVerificationSenders"() {

        when:
        def contextRunner = contextRunner
                .withUserConfiguration(WithTooManySmsVerificationSender)

        then:
        contextRunner.run { it ->
            def context = (AssertableApplicationContext) it
            assert context.getStartupFailure() //BeanCreationException
                    .getCause() // BeanInitiationException
                    .getCause() instanceof NoUniqueBeanDefinitionException
        }
    }

    def "it should bind sms verification code properties"() {

        given:

        when:
        def contextRunner = this.contextRunner
                .withPropertyValues(
                "daming.sms.verification.code.expires=15",
                "daming.sms.verification.code.whitelist[0]=13917777711",
                "daming.sms.verification.code.whitelist[1]=13917777712",
        )

        then:
        contextRunner.run { it ->
            SmsVerificationCodeProperties actual = it.getBean(SmsVerificationCodeProperties)
            assert actual.getWhitelist() == ['13917777711', '13917777712']
            assert actual.getExpires() == Duration.ofSeconds(15)
        }
    }
}
