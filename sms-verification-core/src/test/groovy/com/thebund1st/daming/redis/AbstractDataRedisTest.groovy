package com.thebund1st.daming.redis

import com.thebund1st.daming.boot.SmsVerificationCodeConfiguration
import com.thebund1st.daming.boot.redis.RedisConfiguration
import com.thebund1st.daming.boot.time.TimeConfiguration
import com.thebund1st.daming.events.EventPublisher
import org.spockframework.spring.SpringBean
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import redis.embedded.RedisServer
import spock.lang.Specification

@DataRedisTest
@Import([
        RedisConfiguration,
        TimeConfiguration,
        SmsVerificationCodeConfiguration
])
@ActiveProfiles("commit")
class AbstractDataRedisTest extends Specification {

    @SpringBean
    protected EventPublisher eventPublisher = Mock()

    protected RedisServer redisServer

    def port() {
        return 16379
    }

    def setup() {
        this.redisServer = new RedisServer(port())
        redisServer.start()
    }

    def cleanup() {
        this.redisServer.stop()
    }


}
