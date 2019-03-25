package com.thebund1st.daming.sms;

import com.thebund1st.daming.core.MobilePhoneNumber;
import com.thebund1st.daming.core.SmsVerification;
import com.thebund1st.daming.core.SmsVerificationCodeSender;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class WhitelistSmsVerificationCodeSender implements SmsVerificationCodeSender {

    @Getter
    private final SmsVerificationCodeSender target;

    @Getter
    @Setter
    private List<MobilePhoneNumber> whitelist = new ArrayList<>();

    public WhitelistSmsVerificationCodeSender(SmsVerificationCodeSender target) {
        this.target = target;
    }

    @Override
    public void send(SmsVerification verification) {
        if (whitelistIsDisabled() || sentToWhitelist(verification)) {
            log.debug("Attempt to send [{}] code to [{}]", verification.getScope(), verification.getMobile());
            target.send(verification);
        } else {
            log.info("Skip sending [{}] code to [{}] due to whitelist",
                    verification.getScope(), verification.getMobile());
        }
    }

    private boolean sentToWhitelist(SmsVerification verification) {
        return whitelist.stream().anyMatch(verification::matches);
    }

    private boolean whitelistIsDisabled() {
        return whitelist.isEmpty();
    }
}
