package com.thebund1st.daming.application.impl;

import com.thebund1st.daming.application.SmsVerificationCommandHandler;
import com.thebund1st.daming.core.DomainEventPublisher;
import com.thebund1st.daming.core.SmsVerificationCodeGenerator;
import com.thebund1st.daming.core.SmsVerificationRepository;
import com.thebund1st.daming.time.Clock;
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
