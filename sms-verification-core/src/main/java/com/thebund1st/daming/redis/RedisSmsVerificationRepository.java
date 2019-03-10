package com.thebund1st.daming.redis;

import com.thebund1st.daming.core.MobilePhoneNumber;
import com.thebund1st.daming.core.SmsVerification;
import com.thebund1st.daming.core.SmsVerificationRepository;
import com.thebund1st.daming.core.SmsVerificationScope;
import com.thebund1st.daming.core.exceptions.MobileIsNotUnderVerificationException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

@Slf4j
@Setter
public class RedisSmsVerificationRepository implements SmsVerificationRepository {

    private final RedisTemplate<String, SmsVerification> redisTemplate;
    private String keyPrefix = "sms.verification.";

    public RedisSmsVerificationRepository(RedisTemplate<String, SmsVerification> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void store(SmsVerification smsVerification) {
        redisTemplate.opsForValue()
                .set(toKey(smsVerification.getMobile(), smsVerification.getScope()),
                        smsVerification,
                        smsVerification.getExpires());
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
        SmsVerification codeMaybe = redisTemplate.opsForValue().get(toKey(mobile, scope));
        if (codeMaybe == null) {
            throw new MobileIsNotUnderVerificationException(mobile, scope);
        } else {
            return codeMaybe;
        }
    }

    @Override
    public void remove(SmsVerification smsVerification) {
        redisTemplate.delete(toKey(smsVerification.getMobile(), smsVerification.getScope()));
    }
}
