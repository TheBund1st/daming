package com.thebund1st.daming.application.validation.validators;

import com.thebund1st.daming.application.domain.SmsVerificationCode;
import com.thebund1st.daming.application.domain.SmsVerificationCodePattern;
import com.thebund1st.daming.application.validation.ValidSmsVerificationCode;

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
