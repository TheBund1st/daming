package com.thebund1st.daming.web.rest;

import com.thebund1st.daming.security.ratelimiting.TooManyRequestsException;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@ControllerAdvice
public abstract class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {TooManyRequestsException.class})
    protected ResponseEntity<Object> handleTooManyRequests(
            TooManyRequestsException ex, WebRequest request) {
        DefaultErrorAttributes errorAttributes = new DefaultErrorAttributes(false);
        Map<String, Object> errorAttributes1 = errorAttributes.getErrorAttributes(request, false);
        errorAttributes1.put("error", "1001");
        errorAttributes1.put("message", ex.getMessage());
        return handleExceptionInternal(ex, errorAttributes1,
                new HttpHeaders(), HttpStatus.TOO_MANY_REQUESTS, request);
    }


}
