package com.thebund1st.daming.sms;

import com.thebund1st.daming.application.domain.SmsVerification;
import com.thebund1st.daming.application.domain.SmsVerificationCodeSender;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 0.9.6
 */
@Slf4j
public class DefaultSmsVerificationCodeSender implements SmsVerificationCodeSender {

    private final List<SmsVerificationCodeSenderInterceptor> interceptors = new ArrayList<>();
    @Setter
    @Getter
    private SmsVerificationCodeSender target;

    @Override
    public void send(SmsVerification verification) {
        if (interceptors.stream().allMatch(i -> i.preHandle(verification))) {
            log.debug("Attempt to send [{}] code to [{}]", verification.getScope(), verification.getMobile());
            target.send(verification);
            log.info("[{}] code is sent to [{}]", verification.getScope(), verification.getMobile());
            interceptors.forEach(i -> i.postHandle(verification));
        } else {
            log.info("Skip sending [{}] code to [{}] due to interceptor",
                    verification.getScope(), verification.getMobile());
        }
    }

    public void addInterceptor(SmsVerificationCodeSenderInterceptor interceptor) {
        this.interceptors.add(interceptor);
    }
}
