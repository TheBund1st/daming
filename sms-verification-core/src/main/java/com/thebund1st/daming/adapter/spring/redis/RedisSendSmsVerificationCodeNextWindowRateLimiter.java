package com.thebund1st.daming.adapter.spring.redis;

import com.thebund1st.daming.application.commandhandling.interceptor.SendSmsVerificationCodeCommandHandlerInterceptor;
import com.thebund1st.daming.application.command.SendSmsVerificationCodeCommand;
import com.thebund1st.daming.application.domain.MobilePhoneNumber;
import com.thebund1st.daming.application.domain.SmsVerification;
import com.thebund1st.daming.application.domain.SmsVerificationScope;
import com.thebund1st.daming.security.ratelimiting.Errors;
import com.thebund1st.daming.security.ratelimiting.TooManyRequestsException;
import com.thebund1st.daming.time.Clock;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;

import static java.util.concurrent.TimeUnit.SECONDS;

@Slf4j
public class RedisSendSmsVerificationCodeNextWindowRateLimiter
        implements SendSmsVerificationCodeCommandHandlerInterceptor {

    private final StringRedisTemplate redisTemplate;
    private final Clock clock;
    @Setter
    private Duration expires = Duration.ofSeconds(15);
    private String keyPrefix = "sms.verification.rate.limiting.1.send.in.every.x.seconds";

    public RedisSendSmsVerificationCodeNextWindowRateLimiter(StringRedisTemplate redisTemplate,
                                                             Clock clock) {
        this.redisTemplate = redisTemplate;
        this.clock = clock;
    }

    //    @Override
    @Deprecated
    public void check(SendSmsVerificationCodeCommand command, Errors errors) {
        //noinspection ConstantConditions
        if (itShouldBlockThe(command)) {
            errors.append(String.format("Only 1 request is allowed by [%s][%s] in every %d seconds",
                    command.getMobile().getValue(),
                    command.getScope().getValue(),
                    expires.getSeconds()
            ));
            logBlocked(command);
        }
    }

    private Boolean itShouldBlockThe(SendSmsVerificationCodeCommand command) {
        return redisTemplate.hasKey(toKey(command));
    }

    private String toKey(SendSmsVerificationCodeCommand command) {
        return toKey(command.getMobile(), command.getScope());
    }

    private String toKey(MobilePhoneNumber mobile, SmsVerificationScope scope) {
        return String.format("%s.%s.%s", keyPrefix, mobile.getValue(), scope.getValue());
    }

    //    @Override
    @Deprecated
    public void count(SendSmsVerificationCodeCommand command) {
        registerBlockerWith(command.getMobile(), command.getScope());
    }

    private void registerBlockerWith(MobilePhoneNumber mobile, SmsVerificationScope scope) {
        log.debug("Attempt to block {}:{} in the next {}", mobile, scope, expires);
        // use .set(key, value, timeout, timeUnit) instead of .set(key, value, duration)
        // to be compatible with spring-data-redis 1.x
        redisTemplate.opsForValue()
                .set(toKey(mobile, scope),
                        clock.now().toString(),
                        expires.getSeconds(), SECONDS);
    }

    @Override
    public void preHandle(SendSmsVerificationCodeCommand command) {
        boolean blocked = itShouldBlockThe(command);
        if (blocked) {
            logBlocked(command);
            throw new TooManyRequestsException(blockedMessage(command));
        }
    }

    private void logBlocked(SendSmsVerificationCodeCommand command) {
        log.debug(blockedMessage(command));
    }

    private String blockedMessage(SendSmsVerificationCodeCommand command) {
        return String.format("%s is blocked due to the policy 1 request in every %s seconds", command, expires);
    }

    @Override
    public void postHandle(SendSmsVerificationCodeCommand command, SmsVerification smsVerification) {
        registerBlockerWith(smsVerification.getMobile(), smsVerification.getScope());
    }
}
