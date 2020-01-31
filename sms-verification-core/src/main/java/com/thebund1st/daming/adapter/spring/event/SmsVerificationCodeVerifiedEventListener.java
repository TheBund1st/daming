package com.thebund1st.daming.adapter.spring.event;

import com.thebund1st.daming.events.SmsVerificationCodeVerifiedEvent;
import com.thebund1st.daming.redis.RedisSmsVerificationCodeMismatchEventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;

@Slf4j
@RequiredArgsConstructor
public class SmsVerificationCodeVerifiedEventListener
        implements ApplicationListener<PayloadApplicationEvent<SmsVerificationCodeVerifiedEvent>> {

    private final RedisSmsVerificationCodeMismatchEventHandler target;

    @Override
    public void onApplicationEvent(PayloadApplicationEvent<SmsVerificationCodeVerifiedEvent> event) {
        log.debug("Receiving {}", event.getPayload().toString());
        target.on(event.getPayload());
        log.debug("Failure verification attempt count for [{}][{}] is reset", event.getPayload().getMobile(),
                event.getPayload().getScope());
    }
}
