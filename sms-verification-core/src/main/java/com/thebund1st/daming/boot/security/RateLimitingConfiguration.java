package com.thebund1st.daming.boot.security;

import com.thebund1st.daming.security.ratelimiting.ErrorsFactory;
import com.thebund1st.daming.security.ratelimiting.RateLimitedAspect;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RateLimitingConfiguration {

    @Bean
    public RateLimitedAspect rateLimitedAspect() {
        return new RateLimitedAspect();
    }

    @Bean
    public ErrorsFactory errorsFactory() {
        return new ErrorsFactory();
    }

    @ConfigurationProperties(prefix = "daming.rate.limiting.block.sending")
    @Bean
    public BlockSendingProperties blockSendingProperties() {
        return new BlockSendingProperties();
    }
}
