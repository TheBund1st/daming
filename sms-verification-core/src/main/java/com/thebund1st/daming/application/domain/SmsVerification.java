package com.thebund1st.daming.application.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Duration;
import java.time.ZonedDateTime;

@EqualsAndHashCode(of = {"mobile", "scope"})
@ToString(of = {"mobile", "scope"})
@Getter
@Setter
public class SmsVerification {
    private ZonedDateTime createdAt;
    private MobilePhoneNumber mobile;
    private SmsVerificationScope scope;
    private SmsVerificationCode code;
    private Duration expires = Duration.ofSeconds(60);

    public boolean matches(SmsVerificationCode code) {
        return getCode().equals(code);
    }

    public boolean matches(MobilePhoneNumber mobilePhoneNumber) {
        return getMobile().equals(mobilePhoneNumber);
    }

    public boolean matches(SmsVerificationScope scope) {
        return getScope().equals(scope);
    }

    public ZonedDateTime expiresAt() {
        return createdAt.plusSeconds(expires.getSeconds());
    }
}
