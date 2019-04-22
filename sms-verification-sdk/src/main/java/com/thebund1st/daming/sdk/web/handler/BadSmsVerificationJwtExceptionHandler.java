package com.thebund1st.daming.sdk.web.handler;


import com.thebund1st.daming.sdk.jwt.BadSmsVerificationJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RestControllerAdvice
public class BadSmsVerificationJwtExceptionHandler {

    @ExceptionHandler({
            BadSmsVerificationJwtException.class
    })
    public void handle(BadSmsVerificationJwtException ex,
                       WebRequest request, HttpServletResponse response) throws Exception {
        Principal userPrincipal = request.getUserPrincipal();
        if (userPrincipal == null) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
        } else {
            response.sendError(HttpStatus.FORBIDDEN.value(), ex.getMessage());
        }
    }
}
