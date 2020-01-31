package com.thebund1st.daming.boot;

import com.thebund1st.daming.boot.aliyun.sms.AliyunSmsConfiguration;
import com.thebund1st.daming.boot.application.ApplicationConfiguration;
import com.thebund1st.daming.boot.core.BypassConfiguration;
import com.thebund1st.daming.boot.core.CoreConfiguration;
import com.thebund1st.daming.boot.core.SmsVerificationCodeConfiguration;
import com.thebund1st.daming.boot.core.SmsVerificationScopeConfiguration;
import com.thebund1st.daming.boot.eventhandling.EventHandlingConfiguration;
import com.thebund1st.daming.boot.http.EndpointsConfiguration;
import com.thebund1st.daming.boot.jwt.JwtConfiguration;
import com.thebund1st.daming.boot.redis.RedisConfiguration;
import com.thebund1st.daming.boot.security.RateLimitingConfiguration;
import com.thebund1st.daming.boot.sms.DefaultSmsVerificationSenderConfiguration;
import com.thebund1st.daming.boot.sms.SmsVerificationSenderConfiguration;
import com.thebund1st.daming.boot.sms.SmsWhitelistConfiguration;
import com.thebund1st.daming.boot.time.TimeConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@ComponentScan(basePackages = {
        "com.thebund1st.daming.adapter.spring.web",
})
@Import({
        EndpointsConfiguration.class,
        AliyunSmsConfiguration.class,
        RedisConfiguration.class,
        RateLimitingConfiguration.class,
        TimeConfiguration.class,
        JwtConfiguration.class,
        SmsVerificationCodeConfiguration.class,
        SmsVerificationScopeConfiguration.class,
        DefaultSmsVerificationSenderConfiguration.class,
        SmsVerificationSenderConfiguration.class,
        SmsWhitelistConfiguration.class,
        BypassConfiguration.class,
        CoreConfiguration.class,
        EventHandlingConfiguration.class,
        ApplicationConfiguration.class,
})
@Configuration
public class SmsVerificationAutoConfiguration {


}
//TODO figure out is @ComponentScan is a good practice or not?
