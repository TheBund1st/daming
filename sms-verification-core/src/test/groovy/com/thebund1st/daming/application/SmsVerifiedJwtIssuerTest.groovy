package com.thebund1st.daming.application

import com.thebund1st.daming.jwt.SmsVerifiedJwtIssuer
import com.thebund1st.daming.jwt.key.JwtPrivateKeyLoader
import com.thebund1st.daming.jwt.key.file.FileKeyLoader
import com.thebund1st.daming.time.Clock
import io.jsonwebtoken.Jwts
import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Paths
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec

import static com.thebund1st.daming.application.domain.TestingMobile.aMobilePhoneNumber
import static com.thebund1st.daming.application.domain.TestingSmsVerificationScope.anyScope

class SmsVerifiedJwtIssuerTest extends Specification {

    private Clock clock = new Clock()
    private SmsVerifiedJwtIssuer subject = new SmsVerifiedJwtIssuer(clock,
            new JwtPrivateKeyLoader(new FileKeyLoader()).getKey())

    def "it should generate a JWS with verified mobile phone number"() {
        given:
        def mobile = aMobilePhoneNumber()
        def scope = anyScope()

        when:
        def actual = subject.issue(mobile, scope)

        then:
        def claims = Jwts.parser().setClock({
            return Date.from(clock.now().toInstant())
        }).setSigningKey(get("./sms-verification-public.der"))
                .parseClaimsJws(actual).getBody()

        assert claims.get("mobile") == mobile.value
        assert claims.get("scope") == scope.value
    }

    private PublicKey get(String filename) throws Exception {

        byte[] keyBytes = Files.readAllBytes(Paths.get(filename))

        X509EncodedKeySpec spec =
                new X509EncodedKeySpec(keyBytes)
        KeyFactory kf = KeyFactory.getInstance("RSA")
        return kf.generatePublic(spec)
    }
}
