package com.thebund1st.daming.boot.application;

import com.thebund1st.daming.application.SmsVerificationCommandHandler;
import com.thebund1st.daming.application.SmsVerifiedJwtIssuer;
import com.thebund1st.daming.boot.SmsVerificationCodeProperties;
import com.thebund1st.daming.boot.jwt.JwtProperties;
import com.thebund1st.daming.core.SmsVerificationCodeGenerator;
import com.thebund1st.daming.core.SmsVerificationRepository;
import com.thebund1st.daming.jwt.key.JwtKeyLoader;
import com.thebund1st.daming.time.Clock;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class ApplicationConfiguration {

    private final SmsVerificationCodeProperties smsVerificationCodeProperties;

    private final SmsVerificationRepository smsVerificationRepository;

    private final SmsVerificationCodeGenerator smsVerificationCodeGenerator;

    private final Clock clock;

    private final JwtProperties jwtProperties;

    private final JwtKeyLoader jwtKeyLoader;

    @Bean
    public SmsVerificationCommandHandler smsVerificationCommandHandler() {
        SmsVerificationCommandHandler commandHandler =
                new SmsVerificationCommandHandler(smsVerificationRepository, smsVerificationCodeGenerator, clock);
        commandHandler.setExpires(smsVerificationCodeProperties.getExpires());
        return commandHandler;
    }

    @ConditionalOnMissingBean(SmsVerifiedJwtIssuer.class)
    @Bean
    public SmsVerifiedJwtIssuer smsVerifiedJwtIssuer() {
        SmsVerifiedJwtIssuer issuer = new SmsVerifiedJwtIssuer(clock, jwtKeyLoader.getKey());
        issuer.setExpires(jwtProperties.getExpires());
        return issuer;
    }

}
