package com.thebund1st.daming


import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

//TODO Can I test without a @SpringBootApplication?
@SpringBootApplication
class TestApplication {

    static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args)
    }
}
