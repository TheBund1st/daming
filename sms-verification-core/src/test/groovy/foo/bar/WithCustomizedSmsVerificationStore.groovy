package foo.bar


import com.thebund1st.daming.application.domain.MobilePhoneNumber
import com.thebund1st.daming.application.domain.SmsVerification
import com.thebund1st.daming.application.domain.SmsVerificationRepository
import com.thebund1st.daming.application.domain.SmsVerificationScope
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
        boolean exists(MobilePhoneNumber mobile, SmsVerificationScope scope) {
            return false
        }

        @Override
        SmsVerification shouldFindBy(MobilePhoneNumber mobile, SmsVerificationScope scope) {
            return null
        }

        @Override
        Optional<SmsVerification> findBy(MobilePhoneNumber mobile, SmsVerificationScope scope) {
            return null
        }

        @Override
        void remove(SmsVerification smsVerification) {

        }
    }
}