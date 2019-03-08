package foo.bar

import com.thebund1st.daming.application.SmsVerifiedJwtIssuer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class WithDumbSmsVerifiedJwtIssuer {

    @Bean
    SmsVerifiedJwtIssuer smsVerifiedJwtIssuer() {
        new SmsVerifiedJwtIssuer(null, null)
    }
}