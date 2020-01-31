package com.thebund1st.daming.adapter.jackson.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.thebund1st.daming.application.domain.SmsVerificationScope;

import java.io.IOException;

public class SmsVerificationScopeJsonSerializer extends JsonSerializer<SmsVerificationScope> {

    @Override
    public void serialize(SmsVerificationScope value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.getValue());
    }
}
