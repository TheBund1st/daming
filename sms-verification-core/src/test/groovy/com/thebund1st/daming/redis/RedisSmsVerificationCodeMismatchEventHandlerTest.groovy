package com.thebund1st.daming.redis

import com.thebund1st.daming.core.SmsVerification
import com.thebund1st.daming.events.SmsVerificationCodeMismatchEvent
import com.thebund1st.daming.events.TooManyFailureSmsVerificationAttemptsEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.redis.core.StringRedisTemplate

import java.time.Duration
import java.time.ZonedDateTime
import java.util.concurrent.TimeUnit

import static com.thebund1st.daming.core.SmsVerificationFixture.aSmsVerification
import static java.time.temporal.ChronoUnit.SECONDS
import static org.awaitility.Awaitility.await

class RedisSmsVerificationCodeMismatchEventHandlerTest extends AbstractDataRedisTest {

    @Autowired
    private ApplicationEventPublisher publisher

    @Autowired
    private StringRedisTemplate stringRedisTemplate

    @Autowired
    private RedisSmsVerificationCodeMismatchEventHandler subject

    def setup() {
        subject.setExpires(Duration.ofSeconds(2))
    }

    def "it should count mismatch if absent"() {
        given:
        def smsVerification = aSmsVerification().expiresIn(2, SECONDS).build()
        assert !stringRedisTemplate
                .hasKey(toKey(smsVerification))

        when:
        publisher.publishEvent(new SmsVerificationCodeMismatchEvent(UUID.randomUUID().toString(),
                ZonedDateTime.now(),
                smsVerification.getMobile(),
                smsVerification.getScope()))

        then:
        assert stringRedisTemplate
                .opsForSet().size(toKey(smsVerification)) == 1

        await().atMost(3, TimeUnit.SECONDS).until {
            !stringRedisTemplate.hasKey(toKey(smsVerification))
        }
    }

    def "it should emit event given too many failure attempts"() {
        given:
        def smsVerification = aSmsVerification().expiresIn(60, SECONDS).build()

        and: "almost reaching too many attempts"
        4.times {
            publisher.publishEvent(new SmsVerificationCodeMismatchEvent(UUID.randomUUID().toString(),
                    ZonedDateTime.now(),
                    smsVerification.getMobile(),
                    smsVerification.getScope()))
        }
        assert stringRedisTemplate
                .opsForSet().size(toKey(smsVerification)) == 4

        when:
        publisher.publishEvent(new SmsVerificationCodeMismatchEvent(UUID.randomUUID().toString(),
                ZonedDateTime.now(),
                smsVerification.getMobile(),
                smsVerification.getScope()))

        then:
        assert stringRedisTemplate
                .opsForSet().size(toKey(smsVerification)) == 5

        and:
        1 * eventPublisher.publish(_ as TooManyFailureSmsVerificationAttemptsEvent)

    }

    private String toKey(SmsVerification smsVerification) {
        "sms.verification.code.mismatch.${smsVerification.getMobile().getValue()}.${smsVerification.getScope().getValue()}"
    }
}