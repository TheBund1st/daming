package com.thebund1st.daming.boot.application.interceptor

import com.thebund1st.daming.application.interceptor.SendSmsVerificationCodeCommandHandlerInterceptorAspect
import com.thebund1st.daming.boot.AbstractAutoConfigurationTest

class CommandHandlerInterceptorConfigurationTest extends AbstractAutoConfigurationTest {

    def "it should create a SendSmsVerificationCodeCommandHandlerInterceptorAspect"() {

        when:
        def contextRunner = this.contextRunner

        then:
        contextRunner.run { it ->
            SendSmsVerificationCodeCommandHandlerInterceptorAspect actual = it
                    .getBean(SendSmsVerificationCodeCommandHandlerInterceptorAspect)
            assert actual != null
        }
    }
}
