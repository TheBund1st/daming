package com.thebund1st.daming.adapter.event.spring;

import com.thebund1st.daming.application.domain.SmsVerificationCodeSender;
import com.thebund1st.daming.application.event.SmsVerificationRequestedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;

@Slf4j
@RequiredArgsConstructor
public class SmsVerificationRequestedEventListener
        implements ApplicationListener<PayloadApplicationEvent<SmsVerificationRequestedEvent>> {

    private final SmsVerificationCodeSender target;

    @Override
    public void onApplicationEvent(PayloadApplicationEvent<SmsVerificationRequestedEvent> event) {
        log.debug("Receiving {}", event.getPayload().toString());
        target.send(event.getPayload().getSmsVerification());
    }
}
