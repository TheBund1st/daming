package com.thebund1st.daming.core;

import java.util.Optional;

public interface SmsVerificationRepository {
    void store(SmsVerification code);

    @Deprecated
    boolean exists(MobilePhoneNumber mobile, SmsVerificationScope scope);

    SmsVerification shouldFindBy(MobilePhoneNumber mobile, SmsVerificationScope scope);

    Optional<SmsVerification> findBy(MobilePhoneNumber mobile, SmsVerificationScope scope);

    void remove(SmsVerification smsVerification);
}
