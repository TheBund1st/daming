package com.thebund1st.daming.events;

import com.thebund1st.daming.core.SmsVerification;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.ZonedDateTime;


@Getter
@EqualsAndHashCode
@ToString
public class SmsVerificationRequestedEvent {

    private String uuid;
    private ZonedDateTime when;

    private SmsVerification smsVerification;

    public SmsVerificationRequestedEvent(String uuid, ZonedDateTime when,
                                         SmsVerification smsVerification) {
        this.uuid = uuid;
        this.when = when;
        this.smsVerification = smsVerification;
    }
}
