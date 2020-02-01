package foo.bar

import com.thebund1st.daming.boot.adapter.aliyun.CustomizedAcsClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class WithCustomizedAcsClient {

    @Bean(name = "acsClient")
    CustomizedAcsClient customized() {
        new CustomizedAcsClient()
    }
}