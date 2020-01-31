package com.thebund1st.daming.adapter.redis

import com.thebund1st.daming.adapter.redis.RedisSmsVerificationRepository
import com.thebund1st.daming.core.TestingVerificationCode
import com.thebund1st.daming.core.exceptions.MobileIsNotUnderVerificationException
import com.thebund1st.daming.redis.AbstractDataRedisTest
import org.springframework.beans.factory.annotation.Autowired

import java.util.concurrent.TimeUnit

import static com.thebund1st.daming.core.SmsVerificationFixture.aSmsVerification
import static java.time.temporal.ChronoUnit.SECONDS
import static org.awaitility.Awaitility.await

class RedisSmsVerificationRepositoryTest extends AbstractDataRedisTest {

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

    def "it should throw given the verification does not exit"() {
        given:
        def smsVerification = aSmsVerification().build()
        when:
        subject.shouldFindBy(smsVerification.getMobile(), smsVerification.getScope())

        then:
        Throwable thrown = thrown()

        assert thrown instanceof MobileIsNotUnderVerificationException
    }

    def "it should throw exception when store sms verification given it exists"() {
        given:
        def smsVerification = aSmsVerification().build()
        subject.store(smsVerification)

        and:
        def anotherCode = TestingVerificationCode.aSmsVerificationCodeOf(6)

        when:
        smsVerification.setCode(anotherCode)
        subject.store(smsVerification)

        then:
        def verification = subject.shouldFindBy(smsVerification.getMobile(), smsVerification.getScope())
        assert verification.matches(anotherCode)
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
