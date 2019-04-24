package com.thebund1st.daming.sdk.jwt;

public class BadSmsVerificationJwtException extends SmsVerificationJwtException {

    public BadSmsVerificationJwtException(String message) {
        super(message);
    }

    public BadSmsVerificationJwtException(String message, Exception cause) {
        super(message, cause);
    }
}
