package com.thebund1st.daming.adapter.event.spring;

import com.thebund1st.daming.application.event.SmsVerificationCodeMismatchEvent;
import com.thebund1st.daming.adapter.redis.spirng.RedisSmsVerificationCodeMismatchEventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;

@Slf4j
@RequiredArgsConstructor
public class SmsVerificationCodeMismatchEventListener
        implements ApplicationListener<PayloadApplicationEvent<SmsVerificationCodeMismatchEvent>> {

    private final RedisSmsVerificationCodeMismatchEventHandler target;

    @Override
    public void onApplicationEvent(PayloadApplicationEvent<SmsVerificationCodeMismatchEvent> event) {
        log.debug("Receiving {}", event.getPayload().toString());
        target.on(event.getPayload());
    }
}
