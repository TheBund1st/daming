package com.thebund1st.daming.application;

import com.thebund1st.daming.core.SmsVerification;

public interface SmsVerificationSender {

    void send(SmsVerification verification);
}
