package com.thebund1st.daming.redis


import com.thebund1st.daming.core.exceptions.MobileIsStillUnderVerificationException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import redis.embedded.RedisServer
import spock.lang.Specification

import java.util.concurrent.TimeUnit

import static com.thebund1st.daming.core.SmsVerificationFixture.aSmsVerification
import static java.time.temporal.ChronoUnit.SECONDS
import static org.awaitility.Awaitility.await

@SpringBootTest
@ActiveProfiles("commit")
class RedisSmsVerificationRepositoryTest extends Specification {

    private RedisServer redisServer

    def setup() {
        this.redisServer = new RedisServer(16379)
        redisServer.start()
    }

    def cleanup() {
        this.redisServer.stop()
    }

    @Autowired
    private RedisSmsVerificationRepository subject

    def "it should store sms verification"() {
        given:
        def smsVerification = aSmsVerification().expiresIn(60, SECONDS).build()
        assert !subject.exists(smsVerification.mobile, smsVerification.scope)

        when:
        subject.store(smsVerification)

        then:
        assert subject.exists(smsVerification.mobile, smsVerification.scope)

        and:
        def found = subject.shouldFindBy(smsVerification.mobile, smsVerification.scope)
        assert found.code == smsVerification.code
        assert found.expires == smsVerification.expires
    }

    def "it should throw exception when store sms verification given it exists"() {
        given:
        def smsVerification = aSmsVerification().build()
        subject.store(smsVerification)

        when:
        subject.store(smsVerification)

        then:
        thrown(MobileIsStillUnderVerificationException)
    }

    def "it should remove sms verification"() {
        given:
        def smsVerification = aSmsVerification().build()
        subject.store(smsVerification)

        assert subject.exists(smsVerification.getMobile(), smsVerification.scope)

        when:
        subject.remove(smsVerification)

        then:
        assert !subject.exists(smsVerification.mobile, smsVerification.scope)
    }

    def "it should expire sms verification"() {
        given:
        def smsVerification = aSmsVerification().expiresIn(2, SECONDS).build()

        when:
        subject.store(smsVerification)

        then:
        assert subject.exists(smsVerification.mobile, smsVerification.scope)

        and:
        await().atMost(3, TimeUnit.SECONDS).until {
            !subject.exists(smsVerification.mobile, smsVerification.scope)
        }
    }
}
