package com.foo.smslogin;

import com.thebund1st.daming.sdk.jwt.SmsVerificationJwtVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private SmsVerificationJwtVerifier smsVerificationJwtVerifier;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .authorizeRequests()
                .antMatchers("/login/sms", "/api/sms/verification/code").permitAll()
                .anyRequest().authenticated()
        .and()
                .addFilterBefore(smsLoginFilter(),
                        UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint(new RestAuthenticationEntryPoint())
        .and()
            .csrf().disable();
        // @formatter:on
    }

    private SmsLoginFilter smsLoginFilter() {
        SmsLoginFilter filter = new SmsLoginFilter(new AntPathRequestMatcher("/login/sms", "POST"));
        filter.setSmsVerificationJwtVerifier(smsVerificationJwtVerifier);
        filter.setUserRepository(userRepository);
        filter.setAuthenticationSuccessHandler((request, response, authentication) -> {
            //do nothing, let the api pass
        });
        return filter;
    }


}
