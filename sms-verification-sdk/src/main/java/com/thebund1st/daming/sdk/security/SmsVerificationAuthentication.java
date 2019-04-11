package com.thebund1st.daming.sdk.security;

import com.thebund1st.daming.sdk.jwt.SmsVerificationClaims;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class SmsVerificationAuthentication extends AbstractAuthenticationToken {

    private SmsVerificationClaims smsVerificationClaims;

    public SmsVerificationAuthentication(SmsVerificationClaims smsVerificationClaims,
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
