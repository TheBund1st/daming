package com.thebund1st.daming.sdk.jwt;

public abstract class SmsVerificationJwtException extends RuntimeException {
    public SmsVerificationJwtException(String message) {
        super(message);
    }

    public SmsVerificationJwtException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
