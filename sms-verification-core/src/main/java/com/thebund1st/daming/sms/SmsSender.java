package com.thebund1st.daming.sms;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(METHOD)
@Retention(RUNTIME)
public @interface SmsSender {
    /**
     * @return spring bean name
     */
    String delegateTo();
}
