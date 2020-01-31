package com.thebund1st.daming.adapter.spring.redis;

import com.thebund1st.daming.application.domain.MobilePhoneNumber;
import com.thebund1st.daming.application.domain.SmsVerificationScope;
import com.thebund1st.daming.application.domain.DomainEventPublisher;
import com.thebund1st.daming.application.event.SmsVerificationCodeMismatchEvent;
import com.thebund1st.daming.application.event.SmsVerificationCodeVerifiedEvent;
import com.thebund1st.daming.application.event.TooManyFailureSmsVerificationAttemptsEvent;
import com.thebund1st.daming.redis.DeleteFromRedis;
import com.thebund1st.daming.time.Clock;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.joining;

@Slf4j
@RequiredArgsConstructor
public class RedisSmsVerificationCodeMismatchEventHandler {

    private static final String DELIMITER = ",";
    private final StringRedisTemplate redisTemplate;
    private final DeleteFromRedis deleteFromRedis;
    private final DomainEventPublisher domainEventPublisher;

    private final Clock clock;

    @Setter
    private int threshold = 5;

    public void on(SmsVerificationCodeMismatchEvent event) {
        String key = toKey(event.getMobile(), event.getScope());
        List<Object> attempts = redisTemplate.executePipelined((RedisCallback<Long>) connection -> {
            StringRedisConnection conn = (StringRedisConnection) connection;
            conn.sAdd(key, event.toString());
            long expires = Duration.between(event.getWhen(), event.getExpiresAt()).getSeconds();
            conn.expire(key, expires);
            conn.sCard(key);
            return null;
        });
        log.debug("Got Redis pipeline {}",
                attempts.stream().map(Object::toString).collect(joining(DELIMITER)));
        if (attempts.size() == 3) {
            if (toAttempts(attempts) >= threshold) {
                log.info("Too many failure verification attempts for {} {}", event.getMobile(), event.getScope());
                remove(key);
                domainEventPublisher.publish(new TooManyFailureSmsVerificationAttemptsEvent(UUID.randomUUID().toString(),
                        clock.now(),
                        event.getMobile(),
                        event.getScope()));
            }
        }
    }

    public void on(SmsVerificationCodeVerifiedEvent event) {
        String key = toKey(event.getMobile(), event.getScope());
        remove(key);
    }

    private void remove(String key) {
        deleteFromRedis.delete(key);
    }

    private Long toAttempts(List<Object> attempts) {
        return (Long) attempts.get(attempts.size() - 1);
    }

    private String toKey(MobilePhoneNumber mobile, SmsVerificationScope scope) {
        return String.format("sms.verification.code.mismatch.%s.%s",
                mobile.getValue(), scope.getValue());
    }
}
