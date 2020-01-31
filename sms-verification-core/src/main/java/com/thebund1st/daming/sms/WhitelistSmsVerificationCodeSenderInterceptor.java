package com.thebund1st.daming.sms;

import com.thebund1st.daming.application.domain.MobilePhoneNumber;
import com.thebund1st.daming.application.domain.SmsVerification;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 0.9.6
 */
@Slf4j
@ToString
public class WhitelistSmsVerificationCodeSenderInterceptor implements SmsVerificationCodeSenderInterceptor {

    @Getter
    @Setter
    private List<MobilePhoneNumber> whitelist = new ArrayList<>();

    @Override
    public boolean preHandle(SmsVerification verification) {
        if (whitelistIsDisabled() || sentToWhitelist(verification)) {
            return true;
        } else {
            log.debug("Block sending [{}] code to non-whitelist [{}] due to whitelist is enabled",
                    verification.getScope(), verification.getMobile());
            return false;
        }
    }

    private boolean sentToWhitelist(SmsVerification verification) {
        return whitelist.stream().anyMatch(verification::matches);
    }

    private boolean whitelistIsDisabled() {
        return whitelist.isEmpty();
    }
}
