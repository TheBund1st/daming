package com.thebund1st.daming.boot.sms;

import com.thebund1st.daming.application.domain.SmsVerificationCodeSender;
import com.thebund1st.daming.sms.DefaultSmsVerificationCodeSender;
import com.thebund1st.daming.sms.SmsVerificationCodeSenderStub;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Configuration
public class SmsVerificationSenderConfiguration {

    /**
     * FIXME: Cannot using constructor, otherwise injection is not working
     */
    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private Map<String, SmsVerificationCodeSender> senders = new HashMap<>();

    @PostConstruct
    public void setupSmsVerificationCodeSender() {
        if (senders.size() > 2) {
            throw new NoUniqueBeanDefinitionException(SmsVerificationCodeSender.class, senders.keySet());
        }
        SmsVerificationCodeSender sender = senders.get("smsVerificationSender");
        if (sender.getClass().isAssignableFrom(DefaultSmsVerificationCodeSender.class)) {
            DefaultSmsVerificationCodeSender defaultSmsVerificationCodeSender = (DefaultSmsVerificationCodeSender) sender;
            defaultSmsVerificationCodeSender.setTarget(findTarget());
        }
    }


    @SuppressWarnings("OptionalGetWithoutIsPresent")//a default stub is added
    private SmsVerificationCodeSender findTarget() {
        Optional<Map.Entry<String, SmsVerificationCodeSender>> targetEntry = senders
                .entrySet().stream().filter(e -> !e.getKey().equals("smsVerificationSender"))
                .findFirst();
        if (targetEntry.isPresent()) {
            return targetEntry.get().getValue();
        } else {
            return new SmsVerificationCodeSenderStub();
        }
    }

}
