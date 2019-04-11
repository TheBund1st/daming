package com.thebund1st.daming.boot.jwt.key.file

import com.thebund1st.daming.boot.AbstractAutoConfigurationTest
import com.thebund1st.daming.jwt.key.KeyBytesLoader
import com.thebund1st.daming.jwt.key.file.FileKeyLoader

class FileJwtKeyConfigurationTest extends AbstractAutoConfigurationTest {

    def "it should provide a FileJwtKeyLoader given key provider is not explicitly set"() {

        when:
        def contextRunner = this.contextRunner
                .withPropertyValues("daming.jwt.key.file.location=./sms-verification-private.der")

        then:
        contextRunner.run { it ->
            KeyBytesLoader actual = it.getBean("smsVerificationJwtSigningKeyBytesLoader")
            assert actual instanceof FileKeyLoader
            assert actual.getBytes() != null
        }
    }

    def "it should provide a FileJwtKeyLoader given key provider is 'file'"() {

        when:
        def contextRunner = this.contextRunner
                .withPropertyValues("daming.jwt.key.provider=file")
                .withPropertyValues("daming.jwt.key.file.location=./sms-verification-private.der")

        then:
        contextRunner.run { it ->
            KeyBytesLoader actual = it.getBean("smsVerificationJwtSigningKeyBytesLoader")
            assert actual instanceof FileKeyLoader
            assert actual.getBytes() != null
        }
    }

    def "it should provide a FileJwtKeyLoader given key provider is 'file' and deprecated location property value"() {

        when:
        def contextRunner = this.contextRunner
                .withPropertyValues("daming.jwt.privateKeyFileLocation=./sms-verification-private.der")

        then:
        contextRunner.run { it ->
            KeyBytesLoader actual = it.getBean(KeyBytesLoader)
            assert actual instanceof FileKeyLoader
            assert actual.getBytes() != null
        }
    }

    def "it should provide a FileJwtKeyLoader given key provider is 'file' and prefer new location property"() {

        when:
        def contextRunner = this.contextRunner
                .withPropertyValues("daming.jwt.key.file.location=./sms-verification-private.der")
                .withPropertyValues("daming.jwt.privateKeyFileLocation=./you-dont-find-the-file.der")

        then:
        contextRunner.run { it ->
            KeyBytesLoader actual = it.getBean(KeyBytesLoader)
            assert actual instanceof FileKeyLoader
            assert actual.getBytes() != null
        }
    }
}
