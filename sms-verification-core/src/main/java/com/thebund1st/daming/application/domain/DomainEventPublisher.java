package com.thebund1st.daming.application.domain;

public interface DomainEventPublisher {
    void publish(Object event);
}
