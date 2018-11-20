package com.thebund1st.daming.sms;

import com.thebund1st.daming.application.SmsVerificationSender;
import com.thebund1st.daming.core.SmsVerification;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class WhitelistSmsVerificationSender implements SmsVerificationSender {

    private final SmsVerificationSender target;
    @Setter
    private List<String> whitelist = new ArrayList<>();

    public WhitelistSmsVerificationSender(SmsVerificationSender target) {
        this.target = target;
    }

    @Override
    public void send(SmsVerification verification) {
        //FIXME whitelist
        target.send(verification);
    }
}
