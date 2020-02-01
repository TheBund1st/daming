package com.thebund1st.daming.boot.adapter.aliyun.aliyun;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class AliyunConfiguration {
    @ConfigurationProperties(prefix = "daming.aliyun")
    @Bean
    public AliyunCredentialsProperties aliyunCredentialsProperties() {
        return new AliyunCredentialsProperties();
    }

}
