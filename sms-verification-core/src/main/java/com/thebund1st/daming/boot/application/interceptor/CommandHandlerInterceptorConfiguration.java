package com.thebund1st.daming.boot.application.interceptor;

import com.thebund1st.daming.application.commandhandling.interceptor.SendSmsVerificationCodeCommandHandlerInterceptorAspect;
import com.thebund1st.daming.adapter.redis.RedisSendSmsVerificationCodeNextWindowRateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class CommandHandlerInterceptorConfiguration {

    @Autowired
    private RedisSendSmsVerificationCodeNextWindowRateLimiter redisSendSmsVerificationCodeNextWindowRateLimiter;

    @Bean
    public SendSmsVerificationCodeCommandHandlerInterceptorAspect oneSmsVerificationEveryXSeconds() {
        SendSmsVerificationCodeCommandHandlerInterceptorAspect aspect =
                new SendSmsVerificationCodeCommandHandlerInterceptorAspect();
        aspect.setCommandHandlerInterceptor(redisSendSmsVerificationCodeNextWindowRateLimiter);
        aspect.setOrder(1000);
        return aspect;
    }

}
