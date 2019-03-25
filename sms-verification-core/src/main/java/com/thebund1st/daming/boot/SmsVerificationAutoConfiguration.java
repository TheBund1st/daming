package com.thebund1st.daming.boot;

import com.thebund1st.daming.boot.aliyun.sms.AliyunSmsConfiguration;
import com.thebund1st.daming.boot.application.ApplicationConfiguration;
import com.thebund1st.daming.boot.core.CoreConfiguration;
import com.thebund1st.daming.boot.core.SmsVerificationCodeConfiguration;
import com.thebund1st.daming.boot.core.SmsVerificationScopeConfiguration;
import com.thebund1st.daming.boot.eventhandling.EventHandlingConfiguration;
import com.thebund1st.daming.boot.jwt.JwtConfiguration;
import com.thebund1st.daming.boot.redis.RedisConfiguration;
import com.thebund1st.daming.boot.security.RateLimitingConfiguration;
import com.thebund1st.daming.boot.sms.SmsWhitelistConfiguration;
import com.thebund1st.daming.boot.time.TimeConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

//TODO figure out is @ComponentScan is a good practice or not?
@ComponentScan(basePackages = {
        "com.thebund1st.daming.web",
})
@Import({
        AliyunSmsConfiguration.class,
        RedisConfiguration.class,
        SmsWhitelistConfiguration.class,
        RateLimitingConfiguration.class,
        TimeConfiguration.class,
        JwtConfiguration.class,
        SmsVerificationCodeConfiguration.class,
        SmsVerificationScopeConfiguration.class,
        ApplicationConfiguration.class,
        CoreConfiguration.class,
        EventHandlingConfiguration.class
})
@Configuration
public class SmsVerificationAutoConfiguration {


}