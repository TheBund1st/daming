package com.foo.smslogin;

import com.thebund1st.daming.sdk.jwt.SmsVerificationClaims;
import com.thebund1st.daming.sdk.jwt.SmsVerificationJwtVerifier;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SmsLoginFilter extends AbstractAuthenticationProcessingFilter {

    @Setter
    private SmsVerificationJwtVerifier smsVerificationJwtVerifier;
    @Setter
    private UserRepository userRepository;

    public SmsLoginFilter(RequestMatcher requestMatcher) {
        super(requestMatcher);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        String jwt = request.getHeader("X-SMS-VERIFICATION-JWT");
        SmsVerificationClaims claims = smsVerificationJwtVerifier.verify(jwt, "SMS_LOGIN");
        String mobile = claims.getMobile();
        return userRepository.mustFindByMobile(mobile);
    }
}
