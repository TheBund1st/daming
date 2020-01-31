package com.thebund1st.daming.boot.application.interceptor;

import com.thebund1st.daming.application.interceptor.SendSmsVerificationCodeCommandHandlerInterceptorAspect;
import com.thebund1st.daming.redis.BlockSendingRateLimitingHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class CommandHandlerInterceptorConfiguration {

    @Autowired
    private BlockSendingRateLimitingHandler blockSendingRateLimitingHandler;

    @Bean
    public SendSmsVerificationCodeCommandHandlerInterceptorAspect oneSmsVerificationEveryXSeconds() {
        SendSmsVerificationCodeCommandHandlerInterceptorAspect aspect =
                new SendSmsVerificationCodeCommandHandlerInterceptorAspect();
        aspect.setCommandHandlerInterceptor(blockSendingRateLimitingHandler);
        return aspect;
    }


}
