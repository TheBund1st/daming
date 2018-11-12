package com.thebund1st.daming.core;

public interface SmsVerificationStore {
    void store(SmsVerification code);

    boolean exists(MobilePhoneNumber mobile);

    SmsVerification shouldFindBy(MobilePhoneNumber mobile);

    void remove(SmsVerification smsVerification);
}
