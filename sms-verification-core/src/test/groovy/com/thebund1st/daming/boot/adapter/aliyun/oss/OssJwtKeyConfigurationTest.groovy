package com.thebund1st.daming.boot.adapter.aliyun.oss

import com.thebund1st.daming.adapter.aliyun.oss.OssKeyLoader
import com.thebund1st.daming.boot.AbstractAutoConfigurationTest
import com.thebund1st.daming.jwt.key.KeyBytesLoader
import foo.bar.WithDumbSmsVerifiedJwtIssuer

class OssJwtKeyConfigurationTest extends AbstractAutoConfigurationTest {

    def "it should provide a OssJwtKeyLoader given key provider is 'aliyun.oss'"() {

        when:
        def contextRunner = this.contextRunner
                .withPropertyValues("daming.aliyun.accessKeyId=foo")
                .withPropertyValues("daming.aliyun.accessKeySecret=keepitsecret")
                .withPropertyValues("daming.jwt.key.provider=aliyun.oss")
                .withPropertyValues("daming.jwt.key.aliyun.oss.endpoint=https://oss-cn-shenzhen.aliyuncs.com")
                .withPropertyValues("daming.jwt.key.aliyun.oss.bucketName=keepitsecret")
                .withPropertyValues("daming.jwt.key.aliyun.oss.objectName=sms-verification-private.der")
                .withUserConfiguration(WithDumbSmsVerifiedJwtIssuer) // to skip fetching the key

        then:
        contextRunner.run { it ->
            KeyBytesLoader actual = it.getBean("smsVerificationJwtSigningKeyBytesLoader")
            assert actual instanceof OssKeyLoader
        }
    }
}
