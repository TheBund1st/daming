package com.thebund1st.daming.boot.application;

import com.thebund1st.daming.application.SmsVerificationCommandHandler;
import com.thebund1st.daming.application.SmsVerifiedJwtIssuer;
import com.thebund1st.daming.boot.SmsVerificationCodeProperties;
import com.thebund1st.daming.core.SmsVerificationCodeGenerator;
import com.thebund1st.daming.core.SmsVerificationRepository;
import com.thebund1st.daming.jwt.FileJwtKeyLoader;
import com.thebund1st.daming.jwt.JwtKeyLoader;
import com.thebund1st.daming.time.Clock;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "daming.jwt")
public class ApplicationConfiguration {

    private final SmsVerificationCodeProperties smsVerificationCodeProperties;

    private final SmsVerificationRepository smsVerificationRepository;

    private final SmsVerificationCodeGenerator smsVerificationCodeGenerator;

    private final Clock clock;

    // make it configurable
    @Setter
    private int expiresInSeconds = 900;
    // make it configurable
    @Setter
    private String privateKeyFileLocation = "./sms-verification-private.der";

    @Bean
    public SmsVerificationCommandHandler smsVerificationCommandHandler() {
        SmsVerificationCommandHandler commandHandler =
                new SmsVerificationCommandHandler(smsVerificationRepository, smsVerificationCodeGenerator, clock);
        commandHandler.setExpires(smsVerificationCodeProperties.getExpires());
        return commandHandler;
    }

    @Bean
    public JwtKeyLoader fileJwtKeyLoader() {
        FileJwtKeyLoader jwtKeyLoader = new FileJwtKeyLoader();
        jwtKeyLoader.setPrivateKeyFileLocation(privateKeyFileLocation);
        return jwtKeyLoader;
    }

    @Bean
    public SmsVerifiedJwtIssuer smsVerifiedJwtIssuer() {
        SmsVerifiedJwtIssuer issuer = new SmsVerifiedJwtIssuer(clock, fileJwtKeyLoader().getKey());
        issuer.setExpiresInSeconds(expiresInSeconds);
        return issuer;
    }

}
