package foo.bar


import com.thebund1st.daming.jwt.key.JwtKeyLoader
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import java.security.Key

@Configuration
class WithCustomizedJwtSigningKeyLoader {

    @Bean(name="smsVerificationJwtSigningKeyLoader")
    JwtKeyLoader smsVerifiedJwtIssuer() {
        new CustomizedJwtKeyLoader()
    }

    class CustomizedJwtKeyLoader implements JwtKeyLoader {

        @Override
        Key getKey() {
            return null
        }
    }
}