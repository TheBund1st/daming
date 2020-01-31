package com.foo.bar

import com.thebund1st.daming.application.domain.DomainEventPublisher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
class AbstractSpringDataRedis1Test extends Specification {

    @Autowired
    protected DomainEventPublisher eventPublisher


}
