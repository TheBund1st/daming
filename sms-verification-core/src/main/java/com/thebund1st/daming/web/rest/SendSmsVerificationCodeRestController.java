package com.thebund1st.daming.web.rest;

import com.thebund1st.daming.application.SmsVerificationCommandHandler;
import com.thebund1st.daming.commands.SendSmsVerificationCodeCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.ACCEPTED;

@RequiredArgsConstructor
@RestController
public class SendSmsVerificationCodeRestController {

    private final SmsVerificationCommandHandler smsVerificationCommandHandler;

    @PostMapping("#{endpointProperties.sendSmsVerificationCodePath}")
    @ResponseStatus(ACCEPTED)
    public void handle(@RequestBody SendSmsVerificationCodeCommand command) {
        smsVerificationCommandHandler.handle(command);
    }
}
