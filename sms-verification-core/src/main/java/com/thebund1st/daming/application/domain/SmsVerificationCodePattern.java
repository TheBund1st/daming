package com.thebund1st.daming.application.domain;

public interface SmsVerificationCodePattern {

    boolean matches(SmsVerificationCode value);
}
