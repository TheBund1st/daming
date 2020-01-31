package com.thebund1st.daming.application.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class SmsVerificationScope {
    private String value;

    private SmsVerificationScope(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static SmsVerificationScope smsVerificationScopeOf(String value) {
        return new SmsVerificationScope(value);
    }
}
