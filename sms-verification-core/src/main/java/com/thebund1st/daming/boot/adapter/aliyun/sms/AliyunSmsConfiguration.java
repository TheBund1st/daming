package com.thebund1st.daming.boot.adapter.aliyun.sms;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.thebund1st.daming.adapter.aliyun.sms.AliyunSmsVerificationCodeSender;
import com.thebund1st.daming.boot.adapter.aliyun.aliyun.AliyunConfiguration;
import com.thebund1st.daming.boot.adapter.aliyun.aliyun.AliyunCredentialsProperties;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Slf4j
@ConfigurationProperties(prefix = "daming.aliyun.sms")
@ConditionalOnProperty(prefix = "daming.sms", name = "provider", havingValue = "aliyun")
@Import(AliyunConfiguration.class)
@Configuration
@RequiredArgsConstructor
public class AliyunSmsConfiguration {
    private final AliyunCredentialsProperties aliyunCredentialsProperties;

    @Setter
    private String signature;
    @Setter
    private String templateCode;

    private String product = "Dysmsapi";
    private String domain = "dysmsapi.aliyuncs.com";
    private String regionId = "cn-hangzhou";

    @ConditionalOnMissingBean(name = "acsClient")
    @SneakyThrows
    @Bean
    public DefaultAcsClient acsClient() {
        IClientProfile profile = DefaultProfile.getProfile(regionId,
                aliyunCredentialsProperties.getAccessKeyId(), aliyunCredentialsProperties.getAccessKeySecret());
        DefaultProfile.addEndpoint(regionId, regionId, product, domain);
        return new DefaultAcsClient(profile);
    }

    @Bean
    public AliyunSmsVerificationCodeSender aliyunSmsVerificationSender(IAcsClient acsClient) {
        AliyunSmsVerificationCodeSender sender = new AliyunSmsVerificationCodeSender(acsClient);
        sender.setSignature(signature);
        sender.setTemplateCode(templateCode);
        return sender;
    }
}
