package com.xincan.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
})
public class XincanTransactionUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(XincanTransactionUserApplication.class, args);
    }

}
