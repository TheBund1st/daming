package com.thebund1st.daming.application;

import com.thebund1st.daming.commands.SendSmsVerificationCodeCommand;
import com.thebund1st.daming.commands.VerifySmsVerificationCodeCommand;
import com.thebund1st.daming.core.SmsVerification;
import com.thebund1st.daming.core.SmsVerificationCode;
import com.thebund1st.daming.core.SmsVerificationCodeGenerator;
import com.thebund1st.daming.core.SmsVerificationRepository;
import com.thebund1st.daming.core.exceptions.SmsVerificationCodeMismatchException;
import com.thebund1st.daming.events.EventPublisher;
import com.thebund1st.daming.events.SmsVerificationCodeMismatchEvent;
import com.thebund1st.daming.events.SmsVerificationCodeVerifiedEvent;
import com.thebund1st.daming.security.ratelimiting.RateLimited;
import com.thebund1st.daming.sms.SmsSender;
import com.thebund1st.daming.time.Clock;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.time.Duration;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Validated
@Transactional
public class SmsVerificationCommandHandler {

    private final SmsVerificationRepository smsVerificationRepository;

    private final SmsVerificationCodeGenerator smsVerificationCodeGenerator;

    private final EventPublisher eventPublisher;

    private final Clock clock;

    @Setter
    @Getter
    private Duration expires = Duration.ofSeconds(60);

    @SmsSender(delegateTo = "smsVerificationSender") //TODO make it configurable
    @RateLimited(action = "sendSmsVerificationCodeCommand")
    public SmsVerification handle(@Valid SendSmsVerificationCodeCommand command) {
        SmsVerificationCode code = smsVerificationCodeGenerator.generate();
        SmsVerification verification = new SmsVerification();
        verification.setCreatedAt(clock.now().toLocalDateTime());
        verification.setMobile(command.getMobile());
        verification.setScope(command.getScope());
        verification.setCode(code);
        verification.setExpires(expires);
        smsVerificationRepository.store(verification);
        return verification;
    }

    public void handle(@Valid VerifySmsVerificationCodeCommand command) {
        SmsVerification smsVerification = smsVerificationRepository
                .shouldFindBy(command.getMobile(), command.getScope());
        if (smsVerification.matches(command.getCode())) {
            smsVerificationRepository.remove(smsVerification);
            eventPublisher.publish(new SmsVerificationCodeVerifiedEvent(UUID.randomUUID().toString(),
                    clock.now(),
                    command.getMobile(), command.getScope()));
        } else {
            eventPublisher.publish(new SmsVerificationCodeMismatchEvent(UUID.randomUUID().toString(),
                    clock.now(),
                    command.getMobile(), command.getScope()));
            throw new SmsVerificationCodeMismatchException(smsVerification, command.getCode());
        }
    }
}
