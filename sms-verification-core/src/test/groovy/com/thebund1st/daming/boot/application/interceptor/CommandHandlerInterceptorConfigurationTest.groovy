package com.thebund1st.daming.boot.application.interceptor

import com.thebund1st.daming.application.interceptor.SendSmsVerificationCodeCommandHandlerInterceptorAspect
import com.thebund1st.daming.boot.AbstractAutoConfigurationTest

class CommandHandlerInterceptorConfigurationTest extends AbstractAutoConfigurationTest {

    def "it should create a SendSmsVerificationCodeCommandHandlerInterceptorAspect"() {

        when:
        def contextRunner = this.contextRunner

        then:
        contextRunner.run { it ->
            Map<String, SendSmsVerificationCodeCommandHandlerInterceptorAspect> aspects = it
                    .getBeansOfType(SendSmsVerificationCodeCommandHandlerInterceptorAspect)
            assert aspects.size() == 1
        }
    }

    def "it should create a redis sliding window rate limiter"() {

        when:
        def contextRunner = this.contextRunner
                .withPropertyValues("daming.sms.verification.code.sliding.window.enabled=true")

        then:
        contextRunner.run { it ->
            Map<String, SendSmsVerificationCodeCommandHandlerInterceptorAspect> aspects = it
                    .getBeansOfType(SendSmsVerificationCodeCommandHandlerInterceptorAspect)
            assert aspects.size() == 2
        }
    }
}
