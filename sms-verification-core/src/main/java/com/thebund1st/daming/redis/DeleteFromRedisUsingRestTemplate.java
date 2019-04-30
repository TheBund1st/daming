package com.thebund1st.daming.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

@RequiredArgsConstructor
public class DeleteFromRedisUsingRestTemplate implements DeleteFromRedis {

    private final RedisTemplate redisTemplate;

    @Override
    public void delete(String key) {
        //noinspection unchecked
        redisTemplate.delete(key);
    }
}
