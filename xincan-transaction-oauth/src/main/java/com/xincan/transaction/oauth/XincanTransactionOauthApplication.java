package com.xincan.transaction.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * 集成Oauth2 Server端
 */
@SpringBootApplication
@EnableWebMvc
public class XincanTransactionOauthApplication {

    public static void main(String[] args) {
        SpringApplication.run(XincanTransactionOauthApplication.class, args);
    }

}

