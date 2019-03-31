package com.thebund1st.daming.boot.http;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EndpointsConfiguration {

    @ConfigurationProperties(prefix = "daming.http.endpoints")
    @Bean
    public EndpointProperties endpointProperties() {
        return new EndpointProperties();
    }

}
