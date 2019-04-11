package com.thebund1st.daming.sdk.boot.jjwt;

import com.thebund1st.daming.sdk.jjwt.ZonedClock;
import io.jsonwebtoken.Clock;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class JjwtConfiguration {

    @Bean(name = "jjwt.ZonedClock")
    public Clock clock() {
        return new ZonedClock();
    }
}
