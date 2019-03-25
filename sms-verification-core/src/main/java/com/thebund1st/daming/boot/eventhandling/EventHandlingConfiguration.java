package com.thebund1st.daming.boot.eventhandling;

import com.thebund1st.daming.core.SmsVerificationRepository;
import com.thebund1st.daming.eventhandling.SmsVerificationCodeMismatchEventListener;
import com.thebund1st.daming.eventhandling.SmsVerificationCodeVerifiedEventListener;
import com.thebund1st.daming.eventhandling.TooManyFailureSmsVerificationAttemptsEventListener;
import com.thebund1st.daming.events.EventPublisher;
import com.thebund1st.daming.redis.RedisSmsVerificationCodeMismatchEventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@ConditionalOnMissingBean(EventPublisher.class)
@Configuration
@Slf4j
public class EventHandlingConfiguration {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Bean
    public EventPublisher eventPublisher() {
        return event -> {
            log.debug("Attempt to publish {}", event);
            applicationEventPublisher.publishEvent(event);
        };
    }

    @Bean
    public SmsVerificationCodeMismatchEventListener smsVerificationCodeMismatchEventListener(
            RedisSmsVerificationCodeMismatchEventHandler handler) {
        return new SmsVerificationCodeMismatchEventListener(handler);
    }

    @Bean
    public SmsVerificationCodeVerifiedEventListener smsVerificationCodeVerifiedEventListener(
            RedisSmsVerificationCodeMismatchEventHandler handler) {
        return new SmsVerificationCodeVerifiedEventListener(handler);
    }

    @Bean
    public TooManyFailureSmsVerificationAttemptsEventListener tooManyFailureSmsVerificationAttemptsEventListener(
            SmsVerificationRepository repository) {
        return new TooManyFailureSmsVerificationAttemptsEventListener(repository);
    }
}
