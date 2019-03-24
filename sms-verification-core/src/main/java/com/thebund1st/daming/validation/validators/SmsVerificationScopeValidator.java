package com.thebund1st.daming.validation.validators;

import com.thebund1st.daming.core.SmsVerificationScope;
import com.thebund1st.daming.core.SmsVerificationScopePattern;
import com.thebund1st.daming.validation.ValidSmsVerificationScope;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SmsVerificationScopeValidator
        implements ConstraintValidator<ValidSmsVerificationScope, SmsVerificationScope> {

    private SmsVerificationScopePattern smsVerificationScopePattern;

    public SmsVerificationScopeValidator(SmsVerificationScopePattern pattern) {
        this.smsVerificationScopePattern = pattern;
    }

    @Override
    public void initialize(ValidSmsVerificationScope constraintAnnotation) {

    }

    @Override
    public boolean isValid(SmsVerificationScope value, ConstraintValidatorContext context) {
        boolean isValid = smsVerificationScopePattern.matches(value);
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(String.format("Invalid sms verification scope [%s]", value))
                    .addConstraintViolation();
        }
        return isValid;
    }

}
