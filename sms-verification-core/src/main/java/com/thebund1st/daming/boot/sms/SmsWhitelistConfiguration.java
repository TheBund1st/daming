package com.thebund1st.daming.boot.sms;

import com.thebund1st.daming.application.SmsVerificationSender;
import com.thebund1st.daming.boot.SmsVerificationCodeProperties;
import com.thebund1st.daming.sms.SmsSenderAspect;
import com.thebund1st.daming.sms.LoggingSmsVerificationSender;
import com.thebund1st.daming.sms.WhitelistSmsVerificationSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class SmsWhitelistConfiguration {

    /**
     * FIXME: Cannot using constructor, otherwise injection is not working
     */
    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private Map<String, SmsVerificationSender> senders = new HashMap<>();

    private final SmsVerificationCodeProperties smsVerificationCodeProperties;

    @Bean(name = "smsVerificationSender")
    public WhitelistSmsVerificationSender smsVerificationSender() {
        if (senders.size() > 1) {
            throw new NoUniqueBeanDefinitionException(SmsVerificationSender.class, senders.keySet());
        }
        if (CollectionUtils.isEmpty(senders)) {
            this.senders.put("loggingSmsVerificationSender", new LoggingSmsVerificationSender());
        }
        WhitelistSmsVerificationSender sender = new WhitelistSmsVerificationSender(findUnique());
        sender.setWhitelist(smsVerificationCodeProperties.whitelist());
        return sender;
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")//a default stub is added
    private SmsVerificationSender findUnique() {
        return senders.values().stream().findFirst().get();
    }

    @Bean
    public SmsSenderAspect smsSenderAspect() {
        return new SmsSenderAspect();
    }
}
