package com.thebund1st.daming.application.commandhandling.impl;

import com.thebund1st.daming.core.DomainEventPublisher;
import com.thebund1st.daming.core.SmsVerificationCodeGenerator;
import com.thebund1st.daming.core.SmsVerificationRepository;
import com.thebund1st.daming.time.Clock;
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
