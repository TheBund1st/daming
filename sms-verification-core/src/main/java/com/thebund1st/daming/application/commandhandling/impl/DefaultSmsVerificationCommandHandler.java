package com.thebund1st.daming.application.commandhandling.impl;

import com.thebund1st.daming.application.domain.DomainEventPublisher;
import com.thebund1st.daming.application.domain.SmsVerificationCodeGenerator;
import com.thebund1st.daming.application.domain.SmsVerificationRepository;
import com.thebund1st.daming.application.time.Clock;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultSmsVerificationCommandHandler extends SmsVerificationCommandHandler {


    public DefaultSmsVerificationCommandHandler(SmsVerificationRepository smsVerificationRepository,
                                                SmsVerificationCodeGenerator smsVerificationCodeGenerator,
                                                DomainEventPublisher domainEventPublisher,
                                                Clock clock) {
        super(smsVerificationRepository, smsVerificationCodeGenerator, domainEventPublisher, clock);
    }
}
