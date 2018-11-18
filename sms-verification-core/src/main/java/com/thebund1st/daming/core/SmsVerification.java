package com.thebund1st.daming.core;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Duration;
import java.time.LocalDateTime;

@EqualsAndHashCode(of = "mobile")
@ToString(of = "mobile")
@Getter
@Setter
public class SmsVerification {
    private LocalDateTime createdAt;
    private MobilePhoneNumber mobile;
    private SmsVerificationCode code;
    private Duration expires = Duration.ofSeconds(60);

    public boolean matches(SmsVerificationCode code) {
        return getCode().equals(code);
    }

}
