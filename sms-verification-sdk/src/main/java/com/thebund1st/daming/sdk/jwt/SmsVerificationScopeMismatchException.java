package com.thebund1st.daming.sdk.jwt;

public class SmsVerificationScopeMismatchException extends SmsVerificationJwtException {
    public SmsVerificationScopeMismatchException(SmsVerificationClaims claims, String scope) {
        super(String.format("SmsVerificationScope mismatched, expect %s, got %s", scope, claims.getScope()));
    }
}
