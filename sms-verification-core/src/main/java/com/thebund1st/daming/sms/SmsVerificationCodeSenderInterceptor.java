package com.thebund1st.daming.sms;

import com.thebund1st.daming.application.domain.SmsVerification;

/**
 * @since 0.9.6
 */
public interface SmsVerificationCodeSenderInterceptor {

    default boolean preHandle(SmsVerification smsVerification) {
        return true;
    }

    default void postHandle(SmsVerification verification) {

    }
}