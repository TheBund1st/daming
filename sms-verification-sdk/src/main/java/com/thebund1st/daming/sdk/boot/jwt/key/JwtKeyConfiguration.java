package com.thebund1st.daming.sdk.boot.jwt.key;

import com.thebund1st.daming.jwt.key.JwtPublicKeyLoader;
import com.thebund1st.daming.jwt.key.KeyBytesLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@RequiredArgsConstructor
@Configuration
@Import({FileJwtKeyConfiguration.class})
public class JwtKeyConfiguration {

    @ConditionalOnMissingBean(name = "smsVerificationJwtParsingKeyLoader")
    @Bean(name = "smsVerificationJwtParsingKeyLoader")
    public JwtPublicKeyLoader jwtPublicKeyLoader(
            @Qualifier("smsVerificationJwtParsingKeyBytesLoader") KeyBytesLoader keyBytesLoader) {
        return new JwtPublicKeyLoader(keyBytesLoader);
    }
}
