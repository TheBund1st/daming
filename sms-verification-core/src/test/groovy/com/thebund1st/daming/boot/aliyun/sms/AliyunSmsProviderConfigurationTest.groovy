package com.thebund1st.daming.boot.aliyun.sms


import com.aliyuncs.IAcsClient
import com.thebund1st.daming.aliyun.sms.AliyunSmsVerificationSender
import com.thebund1st.daming.boot.AbstractAutoConfigurationTest
import com.thebund1st.daming.boot.aliyun.AliyunConfiguration
import com.thebund1st.daming.boot.aliyun.CustomizedAcsClient
import foo.bar.WithCustomizedAcsClient

class AliyunSmsProviderConfigurationTest extends AbstractAutoConfigurationTest {

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

            def aliyunSmsConfiguration = it.getBean(AliyunConfiguration)
            assert aliyunSmsConfiguration.accessKeyId == 'foo'
            assert aliyunSmsConfiguration.accessKeySecret == 'bar'
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
                .withPropertyValues("daming.sms.provider=aliyun")

        then:
        contextRunner.run { it ->
            AliyunSmsVerificationSender actual = it.getBean(AliyunSmsVerificationSender)
            assert actual != null
        }
    }
}
