package com.thebund1st.daming.boot.adapter.redis.spring;

import com.thebund1st.daming.adapter.redis.spirng.RedisSendSmsVerificationCodeNextWindowRateLimiter;
import com.thebund1st.daming.application.commandhandling.interceptor.SendSmsVerificationCodeCommandHandlerInterceptorAspect;
import com.thebund1st.daming.boot.core.SmsVerificationCodeProperties;
import com.thebund1st.daming.time.Clock;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

@ConditionalOnProperty(prefix = "daming.sms.verification.code.block", name = "enabled",
        havingValue = "true", matchIfMissing = true)
@Configuration
public class RedisSendSmsVerificationCodeNextWindowRateLimiterConfiguration {

    @Bean
    public RedisSendSmsVerificationCodeNextWindowRateLimiter redisBlockSendingInNextXSecondsRateLimiter(
            StringRedisTemplate redisTemplate, Clock clock, SmsVerificationCodeProperties properties) {
        RedisSendSmsVerificationCodeNextWindowRateLimiter handler =
                new RedisSendSmsVerificationCodeNextWindowRateLimiter(redisTemplate, clock);
        handler.setExpires(properties.getBlock());
        return handler;
    }

    @Bean
    public SendSmsVerificationCodeCommandHandlerInterceptorAspect oneSmsVerificationEveryXSeconds(
            RedisSendSmsVerificationCodeNextWindowRateLimiter redisSendSmsVerificationCodeNextWindowRateLimiter) {
        SendSmsVerificationCodeCommandHandlerInterceptorAspect aspect =
                new SendSmsVerificationCodeCommandHandlerInterceptorAspect();
        aspect.setCommandHandlerInterceptor(redisSendSmsVerificationCodeNextWindowRateLimiter);
        aspect.setOrder(1000);
        return aspect;
    }
}
