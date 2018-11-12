package com.thebund1st.daming.core;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode(of = "mobile")
@ToString(of = "mobile")
@Getter
@Setter
public class SmsVerification {
    private MobilePhoneNumber mobile;
    private SmsVerificationCode code;

    public boolean matches(SmsVerificationCode code) {
        return getCode().equals(code);
    }

}
