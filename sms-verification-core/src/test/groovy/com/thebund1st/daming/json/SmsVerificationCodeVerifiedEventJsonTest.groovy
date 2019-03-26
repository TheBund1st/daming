package com.thebund1st.daming.json

import com.thebund1st.daming.events.SmsVerificationCodeVerifiedEvent
import org.springframework.boot.test.json.JacksonTester

import java.time.ZonedDateTime

import static com.thebund1st.daming.core.SmsVerificationFixture.aSmsVerification
import static org.assertj.core.api.Java6Assertions.assertThat

class SmsVerificationCodeVerifiedEventJsonTest extends AbstractJsonTest {

    private JacksonTester<SmsVerificationCodeVerifiedEvent> json

    def "it should serialize SmsVerificationCodeVerifiedEvent"() {

        given:
        def verification = aSmsVerification().build()
        def event = new SmsVerificationCodeVerifiedEvent(UUID.randomUUID().toString(),
                ZonedDateTime.now(),
                verification.getMobile(),
                verification.getScope())

        when:
        def content = this.json.write(event)

        then:
        assertThat(content)
                .extractingJsonPathStringValue("@.scope").isEqualTo(verification.getScope().value)
        assertThat(content)
                .extractingJsonPathStringValue("@.mobile").isEqualTo(verification.getMobile().value)

    }
}
