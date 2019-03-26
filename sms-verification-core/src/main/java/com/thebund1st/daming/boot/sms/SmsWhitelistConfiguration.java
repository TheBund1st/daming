package com.thebund1st.daming.boot.sms;

import com.thebund1st.daming.boot.core.SmsVerificationCodeProperties;
import com.thebund1st.daming.core.SmsVerificationCodeSender;
import com.thebund1st.daming.sms.SmsVerificationCodeSenderStub;
import com.thebund1st.daming.sms.WhitelistSmsVerificationCodeSender;
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
    private Map<String, SmsVerificationCodeSender> senders = new HashMap<>();

    private final SmsVerificationCodeProperties smsVerificationCodeProperties;

    @Bean(name = "smsVerificationSender")
    public WhitelistSmsVerificationCodeSender smsVerificationSender() {
        if (senders.size() > 1) {
            throw new NoUniqueBeanDefinitionException(SmsVerificationCodeSender.class, senders.keySet());
        }
        if (CollectionUtils.isEmpty(senders)) {
            this.senders.put("smsVerificationSenderStub", new SmsVerificationCodeSenderStub());
        }
        WhitelistSmsVerificationCodeSender sender = new WhitelistSmsVerificationCodeSender(findUnique());
        sender.setWhitelist(smsVerificationCodeProperties.whitelist());
        return sender;
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")//a default stub is added
    private SmsVerificationCodeSender findUnique() {
        return senders.values().stream().findFirst().get();
    }

}
