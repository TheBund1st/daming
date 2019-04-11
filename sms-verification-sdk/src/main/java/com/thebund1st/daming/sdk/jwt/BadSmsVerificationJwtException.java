package com.thebund1st.daming.sdk.jwt;

import org.springframework.security.core.AuthenticationException;

public class BadSmsVerificationJwtException extends AuthenticationException {

    public BadSmsVerificationJwtException(String message) {
        super(message);
    }

    public BadSmsVerificationJwtException(String message, Exception cause) {
        super(message, cause);
    }
}
