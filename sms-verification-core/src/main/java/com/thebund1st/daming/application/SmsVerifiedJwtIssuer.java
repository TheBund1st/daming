package com.thebund1st.daming.application;

import com.thebund1st.daming.core.MobilePhoneNumber;
import com.thebund1st.daming.time.Clock;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class SmsVerifiedJwtIssuer {
    // make it configurable
    @Setter
    private int expiresInSeconds = 900;
    // make it configurable
    @Setter
    private String privateKeyFileLocation = "./sms-verification-private.der";

    private final Clock clock;

    public String issue(MobilePhoneNumber mobilePhoneNumber) {
        return Jwts.builder().setSubject("verifiedMobilePhoneNumber")
                .claim("mobile", mobilePhoneNumber.getValue())
                .signWith(get(privateKeyFileLocation))
                .setExpiration(Date.from(clock.now().plusSeconds(expiresInSeconds).toInstant()))
                .compact();
    }

    @SneakyThrows
    private PrivateKey get(String filename) {

        byte[] keyBytes = Files.readAllBytes(Paths.get(filename));

        PKCS8EncodedKeySpec spec =
                new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }
}
