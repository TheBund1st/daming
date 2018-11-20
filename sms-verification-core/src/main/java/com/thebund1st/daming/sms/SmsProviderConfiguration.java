package com.thebund1st.daming.sms;

import com.thebund1st.daming.application.SmsVerificationSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SmsProviderConfiguration {

    @Autowired
    private List<SmsVerificationSender> senders = new ArrayList<>();

    @Bean(name = "smsVerificationSender")
    public WhitelistSmsVerificationSender smsVerificationSender() {
        if (CollectionUtils.isEmpty(senders)) {
            this.senders.add(new SmsVerificationSenderStub());
        }
        WhitelistSmsVerificationSender sender = new WhitelistSmsVerificationSender(senders.get(0));
        //TODO configure whitelist
        return sender;
    }

    @Bean
    public SmsSenderAspect smsSenderAspect() {
        return new SmsSenderAspect();
    }
}
