package com.thebund1st.daming.adapter.ratelimitj


import es.moki.ratelimitj.core.limiter.request.RequestLimitRule
import es.moki.ratelimitj.core.limiter.request.RequestRateLimiter
import es.moki.ratelimitj.redis.request.RedisRateLimiterFactory
import io.lettuce.core.RedisClient
import spock.lang.Specification

import java.time.Duration

class RedisSlidingWindowRateLimiterLearningTest extends Specification {

    def "it should limit 3 hits every 3 seconds"() {
        given:
        RedisRateLimiterFactory factory = new RedisRateLimiterFactory(RedisClient.create("redis://localhost"))

        Set<RequestLimitRule> rules = Collections.singleton(RequestLimitRule.of(Duration.ofSeconds(1), 2)) // 50 request per minute, per key
        RequestRateLimiter requestRateLimiter = factory.getInstance(rules)

        when:

        1.upto(5, {
            println(requestRateLimiter.overLimitWhenIncremented("ip:127.0.0.2"))
        })

        then:
        println("")
    }
}
