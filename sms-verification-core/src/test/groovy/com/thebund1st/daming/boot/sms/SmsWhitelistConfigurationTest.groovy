package com.thebund1st.daming.boot.sms

import com.thebund1st.daming.core.SmsVerificationCodeSender
import com.thebund1st.daming.boot.AbstractAutoConfigurationTest
import com.thebund1st.daming.boot.core.SmsVerificationCodeProperties
import com.thebund1st.daming.sms.LoggingSmsVerificationCodeSender
import com.thebund1st.daming.sms.WhitelistSmsVerificationCodeSender
import foo.bar.WithCustomizedSmsVerificationSender
import foo.bar.WithTooManySmsVerificationSender
import org.springframework.beans.factory.NoUniqueBeanDefinitionException
import org.springframework.boot.test.context.assertj.AssertableApplicationContext

import java.time.Duration

import static com.thebund1st.daming.core.MobilePhoneNumber.mobilePhoneNumberOf

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
            SmsVerificationCodeSender actual = it.getBean("smsVerificationSender", SmsVerificationCodeSender)
            assert actual instanceof WhitelistSmsVerificationCodeSender

            WhitelistSmsVerificationCodeSender whitelist = (WhitelistSmsVerificationCodeSender) actual
            assert whitelist.getWhitelist() == ['13917777711', '13917777712'].collect { mobilePhoneNumberOf(it) }
            assert whitelist.getTarget() instanceof LoggingSmsVerificationCodeSender
        }
    }

    def "it should inject specified SmsVerificationSender to WhitelistSmsVerificationSender instance"() {

        when:
        def contextRunner = contextRunner
                .withUserConfiguration(WithCustomizedSmsVerificationSender)

        then:
        contextRunner.run { it ->
            SmsVerificationCodeSender actual = it.getBean("smsVerificationSender", SmsVerificationCodeSender)
            assert actual instanceof WhitelistSmsVerificationCodeSender

            WhitelistSmsVerificationCodeSender whitelist = (WhitelistSmsVerificationCodeSender) actual
            assert whitelist.getTarget() instanceof WithCustomizedSmsVerificationSender.AnotherSmsVerificationCodeSender
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
