package com.thebund1st.daming.boot.time;

import com.thebund1st.daming.application.time.Clock;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class TimeConfiguration {

    @Bean
    public Clock clock() {
        return new Clock();
    }

}
