package com.thebund1st.daming

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles("commit")
class ApplicationContextSmokeTest extends Specification {

    def "it should load application context"() {
        when: "it launch the application context"

        then: "it should load successfully"
    }
}
