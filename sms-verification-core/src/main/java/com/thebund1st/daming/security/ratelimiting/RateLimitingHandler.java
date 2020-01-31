package com.thebund1st.daming.security.ratelimiting;

@Deprecated
public interface RateLimitingHandler<T> {

    void check(T command, Errors errors);

    void count(T command);

}
