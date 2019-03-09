package com.thebund1st.daming.redis

import com.thebund1st.daming.boot.redis.RedisConfiguration
import com.thebund1st.daming.boot.time.TimeConfiguration
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import redis.embedded.RedisServer
import spock.lang.Specification

@DataRedisTest
@Import([
        RedisConfiguration,
        TimeConfiguration
])
@ActiveProfiles("commit")
class AbstractDataRedisTest extends Specification {

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
