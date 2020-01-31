package com.thebund1st.daming.application.commandhandling.interceptor;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation to easily weave CommandHandlerInterceptor into CommandHandlerMethod.
 *
 * @see AbstractCommandHandlerInterceptorAspect
 * @see CommandHandlerInterceptor
 * @since 0.10.0
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface CommandHandler {

}
