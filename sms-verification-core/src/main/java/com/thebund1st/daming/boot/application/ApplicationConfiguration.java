package com.thebund1st.daming.boot.application;

import com.thebund1st.daming.application.commandhandling.impl.SmsVerificationCommandHandler;
import com.thebund1st.daming.application.commandhandling.impl.DefaultSmsVerificationCommandHandler;
import com.thebund1st.daming.application.commandhandling.impl.ValidatedSmsVerificationCommandHandler;
import com.thebund1st.daming.boot.core.SmsVerificationCodeProperties;
import com.thebund1st.daming.application.domain.DomainEventPublisher;
import com.thebund1st.daming.application.domain.SmsVerificationCodeGenerator;
import com.thebund1st.daming.application.domain.SmsVerificationRepository;
import com.thebund1st.daming.time.Clock;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class ApplicationConfiguration {

    private final SmsVerificationCodeProperties smsVerificationCodeProperties;

    private final SmsVerificationRepository smsVerificationRepository;

    private final SmsVerificationCodeGenerator smsVerificationCodeGenerator;

    private final Clock clock;

    private final DomainEventPublisher domainEventPublisher;

    @ConditionalOnProperty(prefix = "daming.application.validation",
            name = "enabled",
            havingValue = "true"
    )
    @Bean
    public SmsVerificationCommandHandler validatedSmsVerificationCommandHandler() {
        SmsVerificationCommandHandler commandHandler =
                new ValidatedSmsVerificationCommandHandler(smsVerificationRepository,
                        smsVerificationCodeGenerator,
                        domainEventPublisher,
                        clock);
        setupExpires(commandHandler);
        return commandHandler;
    }

    private void setupExpires(SmsVerificationCommandHandler commandHandler) {
        commandHandler.setExpires(smsVerificationCodeProperties.getExpires());
    }

    @ConditionalOnMissingBean(SmsVerificationCommandHandler.class)
    @Bean
    public SmsVerificationCommandHandler defaultSmsVerificationCommandHandler() {
        SmsVerificationCommandHandler commandHandler =
                new DefaultSmsVerificationCommandHandler(smsVerificationRepository,
                        smsVerificationCodeGenerator,
                        domainEventPublisher,
                        clock);
        setupExpires(commandHandler);
        return commandHandler;
    }


}
