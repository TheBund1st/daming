package com.thebund1st.daming.boot.time

import com.thebund1st.daming.boot.AbstractAutoConfigurationTest
import com.thebund1st.daming.time.Clock

class TimeConfigurationTest extends AbstractAutoConfigurationTest {

    def "it should provide one bean of Clock given no customized configuration"() {

        when:
        def contextRunner = this.contextRunner

        then:
        contextRunner.run { it ->
            Clock actual = it.getBean(Clock)
            assert actual != null
        }
    }
}
