package com.thebund1st.daming.json.mixin;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thebund1st.daming.core.MobilePhoneNumber;
import com.thebund1st.daming.core.SmsVerificationCode;
import com.thebund1st.daming.json.deserializers.MobilePhoneNumberJsonDeserializer;
import com.thebund1st.daming.json.deserializers.SmsVerificationCodeJsonDeserializer;
import com.thebund1st.daming.json.serializers.MobilePhoneNumberJsonSerializer;
import com.thebund1st.daming.json.serializers.SmsVerificationCodeJsonSerializer;

public abstract class SmsVerificationMixin {

    @JsonSerialize(using = MobilePhoneNumberJsonSerializer.class)
    @JsonDeserialize(using = MobilePhoneNumberJsonDeserializer.class)
    public abstract MobilePhoneNumber getMobile();

    @JsonSerialize(using = SmsVerificationCodeJsonSerializer.class)
    @JsonDeserialize(using = SmsVerificationCodeJsonDeserializer.class)
    public abstract SmsVerificationCode getCode();


}
