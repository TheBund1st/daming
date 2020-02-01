package com.thebund1st.daming.application.commandhandling.impl;

import com.thebund1st.daming.application.domain.DomainEventPublisher;
import com.thebund1st.daming.application.domain.SmsVerificationCodeGenerator;
import com.thebund1st.daming.application.domain.SmsVerificationRepository;
import com.thebund1st.daming.application.time.Clock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
public class ValidatedSmsVerificationCommandHandler extends SmsVerificationCommandHandler {


    public ValidatedSmsVerificationCommandHandler(SmsVerificationRepository smsVerificationRepository,
                                                  SmsVerificationCodeGenerator smsVerificationCodeGenerator,
                                                  DomainEventPublisher domainEventPublisher,
                                                  Clock clock) {
        super(smsVerificationRepository, smsVerificationCodeGenerator, domainEventPublisher, clock);
    }
}
