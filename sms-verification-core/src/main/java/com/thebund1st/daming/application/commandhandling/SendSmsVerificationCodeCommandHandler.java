package com.thebund1st.daming.application.commandhandling;

import com.thebund1st.daming.commands.SendSmsVerificationCodeCommand;
import com.thebund1st.daming.core.SmsVerification;

import javax.validation.Valid;

public interface SendSmsVerificationCodeCommandHandler {

    SmsVerification handle(@Valid SendSmsVerificationCodeCommand command);

}
