package com.thebund1st.daming.adapter.jackson


import com.thebund1st.daming.application.event.SmsVerificationRequestedEvent
import com.thebund1st.daming.json.AbstractJsonTest
import org.springframework.boot.test.json.JacksonTester

import java.time.ZonedDateTime

import static com.thebund1st.daming.application.domain.SmsVerificationFixture.aSmsVerification
import static org.assertj.core.api.Java6Assertions.assertThat

class SmsVerificationRequestedEventJsonTest extends AbstractJsonTest {

    private JacksonTester<SmsVerificationRequestedEvent> json

    def "it should handle SmsVerificationRequestedEvent"() {


        given:
        def verification = aSmsVerification().build()
        def event = new SmsVerificationRequestedEvent(UUID.randomUUID().toString(), ZonedDateTime.now(), verification)

        when:
        def content = this.json.write(event)

        then:
        println(content)

        and:
        assertThat(content)
                .extractingJsonPathStringValue("@.smsVerification.scope")
                .isEqualTo(verification.getScope().value)
        assertThat(content)
                .extractingJsonPathStringValue("@.smsVerification.mobile")
                .isEqualTo(verification.getMobile().value)
        assertThat(content)
                .extractingJsonPathStringValue("@.smsVerification.code")
                .isEqualTo(verification.getCode().value)
        assertThat(content)
                .extractingJsonPathNumberValue("@.smsVerification.expires")
                .isEqualTo(Double.valueOf(verification.getExpires().getSeconds()))

    }
}
