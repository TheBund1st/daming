package com.thebund1st.daming.application.commandhandling;

import com.thebund1st.daming.application.command.SendSmsVerificationCodeCommand;
import com.thebund1st.daming.application.domain.SmsVerification;

import javax.validation.Valid;

public interface SendSmsVerificationCodeCommandHandler {

    SmsVerification handle(@Valid SendSmsVerificationCodeCommand command);

}
