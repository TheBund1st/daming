package com.thebund1st.daming.sdk.boot.jwt

import com.thebund1st.daming.jwt.key.JwtPublicKeyLoader
import com.thebund1st.daming.jwt.key.KeyBytesLoader
import com.thebund1st.daming.jwt.key.file.FileKeyLoader
import com.thebund1st.daming.sdk.boot.AbstractAutoConfigurationTest
import com.thebund1st.daming.sdk.jwt.SmsVerificationJwtVerifier

class JwtConfigurationTest extends AbstractAutoConfigurationTest {

    def "it should provide a FileJwtKeyLoader"() {

        when:
        when:
        def then = this.contextRunner
                .withPropertyValues("daming.sdk.jwt.key.file.location=../sms-verification-core/sms-verification-public.der")

        then:
        then.run { it ->
            KeyBytesLoader actual = it.getBean("smsVerificationJwtParsingKeyBytesLoader")
            assert actual instanceof FileKeyLoader
            assert actual.getBytes() != null
        }
    }

    def "it should provide a JwtPublicKeyLoader"() {

        when:
        when:
        def then = this.contextRunner
                .withPropertyValues("daming.sdk.jwt.key.file.location=../sms-verification-core/sms-verification-public.der")

        then:
        then.run { it ->
            JwtPublicKeyLoader actual = it.getBean("smsVerificationJwtParsingKeyLoader")
            assert actual instanceof JwtPublicKeyLoader
        }
    }

    def "it should provide a SmsVerificationJwtVerifier"() {

        when:
        when:
        def then = this.contextRunner
                .withPropertyValues("daming.sdk.jwt.key.file.location=../sms-verification-core/sms-verification-public.der")

        then:
        then.run { it ->
            SmsVerificationJwtVerifier actual = it.getBean(SmsVerificationJwtVerifier)
            assert actual != null
        }
    }

}
