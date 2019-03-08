package com.thebund1st.daming.validation;

import com.thebund1st.daming.validation.validators.SmsVerificationCodeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {SmsVerificationCodeValidator.class})
@Target({FIELD})
@Retention(RUNTIME)
public @interface ValidSmsVerificationCode {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
