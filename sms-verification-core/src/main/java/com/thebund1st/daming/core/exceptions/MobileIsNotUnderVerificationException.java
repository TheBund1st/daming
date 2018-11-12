package com.thebund1st.daming.core.exceptions;

import com.thebund1st.daming.core.MobilePhoneNumber;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.PRECONDITION_FAILED;

@ResponseStatus(code = PRECONDITION_FAILED)
public class MobileIsNotUnderVerificationException extends RuntimeException {

    public MobileIsNotUnderVerificationException(MobilePhoneNumber mobilePhoneNumber) {
        super(String.format("The mobile [%s] is not under verification.", mobilePhoneNumber.toString()));
    }

}
