package com.foo.bar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.Filter;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String VERY_IMPORTANT_OPERATION = "/very/important/operation";

    @Value("${daming.jwt.publicKeyFileLocation}")
    private String publicKeyFileLocation;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .authorizeRequests()
                .antMatchers(VERY_IMPORTANT_OPERATION).authenticated()
                .anyRequest().permitAll()
        .and()
            .addFilterBefore(smsVerificationFilter(), UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling().authenticationEntryPoint(new RestAuthenticationEntryPoint())
        .and()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(STATELESS);
        // @formatter:on
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("admin")
                .roles("ADMIN");
    }

    private Filter smsVerificationFilter() throws Exception {
        SmsVerificationFilter filter =
                new SmsVerificationFilter(
                        new AntPathRequestMatcher(VERY_IMPORTANT_OPERATION)
                );
        filter.setSmsVerificationJwtVerifier(smsVerificationJwtVerifier());
        filter.setAuthenticationSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> {
            // does nothing, just let the request pass
        });
        filter.setAuthenticationFailureHandler(new SmsVerificationAuthenticationFailureHandler());
        return filter;
    }

    @Bean
    public SmsVerificationJwtVerifier smsVerificationJwtVerifier() throws Exception {
        return new SmsVerificationJwtVerifier(get(publicKeyFileLocation));
    }

    private PublicKey get(String filename) throws Exception {

        byte[] keyBytes = Files.readAllBytes(Paths.get(filename));
        X509EncodedKeySpec spec =
                new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }
}
