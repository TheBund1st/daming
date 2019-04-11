package com.foo.bar;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;

import java.security.PublicKey;

@RequiredArgsConstructor
public class SmsVerificationJwtVerifier {

    private final PublicKey publicKey;

    public SmsVerificationClaims verify(String jwt) {
        if (jwt == null) {
            throw new BadCredentialsException("The jwt must not be null");
        }
        try {
            Jws<Claims> claims = Jwts.parser()
//                .setClock(new DefaultClock())
                    .setSigningKey(publicKey)
                    .parseClaimsJws(jwt);
            String mobile = claims.getBody().get("mobile", String.class);
            String scope = claims.getBody().get("scope", String.class);
            return new SmsVerificationClaims(mobile, scope);
        } catch (Exception err) {
            throw new BadCredentialsException(err.getMessage(), err);
        }
    }
}
