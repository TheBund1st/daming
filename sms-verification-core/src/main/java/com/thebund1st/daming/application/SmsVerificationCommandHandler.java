package com.thebund1st.daming.application;

import com.thebund1st.daming.commands.SendSmsVerificationCodeCommand;
import com.thebund1st.daming.commands.VerifySmsVerificationCodeCommand;
import com.thebund1st.daming.core.SmsVerification;
import com.thebund1st.daming.core.SmsVerificationCode;
import com.thebund1st.daming.core.SmsVerificationCodeGenerator;
import com.thebund1st.daming.core.SmsVerificationRepository;
import com.thebund1st.daming.core.exceptions.MobileIsStillUnderVerificationException;
import com.thebund1st.daming.core.exceptions.SmsVerificationCodeMismatchException;
import com.thebund1st.daming.sms.SmsSender;
import com.thebund1st.daming.time.Clock;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
@Transactional
public class SmsVerificationCommandHandler {

    private final SmsVerificationRepository smsVerificationRepository;

    private final SmsVerificationCodeGenerator smsVerificationCodeGenerator;

    private final Clock clock;

    @Setter
    @Getter
    private Duration expires = Duration.ofSeconds(60);

    @SmsSender(delegateTo = "smsVerificationSender") //TODO make it configurable
    public SmsVerification handle(SendSmsVerificationCodeCommand command) {
        if (smsVerificationRepository.exists(command.getMobile(), command.getScope())) {
            throw new MobileIsStillUnderVerificationException(command.getMobile(), command.getScope());
        } else {
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
    }

    public void handle(VerifySmsVerificationCodeCommand command) {
        SmsVerification smsVerification = smsVerificationRepository
                .shouldFindBy(command.getMobile(), command.getScope());
        if (smsVerification.matches(command.getCode())) {
            smsVerificationRepository.remove(smsVerification);
        } else {
            throw new SmsVerificationCodeMismatchException(smsVerification, command.getCode());
        }
    }
}
