package com.thebund1st.daming.core;

public interface DomainEventPublisher {
    void publish(Object event);
}
