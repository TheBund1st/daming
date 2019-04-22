package com.foo.bar;

import com.thebund1st.daming.sdk.jwt.SmsVerificationJwtVerifier;
import com.thebund1st.daming.sdk.web.handler.BadSmsVerificationJwtExceptionHandler;
import com.thebund1st.daming.sdk.web.handler.SmsVerificationRequiredHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private SmsVerificationJwtVerifier smsVerificationJwtVerifier;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SmsVerificationRequiredHandlerInterceptor(smsVerificationJwtVerifier))
                .addPathPatterns("/very/important/operation")
                .addPathPatterns("/very/very/important/operation");
    }

    @Bean
    public BadSmsVerificationJwtExceptionHandler badSmsVerificationJwtExceptionHandler() {
        return new BadSmsVerificationJwtExceptionHandler();
    }
}
