package com.thebund1st.daming.web.rest;

import com.thebund1st.daming.application.SmsVerificationCommandHandler;
import com.thebund1st.daming.commands.VerifySmsVerificationCodeCommand;
import com.thebund1st.daming.jwt.SmsVerifiedJwtIssuer;
import com.thebund1st.daming.web.rest.resources.SmsVerifiedJwtResource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@ConditionalOnProperty(prefix = "daming.jwt", name = "enabled", havingValue = "true", matchIfMissing = true)
@RequiredArgsConstructor
@RestController
public class VerifySmsVerificationCodeRestController {

    private final SmsVerificationCommandHandler smsVerificationCommandHandler;

    private final SmsVerifiedJwtIssuer smsVerifiedJwtIssuer;


    @DeleteMapping("#{endpointProperties.verifySmsVerificationCodePath}")
    public SmsVerifiedJwtResource handle(@Valid @RequestBody VerifySmsVerificationCodeCommand command) {
        smsVerificationCommandHandler.handle(command);
        return new SmsVerifiedJwtResource(smsVerifiedJwtIssuer.issue(command.getMobile(), command.getScope()));
    }
}
