package com.thebund1st.daming.boot;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan("com.thebund1st.daming")
@Configuration
public class SmsVerificationAutoConfiguration {

    public SmsVerificationAutoConfiguration() {
        System.out.println("xxxx");
    }
}