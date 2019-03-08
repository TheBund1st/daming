package com.thebund1st.daming.boot.jwt.key.file;

import com.thebund1st.daming.jwt.key.JwtKeyLoader;
import com.thebund1st.daming.jwt.key.KeyBytesLoader;
import com.thebund1st.daming.jwt.key.file.FileKeyLoader;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "daming.jwt.key.file")
@ConditionalOnProperty(prefix = "daming.jwt.key", name = "provider", havingValue = "file", matchIfMissing = true)
public class FileJwtKeyConfiguration {

    @Setter
    private String location;

    @ConfigurationProperties(prefix = "daming.jwt")
    @Bean
    public DeprecatedFileJwtKeyProperties deprecatedFileJwtKeyProperties() {
        return new DeprecatedFileJwtKeyProperties();
    }

    @Bean
    public KeyBytesLoader fileKeyBytesLoader() {
        FileKeyLoader loader = new FileKeyLoader();
        loader.setPrivateKeyFileLocation(getLocation());
        return loader;
    }

    private String getLocation() {
        // to adapt deprecated properties
        return !StringUtils.isEmpty(location) ? location : deprecatedFileJwtKeyProperties().getPrivateKeyFileLocation();
    }

}
