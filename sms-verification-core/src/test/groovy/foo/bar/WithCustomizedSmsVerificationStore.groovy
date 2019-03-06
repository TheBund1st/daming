package foo.bar


import com.thebund1st.daming.core.MobilePhoneNumber
import com.thebund1st.daming.core.SmsVerification
import com.thebund1st.daming.core.SmsVerificationStore
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class WithCustomizedSmsVerificationStore {

    @Bean
    SmsVerificationStore smsVerificationStore() {
        new SmsVerificationStoreStub()
    }

    class SmsVerificationStoreStub implements SmsVerificationStore {

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