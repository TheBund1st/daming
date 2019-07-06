package com.thebund1st.daming.redis

import com.thebund1st.daming.boot.core.SmsVerificationCodeConfiguration
import com.thebund1st.daming.boot.redis.RedisConfiguration
import com.thebund1st.daming.boot.security.SlidingWindowConfiguration
import com.thebund1st.daming.boot.time.TimeConfiguration
import com.thebund1st.daming.core.DomainEventPublisher
import org.spockframework.spring.SpringBean
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@DataRedisTest
@Import([
        RedisConfiguration,
        TimeConfiguration,
        SmsVerificationCodeConfiguration,
        SlidingWindowConfiguration
])
@ActiveProfiles("commit")
class AbstractDataRedisTest extends Specification {

    @SpringBean
    protected DomainEventPublisher eventPublisher = Mock()

}
