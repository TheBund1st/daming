package com.thebund1st.daming.security.ratelimiting

import com.thebund1st.daming.redis.AbstractDataRedisTest
import com.thebund1st.daming.redis.BlockSendingRateLimitingHandler
import org.springframework.beans.factory.annotation.Autowired

import java.time.Duration

import static com.thebund1st.daming.commands.SendSmsVerificationCodeCommandFixture.aSendSmsVerificationCodeCommand
import static java.util.concurrent.TimeUnit.SECONDS
import static org.awaitility.Awaitility.await

class BlockSendingRateLimitingHandlerTest extends AbstractDataRedisTest {

    @Autowired
    private BlockSendingRateLimitingHandler subject

    def setup() {
        subject.setExpires(Duration.ofSeconds(2))
    }

    def "it should pass given there is no request in given time window"() {
        given:
        def command = aSendSmsVerificationCodeCommand().build()
        def errors = Errors.empty()

        when:
        subject.check(command, errors)

        then:
        assert errors.isEmpty()
    }

    def "it should pass given there is at least one request in given time window"() {
        given:
        def command = aSendSmsVerificationCodeCommand().build()
        def errors = Errors.empty()

        and:
        subject.count(command)

        when:
        subject.check(command, errors)

        then:
        assert !errors.isEmpty()
    }

    def "it should pass given the blocking expires"() {
        given:
        def command = aSendSmsVerificationCodeCommand().build()

        and:
        subject.count(command)

        when:
        println("")

        then:
        await().atMost(3, SECONDS).until {
            def errors = Errors.empty()
            subject.check(command, errors)
            errors.isEmpty()
        }
    }
}
