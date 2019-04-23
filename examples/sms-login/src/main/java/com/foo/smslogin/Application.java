package com.foo.smslogin;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public SmsVerificationCodeSenderStub smsVerificationSenderStub() {
        return new SmsVerificationCodeSenderStub();
    }

    @GetMapping("/me")
    public Object me(Authentication authentication) {
        return authentication.getPrincipal();
    }

}
