package com.thebund1st.daming.boot.aliyun.sms


import com.aliyuncs.IAcsClient
import com.thebund1st.daming.aliyun.sms.AliyunSmsVerificationCodeSender
import com.thebund1st.daming.boot.AbstractAutoConfigurationTest
import com.thebund1st.daming.boot.aliyun.AliyunConfiguration
import com.thebund1st.daming.boot.aliyun.AliyunCredentialsProperties
import com.thebund1st.daming.boot.aliyun.CustomizedAcsClient
import foo.bar.WithCustomizedAcsClient

class AliyunSmsConfigurationTest extends AbstractAutoConfigurationTest {

    def "it should provide a default IAcsClient instance given no customized one"() {

        when:
        def contextRunner = this.contextRunner
                .withPropertyValues("daming.sms.provider=aliyun",
                "daming.aliyun.accessKeyId=foo",
                "daming.aliyun.accessKeySecret=bar")

        then:
        contextRunner.run { it ->
            IAcsClient actual = it.getBean(IAcsClient)
            assert actual != null

            def properties = it.getBean(AliyunCredentialsProperties)
            assert properties.accessKeyId == 'foo'
            assert properties.accessKeySecret == 'bar'
        }
    }

    def "it should skip providing a default IAcsClient instance given customized one exists"() {

        when:
        def contextRunner = this.contextRunner
                .withPropertyValues("daming.sms.provider=aliyun")
                .withUserConfiguration(WithCustomizedAcsClient)

        then:
        contextRunner.run { it ->
            IAcsClient actual = it.getBean(IAcsClient)
            assert actual instanceof CustomizedAcsClient
        }
    }

    def "it should provide a AliyunSmsVerificationSender instance given sms provider is aliyun"() {

        when:
        def contextRunner = this.contextRunner
                .withPropertyValues("daming.sms.provider=aliyun",
                "daming.aliyun.sms.signature=foo",
                "daming.aliyun.sms.templateCode=bar")

        then:
        contextRunner.run { it ->
            AliyunSmsVerificationCodeSender actual = it.getBean(AliyunSmsVerificationCodeSender)
            assert actual != null
            assert actual.getSignature() == "foo"
            assert actual.getTemplateCode() == "bar"
        }
    }
}
