package com.thebund1st.daming.application.commandhandling;

import com.thebund1st.daming.application.command.VerifySmsVerificationCodeCommand;

import javax.validation.Valid;

public interface VerifySmsVerificationCodeCommandHandler {

    void handle(@Valid VerifySmsVerificationCodeCommand command);

}
