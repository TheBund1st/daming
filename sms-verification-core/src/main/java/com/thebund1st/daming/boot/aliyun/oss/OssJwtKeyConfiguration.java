package com.thebund1st.daming.boot.aliyun.oss;

import com.aliyun.oss.OSSClient;
import com.thebund1st.daming.aliyun.oss.OssKeyLoader;
import com.thebund1st.daming.boot.aliyun.AliyunConfiguration;
import com.thebund1st.daming.boot.aliyun.AliyunCredentialsProperties;
import com.thebund1st.daming.jwt.key.JwtKeyLoader;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@RequiredArgsConstructor
@Import(AliyunConfiguration.class)
@Configuration
@ConfigurationProperties(prefix = "daming.jwt.key.aliyun.oss")
@ConditionalOnProperty(prefix = "daming.jwt.key", name = "provider", havingValue = "aliyun.oss")
public class OssJwtKeyConfiguration {

    private final AliyunCredentialsProperties aliyunCredentialsProperties;

    @Setter
    private String endpoint;

    @Setter
    private String bucketName;

    @Setter
    private String objectName;


    @ConditionalOnMissingBean(name = "daming.jwt.key.ossClient")
    @Bean(name = "daming.jwt.key.ossClient")
    public OSSClient ossClient() {
        return new OSSClient(endpoint,
                aliyunCredentialsProperties.getAccessKeyId(), aliyunCredentialsProperties.getAccessKeySecret());
    }

    @Bean
    public OssKeyLoader ossJwtKeyLoader(@Qualifier("daming.jwt.key.ossClient") OSSClient ossClient) {
        OssKeyLoader loader = new OssKeyLoader(ossClient);
        loader.setBucketName(bucketName);
        loader.setObjectName(objectName);
        return loader;
    }

}
