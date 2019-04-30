package com.foo.bar;

import com.thebund1st.daming.redis.DeleteFromRedis;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DeleteFromRedisUsingSpringDataRedis1 implements DeleteFromRedis {

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }
}
