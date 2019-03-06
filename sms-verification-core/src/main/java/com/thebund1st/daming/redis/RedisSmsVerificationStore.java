package com.thebund1st.daming.redis;

import com.thebund1st.daming.core.MobilePhoneNumber;
import com.thebund1st.daming.core.SmsVerification;
import com.thebund1st.daming.core.SmsVerificationStore;
import com.thebund1st.daming.core.exceptions.MobileIsNotUnderVerificationException;
import com.thebund1st.daming.core.exceptions.MobileIsStillUnderVerificationException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

@Slf4j
@Setter
public class RedisSmsVerificationStore implements SmsVerificationStore {

    private final RedisTemplate<String, SmsVerification> redisTemplate;
    private String keyPrefix = "sms.verification.";

    public RedisSmsVerificationStore(RedisTemplate<String, SmsVerification> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void store(SmsVerification smsVerification) {
        MobilePhoneNumber mobile = smsVerification.getMobile();
        Boolean ifAbsent = redisTemplate.opsForValue()
                .setIfAbsent(toKey(mobile), smsVerification, smsVerification.getExpires());
        if (ifAbsent) {
            // do nothing as we don't overwrite the entry
        } else {
            throw new MobileIsStillUnderVerificationException(smsVerification.getMobile());
        }
    }

    private String toKey(MobilePhoneNumber mobile) {
        return keyPrefix + mobile.getValue();
    }

    @Override
    public boolean exists(MobilePhoneNumber mobile) {
        return redisTemplate.hasKey(toKey(mobile));
    }

    @Override
    public SmsVerification shouldFindBy(MobilePhoneNumber mobile) {
        SmsVerification codeMaybe = redisTemplate.opsForValue().get(toKey(mobile));
        if (codeMaybe == null) {
            throw new MobileIsNotUnderVerificationException(mobile);
        } else {
            return codeMaybe;
        }
    }

    @Override
    public void remove(SmsVerification smsVerification) {
        redisTemplate.delete(toKey(smsVerification.getMobile()));
    }
}
