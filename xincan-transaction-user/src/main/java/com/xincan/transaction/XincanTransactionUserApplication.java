package com.xincan.transaction;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
public class XincanTransactionUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(XincanTransactionUserApplication.class, args);
    }

}
