package com.thebund1st.daming.application.domain;

public interface SmsVerificationCodeSender {

    void send(SmsVerification verification);
}
