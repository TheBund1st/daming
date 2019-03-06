package com.thebund1st.daming.commands;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.thebund1st.daming.core.MobilePhoneNumber;
import com.thebund1st.daming.core.SmsVerificationCode;
import com.thebund1st.daming.json.deserializers.MobilePhoneNumberJsonDeserializer;
import com.thebund1st.daming.json.deserializers.SmsVerificationCodeJsonDeserializer;
import com.thebund1st.daming.validation.ValidMobilePhoneNumber;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class VerifySmsVerificationCodeCommand {
    @ValidMobilePhoneNumber
    @JsonDeserialize(using = MobilePhoneNumberJsonDeserializer.class)
    private MobilePhoneNumber mobile;

    @JsonDeserialize(using = SmsVerificationCodeJsonDeserializer.class)
    private SmsVerificationCode code;
}
