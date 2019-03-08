package com.thebund1st.daming.boot.jwt;

import lombok.Data;
import org.springframework.boot.convert.DurationUnit;

import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;


@Data
public class JwtProperties {
    @DurationUnit(SECONDS)
    private Duration expires = Duration.ofSeconds(900);

}
