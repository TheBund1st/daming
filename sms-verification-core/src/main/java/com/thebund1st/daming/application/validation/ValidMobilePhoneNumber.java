package com.thebund1st.daming.application.validation;

import com.thebund1st.daming.application.validation.validators.MobilePhoneNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {MobilePhoneNumberValidator.class})
@Target({FIELD})
@Retention(RUNTIME)
public @interface ValidMobilePhoneNumber {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
