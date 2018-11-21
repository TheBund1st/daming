package com.thebund1st.daming.application;

import com.thebund1st.daming.commands.SendSmsVerificationCodeCommand;
import com.thebund1st.daming.commands.VerifySmsVerificationCodeCommand;
import com.thebund1st.daming.core.SmsVerification;
import com.thebund1st.daming.core.SmsVerificationCode;
import com.thebund1st.daming.core.SmsVerificationCodeGenerator;
import com.thebund1st.daming.core.SmsVerificationStore;
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

    private final SmsVerificationStore smsVerificationStore;

    private final SmsVerificationCodeGenerator smsVerificationCodeGenerator;

    private final Clock clock;

    @Setter
    @Getter
    private Duration expires = Duration.ofSeconds(60);

    @SmsSender(delegateTo = "smsVerificationSender") //TODO make it configurable
    public SmsVerification handle(SendSmsVerificationCodeCommand command) {
        if (smsVerificationStore.exists(command.getMobile())) {
            throw new MobileIsStillUnderVerificationException(command.getMobile());
        } else {
            SmsVerificationCode code = smsVerificationCodeGenerator.generate();
            SmsVerification verification = new SmsVerification();
            verification.setCreatedAt(clock.now().toLocalDateTime());
            verification.setMobile(command.getMobile());
            verification.setCode(code);
            verification.setExpires(expires);
            smsVerificationStore.store(verification);
            return verification;
        }
    }

    public void handle(VerifySmsVerificationCodeCommand command) {
        SmsVerification smsVerification = smsVerificationStore.shouldFindBy(command.getMobile());
        if (smsVerification.matches(command.getCode())) {
            smsVerificationStore.remove(smsVerification);
        } else {
            throw new SmsVerificationCodeMismatchException(smsVerification, command.getCode());
        }
    }
}
