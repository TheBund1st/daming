package com.thebund1st.daming.boot.jwt


import com.thebund1st.daming.boot.AbstractAutoConfigurationTest
import com.thebund1st.daming.jwt.key.JwtKeyLoader
import foo.bar.WithCustomizedJwtSigningKeyLoader

import java.time.Duration

class JwtConfigurationTest extends AbstractAutoConfigurationTest {

    def "it should populate Jwt properties"() {

        when:
        def contextRunner = this.contextRunner
                .withPropertyValues(
                "daming.jwt.expires=20"
        )

        then:
        contextRunner.run { it ->
            JwtProperties actual = it.getBean(JwtProperties)
            assert actual.getExpires() == Duration.ofSeconds(20)
        }
    }

    def "it should skip built-in smsVerificationJwtSigningKeyLoader given customized one is provided"() {

        when:
        def then = this.contextRunner
                .withUserConfiguration(WithCustomizedJwtSigningKeyLoader)

        then:
        then.run { it ->
            JwtKeyLoader actual = it.getBean("smsVerificationJwtSigningKeyLoader")
            assert actual instanceof WithCustomizedJwtSigningKeyLoader.CustomizedJwtKeyLoader
        }
    }

}
