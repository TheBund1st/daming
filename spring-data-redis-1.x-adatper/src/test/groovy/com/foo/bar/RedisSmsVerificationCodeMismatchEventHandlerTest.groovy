package com.foo.bar

import com.thebund1st.daming.application.domain.SmsVerification
import com.thebund1st.daming.application.event.SmsVerificationCodeMismatchEvent
import com.thebund1st.daming.application.event.SmsVerificationCodeVerifiedEvent
import com.thebund1st.daming.application.event.TooManyFailureSmsVerificationAttemptsEvent
import com.thebund1st.daming.adapter.spring.redis.RedisSmsVerificationCodeMismatchEventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.StringRedisTemplate

import java.time.ZonedDateTime
import java.util.concurrent.TimeUnit

import static com.thebund1st.daming.application.domain.SmsVerificationFixture.aSmsVerification
import static java.time.temporal.ChronoUnit.SECONDS
import static org.awaitility.Awaitility.await

class RedisSmsVerificationCodeMismatchEventHandlerTest extends AbstractSpringDataRedis1Test {

    @Autowired
    private StringRedisTemplate stringRedisTemplate

    @Autowired
    private RedisSmsVerificationCodeMismatchEventHandler subject


    def "it should count mismatch if absent"() {
        given:
        def smsVerification = aSmsVerification().expiresIn(10, SECONDS).build()
        assert !stringRedisTemplate
                .hasKey(toKey(smsVerification))

        when:
        subject.on(new SmsVerificationCodeMismatchEvent(UUID.randomUUID().toString(),
                ZonedDateTime.now(),
                smsVerification.getMobile(),
                smsVerification.getScope(),
                smsVerification.expiresAt()))

        then:
        assert stringRedisTemplate
                .opsForSet().size(toKey(smsVerification)) == 1

        await().atMost(12, TimeUnit.SECONDS).until {
            !stringRedisTemplate.hasKey(toKey(smsVerification))
        }
    }

    def "it should emit event given too many failure attempts"() {
        given:
        def smsVerification = aSmsVerification().expiresIn(60, SECONDS).build()

        and: "almost reaching too many attempts"
        4.times {
            subject.on(new SmsVerificationCodeMismatchEvent(UUID.randomUUID().toString(),
                    ZonedDateTime.now(),
                    smsVerification.getMobile(),
                    smsVerification.getScope(),
                    smsVerification.expiresAt()))
        }
        assert stringRedisTemplate
                .opsForSet().size(toKey(smsVerification)) == 4

        when:
        subject.on(new SmsVerificationCodeMismatchEvent(UUID.randomUUID().toString(),
                ZonedDateTime.now(),
                smsVerification.getMobile(),
                smsVerification.getScope(),
                smsVerification.expiresAt()))

        then:
        assert !stringRedisTemplate
                .hasKey(toKey(smsVerification))

        and:
        1 * eventPublisher.publish(_ as TooManyFailureSmsVerificationAttemptsEvent)

    }

    def "it should remove mismatch count given verified"() {
        given:
        def smsVerification = aSmsVerification().build()
        assert !stringRedisTemplate
                .hasKey(toKey(smsVerification))

        and:
        subject.on(new SmsVerificationCodeMismatchEvent(UUID.randomUUID().toString(),
                ZonedDateTime.now(),
                smsVerification.getMobile(),
                smsVerification.getScope(),
                smsVerification.expiresAt()))

        assert stringRedisTemplate
                .hasKey(toKey(smsVerification))

        when:
        subject.on(new SmsVerificationCodeVerifiedEvent(UUID.randomUUID().toString(),
                ZonedDateTime.now(),
                smsVerification.getMobile(),
                smsVerification.getScope()))

        then:
        assert !stringRedisTemplate
                .hasKey(toKey(smsVerification))

    }

    private String toKey(SmsVerification smsVerification) {
        "sms.verification.code.mismatch.${smsVerification.getMobile().getValue()}.${smsVerification.getScope().getValue()}"
    }
}
