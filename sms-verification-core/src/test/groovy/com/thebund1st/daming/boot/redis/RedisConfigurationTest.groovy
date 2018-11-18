package com.thebund1st.daming.boot.redis

import com.thebund1st.daming.boot.SmsVerificationAutoConfiguration
import com.thebund1st.daming.core.SmsVerification
import foo.bar.WithCustomizedRedisTemplate
import org.springframework.boot.autoconfigure.AutoConfigurations
import org.springframework.boot.test.context.runner.ApplicationContextRunner
import org.springframework.data.redis.core.RedisTemplate
import spock.lang.Specification

class RedisConfigurationTest extends Specification {
    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(SmsVerificationAutoConfiguration.class))

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
