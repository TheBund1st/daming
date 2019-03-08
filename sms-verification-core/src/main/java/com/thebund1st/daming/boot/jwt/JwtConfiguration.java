package com.thebund1st.daming.boot.jwt;

import com.thebund1st.daming.boot.aliyun.oss.OssJwtKeyConfiguration;
import com.thebund1st.daming.boot.jwt.key.file.FileJwtKeyConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@RequiredArgsConstructor
@Configuration
@Import({FileJwtKeyConfiguration.class, OssJwtKeyConfiguration.class})
public class JwtConfiguration {

    @ConfigurationProperties(prefix = "daming.jwt")
    @Bean
    public JwtProperties jwtProperties() {
        return new JwtProperties();
    }
}
