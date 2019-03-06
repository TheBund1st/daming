package com.thebund1st.daming.core.exceptions;

import com.thebund1st.daming.core.SmsVerification;
import com.thebund1st.daming.core.SmsVerificationCode;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.PRECONDITION_FAILED;

@ResponseStatus(code = PRECONDITION_FAILED)
public class SmsVerificationCodeMismatchException extends RuntimeException {

    public SmsVerificationCodeMismatchException(SmsVerification smsVerification, SmsVerificationCode actual) {
        super(String.format("The actual code [%s] does not match the code [%s] sent to [%s][%s].",
                actual, smsVerification.getCode(), smsVerification.getMobile(), smsVerification.getScope()));
    }

}
