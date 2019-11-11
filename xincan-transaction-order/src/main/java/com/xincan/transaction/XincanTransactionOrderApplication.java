package com.xincan.transaction;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class XincanTransactionOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(XincanTransactionOrderApplication.class, args);
    }

}
