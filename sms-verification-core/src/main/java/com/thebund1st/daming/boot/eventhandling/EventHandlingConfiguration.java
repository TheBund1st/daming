package com.thebund1st.daming.boot.eventhandling;

import com.thebund1st.daming.application.domain.DomainEventPublisher;
import com.thebund1st.daming.application.domain.SmsVerificationCodeSender;
import com.thebund1st.daming.application.domain.SmsVerificationRepository;
import com.thebund1st.daming.adapter.spring.event.SmsVerificationCodeMismatchEventListener;
import com.thebund1st.daming.adapter.spring.event.SmsVerificationCodeVerifiedEventListener;
import com.thebund1st.daming.adapter.spring.event.SmsVerificationRequestedEventListener;
import com.thebund1st.daming.adapter.spring.event.TooManyFailureSmsVerificationAttemptsEventListener;
import com.thebund1st.daming.adapter.spring.redis.RedisSmsVerificationCodeMismatchEventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@ConditionalOnMissingBean(DomainEventPublisher.class)
@Configuration
@Slf4j
public class EventHandlingConfiguration {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Bean
    public DomainEventPublisher eventPublisher() {
        return event -> {
            log.debug("Attempt to publish {}", event);
            applicationEventPublisher.publishEvent(event);
        };
    }

    @Bean
    public SmsVerificationRequestedEventListener smsVerificationRequestedEventListener(
            @Qualifier("smsVerificationSender") SmsVerificationCodeSender sender) {
        return new SmsVerificationRequestedEventListener(sender);
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
