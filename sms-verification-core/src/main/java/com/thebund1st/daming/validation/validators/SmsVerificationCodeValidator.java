package com.thebund1st.daming.validation.validators;

import com.thebund1st.daming.core.SmsVerificationCode;
import com.thebund1st.daming.core.SmsVerificationCodePattern;
import com.thebund1st.daming.validation.ValidSmsVerificationCode;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SmsVerificationCodeValidator implements ConstraintValidator<ValidSmsVerificationCode, SmsVerificationCode> {

    private SmsVerificationCodePattern smsVerificationCodePattern;

    public SmsVerificationCodeValidator(SmsVerificationCodePattern pattern) {
        this.smsVerificationCodePattern = pattern;
    }

    @Override
    public void initialize(ValidSmsVerificationCode constraintAnnotation) {

    }

    @Override
    public boolean isValid(SmsVerificationCode value, ConstraintValidatorContext context) {
        boolean isValid = smsVerificationCodePattern.matches(value);
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Invalid sms verification code")
                    .addConstraintViolation();
        }
        return isValid;
    }

}
