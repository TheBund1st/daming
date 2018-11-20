package com.thebund1st.daming.boot.aliyun.sms;

import com.aliyuncs.IAcsClient;
import com.thebund1st.daming.aliyun.sms.AliyunSmsVerificationSender;
import com.thebund1st.daming.boot.aliyun.AliyunConfiguration;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
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
public class AliyunSmsConfiguration {
    @Setter
    private String signature;
    @Setter
    private String templateCode;

    @Bean
    public AliyunSmsVerificationSender aliyunSmsVerificationSender(IAcsClient acsClient) {
        AliyunSmsVerificationSender sender = new AliyunSmsVerificationSender(acsClient);
        sender.setSignature(signature);
        sender.setTemplateCode(templateCode);
        return sender;
    }
}
