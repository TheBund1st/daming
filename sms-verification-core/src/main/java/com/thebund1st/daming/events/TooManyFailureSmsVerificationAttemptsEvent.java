package com.thebund1st.daming.events;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thebund1st.daming.core.MobilePhoneNumber;
import com.thebund1st.daming.core.SmsVerificationScope;
import com.thebund1st.daming.json.deserializers.MobilePhoneNumberJsonDeserializer;
import com.thebund1st.daming.json.deserializers.SmsVerificationScopeJsonDeserializer;
import com.thebund1st.daming.json.serializers.MobilePhoneNumberJsonSerializer;
import com.thebund1st.daming.json.serializers.SmsVerificationScopeJsonSerializer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.ZonedDateTime;


@Getter
@EqualsAndHashCode
@ToString
public class TooManyFailureSmsVerificationAttemptsEvent {

    private String uuid;
    private ZonedDateTime when;

    @JsonDeserialize(using = MobilePhoneNumberJsonDeserializer.class)
    @JsonSerialize(using = MobilePhoneNumberJsonSerializer.class)
    private MobilePhoneNumber mobile;

    @JsonDeserialize(using = SmsVerificationScopeJsonDeserializer.class)
    @JsonSerialize(using = SmsVerificationScopeJsonSerializer.class)
    private SmsVerificationScope scope;

    public TooManyFailureSmsVerificationAttemptsEvent(String uuid, ZonedDateTime when,
                                                      MobilePhoneNumber mobile, SmsVerificationScope scope) {
        this.uuid = uuid;
        this.when = when;
        this.mobile = mobile;
        this.scope = scope;
    }
}
