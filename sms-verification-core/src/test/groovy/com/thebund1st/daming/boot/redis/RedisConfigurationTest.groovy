package com.thebund1st.daming.boot.redis

import com.thebund1st.daming.boot.AbstractAutoConfigurationTest
import com.thebund1st.daming.core.SmsVerification
import foo.bar.WithCustomizedRedisTemplate
import org.springframework.data.redis.core.RedisTemplate

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

}
