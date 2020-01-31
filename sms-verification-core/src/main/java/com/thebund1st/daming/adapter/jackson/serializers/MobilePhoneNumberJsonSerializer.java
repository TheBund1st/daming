package com.thebund1st.daming.adapter.jackson.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.thebund1st.daming.application.domain.MobilePhoneNumber;

import java.io.IOException;

public class MobilePhoneNumberJsonSerializer extends JsonSerializer<MobilePhoneNumber> {

    @Override
    public void serialize(MobilePhoneNumber value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.getValue());
    }
}
