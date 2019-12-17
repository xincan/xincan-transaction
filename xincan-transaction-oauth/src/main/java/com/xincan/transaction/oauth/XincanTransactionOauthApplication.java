package com.xincan.transaction.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * 集成Oauth2 Server端
 */
@EnableWebMvc
@SpringBootApplication
public class XincanTransactionOauthApplication {

    public static void main(String[] args) {
        SpringApplication.run(XincanTransactionOauthApplication.class, args);
    }

}

