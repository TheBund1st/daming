package com.thebund1st.daming.adapter.spring.web.rest;

import com.thebund1st.daming.application.commandhandling.SendSmsVerificationCodeCommandHandler;
import com.thebund1st.daming.commands.SendSmsVerificationCodeCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.ACCEPTED;

@RequiredArgsConstructor
@RestController
public class SendSmsVerificationCodeRestController {

    private final SendSmsVerificationCodeCommandHandler sendSmsVerificationCodeCommandHandler;

    @PostMapping("#{endpointProperties.sendSmsVerificationCodePath}")
    @ResponseStatus(ACCEPTED)
    public void handle(@Valid @RequestBody SendSmsVerificationCodeCommand command) {
        sendSmsVerificationCodeCommandHandler.handle(command);
    }
}
