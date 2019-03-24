package com.thebund1st.daming.boot.core;

import com.thebund1st.daming.core.MobilePhoneNumber;
import lombok.Data;
import org.springframework.boot.convert.DurationUnit;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.SECONDS;
import static java.util.stream.Collectors.toList;


@Data
public class SmsVerificationCodeProperties {
    @DurationUnit(SECONDS)
    private Duration expires = Duration.ofSeconds(60);

    @DurationUnit(SECONDS)
    private Duration block = Duration.ofSeconds(15);

    private int maxFailures = 5;

    private List<String> whitelist = new ArrayList<>();

    public List<MobilePhoneNumber> whitelist() {
        return getWhitelist().stream().map(MobilePhoneNumber::mobilePhoneNumberOf).collect(toList());
    }


}
