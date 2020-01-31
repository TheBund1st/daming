package com.thebund1st.daming.application.validation.validators;

import com.thebund1st.daming.application.domain.MobilePhoneNumber;
import com.thebund1st.daming.application.domain.MobilePhoneNumberPattern;
import com.thebund1st.daming.application.validation.ValidMobilePhoneNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MobilePhoneNumberValidator implements ConstraintValidator<ValidMobilePhoneNumber, MobilePhoneNumber> {

    private MobilePhoneNumberPattern mobilePhoneNumberPattern;

    public MobilePhoneNumberValidator(MobilePhoneNumberPattern mobilePhoneNumberPattern) {
        this.mobilePhoneNumberPattern = mobilePhoneNumberPattern;
    }

    @Override
    public void initialize(ValidMobilePhoneNumber constraintAnnotation) {

    }

    @Override
    public boolean isValid(MobilePhoneNumber value, ConstraintValidatorContext context) {
        boolean isValid = mobilePhoneNumberPattern.matches(value);
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Invalid mobile phone number")
                    .addConstraintViolation();
        }
        return isValid;
    }

}
