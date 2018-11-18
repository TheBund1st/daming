package com.thebund1st.daming.boot

import com.thebund1st.daming.application.SmsVerificationCommandHandler

import java.time.Duration

class SmsVerificationConfigurationTest extends AbstractAutoConfigurationTest {

    def "it should provide one bean of RedisTemplate<String, SmsVerification> given no customized configuration"() {

        when:
        def contextRunner = contextRunner.withPropertyValues("daming.sms.verification.code.expires=5")

        then:
        contextRunner.run { it ->
            SmsVerificationCommandHandler actual = it.getBean(SmsVerificationCommandHandler)
            assert actual.expires == Duration.ofSeconds(5)
        }
    }
}
