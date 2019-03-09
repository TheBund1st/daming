package com.thebund1st.daming.security.ratelimiting;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;

@ResponseStatus(code = TOO_MANY_REQUESTS)
public class TooManyRequestsException extends RuntimeException {

    public TooManyRequestsException(Errors errors) {
        super(errors.toMessage());
    }
}
