package com.thebund1st.daming.application.interceptor;

/**
 * CommandHandlerInterceptor.
 *
 * <p>
 * Custom {@link CommandHandlerInterceptor} can be used to implement rate limiting or
 * other alternative path.
 * <p>
 *
 * @since 0.10.0
 */
public interface CommandHandlerInterceptor<C, R> {

    default void preHandle(C command) {

    }

    default void postHandle(C command, R returning) {

    }
}
