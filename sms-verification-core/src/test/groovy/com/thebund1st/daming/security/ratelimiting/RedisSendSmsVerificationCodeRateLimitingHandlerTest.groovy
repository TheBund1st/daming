package com.thebund1st.daming.security.ratelimiting

import com.thebund1st.daming.redis.AbstractDataRedisTest
import com.thebund1st.daming.redis.RedisSendSmsVerificationCodeRateLimitingHandler
import org.springframework.beans.factory.annotation.Autowired

import static com.thebund1st.daming.commands.SendSmsVerificationCodeCommandFixture.aSendSmsVerificationCodeCommand

class RedisSendSmsVerificationCodeRateLimitingHandlerTest extends AbstractDataRedisTest {

    @Autowired
    private RedisSendSmsVerificationCodeRateLimitingHandler subject

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
}
