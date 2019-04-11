package com.thebund1st.daming.sdk.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

import java.security.Key;

public class SmsVerificationJwtVerifier {

    private final Key publicKey;
    private Clock clock;

    public SmsVerificationJwtVerifier(Key publicKey) {
        this(publicKey, null);
    }

    public SmsVerificationJwtVerifier(Key publicKey, Clock clock) {
        this.publicKey = publicKey;
        this.clock = clock;
    }

    public SmsVerificationClaims verify(String jwt) {
        if (jwt == null) {
            throw new BadSmsVerificationJwtException("The jwt must not be null");
        }
        try {
            JwtParser parser = Jwts.parser()
                    .setSigningKey(publicKey);
            if (clock != null) {
                parser = parser.setClock(clock);
            }
            Jws<Claims> claims = parser
                    .parseClaimsJws(jwt);
            String mobile = claims.getBody().get("mobile", String.class);
            String scope = claims.getBody().get("scope", String.class);
            return new SmsVerificationClaims(mobile, scope);
        } catch (Exception err) {
            throw new BadSmsVerificationJwtException(err.getMessage(), err);
        }
    }
}
