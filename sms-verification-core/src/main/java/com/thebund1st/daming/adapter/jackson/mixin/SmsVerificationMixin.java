package com.thebund1st.daming.adapter.jackson.mixin;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thebund1st.daming.application.domain.MobilePhoneNumber;
import com.thebund1st.daming.application.domain.SmsVerificationCode;
import com.thebund1st.daming.application.domain.SmsVerificationScope;
import com.thebund1st.daming.adapter.jackson.deserializers.MobilePhoneNumberJsonDeserializer;
import com.thebund1st.daming.adapter.jackson.deserializers.SmsVerificationCodeJsonDeserializer;
import com.thebund1st.daming.adapter.jackson.deserializers.SmsVerificationScopeJsonDeserializer;
import com.thebund1st.daming.adapter.jackson.serializers.MobilePhoneNumberJsonSerializer;
import com.thebund1st.daming.adapter.jackson.serializers.SmsVerificationCodeJsonSerializer;
import com.thebund1st.daming.adapter.jackson.serializers.SmsVerificationScopeJsonSerializer;

public abstract class SmsVerificationMixin {

    @JsonSerialize(using = MobilePhoneNumberJsonSerializer.class)
    @JsonDeserialize(using = MobilePhoneNumberJsonDeserializer.class)
    public abstract MobilePhoneNumber getMobile();

    @JsonSerialize(using = SmsVerificationCodeJsonSerializer.class)
    @JsonDeserialize(using = SmsVerificationCodeJsonDeserializer.class)
    public abstract SmsVerificationCode getCode();

    @JsonSerialize(using = SmsVerificationScopeJsonSerializer.class)
    @JsonDeserialize(using = SmsVerificationScopeJsonDeserializer.class)
    public abstract SmsVerificationScope getScope();

}
