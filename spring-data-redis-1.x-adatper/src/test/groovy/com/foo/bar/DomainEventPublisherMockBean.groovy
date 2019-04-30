package com.foo.bar

import com.thebund1st.daming.core.DomainEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import spock.mock.DetachedMockFactory

@Configuration
class DomainEventPublisherMockBean {
    private DetachedMockFactory factory = new DetachedMockFactory()

    @Bean
    DomainEventPublisher domainEventPublisher() {
        factory.Mock(DomainEventPublisher)
    }
}
