package com.thebund1st.daming.application.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class SmsVerificationCode {
    private String value;

    private SmsVerificationCode(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static SmsVerificationCode smsVerificationCodeOf(String value) {
        return new SmsVerificationCode(value);
    }
}
