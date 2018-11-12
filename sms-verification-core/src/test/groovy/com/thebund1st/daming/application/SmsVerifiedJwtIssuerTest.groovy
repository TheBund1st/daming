package com.thebund1st.daming.application

import io.jsonwebtoken.Jwts
import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Paths
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec

import static com.thebund1st.daming.core.TestingMobile.aMobilePhoneNumber

class SmsVerifiedJwtIssuerTest extends Specification {

    private SmsVerifiedJwtIssuer subject = new SmsVerifiedJwtIssuer()

    def "it should generate a JWS with verified mobile phone number"() {
        given:
        def mobile = aMobilePhoneNumber()

        when:
        def actual = subject.issue(mobile)

        then:
        assert Jwts.parser().setSigningKey(get("./sms-verification-public.der"))
                .parseClaimsJws(actual).getBody().get("mobile") == mobile.value
    }

    private PublicKey get(String filename) throws Exception {

        byte[] keyBytes = Files.readAllBytes(Paths.get(filename))

        X509EncodedKeySpec spec =
                new X509EncodedKeySpec(keyBytes)
        KeyFactory kf = KeyFactory.getInstance("RSA")
        return kf.generatePublic(spec)
    }
}
