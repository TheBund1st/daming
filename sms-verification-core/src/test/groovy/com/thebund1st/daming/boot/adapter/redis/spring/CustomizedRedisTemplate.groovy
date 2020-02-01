package com.thebund1st.daming.boot.adapter.redis.spring

import com.thebund1st.daming.application.domain.SmsVerification
import org.springframework.data.redis.core.RedisTemplate

class CustomizedRedisTemplate extends RedisTemplate<String, SmsVerification> {

}