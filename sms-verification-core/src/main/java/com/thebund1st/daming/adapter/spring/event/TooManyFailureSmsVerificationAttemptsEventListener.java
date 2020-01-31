package com.thebund1st.daming.adapter.spring.event;

import com.thebund1st.daming.core.SmsVerification;
import com.thebund1st.daming.core.SmsVerificationRepository;
import com.thebund1st.daming.events.TooManyFailureSmsVerificationAttemptsEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;

@Slf4j
@RequiredArgsConstructor
public class TooManyFailureSmsVerificationAttemptsEventListener
        implements ApplicationListener<PayloadApplicationEvent<TooManyFailureSmsVerificationAttemptsEvent>> {

    private final SmsVerificationRepository target;

    @Override
    public void onApplicationEvent(PayloadApplicationEvent<TooManyFailureSmsVerificationAttemptsEvent> event) {
        // FIXME, introduce a command handler
        log.debug("Receiving {}", event.getPayload());
        TooManyFailureSmsVerificationAttemptsEvent eventPayload = event.getPayload();
        SmsVerification smsVerification = target.shouldFindBy(eventPayload.getMobile(), eventPayload.getScope());
        target.remove(smsVerification);
        log.info("The [{}] code for [{}] is removed due to too many failure verification attempts",
                smsVerification.getScope(), smsVerification.getMobile());
    }
}
