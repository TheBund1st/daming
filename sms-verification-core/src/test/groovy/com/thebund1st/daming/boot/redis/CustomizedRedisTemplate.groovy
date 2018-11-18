package com.thebund1st.daming.boot.redis

import com.thebund1st.daming.core.SmsVerification
import org.springframework.data.redis.core.RedisTemplate

class CustomizedRedisTemplate extends RedisTemplate<String, SmsVerification> {

}