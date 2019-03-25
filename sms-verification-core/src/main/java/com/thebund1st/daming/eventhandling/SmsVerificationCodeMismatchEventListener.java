package com.thebund1st.daming.eventhandling;

import com.thebund1st.daming.events.SmsVerificationCodeMismatchEvent;
import com.thebund1st.daming.redis.RedisSmsVerificationCodeMismatchEventHandler;
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
