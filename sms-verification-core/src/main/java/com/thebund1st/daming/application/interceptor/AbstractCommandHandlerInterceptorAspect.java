package com.thebund1st.daming.application.interceptor;

import lombok.Setter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.core.Ordered;

import java.util.Arrays;
import java.util.Optional;

/**
 * Abstract Aspect to weave {@link CommandHandlerInterceptor}.
 *
 * <p>
 * Subclass should be annotated with {@link org.aspectj.lang.annotation.Aspect} and
 * override {@link #commandHandlerMethod()} to define the pointcut.
 * </p>
 *
 * @since 0.10.0
 */
public abstract class AbstractCommandHandlerInterceptorAspect<C, R>
        implements Ordered {
    @Setter
    private CommandHandlerInterceptor<C, R> commandHandlerInterceptor;

    @Setter
    private int order = 0;

    @Around("commandHandlerMethod()")
    public Object intercept(ProceedingJoinPoint joinPoint)
            throws Throwable {
        C command = extractCommandFrom(joinPoint);
        commandHandlerInterceptor.preHandle(command);
        R returning = proceed(joinPoint);
        commandHandlerInterceptor.postHandle(command, returning);
        return returning;
    }

    @SuppressWarnings("unchecked")
    private R proceed(ProceedingJoinPoint joinPoint) throws Throwable {
        return (R) joinPoint.proceed();
    }

    @SuppressWarnings({"unchecked", "OptionalGetWithoutIsPresent"})
    private C extractCommandFrom(ProceedingJoinPoint joinPoint) {
        Optional<Object> commandOptional = Arrays.stream(joinPoint.getArgs())
                .findFirst();
        return (C) commandOptional.get();
    }


    @Override
    public int getOrder() {
        return order;
    }

    protected abstract void commandHandlerMethod();
}
