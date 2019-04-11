package com.thebund1st.daming.sdk.boot;

import com.thebund1st.daming.sdk.boot.jwt.JwtConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
        JwtConfiguration.class
})
@Configuration
public class SmsVerificationAutoConfiguration {


}