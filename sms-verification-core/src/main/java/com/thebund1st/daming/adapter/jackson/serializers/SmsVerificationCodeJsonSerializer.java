package com.thebund1st.daming.adapter.jackson.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.thebund1st.daming.application.domain.SmsVerificationCode;

import java.io.IOException;

public class SmsVerificationCodeJsonSerializer extends JsonSerializer<SmsVerificationCode> {

    @Override
    public void serialize(SmsVerificationCode value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.getValue());
    }
}
