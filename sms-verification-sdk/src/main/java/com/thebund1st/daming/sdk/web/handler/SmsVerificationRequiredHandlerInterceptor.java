package com.thebund1st.daming.sdk.web.handler;

import com.thebund1st.daming.sdk.jwt.SmsVerificationClaims;
import com.thebund1st.daming.sdk.jwt.SmsVerificationJwtVerifier;
import com.thebund1st.daming.sdk.web.annotation.SmsVerificationRequired;
import lombok.RequiredArgsConstructor;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class SmsVerificationRequiredHandlerInterceptor extends HandlerInterceptorAdapter {
    private final SmsVerificationJwtVerifier smsVerificationJwtVerifier;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if (handlerMethod.hasMethodAnnotation(SmsVerificationRequired.class)) {
            final String jwt = request.getHeader("X-SMS-VERIFICATION-JWT");
            SmsVerificationClaims claims = smsVerificationJwtVerifier.verify(jwt);
            request.setAttribute("smsVerificationClaims", claims);
            return true;
        } else {
            return true;
        }
    }
}
