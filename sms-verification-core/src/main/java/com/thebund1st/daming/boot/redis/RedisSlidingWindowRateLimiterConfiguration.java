package com.thebund1st.daming.boot.redis;

import com.thebund1st.daming.application.commandhandling.interceptor.SendSmsVerificationCodeCommandHandlerInterceptorAspect;
import com.thebund1st.daming.boot.security.SlidingWindowProperties;
import com.thebund1st.daming.adapter.ratelimitj.RateLimitJSendSmsVerificationCodeSlidingWindowRateLimiter;
import es.moki.ratelimitj.core.limiter.request.RequestLimitRule;
import es.moki.ratelimitj.core.limiter.request.RequestRateLimiter;
import es.moki.ratelimitj.core.limiter.request.RequestRateLimiterFactory;
import es.moki.ratelimitj.redis.request.RedisRateLimiterFactory;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.resource.ClientResources;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Set;

@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "daming.sms.verification.code.sliding.window", name = "enabled",
        havingValue = "true")
public class RedisSlidingWindowRateLimiterConfiguration {

    @Bean
    @ConditionalOnMissingBean(value = RequestRateLimiterFactory.class)
    public RedisRateLimiterFactory redisRateLimiterFactory(ClientResources redisClientResources, RedisProperties redisProperties) {
        RedisURI.Builder builder = RedisURI.builder()
                .withHost(redisProperties.getHost())
                .withPort(redisProperties.getPort());
        if (!StringUtils.isEmpty(redisProperties.getPassword())) {
            builder = builder.withPassword(redisProperties.getPassword());
        }
        if (null != redisProperties.getTimeout()) {
            builder = builder.withTimeout(redisProperties.getTimeout());
        }
        builder = builder.withDatabase(redisProperties.getDatabase())
                .withSsl(redisProperties.isSsl());
        RedisURI redisUri = builder.build();
        return new RedisRateLimiterFactory(RedisClient.create(redisClientResources, redisUri));
    }

    @Bean
    public RateLimitJSendSmsVerificationCodeSlidingWindowRateLimiter redisSlidingWindowByMobileRateLimiter(
            RequestRateLimiterFactory redisRateLimiterFactory, SlidingWindowProperties slidingWindowProperties) {
        Set<RequestLimitRule> rules = Collections.singleton(
                RequestLimitRule.of(slidingWindowProperties.getDuration(), slidingWindowProperties.getLimit()));
        RequestRateLimiter requestRateLimiter = redisRateLimiterFactory.getInstance(rules);
        return new RateLimitJSendSmsVerificationCodeSlidingWindowRateLimiter(requestRateLimiter);
    }


    @Bean
    public SendSmsVerificationCodeCommandHandlerInterceptorAspect slidingWindowRateLimiterForSendSmsVerificationCode(
            RateLimitJSendSmsVerificationCodeSlidingWindowRateLimiter rateLimitJSendSmsVerificationCodeSlidingWindowRateLimiter) {
        SendSmsVerificationCodeCommandHandlerInterceptorAspect aspect =
                new SendSmsVerificationCodeCommandHandlerInterceptorAspect();
        aspect.setCommandHandlerInterceptor(rateLimitJSendSmsVerificationCodeSlidingWindowRateLimiter);
        aspect.setOrder(1001);
        return aspect;
    }
}
