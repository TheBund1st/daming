package com.thebund1st.daming.sms;

import com.thebund1st.daming.application.SmsVerificationSender;
import com.thebund1st.daming.core.MobilePhoneNumber;
import com.thebund1st.daming.core.SmsVerification;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class WhitelistSmsVerificationSender implements SmsVerificationSender {

    @Getter
    private final SmsVerificationSender target;

    @Getter
    @Setter
    private List<String> whitelist = new ArrayList<>();

    public WhitelistSmsVerificationSender(SmsVerificationSender target) {
        this.target = target;
    }

    @Override
    public void send(SmsVerification verification) {
        if (whitelistIsDisabled() || sentToWhitelist(verification)) {
            target.send(verification);
        } else {
            log.info("Skip sending verification code to {} due to abc", verification.getMobile());
        }
    }

    private boolean sentToWhitelist(SmsVerification verification) {
        return whitelist.stream().map(MobilePhoneNumber::mobilePhoneNumberOf).anyMatch(verification::matches);
    }

    private boolean whitelistIsDisabled() {
        return whitelist.isEmpty();
    }
}
