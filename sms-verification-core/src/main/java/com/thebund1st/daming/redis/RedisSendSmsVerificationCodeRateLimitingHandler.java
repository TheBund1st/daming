package com.thebund1st.daming.redis;

import com.thebund1st.daming.commands.SendSmsVerificationCodeCommand;
import com.thebund1st.daming.security.ratelimiting.Errors;
import com.thebund1st.daming.security.ratelimiting.RateLimitingHandler;
import com.thebund1st.daming.time.Clock;
import lombok.Setter;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;

public class RedisSendSmsVerificationCodeRateLimitingHandler
        implements RateLimitingHandler<SendSmsVerificationCodeCommand> {

    private final StringRedisTemplate redisTemplate;
    private final Clock clock;
    @Setter
    private Duration expires = Duration.ofSeconds(15);
    private String keyPrefix = "sms.verification.rate.limiting.1.send.in.every.x.seconds";

    public RedisSendSmsVerificationCodeRateLimitingHandler(StringRedisTemplate redisTemplate,
                                                           Clock clock) {
        this.redisTemplate = redisTemplate;
        this.clock = clock;
    }

    @Override
    public void check(SendSmsVerificationCodeCommand command, Errors errors) {
        //noinspection ConstantConditions
        if (redisTemplate.hasKey(toKey(command))) {
            errors.append(String.format("Too many requests by [%s][%s] in %d seconds",
                    command.getMobile().getValue(),
                    command.getScope().getValue(),
                    expires.getSeconds()
            ));
        }
    }

    private String toKey(SendSmsVerificationCodeCommand command) {
        return String.format("%s.%s.%s", keyPrefix, command.getMobile().getValue(), command.getScope().getValue());
    }

    @Override
    public void count(SendSmsVerificationCodeCommand command) {
        redisTemplate.opsForValue()
                .set(toKey(command),
                        clock.now().toString(),
                        expires);
    }
}
