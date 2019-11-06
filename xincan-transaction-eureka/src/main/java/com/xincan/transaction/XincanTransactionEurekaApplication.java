package com.xincan.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class XincanTransactionEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(XincanTransactionEurekaApplication.class, args);
    }

}
