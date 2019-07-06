package com.thebund1st.daming.boot.security;

import lombok.Data;
import org.springframework.boot.convert.DurationUnit;

import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;


@Data
public class SlidingWindowProperties {
    @DurationUnit(SECONDS)
    private Duration duration = Duration.ofSeconds(3600);

    private int limit = 240;

}
