package com.thebund1st.daming.boot

import com.thebund1st.daming.application.SmsVerificationCommandHandler
import org.springframework.boot.autoconfigure.AutoConfigurations
import org.springframework.boot.test.context.runner.ApplicationContextRunner
import spock.lang.Specification

import java.time.Duration

class SmsVerificationConfigurationTest extends Specification {
    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(SmsVerificationAutoConfiguration.class))

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
