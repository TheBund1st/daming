package com.foo.bar

import com.thebund1st.daming.core.DomainEventPublisher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import redis.embedded.RedisServer
import spock.lang.Shared
import spock.lang.Specification

@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
class AbstractSpringDataRedis1Test extends Specification {

    @Autowired
    protected DomainEventPublisher eventPublisher


}
