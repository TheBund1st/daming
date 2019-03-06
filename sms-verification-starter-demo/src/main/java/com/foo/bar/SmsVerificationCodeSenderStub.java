package com.foo.bar;

import com.thebund1st.daming.core.MobilePhoneNumber;
import com.thebund1st.daming.core.SmsVerification;
import com.thebund1st.daming.core.SmsVerificationCodeSender;
import com.thebund1st.daming.core.SmsVerificationScope;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
public class SmsVerificationCodeSenderStub implements SmsVerificationCodeSender {

    private List<SmsVerification> collected = new ArrayList<>();

    @Override
    public void send(SmsVerification verification) {
        log.info("Sending {} verification code {} to mobile {}, the code is expiring in {}",
                verification.getScope(), verification.getCode(), verification.getMobile(), verification.getExpires());
        this.collected.add(verification);
    }

    public long sendCount(MobilePhoneNumber mobilePhoneNumber, SmsVerificationScope scope) {
        return filteredStream(mobilePhoneNumber, scope)
                .count();
    }

    public Optional<SmsVerification> getTheOnly(MobilePhoneNumber mobilePhoneNumber, SmsVerificationScope scope) {
        return filteredStream(mobilePhoneNumber, scope)
                .findFirst();
    }

    private Stream<SmsVerification> filteredStream(MobilePhoneNumber mobilePhoneNumber, SmsVerificationScope scope) {
        return collected.stream()
                .filter(v -> v.matches(mobilePhoneNumber))
                .filter(v -> v.matches(scope));
    }
}
