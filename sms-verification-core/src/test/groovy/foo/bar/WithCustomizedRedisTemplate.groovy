package foo.bar

import com.thebund1st.daming.application.domain.SmsVerification
import com.thebund1st.daming.boot.adapter.redis.spring.CustomizedRedisTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate

@Configuration
class WithCustomizedRedisTemplate {

    @Bean(name = "smsVerificationRedisTemplate")
    RedisTemplate<String, SmsVerification> customized(RedisConnectionFactory redisConnectionFactory) {
        def template = new CustomizedRedisTemplate()
        template.setConnectionFactory(redisConnectionFactory)
        template
    }
}