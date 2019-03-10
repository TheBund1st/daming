package com.thebund1st.daming.boot.security;

import lombok.Data;
import org.springframework.boot.convert.DurationUnit;

import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;


@Data
public class BlockSendingProperties {
    @DurationUnit(SECONDS)
    private Duration expires = Duration.ofSeconds(60);
}
