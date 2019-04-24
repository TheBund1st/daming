package com.thebund1st.daming.sdk.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

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

    /**
     * @param jwt, JWT issued by daming.
     * @return claims that contains verified mobile and scope.
     * @see #verify(String, String)
     */
    @Deprecated
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

    public SmsVerificationClaims verify(String jwt, String scope) {
        SmsVerificationClaims claims = verify(jwt);
        if (!claims.scopeMatches(scope)) {
            throw new SmsVerificationScopeMismatchException(claims, scope);
        }
        return claims;
    }
}
