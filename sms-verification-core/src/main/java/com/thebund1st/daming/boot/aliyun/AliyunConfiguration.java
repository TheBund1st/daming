package com.thebund1st.daming.boot.aliyun;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@ConfigurationProperties(prefix = "daming.aliyun")
@Configuration
public class AliyunConfiguration {
    private String product = "Dysmsapi";
    private String domain = "dysmsapi.aliyuncs.com";
    private String regionId = "cn-hangzhou";

    @Getter
    @Setter
    private String accessKeyId;
    @Getter
    @Setter
    private String accessKeySecret;


    @ConditionalOnMissingBean(name = "acsClient")
    @SneakyThrows
    @Bean
    public DefaultAcsClient acsClient() {
        IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint(regionId, regionId, product, domain);
        return new DefaultAcsClient(profile);
    }
}
