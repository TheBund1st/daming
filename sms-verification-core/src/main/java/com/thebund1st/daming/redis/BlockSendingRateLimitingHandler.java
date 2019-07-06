package com.thebund1st.daming.redis;

import com.thebund1st.daming.commands.SendSmsVerificationCodeCommand;
import com.thebund1st.daming.security.ratelimiting.Errors;
import com.thebund1st.daming.security.ratelimiting.RateLimitingHandler;
import com.thebund1st.daming.time.Clock;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;

import static java.util.concurrent.TimeUnit.SECONDS;

@Slf4j
@Order(1000)
public class BlockSendingRateLimitingHandler
        implements RateLimitingHandler<SendSmsVerificationCodeCommand> {

    private final StringRedisTemplate redisTemplate;
    private final Clock clock;
    @Setter
    private Duration expires = Duration.ofSeconds(15);
    private String keyPrefix = "sms.verification.rate.limiting.1.send.in.every.x.seconds";

    public BlockSendingRateLimitingHandler(StringRedisTemplate redisTemplate,
                                           Clock clock) {
        this.redisTemplate = redisTemplate;
        this.clock = clock;
    }

    @Override
    public void check(SendSmsVerificationCodeCommand command, Errors errors) {
        //noinspection ConstantConditions
        if (redisTemplate.hasKey(toKey(command))) {
            errors.append(String.format("Only 1 request is allowed by [%s][%s] in every %d seconds",
                    command.getMobile().getValue(),
                    command.getScope().getValue(),
                    expires.getSeconds()
            ));
            log.debug("{} is blocked due to the policy 1 request in every {}", command, expires);
        }
    }

    private String toKey(SendSmsVerificationCodeCommand command) {
        return String.format("%s.%s.%s", keyPrefix, command.getMobile().getValue(), command.getScope().getValue());
    }

    @Override
    public void count(SendSmsVerificationCodeCommand command) {
        log.debug("Attempt to block {} in the next {}", command, expires);
        // use .set(key, value, timeout, timeUnit) instead of .set(key, value, duration)
        // to be compatible with spring-data-redis 1.x
        redisTemplate.opsForValue()
                .set(toKey(command),
                        clock.now().toString(),
                        expires.getSeconds(), SECONDS);
    }
}
