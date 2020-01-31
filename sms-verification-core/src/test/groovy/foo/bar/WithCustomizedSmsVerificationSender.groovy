package foo.bar


import com.thebund1st.daming.application.domain.SmsVerificationCodeSender
import com.thebund1st.daming.application.domain.SmsVerification
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class WithCustomizedSmsVerificationSender {

    @Bean
    SmsVerificationCodeSender anotherSmsVerificationSender() {
        new AnotherSmsVerificationCodeSender()
    }

    static class AnotherSmsVerificationCodeSender implements SmsVerificationCodeSender {

        @Override
        void send(SmsVerification verification) {

        }
    }
}