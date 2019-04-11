package com.thebund1st.daming.sdk.boot.jwt.key;

import com.thebund1st.daming.jwt.key.KeyBytesLoader;
import com.thebund1st.daming.jwt.key.file.FileKeyLoader;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class FileJwtKeyConfiguration {

    @Value("${daming.sdk.jwt.key.file.location}")
    @Setter
    private String location;

    @Bean(name = "smsVerificationJwtParsingKeyBytesLoader")
    public KeyBytesLoader fileKeyBytesLoader() {
        FileKeyLoader loader = new FileKeyLoader();
        loader.setPrivateKeyFileLocation(location);
        return loader;
    }


}
