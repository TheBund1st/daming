package com.thebund1st.daming.boot.core

import com.thebund1st.daming.boot.AbstractAutoConfigurationTest
import com.thebund1st.daming.core.SmsVerificationCodeGenerator

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
}
