package com.thebund1st.daming.boot.sms;

import com.thebund1st.daming.boot.core.SmsVerificationCodeProperties;
import com.thebund1st.daming.adapter.sms.DefaultSmsVerificationCodeSender;
import com.thebund1st.daming.adapter.sms.WhitelistSmsVerificationCodeSenderInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Configuration
public class SmsWhitelistConfiguration {

    @Autowired
    private SmsVerificationCodeProperties smsVerificationCodeProperties;
    @Autowired
    private DefaultSmsVerificationCodeSender defaultSmsVerificationCodeSender;

    private WhitelistSmsVerificationCodeSenderInterceptor whitelistSmsVerificationCodeSenderInterceptor() {
        WhitelistSmsVerificationCodeSenderInterceptor interceptor = new WhitelistSmsVerificationCodeSenderInterceptor();
        interceptor.setWhitelist(smsVerificationCodeProperties.whitelist());
        return interceptor;
    }

    @PostConstruct
    public void blockSmsSending() {
        defaultSmsVerificationCodeSender.addInterceptor(whitelistSmsVerificationCodeSenderInterceptor());
    }

}
