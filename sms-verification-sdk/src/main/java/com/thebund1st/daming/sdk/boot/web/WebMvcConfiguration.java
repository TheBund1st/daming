package com.thebund1st.daming.sdk.boot.web;

import com.thebund1st.daming.sdk.jwt.SmsVerificationJwtVerifier;
import com.thebund1st.daming.sdk.web.handler.SmsVerificationClaimsHandlerMethodArgumentResolver;
import com.thebund1st.daming.sdk.web.handler.SmsVerificationRequiredHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private SmsVerificationJwtVerifier smsVerificationJwtVerifier;

    @Autowired
    private WebProperties webProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(
                new SmsVerificationRequiredHandlerInterceptor(smsVerificationJwtVerifier,
                        webProperties.getJwtHeaderName())
        );
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new SmsVerificationClaimsHandlerMethodArgumentResolver());
    }

}
