package com.xincan.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
//@EnableHystrix
@SpringBootApplication
public class XincanTransactionUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(XincanTransactionUserApplication.class, args);
    }

}
