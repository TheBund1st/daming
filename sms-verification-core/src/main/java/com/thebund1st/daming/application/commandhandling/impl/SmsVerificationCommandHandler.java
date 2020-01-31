package com.thebund1st.daming.application.commandhandling.impl;

import com.thebund1st.daming.application.commandhandling.SendSmsVerificationCodeCommandHandler;
import com.thebund1st.daming.application.commandhandling.VerifySmsVerificationCodeCommandHandler;
import com.thebund1st.daming.application.commandhandling.interceptor.CommandHandler;
import com.thebund1st.daming.commands.SendSmsVerificationCodeCommand;
import com.thebund1st.daming.commands.VerifySmsVerificationCodeCommand;
import com.thebund1st.daming.core.DomainEventPublisher;
import com.thebund1st.daming.core.SmsVerification;
import com.thebund1st.daming.core.SmsVerificationCode;
import com.thebund1st.daming.core.SmsVerificationCodeGenerator;
import com.thebund1st.daming.core.SmsVerificationRepository;
import com.thebund1st.daming.core.exceptions.SmsVerificationCodeMismatchException;
import com.thebund1st.daming.events.SmsVerificationCodeMismatchEvent;
import com.thebund1st.daming.events.SmsVerificationCodeVerifiedEvent;
import com.thebund1st.daming.events.SmsVerificationRequestedEvent;
import com.thebund1st.daming.security.ratelimiting.RateLimited;
import com.thebund1st.daming.time.Clock;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Validated
@Transactional
public abstract class SmsVerificationCommandHandler implements
        SendSmsVerificationCodeCommandHandler,
        VerifySmsVerificationCodeCommandHandler {

    private final SmsVerificationRepository smsVerificationRepository;

    private final SmsVerificationCodeGenerator smsVerificationCodeGenerator;

    private final DomainEventPublisher domainEventPublisher;

    private final Clock clock;

    @Setter
    @Getter
    private Duration expires = Duration.ofSeconds(60);

    @Override
    @RateLimited(action = "sendSmsVerificationCodeCommand")
    @CommandHandler
    public SmsVerification handle(SendSmsVerificationCodeCommand command) {
        SmsVerificationCode code = smsVerificationCodeGenerator.generate();
        SmsVerification verification = new SmsVerification();
        verification.setCreatedAt(clock.now());
        verification.setMobile(command.getMobile());
        verification.setScope(command.getScope());
        verification.setCode(code);
        verification.setExpires(expires);
        smsVerificationRepository.store(verification);
        domainEventPublisher.publish(new SmsVerificationRequestedEvent(nextEventId(),
                verification.getCreatedAt(),
                verification));
        return verification;
    }

    private String nextEventId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void handle(VerifySmsVerificationCodeCommand command) {
        SmsVerification smsVerification = smsVerificationRepository
                .shouldFindBy(command.getMobile(), command.getScope());
        if (smsVerification.matches(command.getCode())) {
            smsVerificationRepository.remove(smsVerification);
            domainEventPublisher.publish(new SmsVerificationCodeVerifiedEvent(nextEventId(),
                    clock.now(),
                    command.getMobile(), command.getScope()));
        } else {
            domainEventPublisher.publish(new SmsVerificationCodeMismatchEvent(nextEventId(),
                    clock.now(),
                    command.getMobile(),
                    command.getScope(),
                    smsVerification.expiresAt()));
            throw new SmsVerificationCodeMismatchException(smsVerification, command.getCode());
        }
    }
}
