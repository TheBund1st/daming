package com.thebund1st.daming.core.exceptions;

import com.thebund1st.daming.core.MobilePhoneNumber;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.PRECONDITION_FAILED;

@ResponseStatus(code = PRECONDITION_FAILED)
public class MobileIsStillUnderVerificationException extends RuntimeException {

    public MobileIsStillUnderVerificationException(MobilePhoneNumber mobilePhoneNumber) {
        super(String.format("The mobile [%s] is under verification.", mobilePhoneNumber.toString()));
    }

}
