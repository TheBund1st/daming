package com.thebund1st.daming.sdk.boot.jwt;

import com.thebund1st.daming.jwt.key.JwtPublicKeyLoader;
import com.thebund1st.daming.sdk.boot.jwt.key.JwtKeyConfiguration;
import com.thebund1st.daming.sdk.jwt.SmsVerificationJwtVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@RequiredArgsConstructor
@Configuration
@Import({JwtKeyConfiguration.class})
public class JwtConfiguration {

    @ConditionalOnMissingBean(SmsVerificationJwtVerifier.class)
    @Bean
    public SmsVerificationJwtVerifier smsVerificationJwtVerifier(JwtPublicKeyLoader jwtPublicKeyLoader) {
        return new SmsVerificationJwtVerifier(jwtPublicKeyLoader.getKey());
    }
}
