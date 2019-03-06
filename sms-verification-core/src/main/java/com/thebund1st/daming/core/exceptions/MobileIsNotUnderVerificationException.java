package com.thebund1st.daming.core.exceptions;

import com.thebund1st.daming.core.MobilePhoneNumber;
import com.thebund1st.daming.core.SmsVerificationScope;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.PRECONDITION_FAILED;

@ResponseStatus(code = PRECONDITION_FAILED)
public class MobileIsNotUnderVerificationException extends RuntimeException {

    public MobileIsNotUnderVerificationException(MobilePhoneNumber mobilePhoneNumber, SmsVerificationScope scope) {
        super(String.format("The mobile [%s] is not under [%s] verification.",
                mobilePhoneNumber.toString(), scope));
    }

}
