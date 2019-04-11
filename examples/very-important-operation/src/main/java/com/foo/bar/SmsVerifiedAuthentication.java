package com.foo.bar;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class SmsVerifiedAuthentication extends AbstractAuthenticationToken {

    private SmsVerificationClaims smsVerificationClaims;

    public SmsVerifiedAuthentication(SmsVerificationClaims smsVerificationClaims,
                                     Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.smsVerificationClaims = smsVerificationClaims;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return smsVerificationClaims;
    }
}
