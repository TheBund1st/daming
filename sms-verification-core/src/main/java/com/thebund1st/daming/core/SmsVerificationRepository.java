package com.thebund1st.daming.core;

public interface SmsVerificationRepository {
    void store(SmsVerification code);

    @Deprecated
    boolean exists(MobilePhoneNumber mobile, SmsVerificationScope scope);

    SmsVerification shouldFindBy(MobilePhoneNumber mobile, SmsVerificationScope scope);

    void remove(SmsVerification smsVerification);
}
