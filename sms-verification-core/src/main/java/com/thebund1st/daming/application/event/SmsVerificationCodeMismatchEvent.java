package com.thebund1st.daming.application.event;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thebund1st.daming.application.domain.MobilePhoneNumber;
import com.thebund1st.daming.application.domain.SmsVerificationScope;
import com.thebund1st.daming.adapter.jackson.deserializers.MobilePhoneNumberJsonDeserializer;
import com.thebund1st.daming.adapter.jackson.deserializers.SmsVerificationScopeJsonDeserializer;
import com.thebund1st.daming.adapter.jackson.serializers.MobilePhoneNumberJsonSerializer;
import com.thebund1st.daming.adapter.jackson.serializers.SmsVerificationScopeJsonSerializer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.ZonedDateTime;


@Getter
@EqualsAndHashCode
@ToString
public class SmsVerificationCodeMismatchEvent {

    private String uuid;
    private ZonedDateTime when;

    @JsonDeserialize(using = MobilePhoneNumberJsonDeserializer.class)
    @JsonSerialize(using = MobilePhoneNumberJsonSerializer.class)
    private MobilePhoneNumber mobile;

    @JsonDeserialize(using = SmsVerificationScopeJsonDeserializer.class)
    @JsonSerialize(using = SmsVerificationScopeJsonSerializer.class)
    private SmsVerificationScope scope;

    private ZonedDateTime expiresAt;

    public SmsVerificationCodeMismatchEvent(String uuid, ZonedDateTime when,
                                            MobilePhoneNumber mobile, SmsVerificationScope scope,
                                            ZonedDateTime expiresAt) {
        this.uuid = uuid;
        this.when = when;
        this.mobile = mobile;
        this.scope = scope;
        this.expiresAt = expiresAt;
    }
}
