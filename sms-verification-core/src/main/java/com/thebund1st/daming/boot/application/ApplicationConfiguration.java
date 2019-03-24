package com.thebund1st.daming.boot.application;

import com.thebund1st.daming.application.SmsVerificationCommandHandler;
import com.thebund1st.daming.boot.core.SmsVerificationCodeProperties;
import com.thebund1st.daming.core.SmsVerificationCodeGenerator;
import com.thebund1st.daming.core.SmsVerificationRepository;
import com.thebund1st.daming.events.EventPublisher;
import com.thebund1st.daming.time.Clock;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class ApplicationConfiguration {

    private final SmsVerificationCodeProperties smsVerificationCodeProperties;

    private final SmsVerificationRepository smsVerificationRepository;

    private final SmsVerificationCodeGenerator smsVerificationCodeGenerator;

    private final Clock clock;

    private final EventPublisher eventPublisher;

    @Bean
    public SmsVerificationCommandHandler smsVerificationCommandHandler() {
        SmsVerificationCommandHandler commandHandler =
                new SmsVerificationCommandHandler(smsVerificationRepository,
                        smsVerificationCodeGenerator,
                        eventPublisher,
                        clock);
        commandHandler.setExpires(smsVerificationCodeProperties.getExpires());
        return commandHandler;
    }


}
