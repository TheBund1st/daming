package com.thebund1st.daming.boot.application

import com.thebund1st.daming.application.commandhandling.impl.SmsVerificationCommandHandler
import com.thebund1st.daming.boot.AbstractAutoConfigurationTest

import java.time.Duration

class ApplicationConfigurationTest extends AbstractAutoConfigurationTest {

    def "it should provide a SmsVerificationCommandHandler instance"() {

        when:
        def contextRunner = this.contextRunner
                .withPropertyValues(
                "daming.sms.verification.code.expires=20"
        )

        then:
        contextRunner.run { it ->
            SmsVerificationCommandHandler actual = it.getBean(SmsVerificationCommandHandler)
            assert actual.getExpires() == Duration.ofSeconds(20)
        }
    }
}
