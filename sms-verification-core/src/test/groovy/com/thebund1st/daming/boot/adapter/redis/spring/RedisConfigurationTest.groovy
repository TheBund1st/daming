package com.thebund1st.daming.boot.adapter.redis.spring


import com.thebund1st.daming.boot.AbstractAutoConfigurationTest
import com.thebund1st.daming.application.domain.SmsVerification
import com.thebund1st.daming.application.domain.SmsVerificationRepository
import com.thebund1st.daming.adapter.redis.spirng.RedisSendSmsVerificationCodeNextWindowRateLimiter
import com.thebund1st.daming.adapter.redis.spirng.RedisSmsVerificationCodeMismatchEventHandler
import com.thebund1st.daming.adapter.redis.spirng.RedisSmsVerificationRepository
import foo.bar.WithCustomizedRedisTemplate
import foo.bar.WithCustomizedSmsVerificationStore
import org.springframework.data.redis.core.RedisTemplate

import java.time.Duration

class RedisConfigurationTest extends AbstractAutoConfigurationTest {

    def "it should provide one bean of RedisTemplate<String, SmsVerification> given no customized configuration"() {

        when:
        this.contextRunner

        then:
        this.contextRunner.run { it ->
            //FIXME this does not work
            //assert it.getBeanNamesForType(ResolvableType.forClassWithGenerics(RedisTemplate, String, SmsVerification)).length == 1
            RedisTemplate<String, SmsVerification> actual = it.getBean("smsVerificationRedisTemplate")
            assert actual != null
        }
    }

    def "it should skip default instance of RedisTemplate<String, SmsVerification> given customized one is provided"() {

        when:
        def contextRunner = this.contextRunner
                .withUserConfiguration(WithCustomizedRedisTemplate)

        then:

        contextRunner.run { it ->
            RedisTemplate<String, SmsVerification> actual = it.getBean("smsVerificationRedisTemplate")
            assert actual != null
            assert actual instanceof CustomizedRedisTemplate
        }
    }

    def "it should provide one bean of RedisSmsVerificationStore given no customized configuration"() {

        when:
        this.contextRunner

        then:
        this.contextRunner.run { it ->
            SmsVerificationRepository actual = it.getBean(SmsVerificationRepository)
            assert actual instanceof RedisSmsVerificationRepository
        }
    }

    def "it should skip default RedisSmsVerificationStore given customized configuration"() {

        when:
        def contextRunner = this.contextRunner.withUserConfiguration(WithCustomizedSmsVerificationStore)

        then:
        contextRunner.run { it ->
            def actual = it.getBean(SmsVerificationRepository)
            assert actual instanceof WithCustomizedSmsVerificationStore.SmsVerificationRepositoryStub
        }
    }

    def "it should provide RedisSendSmsVerificationCodeRateLimitingHandler"() {

        when:
        def contextRunner = this.contextRunner
                .withPropertyValues("daming.sms.verification.code.block=20")

        then:
        contextRunner.run { it ->
            RedisSendSmsVerificationCodeNextWindowRateLimiter actual = it.
                    getBean(RedisSendSmsVerificationCodeNextWindowRateLimiter)
            assert actual.expires == Duration.ofSeconds(20)
        }
    }

    def "it should provide RedisSmsVerificationCodeMismatchEventHandler"() {

        when:
        def contextRunner = this.contextRunner
                .withPropertyValues("daming.sms.verification.code.max-failures=20")

        then:
        contextRunner.run { it ->
            RedisSmsVerificationCodeMismatchEventHandler actual = it.
                    getBean(RedisSmsVerificationCodeMismatchEventHandler)
            assert actual.threshold == 20
        }
    }
}
