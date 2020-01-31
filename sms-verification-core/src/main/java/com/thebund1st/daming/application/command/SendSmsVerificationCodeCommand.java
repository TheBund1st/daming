package com.thebund1st.daming.application.command;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.thebund1st.daming.application.domain.MobilePhoneNumber;
import com.thebund1st.daming.application.domain.SmsVerificationScope;
import com.thebund1st.daming.adapter.jackson.deserializers.MobilePhoneNumberJsonDeserializer;
import com.thebund1st.daming.adapter.jackson.deserializers.SmsVerificationScopeJsonDeserializer;
import com.thebund1st.daming.application.validation.ValidMobilePhoneNumber;
import com.thebund1st.daming.application.validation.ValidSmsVerificationScope;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SendSmsVerificationCodeCommand {

    @ValidMobilePhoneNumber
    @JsonDeserialize(using = MobilePhoneNumberJsonDeserializer.class)
    private MobilePhoneNumber mobile;

    @ValidSmsVerificationScope
    @JsonDeserialize(using = SmsVerificationScopeJsonDeserializer.class)
    private SmsVerificationScope scope;
}
