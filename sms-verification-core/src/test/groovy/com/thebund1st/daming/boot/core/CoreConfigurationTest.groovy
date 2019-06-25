package com.thebund1st.daming.boot.core

import com.thebund1st.daming.boot.AbstractAutoConfigurationTest
import com.thebund1st.daming.core.*

import static com.thebund1st.daming.core.SmsVerificationScope.smsVerificationScopeOf

class CoreConfigurationTest extends AbstractAutoConfigurationTest {

    def "it should provide one bean of SmsVerificationCodeGenerator given no customized configuration"() {

        when:
        def contextRunner = this.contextRunner

        then:
        contextRunner.run { it ->
            SmsVerificationCodeGenerator actual = it.getBean(SmsVerificationCodeGenerator)
            assert actual instanceof RandomNumberSmsVerificationCode
        }
    }

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
        }
    }

    def "it should provide one bean of SmsVerificationCodePattern given no customized configuration"() {

        when:
        def contextRunner = this.contextRunner

        then:
        contextRunner.run { it ->
            SmsVerificationCodePattern actual = it.getBean(SmsVerificationCodePattern)
            assert actual instanceof RandomNumberSmsVerificationCode
        }
    }

    def "it should skip default SmsVerificationCodePattern given customized configuration"() {

        when:
        def contextRunner = this.contextRunner.withPropertyValues("daming.sms.verification.bypass.enabled=true")

        then:
        contextRunner.run { it ->
            SmsVerificationCodePattern actual = it.getBean(SmsVerificationCodePattern)
            assert actual instanceof FixedSmsVerificationCode
        }
    }

    def "it should provide one bean of MobilePhoneNumberPattern given no customized configuration"() {

        when:
        def contextRunner = this.contextRunner

        then:
        contextRunner.run { it ->
            MobilePhoneNumberPattern actual = it.getBean(MobilePhoneNumberPattern)
            assert actual != null
        }
    }

    def "it should provide one bean of SmsVerificationScopePattern given no customized configuration"() {

        when:
        def contextRunner = this.contextRunner
                .withPropertyValues("daming.sms.verification.scope.valid=a,b,c,d")

        then:
        contextRunner.run { it ->
            SmsVerificationScopePattern actual =
                    it.getBean(SmsVerificationScopePattern)
            assert actual != null
            def staticPattern = (StaticSmsVerificationScopePattern) actual
            assert staticPattern.valid.containsAll(['a', 'b', 'c', 'd'].collect { smsVerificationScopeOf(it) })
        }
    }
}
