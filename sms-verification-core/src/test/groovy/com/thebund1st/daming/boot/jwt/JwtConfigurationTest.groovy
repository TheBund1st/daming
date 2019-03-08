package com.thebund1st.daming.boot.jwt


import com.thebund1st.daming.boot.AbstractAutoConfigurationTest

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

}
