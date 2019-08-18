package com.thebund1st.daming.boot.http;

import com.thebund1st.daming.web.rest.DefaultResponseEntityExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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

    @ConditionalOnProperty(prefix = "daming.http.error.attributes",
            name = "default",
            havingValue = "true",
            matchIfMissing = true)
    @Bean
    public DefaultResponseEntityExceptionHandler defaultExceptionHandler() {
        return new DefaultResponseEntityExceptionHandler() {
        };
    }
}
