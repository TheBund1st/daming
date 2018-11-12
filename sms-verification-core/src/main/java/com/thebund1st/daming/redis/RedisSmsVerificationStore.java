package com.thebund1st.daming.redis;

import com.thebund1st.daming.core.MobilePhoneNumber;
import com.thebund1st.daming.core.SmsVerification;
import com.thebund1st.daming.core.SmsVerificationStore;
import com.thebund1st.daming.core.exceptions.MobileIsNotUnderVerificationException;
import com.thebund1st.daming.core.exceptions.MobileIsStillUnderVerificationException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import static com.thebund1st.daming.core.SmsVerificationCode.smsVerificationCodeOf;
import static java.time.Duration.ofSeconds;

@Slf4j
@Component
@Setter
public class RedisSmsVerificationStore implements SmsVerificationStore {

    private final StringRedisTemplate redisTemplate;
    private String keyPrefix = "sms.verification.";
    private int expiresInSeconds = 2;

    @Autowired
    public RedisSmsVerificationStore(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void store(SmsVerification smsVerification) {
        MobilePhoneNumber mobile = smsVerification.getMobile();
        Boolean ifAbsent = redisTemplate.opsForValue()
                .setIfAbsent(toKey(mobile), smsVerification.getCode().getValue(), ofSeconds(expiresInSeconds));
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
        String codeMaybe = redisTemplate.opsForValue().get(toKey(mobile));
        if (StringUtils.isEmpty(codeMaybe)) {
            throw new MobileIsNotUnderVerificationException(mobile);
        } else {
            SmsVerification verification = new SmsVerification();
            verification.setMobile(mobile);
            verification.setCode(smsVerificationCodeOf(codeMaybe));
            return verification;
        }
    }

    @Override
    public void remove(SmsVerification smsVerification) {
        redisTemplate.delete(toKey(smsVerification.getMobile()));
    }
}
