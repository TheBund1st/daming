package com.thebund1st.daming.adapter.redis.spirng

import com.thebund1st.daming.adapter.redis.spirng.RedisSendSmsVerificationCodeNextWindowRateLimiter
import com.thebund1st.daming.redis.AbstractDataRedisTest
import com.thebund1st.daming.security.ratelimiting.TooManyRequestsException
import org.springframework.beans.factory.annotation.Autowired

import java.time.Duration

import static com.thebund1st.daming.application.command.SendSmsVerificationCodeCommandFixture.aSendSmsVerificationCodeCommand
import static com.thebund1st.daming.application.domain.SmsVerificationFixture.aSmsVerification
import static java.util.concurrent.TimeUnit.SECONDS
import static org.awaitility.Awaitility.await

class RedisSendSmsVerificationCodeNextWindowRateLimiterTest extends AbstractDataRedisTest {

    @Autowired
    private RedisSendSmsVerificationCodeNextWindowRateLimiter subject

    def setup() {
        subject.setExpires(Duration.ofSeconds(2))
    }

    def "it should pass given there is no request in given time window"() {
        given:
        def command = aSendSmsVerificationCodeCommand().build()

        when:
        subject.preHandle(command)

        then:
        noExceptionThrown()
    }

    def "it should fail given there is one request in given time window"() {
        given:
        def command = aSendSmsVerificationCodeCommand().build()
        def smsVerification = aSmsVerification()
                .with(command.scope)
                .sendTo(command.mobile)
                .build()
        and:
        subject.preHandle(command)
        and:
        subject.postHandle(command, smsVerification)

        when:
        subject.preHandle(command)

        then:
        def throwable = thrown(TooManyRequestsException)
        assert throwable.message.contains("is blocked due to the policy 1 request in every")
    }

    def "it should pass given the blocking expires"() {
        given:
        def command = aSendSmsVerificationCodeCommand().build()
        def smsVerification = aSmsVerification()
                .with(command.scope)
                .sendTo(command.mobile)
                .build()
        and:
        subject.preHandle(command)
        and:
        subject.postHandle(command, smsVerification)

        when:
        println("")

        then:

        await().atMost(4, SECONDS)
                .untilAsserted {
            Throwable thrown = null
            try {
                subject.preHandle(command)
            } catch (Throwable throwable) {
                thrown = throwable
            }
            assert thrown == null
        }
    }
}
