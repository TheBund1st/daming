package com.thebund1st.daming.web.rest;

import com.thebund1st.daming.application.SmsVerificationCommandHandler;
import com.thebund1st.daming.jwt.SmsVerifiedJwtIssuer;
import com.thebund1st.daming.commands.SendSmsVerificationCodeCommand;
import com.thebund1st.daming.commands.VerifySmsVerificationCodeCommand;
import com.thebund1st.daming.web.rest.resources.SmsVerifiedJwtResource;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.ACCEPTED;

@RequiredArgsConstructor
@RestController
//TODO make the url path configurable
public class SmsVerificationRestController {

    private final SmsVerificationCommandHandler smsVerificationCommandHandler;

    private final SmsVerifiedJwtIssuer smsVerifiedJwtIssuer;


    @PostMapping("/api/sms/verification/code")
    @ResponseStatus(ACCEPTED)
    public void handle(@RequestBody SendSmsVerificationCodeCommand command) {
        smsVerificationCommandHandler.handle(command);
    }

    @DeleteMapping("/api/sms/verification/code")
    public SmsVerifiedJwtResource handle(@RequestBody VerifySmsVerificationCodeCommand command) {
        smsVerificationCommandHandler.handle(command);
        return new SmsVerifiedJwtResource(smsVerifiedJwtIssuer.issue(command.getMobile(), command.getScope()));
    }
}
