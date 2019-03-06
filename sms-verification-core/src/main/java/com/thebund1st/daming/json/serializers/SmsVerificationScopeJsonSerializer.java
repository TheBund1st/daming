package com.thebund1st.daming.json.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.thebund1st.daming.core.SmsVerificationScope;

import java.io.IOException;

public class SmsVerificationScopeJsonSerializer extends JsonSerializer<SmsVerificationScope> {

    @Override
    public void serialize(SmsVerificationScope value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.getValue());
    }
}
