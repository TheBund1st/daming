package com.thebund1st.daming.boot.core

import com.thebund1st.daming.boot.AbstractAutoConfigurationTest
import com.thebund1st.daming.core.MobilePhoneNumberPattern
import com.thebund1st.daming.core.SmsVerificationCodeGenerator
import com.thebund1st.daming.core.SmsVerificationCodePattern

class CoreConfigurationTest extends AbstractAutoConfigurationTest {

    def "it should provide one bean of SmsVerificationCodeGenerator given no customized configuration"() {

        when:
        def contextRunner = this.contextRunner

        then:
        contextRunner.run { it ->
            SmsVerificationCodeGenerator actual = it.getBean(SmsVerificationCodeGenerator)
            assert actual != null
        }
    }

    def "it should provide one bean of SmsVerificationCodePattern given no customized configuration"() {

        when:
        def contextRunner = this.contextRunner

        then:
        contextRunner.run { it ->
            SmsVerificationCodePattern actual = it.getBean(SmsVerificationCodePattern)
            assert actual != null
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
}
