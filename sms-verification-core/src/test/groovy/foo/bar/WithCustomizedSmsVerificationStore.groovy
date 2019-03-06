package foo.bar


import com.thebund1st.daming.core.MobilePhoneNumber
import com.thebund1st.daming.core.SmsVerification
import com.thebund1st.daming.core.SmsVerificationRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class WithCustomizedSmsVerificationStore {

    @Bean
    SmsVerificationRepository smsVerificationStore() {
        new SmsVerificationRepositoryStub()
    }

    class SmsVerificationRepositoryStub implements SmsVerificationRepository {

        @Override
        void store(SmsVerification code) {

        }

        @Override
        boolean exists(MobilePhoneNumber mobile) {
            return false
        }

        @Override
        SmsVerification shouldFindBy(MobilePhoneNumber mobile) {
            return null
        }

        @Override
        void remove(SmsVerification smsVerification) {

        }
    }
}