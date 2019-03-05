package com.foo.bar;

import com.thebund1st.daming.application.SmsVerificationSender;
import com.thebund1st.daming.core.MobilePhoneNumber;
import com.thebund1st.daming.core.SmsVerification;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class SmsVerificationSenderStub implements SmsVerificationSender {

    private List<SmsVerification> collected = new ArrayList<>();

    @Override
    public void send(SmsVerification verification) {
        log.info("Sending verification code {} to mobile {}, the code is expiring in {}",
                verification.getCode(), verification.getMobile(), verification.getExpires());
        this.collected.add(verification);
    }

    public long sendCount(MobilePhoneNumber mobilePhoneNumber) {
        return collected.stream().filter(v -> v.matches(mobilePhoneNumber)).count();
    }

    public Optional<SmsVerification> getTheOnly(MobilePhoneNumber mobilePhoneNumber) {
        return collected.stream().filter(v -> v.matches(mobilePhoneNumber)).findFirst();
    }
}
