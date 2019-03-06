package com.thebund1st.daming.commands;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.thebund1st.daming.core.MobilePhoneNumber;
import com.thebund1st.daming.core.SmsVerificationScope;
import com.thebund1st.daming.json.deserializers.MobilePhoneNumberJsonDeserializer;
import com.thebund1st.daming.json.deserializers.SmsVerificationScopeJsonDeserializer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SendSmsVerificationCodeCommand {
    @JsonDeserialize(using = MobilePhoneNumberJsonDeserializer.class)
    private MobilePhoneNumber mobile;

    @JsonDeserialize(using = SmsVerificationScopeJsonDeserializer.class)
    private SmsVerificationScope scope;
}
