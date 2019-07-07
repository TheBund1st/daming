package com.thebund1st.daming.redis;

import com.thebund1st.daming.core.MobilePhoneNumber;
import com.thebund1st.daming.core.SmsVerification;
import com.thebund1st.daming.core.SmsVerificationRepository;
import com.thebund1st.daming.core.SmsVerificationScope;
import com.thebund1st.daming.core.exceptions.MobileIsNotUnderVerificationException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

@Slf4j
@Setter
public class RedisSmsVerificationRepository implements SmsVerificationRepository {

    private final RedisTemplate<String, SmsVerification> redisTemplate;
    private final DeleteFromRedis deleteFromRedis;
    private String keyPrefix = "sms.verification";

    public RedisSmsVerificationRepository(RedisTemplate<String, SmsVerification> redisTemplate,
                                          DeleteFromRedis deleteFromRedis) {
        this.redisTemplate = redisTemplate;
        this.deleteFromRedis = deleteFromRedis;
    }

    @Override
    public void store(SmsVerification smsVerification) {
        // use .set(key, value, timeout, timeUnit) instead of .set(key, value, duration)
        // to be compatible with spring-data-redis 1.x
        redisTemplate.opsForValue()
                .set(toKey(smsVerification.getMobile(), smsVerification.getScope()),
                        smsVerification,
                        smsVerification.getExpires().getSeconds(), SECONDS);
    }

    private String toKey(MobilePhoneNumber mobile, SmsVerificationScope scope) {
        return String.format("%s.%s.%s", keyPrefix, mobile.getValue(), scope.getValue());
    }

    @Override
    public boolean exists(MobilePhoneNumber mobile, SmsVerificationScope scope) {
        Boolean aBoolean = redisTemplate.hasKey(toKey(mobile, scope));
        return aBoolean == null ? false : aBoolean;
    }

    @Override
    public SmsVerification shouldFindBy(MobilePhoneNumber mobile, SmsVerificationScope scope) {
        return findBy(mobile, scope).orElseThrow(() -> new MobileIsNotUnderVerificationException(mobile, scope));
    }

    @Override
    public Optional<SmsVerification> findBy(MobilePhoneNumber mobile, SmsVerificationScope scope) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(toKey(mobile, scope)));
    }

    @Override
    public void remove(SmsVerification smsVerification) {
        remove(smsVerification.getMobile(), smsVerification.getScope());
    }

    private void remove(MobilePhoneNumber mobile, SmsVerificationScope scope) {
        deleteFromRedis.delete(toKey(mobile, scope));
    }

}
