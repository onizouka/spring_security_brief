package com.example.spring_security_brief;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringSecurityBriefApplication {

    public static void main(String[] args) {
       
        SpringApplication.run(SpringSecurityBriefApplication.class, args);
    }
}