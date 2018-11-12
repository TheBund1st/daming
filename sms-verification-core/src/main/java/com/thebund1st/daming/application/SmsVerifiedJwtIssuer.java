package com.thebund1st.daming.application;

import com.thebund1st.daming.core.MobilePhoneNumber;
import io.jsonwebtoken.Jwts;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class SmsVerifiedJwtIssuer {

    @Setter
    private int expiresInSeconds = 900;
    @Setter
    private String privateKeyFileLocation = "./sms-verification-private.der";

    public String issue(MobilePhoneNumber mobilePhoneNumber) {
        return Jwts.builder().setSubject("verifiedMobilePhoneNumber")
                .claim("mobile", mobilePhoneNumber.getValue())
                .signWith(get(privateKeyFileLocation))
                .setExpiration(Date.from(now().plusSeconds(expiresInSeconds).toInstant()))
                .compact();
    }

    private ZonedDateTime now() {
        return LocalDateTime.now().atZone(ZoneId.of("Asia/Shanghai"));
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
