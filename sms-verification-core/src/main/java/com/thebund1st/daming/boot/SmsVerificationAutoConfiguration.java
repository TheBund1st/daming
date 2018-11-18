package com.thebund1st.daming.boot;

import com.thebund1st.daming.boot.aliyun.sms.AliyunSmsConfiguration;
import com.thebund1st.daming.boot.redis.RedisConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

//TODO figure out is @ComponentScan is a good practice or not?
@ComponentScan(basePackages = {
        "com.thebund1st.daming.application",
        "com.thebund1st.daming.commands",
        "com.thebund1st.daming.core",
        "com.thebund1st.daming.json",
        "com.thebund1st.daming.redis",
        "com.thebund1st.daming.sms",
        "com.thebund1st.daming.time",
        "com.thebund1st.daming.web",
}
)
@Import({AliyunSmsConfiguration.class, RedisConfiguration.class})
@Configuration
public class SmsVerificationAutoConfiguration {

}