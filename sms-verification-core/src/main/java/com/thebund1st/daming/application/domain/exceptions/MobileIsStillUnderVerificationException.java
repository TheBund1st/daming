package com.thebund1st.daming.application.domain.exceptions;

import com.thebund1st.daming.application.domain.MobilePhoneNumber;
import com.thebund1st.daming.application.domain.SmsVerificationScope;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.PRECONDITION_FAILED;

@ResponseStatus(code = PRECONDITION_FAILED)
public class MobileIsStillUnderVerificationException extends RuntimeException {

    public MobileIsStillUnderVerificationException(MobilePhoneNumber mobilePhoneNumber, SmsVerificationScope scope) {
        super(String.format("The mobile [%s] is under [%s] verification.",
                mobilePhoneNumber.toString(), scope.toString()));
    }

}
